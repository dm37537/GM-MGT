<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title><m:message bundle="common" key="common.menu.logview"/></title>
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

<form:form id="searchForm" action="${ctx}/system/log" method="post" modelAttribute="logCondition" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <div>
        <label>用户名：</label><form:input path="userName" htmlEscape="false" class="input-small" maxlength="50"/>
        &nbsp;
        <label>开始日期：</label><input name="beginTime" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
                                   value="${logCondition.beginTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        <label>结束日期：</label><input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
                                   value="${logCondition.endTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        <label for="exception"><input id="exception" name="exception" type="checkbox"${exception eq "Exception"?' checked':''} value="Exception"/>异常信息</label>
        &nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
    </div>
</form:form>
<tags:message message="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
    <thead><tr><th>类型</th><th>操作用户</th><th>操作时间</th><th>URI</th><th>提交方式</th><th>操作者IP</th><th>请求参数</th><th>异常信息</th></thead>
    <tbody>
    <%request.setAttribute("strEnter", "\n"); %>
    <c:forEach items="${page.list}" var="log">
        <tr>
            <td>${log.type}</td>
            <td>${log.createBy}</td>
            <td><fmt:formatDate value="${log.createDate}" type="both"/></td>
            <td><strong>${log.requestUri}</strong></td>
            <td>${log.method}</td>
            <td>${log.remoteAddr}</td>
            <td>${log.params}</td>
            <td>
                <%--<c:if test="${not empty log.exception}">${fn:replace(fns:escapeHtml(log.exception), strEnter, '<br/>')}</c:if>--%>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
