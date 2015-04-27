<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>字典管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<form:form id="searchForm" modelAttribute="dict" action="${ctx}/system/dict/" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <label>类型：</label><form:select id="type" path="type" class="input-large"><form:option value="" label=""/><form:options items="${typeList}" htmlEscape="false"/></form:select>
    &nbsp;&nbsp;<label>描述 ：</label><form:input path="description" htmlEscape="false" maxlength="50" class="input-small"/>
    &nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
    <shiro:hasPermission name="dictmanage_add">
    &nbsp;<a href="${ctx}/system/dict/form" class="btn btn-primary"><m:message bundle="common" key="common.add"/></a>
    </shiro:hasPermission>
</form:form>
<tags:message message="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><tr><th>键值</th><th>标签</th><th>类型</th><th>描述</th><th>排序</th><th>操作</th></tr></thead>
    <tbody>
    <c:forEach items="${page.list}" var="dict">
        <tr>
            <td>${dict.value}</td>
            <td><a href="${ctx}/sys/dict/form?id=${dict.id}">${dict.label}</a></td>
            <td><a href="javascript:" onclick="$('#type').val('${dict.type}');$('#searchForm').submit();return false;">${dict.type}</a></td>
            <td>${dict.description}</td>
            <td>${dict.sort}</td>
            <td>
                <shiro:hasPermission name="dictmanage_update">
                <a href="${ctx}/system/dict/form?id=${dict.id}">修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="dictmanage_delete">
                <a href="${ctx}/system/dict/delete?id=${dict.id}" onclick="return confirmx('确认要删除该字典吗？', this.href)">删除</a>
                </shiro:hasPermission>
                <a href="<c:url value='${fns:getAdminPath()}/system/dict/form?type=${dict.type}&sort=${dict.sort+10}'><c:param name='description' value='${dict.description}'/></c:url>">添加键值</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>