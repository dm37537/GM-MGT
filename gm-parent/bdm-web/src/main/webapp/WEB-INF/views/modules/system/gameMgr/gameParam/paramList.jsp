<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>参数管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#inputForm").validate({
                submitHandler:function(form){
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
        // 生成参数控件
        function addParameterTag(){
            var count = $("#container").children().length-2;
            var content = '<div class="control-group">' +
                    '<input name="game_params[' + count + '].name" type="text" class="input-medium required"/> &nbsp;' +
                    '<input name="game_params[' + count + '].val" type="text" class="input-medium required"/> &nbsp;'<c:if test="${game.level==3}"> +
                    '<input name="game_params[' + count + '].comment" type="text" class="input-medium required"/> &nbsp;'</c:if>
            '</div>';
            $("#container").append(content);
        }
    </script>
</head>
<body style="width: 800px;height: 600px;">

<br>
<div>
    <form:form id="inputForm" modelAttribute="game" action="${ctx}/gameMgr/gameParamMgr/add" method="post" class="form-horizontal">
          <input name="id" value="${game.id}" type="hidden"/>
        <div class="container-fluid breadcrumb" id="container">
            <div class="control-group pull-right">
                <a class="btn btn-primary" onclick="addParameterTag()" href="#">添加参数</a>
            </div>
            <div class="control-group">
                <label class="control-label">参数名</label><label class="control-label">参数值</label><c:if test="${game.level==3}"><label class="control-label">参数描述</label></c:if>
            </div>
            <c:forEach items="${params}"  var="gameParam" varStatus="status">
                <div class="control-group">
                    <input name="game_params[${status.count-1}].name" type="text" class="input-medium required" value="${gameParam.name}"/> &nbsp;
                    <input name="game_params[${status.count-1}].val" type="text" class="input-medium required" value="${gameParam.val}"/> &nbsp;
                    <c:if test="${game.level==3}">
                        <input name="game_params[${status.count-1}].comment" type="text" class="input-medium required" value="${gameParam.comment}"/> &nbsp;
                    </c:if>
                    <c:if test="${gameParam.type==1}">
                        来自于代理商
                    </c:if>
                </div>
            </c:forEach>

        </div>
        <div class="pull-right">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保存" />&nbsp;
        </div>
    </form:form>
</div>

</body>
</html>
