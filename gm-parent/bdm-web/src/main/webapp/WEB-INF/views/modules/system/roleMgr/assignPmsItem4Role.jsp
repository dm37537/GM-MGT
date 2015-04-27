<%@page contentType="text/html;charset=UTF-8" language="java" %>
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
            },callback: {
                onClick: onClick
            },view: {
                showIcon: showIconForTree
            }
        };
        function showIconForTree(treeId, treeNode) {
            return treeNode.pms_item_source != "";
        };
        var zNodes=[
            <c:forEach items="${sortedList}" var="item">{id:'${item.id}',configValue:'${item.configValue}',pms_item_source:'${item.pms_item_source}',icon:'${ctxStatic}/mokylin/img/tree_16.png', pId:'${item.pms_item_parent_id}', name:"${item.pms_item_title}", pms_item_comment:"${item.pms_item_comment}",nocheck:"${item.pms_item_parent_id==0||item.pms_item_source!=""}",open:"${item.pms_item_parent_id==0}",checked: "${item.checked}"},
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
        function save(which){
            var pmsItemIds="";
            var treeObj=$.fn.zTree.getZTreeObj("treeDemo");
            var nodes=treeObj.getCheckedNodes(true);
            for(var i=0;i<nodes.length;i++){
                pmsItemIds+=nodes[i].id+","
            }
            $.post("${ctx}/pmsItemMgr/addConfigs",
                    {
                        pmsItemIds:pmsItemIds,
                        role_id:${role.id}
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
        function onClick(event, treeId, treeNode, clickFlag) {
            if(treeNode.pms_item_source!=''){
                if(treeNode.pms_item_source.indexOf("?") != -1){
                    $("#content").attr("src","${ctx}/"+treeNode.pms_item_source+"&role_id=${role.id}&config_item_id="+treeNode.id);
                }else{
                    $("#content").attr("src","${ctx}/"+treeNode.pms_item_source+"?role_id=${role.id}&config_item_id="+treeNode.id);
                }

            }

        }

        //-->
    </SCRIPT>
</head>
<body>
<h1>[<font color="red">${role.name}</font>]权限操作</h1>
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
    <div id="right" class="span6" style="height:100%">
        <iframe id="content"  name="content" style="overflow:visible;"
                scrolling="yes" frameborder="no" width="100%" height="100%"></iframe>
    </div>
</div>
<script type="text/javascript">
    function wSize(){
        var sizes = ["Height", "Width"].map(function(name){
            return window["inner" + name] ||
                    document.compatMode === "CSS1Compat" && document.documentElement[ "client" + name ] || document.body[ "client" + name ];
        });
        var strs = sizes.toString().split(",");
        $("#right, #content").height(strs[0]-5);
    }

    $(window).resize(function(){
        wSize();
    });
    wSize();
</script>
</body>
</html>
