<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>参数管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#refresh").click(function(){
                document.location.reload()
            });
        });

        function showParamWindow(paramId){
            top.$.jBox.open("iframe:${ctx}/system/actionsetting/listParamValue?paramId="+paramId, "集合值列表", 500, 700,{
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                },persistent:true
                ,buttons:{"关闭":true}
            });
        }
    </script>
</head>
<body>
    <ul class="breadcrumb">
        <li>
            <a href="${ctx}/system/actionsetting">功能配置</a><span class="divider">/</span>
        </li>
        <li><a id="refresh" href="#">参数列表</a></li>
    </ul>
    <tags:message message="${message}"/>
    <div class="row-fluid">
        <div class="span6">
            <shiro:hasPermission name="actionsetting_add">
            <div class="span12 breadcrumb">
                <label>${actionName}</label>
                <a class="btn btn-primary pull-right" href="${ctx}/system/actionsetting/addParamForm?actionId=${actionId}" target="paramContent">添加参数</a>
            </div>
            </shiro:hasPermission>
            <table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
                <tr><th>id</th><th>Key</th><th>Label</th><th>Type</th><th>Require</th><th>Version</th><th>特殊代理商</th><th>备注</th><th>操作</th></tr>
                <c:forEach items="${list}" var="parameter">
                    <tr>
                        <td>${parameter.id}</td>
                        <td>${parameter.key}</td>
                        <td>${parameter.label}</td>
                        <td>${fns:getDictLabel(parameter.type, 'action_param_type', "未知")}</td>
                        <td>${parameter.require}</td>
                        <td>${parameter.version}</td>
                        <td>${parameter.hasSite}</td>
                        <td>${parameter.remark}</td>
                        <td>
                            <shiro:hasPermission name="actionsetting_update">
                            <a href="${ctx}/system/actionsetting/updateParamForm?id=${parameter.id}" target="paramContent"><m:message bundle="common" key="common.modify"/></a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="actionsetting_delete">
                            <a href="${ctx}/system/actionsetting/deleteParam?id=${parameter.id}&actionName=${actionName}" onclick="return confirmx('<m:message bundle="common" key="common.deleteall.confirm"/>?', this.href)"><m:message bundle="common" key="common.delete"/></a>
                            </shiro:hasPermission>
                            <c:if test="${parameter.type eq 'Collection'}">
                                <a href="#" onclick="showParamWindow(${parameter.id})">集合值</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div id="right" class="span6">
            <iframe id="paramContent" name="paramContent" style="overflow: visible" scrolling="yes" frameborder="0" width="100%">
            </iframe>
        </div>
    </div>

    <script type="text/javascript">
        function wSize(){
            var sizes = ["Height", "Width"].map(function(name){
                return window["inner" + name] ||
                        document.compatMode === "CSS1Compat" && document.documentElement[ "client" + name ] || document.body[ "client" + name ];
            });
            var strs = sizes.toString().split(",");
            $("#right, #paramContent").height(strs[0]-5);
        }

        $(window).resize(function(){
            wSize();
        })
        wSize();
    </script>
</body>
</html>
