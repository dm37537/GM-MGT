<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title><m:message bundle="common" key="common.privateinfo"/></title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#inputForm").validate({
                submitHandler: function(form){
                    loading('<m:message bundle="common" key="common.commiting"/>...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function(error, element) {
                    $("#messageBox").text("<m:message bundle="common" key="common.inputerror.pleaseconfirm"/>");
                    if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });

    </script>
</head>
<body>
<ul class="breadcrumb">
    <li><a href="${ctx}/sysMgr/list">系统列表</a> <span class="divider">/</span></li>
    <li><a href="${ctx}/pmsItemMgr/listBySysId?sysId=${sys.id}"> [${sys.system_name}]权限列表</a> <span class="divider">/</span></li>
    <c:if test="${item.pms_item_parent_id!=0}"> <li class="active">添加[<font color="red">${parent_item.pms_item_title}</font> ]的子权限</li></c:if>
    <c:if test="${item.pms_item_parent_id==0}"> <li class="active">添加分类</li></c:if>
</ul>
<form:form id="inputForm" modelAttribute="item" action="${ctx}/pmsItemMgr/save" method="post" class="form-horizontal">
    <form:input path="id" htmlEscape="false"  type="hidden"/>
    <form:input path="sys_id" htmlEscape="false"  type="hidden"/>
    <form:input path="pms_item_parent_id" htmlEscape="false"  type="hidden"/>
    <tags:message message="${message}"/>
    <div class="control-group">
        <label class="control-label">名字:</label>
        <div class="controls">
            <form:input path="pms_item_title" htmlEscape="false" required="true"/>
        </div>
    </div>

    <c:if test="${item.pms_item_parent_id!=0}">
        <div class="control-group">
            <label class="control-label">标识:</label>
            <div class="controls">
                <form:input path="pms_item_name" htmlEscape="false" required="true"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">数据源:</label>
            <div class="controls">
                <form:input path="pms_item_source" htmlEscape="false"/> [tips:游戏区范围（pmsItemMgr/gameTreeList4Pms） ]
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">状态:</label>
            <div class="controls">
                <select name="pms_item_state">
                    <option value="0">正常</option>
                    <option value="-1">禁用</option>
                </select><br />
            </div>
        </div>
    </c:if>
    <div class="control-group">
        <label class="control-label">描述:</label>
        <div class="controls">
            <form:input path="pms_item_comment" htmlEscape="false"/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">序号:</label>
        <div class="controls">
            <form:input path="pms_item_sequence" htmlEscape="false" required="true"/>
        </div>
    </div>


    <div class="modal-footer">
        <button class="btn" type="button" onclick="history.go(-1);">返回</button>
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="<m:message bundle="common" key="common.save"/>"/>
    </div>
</form:form>
</body>
</html>