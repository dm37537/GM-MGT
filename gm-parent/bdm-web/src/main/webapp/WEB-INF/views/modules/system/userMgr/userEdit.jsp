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
    <li><a href="${ctx}/userMgr/list">用户列表</a> <span class="divider">/</span></li>
    <li class="active">编辑用户</li>
</ul>
<form:form id="inputForm" modelAttribute="user" action="${ctx}/userMgr/update" method="post" class="form-horizontal">
    <input type="hidden" name="id" value="${user.id}">
    <tags:message message="${message}"/>
    <div class="control-group">
        <label class="control-label">登陆名:</label>
        <div class="controls">
            <form:input path="userName" htmlEscape="false" class="required"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">用户姓名:</label>
        <div class="controls">
            <form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">邮箱地址:</label>
        <div class="controls">
            <form:input path="email" htmlEscape="false" type="email" class="required"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">手机:</label>
        <div class="controls">
            <form:input path="mobile" htmlEscape="false" type="number" />
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">备注:</label>
        <div class="controls">
            <form:input path="description" htmlEscape="false"/>
        </div>
    </div>
    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="<m:message bundle="common" key="common.save"/>"/>
        <a href="${ctx}/userMgr/list" class="btn">返回</a>
    </div>
</form:form>
</body>
</html>