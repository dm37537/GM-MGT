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

<form:form id="inputForm" modelAttribute="item" action="${ctx}/pmsItemMgr/update" method="post" class="form-horizontal">
    <tags:message message="${message}"/>
    <div class="control-group">
        <label class="control-label">权限名字:</label>
        <div class="controls">
            <form:input path="pms_item_name" htmlEscape="false" required="true"/>
        </div>
    </div>
    <div class="modal-footer">
        <button class="btn" type="button" onclick="history.go(-1);">返回</button>
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="<m:message bundle="common" key="common.save"/>"/>
    </div>
</form:form>
</body>
</html>