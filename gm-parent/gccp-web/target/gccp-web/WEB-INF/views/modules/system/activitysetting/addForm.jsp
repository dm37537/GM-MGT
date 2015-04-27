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
            <a href="${ctx}/system/activitysetting">活动配置</a>
            <span class="divider">/</span>
        </li>
        <li class="active"><m:message bundle="common" key="common.add"/></li>
    </ul>
    <form:form id="inputForm" modelAttribute="activityInfo" action="${ctx}/system/activitysetting/add" method="post" class="form-horizontal">
        <tags:message message="${message}"/>
        <div class="container-fluid">
            <div class="span7">
                <div class="control-group">
                    <label class="control-label">编号:</label>
                    <form:input path="id" readonly="true"></form:input>
                </div>
                <div class="control-group">
                    <label class="control-label">活动名称:</label>
                    <form:input path="name" htmlEscape="false" maxlength="100" class="required"/>
                </div>

                <div class="control-group">
                    <label class="control-label">活动父类型:</label>
                    <form:input path="parentType" htmlEscape="false" maxlength="100" class="required"/>
                </div>

                <div class="control-group">
                    <label class="control-label">活动类型:</label>
                    <form:input path="type" htmlEscape="false" maxlength="100" class="required"/>
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
                    <label class="control-label">特殊代理商site:</label>
                    <form:textarea path="hasSite" class="input-xlarge" htmlEscape="false" maxlength="10000"/>
                </div>
            </div>
         </div>
        <div class="form-actions">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="<m:message bundle="common" key="common.save"/>" />&nbsp;
            <a href="${ctx}/system/activitysetting/list" class="btn"><m:message bundle="common" key="common.back"/></a>
        </div>
    </form:form>
</body>
</html>
