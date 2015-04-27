<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter" %>
<%@ page import="com.mokylin.gm.utils.ResourceManager" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>${fns:getConfig('productName')} <m:message bundle="common" key="login1"/></title>
    <meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/common/typica-login.css">
    <style type="text/css">
        .control-group {
            border-bottom: 0px;
        }
    </style>
    <%-- 动态设置背景图 --%>
    <script src="${ctxStatic}/common/backstretch.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            //设置焦点
            if($("#username").val().length ==0)
                $("#username").focus();
            else
                $("#subBtn").focus();

            $.backstretch([
                "${ctxStatic}/images/bg1.jpg",
                "${ctxStatic}/images/bg2.jpg",
                "${ctxStatic}/images/bg3.jpg"
            ], {duration: 10000, fade: 2000});
            $("#loginForm").validate({
                rules: {
                    validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
                },
                messages: {
                    username: {required: "<m:message bundle="common" key="common.please.input.username"/>."}, password: {required: "<m:message bundle="common" key="common.please.input.password"/>."},
                    validateCode: {remote: "<m:message bundle="common" key="common.validatecode.error"/>.", required: "<m:message bundle="common" key="common.please.input.validatecode"/>."}
                },
                errorLabelContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    error.appendTo($("#loginError").parent());
                }
            });
        });
        // 如果在框架中，则跳转刷新上级页面
        if (self.frameElement && self.frameElement.tagName == "IFRAME") {
            parent.location.reload();
        }
    </script>
</head>
<body>


<div class="container">

	
    
    <div id="login-wraper">
     <form id="loginForm" class="form login-form" action="${ctx}/login" method="post">
      <input type="text" id="username" name="username"  value="${username}" placeholder="用户名"/>
      <input type="password" id="password" name="password"  value="${password}" placeholder="密码"/>
      <br>
      <input id="subBtn" class="btn btn-primary" type="submit" value="登录"/>      
     </form>
     <!--  -->
     <form id="RegisterForm" class="form Register-form" action="${ctx}/register" method="post">
      <input type="text" id="username" name="username"  value="${username}" placeholder="用户名"/>
      <input type="password" id="password" name="password"  value="${password}" placeholder="密码"/>
      <input type="text" id="name" name="name"  value="${name}" placeholder="姓名"/>
      <input type="text" id="email" name="email"  value="${email}" placeholder="电子邮箱"/>
      <input type="text" id="mobile" name="mobile"  value="${mobile}" placeholder="电话"/>
      <br>
      <input id="subBtn" class="btn btn-primary" type="submit" value="注册"/>      
     </form>
     
    </div>

</div>

</body>
</html>