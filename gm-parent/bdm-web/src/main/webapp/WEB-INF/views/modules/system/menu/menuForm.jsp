<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title><m:message bundle="common" key="common.menu.menumanage"/></title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#name").focus();
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
    <form:form id="inputForm" modelAttribute="menu" action="${ctx}/system/menu/save" method="post" class="form-horizontal">
        <form:hidden path="id"/>
        <tags:message message="${message}"/>
        <div class="control-group">
            <label class="control-label"><m:message bundle="common" key="common.parent.menu"/>:</label>
            <div class="controls">
                <tags:treeselect id="menu" name="parent.id" value="${menu.parent.id}" labelName="parent.name" labelValue="${menu.parent.name}"
                                 title="<m:message bundle='common' key='common.menu'/>" url="/system/menu/treeData" extId="${menu.id}" cssClass="required"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label"><m:message bundle="common" key="common.titlename"/>:</label>
            <div class="controls">
                <form:input path="name" htmlEscape="false" maxlength="50" class="required" />
                <label><m:message bundle="common" key="${menu.name}"/></label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label"><m:message bundle="common" key="common.link"/>:</label>
            <div class="controls">
                <form:input path="href" htmlEscape="false" maxlength="200"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label"><m:message bundle="common" key="common.target"/>:</label>
            <div class="controls">
                <form:input path="target" htmlEscape="false" maxlength="10"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label"><m:message bundle="common" key="common.icon"/>:</label>
            <div class="controls">
                <tags:iconselect id="icon" name="icon" value="${menu.icon}"></tags:iconselect>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label"><m:message bundle="common" key="common.sort"/>:</label>
            <div class="controls">
                <form:input path="sort" htmlEscape="false" maxlength="50" class="required digits"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label"><m:message bundle="common" key="common.visible"/>:</label>
            <div class="controls">
                <form:radiobuttons path="show" items="${fns:getDictList('show_hide')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label"><m:message bundle="common" key="common.permission.name"/>:</label>
            <div class="controls">
                <form:input path="permission" htmlEscape="false" maxlength="100"/>
            </div>
        </div>

        <div class="form-actions">
            <%--<shiro:hasPermission name="sys:menu:edit">--%>
                <input id="btnSubmit" class="btn btn-primary" type="submit" value="<m:message bundle="common" key="common.save"/>"/>&nbsp;
            <%--</shiro:hasPermission>--%>
                <a href="${ctx}/system/menu" class="btn"><m:message bundle="common" key="common.back"/></a>
        </div>
    </form:form>
</body>
</html>
