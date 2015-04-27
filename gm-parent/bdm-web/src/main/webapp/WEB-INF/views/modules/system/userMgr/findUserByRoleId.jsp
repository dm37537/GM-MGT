<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
 <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/dialog.jsp"%>
<script type="text/javascript">
    function page(index, size){
        $("#pageNo").val(index);
        $("#pageSize").val(size);
        $("#searchForm").attr("action","${ctx}/userMgr/findUserByRoleId?roleId=${role.id}");
        $("#searchForm").submit();
        return false;
    }
    function  addUsers(which){
        $.post("${ctx}/roleMgr/addUsers",
                {
                    addUserNames:$("#addUserNames").val(),
                    roleId:$("#roleId").val()
                },
                function(data,status){
                    if(data==true){
                        window.location.reload();
                    }else{
                        alert('添加失败');
                    }
                });
    }
    function  delUsers(which){
        $.jBox.confirm("是否要删除此用户？","提示",function (v, h, f) {
            if (v == 'ok') {
                $.post("${ctx}/roleMgr/delUser",
                        {
                            userId:$(which).attr("userId"),
                            roleId:$(which).attr("roleId")
                        },
                        function(data,status){
                            if(data==true){
                                window.location.reload();
                            }else{
                                alert('删除失败'+data);
                            }
                        });
            }
        });
    }
</script>
</head>
<body>
<form:form id="searchForm" modelAttribute="user" action="${ctx}/userMgr/findUserByRoleId?roleId=${role.id}" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}">
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}">
    <input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}">
    <label>用户名:</label><form:input path="userName" htmlEscape="false" class="input-small"/>
    <label>姓名:</label><form:input path="name" htmlEscape="false" class="input-small"/>
    <label>邮箱:</label><form:input path="email" htmlEscape="false" class="input-small"/>
        &nbsp;&nbsp;<input id="btnSubmit" class="btn" type="submit" value="<m:message bundle="common" key="common.query"/>" onclick="return page();"/>
    </div>
</form:form>
<div class="row-fluid">
    <div class="span12 breadcrumb input-append">
        <input type="hidden" id="roleId" value="${role.id}"/>
        <input class="span8" id="addUserNames" type="text" placeholder="添加多个成员 例如：zhangsan,lisi,wangwu">
        <button class="btn" type="button" onclick="addUsers(this)" >添加[<font color="red">${role.name}</font>]成员</button>
    </div>
 </div>


<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
    <thead><tr><th>ID</th><th>用户名</th><th>姓名</th><th>邮箱</th><th>手机</th><th>操作</th></tr></thead>
    <tbody>
    <c:forEach items="${page.list}" var="user">
        <tr>
            <td>${user.id}</td>
            <td>${user.userName}</td>
            <td>${user.name}</td>
            <td>${user.email}</td>
            <td>${user.mobile}</td>
            <td>
                <a href="#" userId="${user.id}" roleId="${role.id}" onclick="delUsers(this)">删除</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>

</body>
</html>
