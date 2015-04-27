<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title><m:message bundle="common" key="common.modify"/></title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#inputForm").validate({
                submitHandler:function(form){
                    $("input[name='gameName']").val($("#game option:selected").text());
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
    <li>
        <a href="${ctx}/system/actionsetting"><m:message bundle="common" key="common.menu.actionsetting"/></a>
        <span class="divider">/</span>
    </li>
    <li class="active"><m:message bundle="common" key="common.modify"/></li>
</ul>
<form:form id="inputForm" modelAttribute="action" action="${ctx}/system/actionsetting/update" method="post" class="form-horizontal">
    <tags:message message="${message}"/>
    <div class="container-fluid">
        <div class="span8">
            <div class="control-group">
                <label class="control-label">编号:</label>
                <form:input path="id" readonly="true"></form:input>
            </div>
            <div class="control-group">
                <label class="control-label">功能名称:</label>
                <form:input path="name" htmlEscape="false" maxlength="100" class="required"/>
            </div>
            <div class="control-group">
                <label class="control-label">功能别称:</label>
                <form:input path="keyName" htmlEscape="false" maxlength="100" class="required"/>
            </div>
            <div class="control-group">
                <label class="control-label">菜单ID:</label>
                <form:input path="menu.id" maxlength="10" class="required"/>
            </div>

            <div class="control-group">
                <label class="control-label">所属游戏:</label>
                <form:select id="game" path="gameId">
                    <c:forEach items="${fns:getGameList()}" var="game">
                        <option value="${game.id}" <c:if test="${action.gameId eq game.id}">selected="true"</c:if>><m:message bundle="common" key="${game.gameName}"/></option>
                    </c:forEach>
                </form:select>
                <input name="gameName" type="hidden"/>
            </div>

            <div class="control-group">
                <label class="control-label">游戏版本:</label>
                <form:input path="gameVersion" class="required" maxlength="100"/>
            </div>

            <div class="control-group">
                <label class="control-label">游戏接口地址:</label>
                <form:input path="url" maxlength="100" />
            </div>

            <div class="control-group">
                <label class="control-label">数据适配类:</label>
                <form:input path="adapterClass" maxlength="150"/>
            </div>

            <div class="control-group">
                <label class="control-label">特殊代理商site:</label>
                <form:textarea path="hasSite" class="input-xlarge" htmlEscape="false" maxlength="10000"/>
            </div>

            <div class="control-group">
                <label class="control-label">脚本:</label>
                <form:textarea path="script" class="input-xlarge" htmlEscape="false" />
            </div>
        </div>
    </div>
    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="<m:message bundle="common" key="common.save"/>" />&nbsp;
        <a href="${ctx}/system/actionsetting/list" class="btn"><m:message bundle="common" key="common.back"/></a>
    </div>
</form:form>
</body>
</html>
