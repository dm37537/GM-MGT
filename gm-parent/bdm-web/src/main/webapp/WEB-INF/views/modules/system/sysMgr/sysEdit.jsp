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
<ul class="breadcrumb">
    <li><a href="${ctx}/sysMgr/list">系统列表</a> <span class="divider">/</span></li>
    <li class="active">系统编辑</li>
</ul>
	<form:form id="inputForm" modelAttribute="sys" action="${ctx}/sysMgr/update" method="post" class="form-horizontal">
		<tags:message message="${message}"/>
		<div class="control-group">
			<label class="control-label">系统id:</label>
			<div class="controls">
				<form:input path="id" htmlEscape="false"   readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">系统名字:</label>
			<div class="controls">
				<form:input path="system_name" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">IP列表:</label>
            <div class="controls">
                <form:input path="ip_list" htmlEscape="false" maxlength="200" placeholder="ip用,隔开 例如 166.166.2.2,166.166.2.3" class="required" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">验证码:</label>
            <div class="controls">
                <form:input path="verify_code" htmlEscape="false" maxlength="200" placeholder="任意字符串" class="required" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">顺序:</label>
            <div class="controls">
                <form:input path="system_sequence" htmlEscape="false"  class="required"/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">创建时间:</label>
			<div class="controls">
				<form:input path="system_created_date" htmlEscape="false" readonly="true"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="<m:message bundle="common" key="common.save"/>"/>
            <a href="${ctx}/sysMgr/list" class="btn">返回</a>
		</div>
	</form:form>
</body>
</html>