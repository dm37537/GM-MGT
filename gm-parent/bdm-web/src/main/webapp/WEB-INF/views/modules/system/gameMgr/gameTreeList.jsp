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
            },callback: {
                onClick: onClick
            },view: {
                nameIsHTML: true,
                showIcon: false
            }
        };

        var zNodes=[
                <c:forEach items="${gameTreeList}"  var="game">{id:'${game.id}',pId:'${game.parent_id}', name:"${game.name} <c:if test="${game.level==0}"> id:${game.id}</c:if> ",level:"${game.level}",nocheck:"true",open:"true",level:"${game.level}",icon:"${ctxStatic}/mokylin/img/game_level_${game.level}_16.png"},
            </c:forEach>];

        var code;

        function setCheck() {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
            type = { "Y" : "ps", "N" : "ps" };
            zTree.setting.check.chkboxType = type;
        }
        function removeNoSon(){
            var treeObj=$.fn.zTree.getZTreeObj("treeDemo");
            for(var i=0;i<zNodes.length;i++){
                var obj= treeObj.getNodeByParam("id",zNodes[i].id, null);
                if(obj==null||obj.children==null||obj.children.length==null||obj.children.length==0){
                    if(obj.level==1){
                        treeObj.hideNode(obj);
                    }
                }
            }
        }
        $(document).ready(function () {
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            setCheck();
            removeNoSon();
        });

        function onClick(event, treeId, treeNode, clickFlag) {
            if(treeNode.level==0){
                $("#content").attr("src","${ctx}/gameMgr/list?parent_id="+treeNode.id);
            }else if(treeNode.level==1){
                $("#content").attr("src","${ctx}/gameMgr/list?parent_id="+treeNode.id);
            }else if(treeNode.level==2){
                $("#content").attr("src","${ctx}/gameMgr/list?parent_id="+treeNode.id);
            }

        }
        function setFontCss(treeId, treeNode) {
            return treeNode.level == 0 ? {color:"red"} : {};
        };

        function saveGame(which){
            var gameName=$("#gameName").val().replace(/(^\s*)|(\s*$)/g, "");
            if(gameName!=""){
                $.post("${ctx}/gameMgr/save",
                        {
                            name:escape(gameName),
                            level:0,
                            parent_id:0,
                            key:""
                        },
                        function(data,status){
                            if(data==true){
                                window.location.reload();
                                $.jBox.tip("添加成功", 'info');
                            }else{
                                loading('添加失败');
                            }
                        });
            }else{
                alert("请填写正确的游戏名字");
            }

        }
        //-->
    </SCRIPT>
</head>
<body>
<div class="row-fluid">
    <div class="span2">
        <div class="accordion-group">
            <div class="accordion-heading">
                <a class="accordion-toggle">游戏树</a>
                <span class="pull-right accordion-toggle">
<shiro:hasPermission name="common.menu.gamemanager.add"><a href="#myModal"  class="btn"   data-toggle="modal"><img width="18px" height="18px"  src="${ctxStatic}/mokylin/img/add_32.png" title="添加游戏"/> </a></shiro:hasPermission>
                </span>
            </div>
            <div class="accordion-body">
                <div class="accordion-inner">
                    <div id="treeDemo" class="ztree"></div>
                </div>
            </div>
        </div>
    </div>
<div id="right" class="span8">
    <iframe id="content"  name="content" style="overflow:visible;"
              scrolling="yes" frameborder="no" width="100%" height="100%"></iframe>
</div>
</div>

<!-- Modal -->
<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="myModalLabel"><img  src="${ctxStatic}/mokylin/img/gameIcon_32.png"/>添加游戏</h3>
    </div>
    <div class="modal-body">
        <p><input type="text" id="gameName"  placeholder="输入游戏名字"></p>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
        <button class="btn btn-primary" onclick="saveGame(this)">保存</button>
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
