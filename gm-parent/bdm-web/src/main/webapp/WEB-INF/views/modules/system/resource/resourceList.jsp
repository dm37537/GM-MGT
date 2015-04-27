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
            $("#searchForm").attr("action","${ctx}/system/resource");
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
    <form:form id="searchForm" modelAttribute="resourceMessage" action="${ctx}/system/resource" method="post" class="breadcrumb form-search">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}">
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}">
        <input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}">
        <div>
            <%-- 语言选择 --%>
            <label><m:message bundle="common" key="common.language"/>:</label>
            <form:select path="locale" onchange="return page();">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('language')}" itemLabel="label" itemValue="value" htmlEscape="false" />
            </form:select>
            <label>bundle:</label><form:input path="bundle" htmlEscape="false" class="input-small"/>
            <label>key:</label><form:input path="key" htmlEscape="false" class="input-small"/>
            <label>value:</label><form:input path="value" htmlEscape="false" class="input-small"/>
            &nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="<m:message bundle="common" key="common.query"/>" onclick="return page();"/>
        </div>
    </form:form>
    <tags:message message="${message}"/>

    <table id="contentTable" class="table table-striped table-bordered table-condensed  table-hover">
        <thead><tr><th>locale</th><th>bundle</th><th>key</th><th>value</th><th>操作</th></tr></thead>
        <tbody>
        <c:forEach items="${page.list}" var="resource">
            <tr>
                <td>${resource.locale}</td>
                <td>${resource.bundle}</td>
                <td>${resource.key}</td>
                <td>${resource.value}</td>
                <%--<shiro:hasPermission name="sys:user:edit">--%>
                <td>
                    <a href="${ctx}/system/resource/form?resource=${resource}"><m:message bundle="common" key="common.modify"/></a>
                    <a href="${ctx}/system/resource/delete?locale=${resource.locale}&key=${resource.key}&bundle=${resource.bundle}" onclick="return confirmx('<m:message bundle="common" key="common.delete.confirm"/>？', this.href)"><m:message bundle="common" key="common.delete"/></a>
                <%--</td></shiro:hasPermission>--%>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagination">${page}</div>
</body>
</html>
