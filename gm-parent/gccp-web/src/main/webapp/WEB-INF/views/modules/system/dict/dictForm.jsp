<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字典管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#value").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="breadcrumb">
        <li>
            <a href="${ctx}/system/dict/"><m:message bundle="common" key="common.menu.resourcemanage"/></a>
            <span class="divider">/</span>
        </li>
        <li class="active">
            <shiro:hasAnyPermissions name="dictmanage_add,dictmanage_delete">
                ${ dict.id eq 0 ? "添加":"修改"}
            </shiro:hasAnyPermissions>
        </li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="dict" action="${ctx}/system/dict/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message message="${message}"/>
		<div class="control-group">
			<label class="control-label">键值:</label>
			<div class="controls">
				<form:input path="value" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标签:</label>
			<div class="controls">
				<form:input path="label" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类型:</label>
			<div class="controls">
				<form:input path="type" htmlEscape="false" maxlength="50" class="required abc"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">描述:</label>
			<div class="controls">
				<form:input path="description" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序:</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="11" class="required digits"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasAnyPermissions name="dictmanage_add,dictmanage_update"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasAnyPermissions>
            <a href="${ctx}/system/dict/list" class="btn"><m:message bundle="common" key="common.back"/></a>
		</div>
	</form:form>
</body>
</html>