<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>已转发工单列表</title>
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
<form:form id="searchForm" action="${ctx}/customersystem/forwardorder" method="post" modelAttribute="queryCondition" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <div>
        <label><m:message bundle="common" key="common.begintime"/>：</label><input name="beginTime" type="text" readonly="readonly" maxlength="20" class="Wdate" style="width: 160px"
                                                                                  value="${queryCondition.beginTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
        <label><m:message bundle="common" key="common.endtime"/>：</label><input name="endTime" type="text" readonly="readonly" maxlength="20" class="Wdate" style="width: 160px"
                                                                                value="${queryCondition.endTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
        &nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
    </div>
</form:form>
<tags:message message="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
    <thead><tr>
        <th><m:message bundle="customersystem" key="customersystem.orderid"/></th>
        <th><m:message bundle="customersystem" key="customersystem.ordertype"/></th>
        <th><m:message bundle="customersystem" key="customersystem.ordertitle"/></th>
        <th><m:message bundle="common" key="common.roleid"/></th>
        <th><m:message bundle="common" key="common.rolename"/></th>
        <th><m:message bundle="common" key="common.role.nickname"/></th>
        <th><m:message bundle="common" key="common.usertype"/></th>
        <th><m:message bundle="customersystem" key="customersystem.questiontype"/></th>
        <th><m:message bundle="common" key="common.createtime"/></th>
        <th><m:message bundle="customersystem" key="customersystem.actor.user"/></th>
        <th><m:message bundle="customersystem" key="customersystem.closeordertime"/></th>
        <tbody>
        <c:forEach items="${page.list}" var="order">
    <tr>
        <td><a href="${ctx}/customersystem/forwardorder/orderForm?id=${order.id}">${order.id}</a></td>
        <td>${order.type}</td>
        <td><a href="${ctx}/customersystem/forwardorder/orderForm?id=${order.id}">${order.title}</a></td>
        <td>${order.roleId}</td>
        <td>${order.roleName}</td>
        <td>${order.roleNickName}</td>
        <td>${order.roleType}</td>
        <td>${order.questionType}</td>
        <td><fmt:formatDate value="${order.create_time}" type="both"/></td>
        <td>${order.final_actor_user}</td>
        <td><fmt:formatDate value="${order.final_actor_time}" type="both"/> </td>
    </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
