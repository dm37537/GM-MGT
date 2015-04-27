<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title><m:message bundle="common" key="common.privateinfo"/></title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<style type="text/css">.table td i{margin:0 2px;}</style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 2});
		});
	</script>
</head>
<body>
<ul class="breadcrumb">
    <li><a href="${ctx}/sysMgr/list">系统列表</a> <span class="divider">/</span></li>
    <li class="active">[${sys.system_name}]权限列表</li>
</ul>
<form:form id="searchForm" modelAttribute="sys" action="${ctx}/sysMgr/list" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}">
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}">
    <input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}">
    <label>权限管理</label>
    <span class="pull-right">
         <a class="btn btn-primary" href="${ctx}/pmsItemMgr/pmsItemAdd?sysId=${sys.id}">添加分类</a>
    </span>
</form:form>
<br>
	<tags:message message="${message}"/>
	<form id="listForm" method="post">
		<table id="treeTable" class="table table-striped table-bordered table-condensed table-hover">
			<tr><th>id</th><th>名称</th><th>标识</th><th>描述</th><th>操作</th></tr>
			<c:forEach items="${sortedList}" var="item">
				<tr id="${item.id}" pId="${item.pms_item_parent_id}">
                    <td>
                        ${item.id}
                    </td>
					<td><c:if test="${item.pms_item_parent_id!=0&&item.pms_item_source!=''}"><img   src="${ctxStatic}/mokylin/img/tree_16.png"/></c:if>${item.pms_item_title}</td>
                    <td>${item.pms_item_name}</td>
                    <td>${item.pms_item_comment}</td>
					<td>
                        <a href="${ctx}/pmsItemMgr/pmsItemEdit?pmsItemId=${item.id}" title="编辑" ><img src="${ctxStatic}/mokylin/img/editIcon${item.pms_item_parent_id==0?'':'-16'}.png"/></a>
                        <c:if test="${item.pms_item_parent_id==0}">
                            <a href="${ctx}/pmsItemMgr/pmsItemAdd?sysId=${sys.id}&pid=${item.id}" title="添加权限" data-original-title="Tooltip on top"  data-toggle="tooltip" data-placement="top"><img src="${ctxStatic}/mokylin/img/addIcon.png"/></a>
                        </c:if>
                        <c:if test="${item.pms_item_parent_id!=0&&item.pms_item_state==0}">
                            <a href="${ctx}/pmsItemMgr/updateState?id=${item.id}&sysId=${sys.id}&state=-1" title="禁用" onclick="return confirmx('要禁用【${item.pms_item_title}】吗？', this.href)"><img src="${ctxStatic}/mokylin/img/forbiddenIcon-16.png"/></a>
                        </c:if>
                        <c:if test="${item.pms_item_state==-1}">
                            <a href="${ctx}/pmsItemMgr/updateState?id=${item.id}&sysId=${sys.id}&state=0" title="启用" onclick="return confirmx('要启用【${item.pms_item_title}】吗？', this.href)"><img src="${ctxStatic}/mokylin/img/startIcon-16.png"/></a>
                        </c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
	 </form>

</body>
</html>
