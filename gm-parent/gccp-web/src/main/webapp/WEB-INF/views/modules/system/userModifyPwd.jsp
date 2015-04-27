<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><m:message bundle="common" key="common.modify.password"/></title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#oldPassword").focus();
			$("#inputForm").validate({
				rules: {
				},
				messages: {
					confirmNewPassword: {equalTo: "<m:message bundle="common" key="common.confirm.samepassword"/>"}

				},
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
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/system/user/info"><m:message bundle="common" key="common.privateinfo"/></a></li>
		<li class="active"><a href="${ctx}/system/user/modifyPwd"><m:message bundle="common" key="common.modify.password"/></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/system/user/modifyPwd" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message message="${message}"/>
		<div class="control-group">
			<label class="control-label"><m:message bundle="common" key="common.old.password"/>:</label>
			<div class="controls">
				<input id="oldPassword" name="oldPassword" type="password" value="" maxlength="50" minlength="3" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><m:message bundle="common" key="common.new.password"/>:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><m:message bundle="common" key="common.confirm.newpassword"/>:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="3" class="required" equalTo="#newPassword"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="<m:message bundle="common" key="common.save"/>"/>
		</div>
	</form:form>
</body>
</html>