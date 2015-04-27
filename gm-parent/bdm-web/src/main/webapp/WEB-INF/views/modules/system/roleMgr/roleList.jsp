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
            $("#searchForm").attr("action","${ctx}/roleMgr/list");
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<form:form id="searchForm" modelAttribute="role" action="${ctx}/roleMgr/list" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}">
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}">
    <input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}">
    <div>

    </div>
</form:form>
<tags:message message="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed  table-hover">
    <thead><tr><th>角色名</th><th>角色名</th><th>操作</th></tr></thead>
    <tbody>
    <c:forEach items="${page.list}" var="role">
        <tr>
            <td>${role.name}</td>
            <td>
                <a href="${ctx}/system/resource/form?resource=${resource}">修改</a>
                <a href="${ctx}/system/resource/form?resource=${resource}">成员管理</a>
                <a href="${ctx}/system/resource/delete?l" onclick="return confirmx('<m:message bundle="common" key="common.delete.confirm"/>？', this.href)">删除</a>
            </td> <%--</shiro:hasPermission>--%>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
