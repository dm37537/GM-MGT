<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>角色管理</title>
    <meta name="decorator" content="default"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%@include file="/WEB-INF/views/include/dialog.jsp"%>
    <script type="text/javascript">
        $(document).ready(function() {

         });
        function page(index, size){
            $("#pageNo").val(index);
            $("#pageSize").val(size);
            $("#searchForm").attr("action","${ctx}/userMgr/listBySysId?sysId=${sys.id}");
            $("#searchForm").submit();
            return false;
        }
        var userLoadUrl="";
        function  openUsers(which){
            userLoadUrl= $(which).attr("href-rel");
            $("#content").load(userLoadUrl);
        }
        function  addRole(which){
            var roleName=$("#roleName").val().replace(/(^\s*)|(\s*$)/g, "");
            if(roleName!=''){
                $.post("${ctx}/roleMgr/addRole",
                        {
                            roleName:escape(roleName),
                            sysId:${sys.id}
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
                alert("请填写正确的角色名字");
            }

        }
        function  delRole(which) {
            $.jBox.confirm("是否要删除此角色？", "提示", function (v, h, f) {
                if (v == 'ok') {
                    $.post("${ctx}/roleMgr/delRole",
                            {
                                roleId: $(which).attr("roleId")
                            },
                            function (data, status) {
                                if (data == true) {
                                    window.location.reload();
                                    $.jBox.tip("删除成功", 'info');
                                } else {
                                    alert('删除失败' + data);
                                }
                            });
                }
            });
        }
        var  assignPmsItem4RoleLoadUrl="";
        function  assignPmsItem4Role(which){
            assignPmsItem4RoleLoadUrl= $(which).attr("href-rel");
            $("#content").load(assignPmsItem4RoleLoadUrl);
        }
    </script>
</head>
<body>
<div class="row-fluid">
    <div class="span12">
    <ul class="breadcrumb">
        <li><a href="${ctx}/sysMgr/list">系统列表</a> <span class="divider">/</span></li>
        <li class="active">[<font color="red"> ${sys.system_name}</font>]角色管理</li>
    </ul>
    </div>
</div>
<tags:message message="${message}"/>
<div class="row-fluid">
    <div class="span4">
        <div >
            <div class="span12  input-append breadcrumb">
                <input class="span8" id="roleName" type="text" placeholder="角色名字">
                <button class="btn" type="button" onclick="addRole(this)" >添加[<font color="red">${sys.system_name}</font>]角色</button>
            </div>
        </div>
        <table id="contentTable" class="table table-striped table-bordered table-condensed  table-hover">
        <thead><tr><th>ID</th><th>角色名</th><th>操作</th></tr></thead>
        <tbody>
        <c:forEach items="${list}" var="role">
            <tr>
                <td>${role.id}</td>
                <td>${role.name}</td>
                <c:if test="${role.del_flag==0}">
                    <td>
                        <a href="${ctx}/roleMgr/edit?roleId=${role.id}">修改</a>
                        <a href="${ctx}/userMgr/findUserByRoleId?roleId=${role.id}" target="content" >成员管理</a>
                        <a href="${ctx}/roleMgr/assignPmsItem4Role?roleId=${role.id}&&sysId=${sys.id}" target="content" >权限分配</a>
                        <a href="#" roleId="${role.id}" onclick="delRole(this)">删除</a>
                    </td>
                </c:if>
                <c:if test="${role.del_flag==1}">
                    <td>
                       已删除
                    </td>
                </c:if>


            </tr>
        </c:forEach>
        </tbody>
        </table>
        </div>
    <div id="right" class="span8" style="height: 100%">
        <iframe   name="content" style="overflow:visible;"
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
