<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>添加参数</title>
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
            });
        });
    </script>
</head>
<body>
    <ul class="breadcrumb">
        <li class="active">添加参数信息</li>
    </ul>
    <tags:message message="${message}"/>
    <form:form id="inputForm" modelAttribute="parameter" action="${ctx}/system/actionsetting/addParam" method="post" class="form-horizontal">
        <div class="container-fluid">
            <div class="span6">
                <div class="control-group">
                    <label class="control-label">key:</label>
                    <form:hidden path="id"/>
                    <form:hidden path="actionId" />
                    <form:input path="key" htmlEscape="false" class="required" maxlength="100" />
                </div>

                <div class="control-group">
                    <label class="control-label">label:</label>
                    <form:input path="label" htmlEscape="false" class="required" maxlength="100" />
                </div>

                <div class="control-group">
                    <label class="control-label">type:</label>
                    <form:select path="type">
                        <c:forEach items="${fns:getDictList('action_param_type')}" var="type">
                            <option value="${type.value}">${type.label}</option>
                        </c:forEach>
                    </form:select>
                </div>

                <div class="control-group">
                    <label class="control-label">require:</label>
                    <form:checkbox path="require" label="必填"/>
                </div>

                <div class="control-group">
                    <label class="control-label">min:</label>
                    <form:input path="min" htmlEscape="false" maxlength="100" />
                </div>

                <div class="control-group">
                    <label class="control-label">max:</label>
                    <form:input path="max" htmlEscape="false" maxlength="100" />
                </div>

                <div class="control-group">
                    <label class="control-label">version:</label>
                    <form:input path="version" maxlength="100" />
                </div>

                <div class="control-group">
                    <label class="control-label">hasSite:</label>
                    <form:textarea path="hasSite" class="input-xlarge" htmlEscape="false" maxlength="100000"/>
                </div>

                <div class="control-group">
                    <label class="control-label">remark:</label>
                    <form:textarea path="remark" class="input-xlarge" htmlEscape="false" maxlength="1000"/>
                </div>

                <div class="form-actions">
                    <input id="btnSubmit" class="btn btn-primary" type="submit" value="<m:message bundle="common" key="common.save"/>" />
                </div>
            </div>
        </div>
    </form:form>
</body>
</html>
