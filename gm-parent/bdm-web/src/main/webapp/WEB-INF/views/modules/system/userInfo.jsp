<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><m:message bundle="common" key="common.privateinfo"/></title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('<m:message bundle="common" key="common.commiting"/>...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("<m:message bundle="common" key="common.inputerror.pleaseconfirm"/>");
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
	<ul class="nav nav-tabs" closable="true">
		<li class="active"><a href="${ctx}/system/user/info"><m:message bundle="common" key="common.privateinfo"/></a></li>
		<li><a href="${ctx}/system/user/modifyPwd"><m:message bundle="common" key="common.modify.password"  /></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/system/user/info" method="post" class="form-horizontal">
		<tags:message message="${message}"/>
		<div class="control-group">
			<label class="control-label"><m:message bundle="common" key="common.name"/>:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><m:message bundle="common" key="common.email"/>:</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="50" class="email"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><m:message bundle="common" key="common.tel"/>:</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="20"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><m:message bundle="common" key="common.remark"/>:</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><m:message bundle="common" key="common.user.type"/>:</label>
			<div class="controls">
				<label class="lbl"><m:message bundle="common" key="common.not.has"/></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><m:message bundle="common" key="common.user.role"/>:</label>
			<div class="controls">
				<label class="lbl"><m:message bundle="common" key="common.not.has"/></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><m:message bundle="common" key="common.last.login"/>:</label>
			<div class="controls">
				<label class="lbl">IP: ${user.lastLoginIp}&nbsp;&nbsp;&nbsp;&nbsp;<m:message bundle="common" key="common.time"/>：${user.lastLoginDate}</label>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="<m:message bundle="common" key="common.save"/>"/>
		</div>
	</form:form>
</body>
</html>