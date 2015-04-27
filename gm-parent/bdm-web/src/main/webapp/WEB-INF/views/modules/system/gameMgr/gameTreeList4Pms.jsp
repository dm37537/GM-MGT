<%@page  contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
    <title></title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/treeview.jsp"%>
    <%@include file="/WEB-INF/views/include/dialog.jsp"%>
    <SCRIPT type="text/javascript">
        <!--
        var setting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            },view: {
                nameIsHTML: true,
                showIcon: false
            }
        };

        var zNodes=[
                <c:forEach items="${gameTreeList}"  var="game">{id:'${game.id}',pId:'${game.parent_id}',key:'${game.id}_${game.super_id}', name:"${game.name}",nocheck:"${game.level!=2}",open:"true",level:"${game.level}",checked: "${game.checked}"},
            </c:forEach>];

        var code;

        function setCheck() {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
            type = { "Y" : "ps", "N" : "ps" };
            zTree.setting.check.chkboxType = type;
        }

        $(document).ready(function () {
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            setCheck();
        });

        function setFontCss(treeId, treeNode) {
            return treeNode.level == 0 ? {color:"red"} : {};
        };
        function save(which){
            var config_item_value="";
            var treeObj=$.fn.zTree.getZTreeObj("treeDemo");
            var nodes=treeObj.getCheckedNodes(true);
            for(var i=0;i<nodes.length;i++){
                config_item_value+=nodes[i].key+","
            }
            $.post("${ctx}/pmsItemMgr/addConfigVal",
                    {
                        config_item_id:${pmsItem.id},
                        <c:if test="${role!=null}">role_id:${role.id},</c:if>
                        <c:if test="${user!=null}">user_id:${user.id},sys_id:${sys.id},</c:if>
                        config_item_value:config_item_value,
                        type:2
                    },
                    function(data,status){
                        if(data==true){
                            $.jBox.tip("保存成功", 'info');
                            window.location.reload();

                        }else{
                            alert('添加失败');
                        }
                    });
        }
        //-->
    </SCRIPT>
</head>
<body><h1>[<font color="red">${pmsItem.pms_item_title}</font>]授权</h1>
<tags:message message="${message}"/>
<div class="row-fluid">
    <div class="span6">
        <div class="content_wrap breadcrumb">
            <span id="tips" style="color:#ff0000"></span><button id="saveBtn" onclick="save(this)" style="float:right">保存</button>
            <div class="zTreeDemoBackground left">
                <ul id="treeDemo" class="ztree"></ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>
