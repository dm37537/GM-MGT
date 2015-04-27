<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <%--国际化资源 管理--%>
    <title><m:message bundle="system" key="system.resourcemessage"/></title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/dialog.jsp"%>
    <script type="text/javascript">
        function page(index, size){
            $("#pageNo").val(index);
            $("#pageSize").val(size);
            $("#searchForm").attr("action","${ctx}/userMgr/list");
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<form:form id="searchForm" modelAttribute="user" action="${ctx}/userMgr/list" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}">
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}">
    <input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}">
    <div>
        <label>用户名:</label><form:input path="userName" htmlEscape="false" class="input-small"/>
        <label>姓名:</label><form:input path="name" htmlEscape="false" class="input-small"/>
        <label>邮箱:</label><form:input path="email" htmlEscape="false" class="input-small"/>
        &nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="<m:message bundle="common" key="common.query"/>" onclick="return page();"/>
        <span class="pull-right">
             <a class="btn btn-primary" href="${ctx}/userMgr/add">添加用户</a>
        </span>
    </div>
</form:form>
<tags:message message="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover  table-hover">
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
                <a href="${ctx}/userMgr/edit?userId=${user.id}">修改</a>
                <c:if test="${user.status==0}"> <a href="${ctx}/userMgr/updateStatus?status=1&userId=${user.id}" onclick="return confirmx('是否启用 【${user.name}】？', this.href)"><font color="red">启用</font></a></c:if>
                <c:if test="${user.status==1}"> <a href="${ctx}/userMgr/updateStatus?status=0&userId=${user.id}" onclick="return confirmx('是否禁用 【${user.name}】？', this.href)">禁用</a></c:if>
                <a href="${ctx}/userMgr/resetPsw?userId=${user.id}" onclick="return confirmx('是否重置 【${user.name}】密码为(123@mokylin)？', this.href)">重置密码</a>
                <a href="${ctx}/userMgr/assignUserPms?userId=${user.id}">私有权限</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
