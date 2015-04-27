<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title></title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/dialog.jsp"%>
    <script type="text/javascript">
        function page(index, size){
            $("#pageNo").val(index);
            $("#pageSize").val(size);
            // $("#searchForm").attr("action","${ctx}/gameMgr/list?parent_id=${parentGame.id}");
            $("#searchForm").submit();
            return false;
        }
        function openDialog(which){
            $.jBox("iframe:"+$(which).attr("data-remote")+"", {
                title:'<img title="参数设置" src="${ctxStatic}/mokylin/img/setIcon_32.png"/>'+$(which).attr("data-title"),
                width: 800,
                height: 600,
                buttons: { '关闭': true },persistent: true, showSpeed: 'slow',iframeScrolling: 'no'
            });
        }
        function saveGame(which){
            var gameName=$("#gameName").val().replace(/(^\s*)|(\s*$)/g, "");
            if(gameName!=""){
                var timestamp = Date.parse(new Date());
                $.post("${ctx}/gameMgr/save",
                        {
                            name:escape(gameName),
                            level:${parentGame.level+1},
                            parent_id:${parentGame.id},
                            datetime:timestamp,
                            key:$("#key").val()
                        },
                        function(data,status){
                            if(data==true){
                                if(${parentGame.level<=1}){
                                    window.parent.window.location.reload();
                                }
                                window.location.reload();
                                $.jBox.tip("添加成功", 'info');
                            }else{
                                loading('添加失败');
                            }
                        });
            }else{
                alert("请填写正确的名字");
            }

        }
        function go2Edit(which){
            var gameStatus=$(which).attr("gameStatus");
            $("input[name='gameStatus'][value="+gameStatus+"]").attr("checked",true);
            $("#gameIdEdit").val($(which).attr("gameId"));
            $("#gameNameEdit").val($(which).attr("gameName"));
            $("#keyEdit").val($(which).attr("gameKey"));
            $('#updateGameModal').modal({
                keyboard: true
            });
        }
        function updateGame(which){
            var gameName=$("#gameNameEdit").val().replace(/(^\s*)|(\s*$)/g, "");
            if(gameName!=""){
                var timestamp = Date.parse(new Date());
                $.post("${ctx}/gameMgr/update",
                        {
                            name:escape(gameName),
                            status:$("input[name='gameStatus']:checked").val(),
                            game_id:$("#gameIdEdit").val(),
                            datetime:timestamp,
                            key:$("#keyEdit").val()
                        },
                        function(data,status){
                            if(data==true){
                                if(${parentGame.level<=1}){
                                    window.parent.window.location.reload();
                                }
                                window.location.reload();
                                $.jBox.tip("修改成功", 'info');
                            }else{
                                loading('修改失败');
                            }
                        });
            }else{
                alert("请填写正确的名字");
            }

        }
    </script>
</head>
<body>
<form:form id="searchForm" modelAttribute="game" action="${ctx}/gameMgr/list?parent_id=${parentGame.id}" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}">
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}">
    <input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}">

    <div>
        <label>ID:</label><input name="id" htmlEscape="false" class="input-middle"/>
        <label>名字:</label><input name="name" htmlEscape="false" class="input-middle"/>
        &nbsp;&nbsp;<input id="btnSubmit" class="btn"  type="submit" value="<m:message bundle="common" key="common.query"/>" onclick="return page();"/>
         <span class="pull-right">
            <c:if test="${parentGame.level==0}"> <a href="#addGameModal" style="background:url('${ctxStatic}/mokylin/img/game_level_${parentGame.level+1}_32.png') no-repeat 0% 50%;" role="button" class="btn" data-toggle="modal">&nbsp;&nbsp;&nbsp;添加[<font color="red">${parentGame.name}</font>]的分类 </a></c:if>
            <c:if test="${parentGame.level==1}"> <a href="#addGameModal" style="background:url('${ctxStatic}/mokylin/img/game_level_${parentGame.level+1}_32.png') no-repeat 0% 50%;"   role="button" class="btn" data-toggle="modal">&nbsp;&nbsp;&nbsp;添加[<font color="red">${parentGame.name}</font>]的代理商 </a></c:if>
            <c:if test="${parentGame.level==2}"> <a href="#addGameModal" style="background:url('${ctxStatic}/mokylin/img/game_level_${parentGame.level+1}_32.png') no-repeat 0% 50%;"   role="button" class="btn" data-toggle="modal">&nbsp;&nbsp;&nbsp;添加[<font color="red">${parentGame.name}</font>]的区服 </a></c:if>
         </span>
    </div>

