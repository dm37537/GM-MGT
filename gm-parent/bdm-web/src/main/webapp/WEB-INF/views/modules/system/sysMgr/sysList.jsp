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
            $("#searchForm").attr("action","${ctx}/sysMgr/list");
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<form:form id="searchForm" modelAttribute="sys" action="${ctx}/sysMgr/list" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}">
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}">
    <input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}">
    <div>
       <label>系统ID:</label><input name="id" htmlEscape="false" class="input-middle"/>
       <label>系统名称:</label><form:input path="system_name" htmlEscape="false" class="input-middle"/>
        &nbsp;&nbsp;<input id="btnSubmit" class="btn" type="submit" value="<m:message bundle="common" key="common.query"/>" onclick="return page();"/>
            <span class="pull-right">
                 <shiro:hasPermission name="common.menu.sysMgr.oper"> <a class="btn btn-primary" href="${ctx}/sysMgr/add">添加系统</a></shiro:hasPermission>
            </span>
    </div>
</form:form>
<tags:message message="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
    <thead><tr><th>系统ID</th><th>系统名字</th><th>序号</th><th>创建时间</th><th>操作</th></tr></thead>
    <tbody>
    <c:forEach items="${page.list}" var="sys">
        <tr>
            <td>${sys.id}</td>
            <td>${sys.system_name}</td>
            <td>${sys.system_sequence}</td>
            <td>${sys.system_created_date}</td>
            <td>
                <shiro:hasPermission name="common.menu.sysMgr.oper"> <a href="${ctx}/sysMgr/edit?id=${sys.id}"><m:message bundle="common" key="common.modify"/></a>
                <a href="${ctx}/sysMgr/delete?id=${sys.id}" onclick="return confirmx('你确定要删除【${sys.system_name}】吗？', this.href)"><m:message bundle="common" key="common.delete"/></a>
                <a href="${ctx}/pmsItemMgr/listBySysId?sysId=${sys.id}">权限管理</a>
                <a href="${ctx}/roleMgr/listBySysId?sysId=${sys.id}">角色管理</a></shiro:hasPermission>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
