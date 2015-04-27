<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title><m:message bundle="common" key="common.add"/></title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#inputForm").validate({
                submitHandler:function(form){
                    loading('<m:message bundle="common" key="common.commiting"/>');
                    form.submit();
                },
                errorContainer:"#messageBox",
                errorPlacement: function(error, element) {
                    $("#messageBox").text("<m:message bundle="common" key="common.inputerror.pleaseconfirm"/>");
                    if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            })
        });
    </script>
</head>
<body>
<ul class="breadcrumb">
    <li>
        <a href="${ctx}/system/resource"><m:message bundle="common" key="common.menu.resourcemanage"/></a>
        <span class="divider">/</span>
    </li>
    <li class="active"><m:message bundle="common" key="common.add"/></li>
</ul>
<form:form id="inputForm" modelAttribute="resource" action="${ctx}/system/resource/add" method="post" class="form-horizontal">
    <tags:message message="${message}"/>
    <div class="control-group">
        <label class="control-label">语言:</label>
        <form:select path="locale">
            <form:options items="${fns:getDictList('language')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
        </form:select>
    </div>
    <div class="control-group">
        <label class="control-label">bundle:</label>
        <form:input path="bundle" htmlEscape="false" maxlength="100" class="required"/>
    </div>
    <div class="control-group">
        <label class="control-label">key:</label>
        <form:input path="key" htmlEscape="false" maxlength="200" class="required"/>
    </div>
    <div class="control-group">
        <label class="control-label">value:</label>
        <form:input path="value" htmlEscape="false" maxlength="200" class="required"/>
    </div>

    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="<m:message bundle="common" key="common.save"/>" />&nbsp;
        <a href="${ctx}/system/resource/list" class="btn"><m:message bundle="common" key="common.back"/></a>
    </div>
</form:form>
</body>
</html>