</form:form>
<tags:message message="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
    <thead><tr><th>ID</th><th>名字</th><c:if test="${parentGame.level==2}"><th>状态</th></c:if><th>创建时间</th><th>操作</th></tr></thead>
    <tbody>
    <c:forEach items="${page.list}" var="game">
        <tr>
            <td>${game.id}</td>
            <td>${game.name}</td>
            <c:if test="${game.level==3}"><td>${fns:getDictLabel(game.status, 'gamestatus', '未知状态')}</td></c:if>
            <td>${game.create_time}</td>
            <td>
                <a href="#"    onclick="go2Edit(this)" gameId="${game.id}" gameName="${game.name}" gameStatus="${game.status}" gameKey="${game.key}" title="修改"><img src="${ctxStatic}/mokylin/img/edit_24.png"/></a>
                &nbsp;&nbsp; &nbsp;&nbsp;<c:if test="${game.level==3||game.level==2}">
                <a  href="#" data-title="[${game.name}]参数设置" data-remote="${ctx}/gameMgr/gameParamMgr/list?gameId=${game.id}"  onclick="openDialog(this)"><img height="24px" width="24px" title="参数设置" src="${ctxStatic}/mokylin/img/setIcon_32.png"/></a>
             </c:if>


            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
<!-- Modal -->
<div id="addGameModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="addGameModalLabel" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="addGameModalLabel">添加</h3>
    </div>
    <div class="modal-body">
        <p>
        <form class="form-horizontal">
            <c:if test="${parentGame.level==0}">
                <div class="control-group">
                    <label class="control-label">分类名称：</label>
                    <div class="controls">
                        <input type="text" id="gameName"  placeholder="输入[${parentGame.name}]的分类名字"> <input type="hidden" id="key"  placeholder="输入的游戏区唯一标识">
                    </div>
                </div>
            </c:if>
            <c:if test="${parentGame.level==1}">
                <div class="control-group">
                    <label class="control-label">代理名称：</label>
                    <div class="controls">
                        <input type="text" id="gameName"  placeholder="输入[${parentGame.name}]的代理商名字"><input type="hidden" id="key"  placeholder="输入的游戏区唯一标识">
                    </div>
                </div>

            </c:if>
            <c:if test="${parentGame.level==2}">
                <div class="control-group">
                    <label class="control-label">游戏区名：</label>
                    <div class="controls">
                        <input type="text" id="gameName"  placeholder="输入[${parentGame.name}]的游戏区名字">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">唯一标识：</label>
                    <div class="controls">
                        <input type="text" id="key"  placeholder="输入的游戏区唯一标识">
                    </div>
                </div>

            </c:if>
        </form>
       </p>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
        <button class="btn btn-primary" onclick="saveGame(this)">保存</button>
    </div>
</div>
<!-- Modal -->
<div id="updateGameModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="updateGameModalLabel" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="updateGameModalLabel"><img src="${ctxStatic}/mokylin/img/editIcon_32.png"/>编辑</h3>
    </div>
    <div class="modal-body">
        <p>
        <form class="form-horizontal">
            <input type="hidden" id="gameIdEdit">
            <div class="control-group">
                <label class="control-label"> 名字：</label>
                <div class="controls">
                    <input type="text" id="gameNameEdit"/>
                </div>
            </div>
            <c:if test="${parentGame.level==2}">
                <div class="control-group">
                    <label class="control-label" >唯一标识：</label>
                    <div class="controls">
                        <input type="text" id="keyEdit"  placeholder="输入的游戏区唯一标识">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">状态：</label>
                    <div class="controls">
                        <c:forEach items="${fns:getDictList('gamestatus')}" var="dic">
                            <label class="radio">
                                <input type="radio" name="gameStatus"  value="${dic.value}" >${dic.label}&nbsp;&nbsp;
                            </label>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
        </form>
        </p>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
        <button class="btn btn-primary" onclick="updateGame(this)">保存</button>
    </div>
</div>
</body>
</html>
