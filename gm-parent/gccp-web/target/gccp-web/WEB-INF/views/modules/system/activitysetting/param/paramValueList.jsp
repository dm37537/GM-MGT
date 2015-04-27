<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>集合值列表</title>
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

            $("#addBtn").click(function(){
                //添加元素
                var count = $("#container").children().length;
                var content = '<div class="control-group">'
                        +'<label>名称:</label> &nbsp;'
                        +'<input name="values[' +count+ '].label" class="input-small required" type="text"/> &nbsp;&nbsp;'
                        +'<label>值:</label> &nbsp;'
                        +'<input name="values[' +count+ '].value" class="input-small required" type="text"/> &nbsp;'
                        +'<a href="#" title="remove"><i class="icon-remove"></i></a>'
                        +'</div>';
                $("#container").append(content);

            });
            // 移除元素
            $("#container div a").live('click', function(){
                $(this).parent().remove();
            });

            //关闭窗口
            $("#btnClose").click(function(){
                top.$.jBox.close(true);
            });
        });

    </script>
</head>
<body>
    <div class="navbar nav-collapse" style="padding-left: 20px">
        <input id="addBtn" type="button" class="btn btn-primary" value="添加值"/>
    </div>
    <form:form id="inputForm" modelAttribute="parameter" action="${ctx}/system/activitysetting/saveParamValue"  method="post" class="form-horizontal">
        <tags:message message="${message}"/>
        <form:hidden path="id"/>
        <form:hidden path="key"/>
        <div class="container-fluid">
            <div id="container" class="span12">
                <c:forEach items="${parameter.values}" var="tmp" varStatus="status">
                    <div class="control-group">
                        <label>名称:</label>&nbsp;
                        <input name="values[${status.index}].label" class="input-small required" type="text" value="${tmp.label}"/>&nbsp;&nbsp;
                        <label>值:</label>&nbsp;
                        <input name="values[${status.index}].value" class="input-small required" type="text" value="${tmp.value}"/>&nbsp;
                        <a href="#" title="remove"><i class="icon-remove"></i></a>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="form-actions">
            <div class="pull-right">
                <input id="btnSubmit" class="btn btn-primary" type="submit" value="<m:message bundle="common" key="common.save"/>">&nbsp;
                <a id="btnClose" href="#" class="btn"><m:message bundle="common" key="common.close"/></a>
            </div>
        </div>
    </form:form>
</body>
</html>
