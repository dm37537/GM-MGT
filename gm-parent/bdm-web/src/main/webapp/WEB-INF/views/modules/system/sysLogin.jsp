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
                "${ctxStatic}/images/bg3.jpg",
                "${ctxStatic}/images/bg4.jpg",
                "${ctxStatic}/images/bg5.jpg"
            ], {duration: 800, fade: 2000});
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
<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>
            <a class="brand" href="${ctx}"><img src="${ctxStatic}/images/logo.gif" alt="BDM Platform"
                                                style="height:40px;"></a>
        </div>
    </div>
</div>

<div class="container">
    <%String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);%>
    <div id="messageBox" class="alert alert-error <%=error==null?"hide":""%>">
        <button data-dismiss="alert" class="close">×</button>
        <label id="loginError"
               class="error"><%=error == null ? "" : "com.cyb.gm.modules.system.security.CaptchaException".equals(error) ? ResourceManager.getString(request, "common","common.validate.error") : ResourceManager.getString(request, "common", "common.usernameorpassword.error") %>
        </label>
    </div>
    <!--[if lte IE 10]>
    <br/><br/><br/><br/>
    <div class='alert alert-error' >
        <a class="close" data-dismiss="alert">x</a>
        <h4><m:message bundle="common" key="common.tips"/>：</h4>
        <p>你使用的IE浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到新版本的Chrome、Firefox、Safari 等。</p>
    </div> <![endif]-->
    <div id="login-wraper">
        <form id="loginForm" class="form login-form" action="${ctx}/login" method="post">
            <legend><span style="color:#08c;">BDM<m:message bundle="common" key="common.system.login"/></span></legend>
            <div class="body">
                <div class="control-group">
                    <div class="controls">
                        <input type="text" id="username" name="username" class="required" value="${username}"
                               placeholder="<m:message bundle="common" key="common.loginname"/>">
                    </div>
                </div>

                <div class="control-group">
                    <div class="controls">
                        <input type="password" id="password" name="password" class="required" placeholder="<m:message bundle="common" key="common.password"/>"/>
                    </div>
                </div>
                <c:if test="${isValidateCodeLogin}">
                    <div class="validateCode">
                        <label for="password"><m:message bundle="common" key="common.validate.code"/>：</label>
                        <tags:validateCode name="validateCode" inputCssStyle="margin-bottom:0;"/>
                    </div>
                </c:if>
            </div>
            <div class="footer">
                <label class="checkbox inline">
                    <input type="checkbox" id="rememberMe" name="rememberMe"> <span style="color:#08c;"><m:message bundle="common" key="common.remeber.me"/></span>
                </label>
                <%--登录 按钮--%>
                <input id="subBtn" class="btn btn-primary" type="submit" value="<m:message key="login" bundle="common"/>" />
            </div>
            <div class="dropdown">
                <%-- 语言选择 --%>
                <div class="dropdown pull-right" >
                    <a class="dropdown-toggle" data-toggle="dropdown"
                       href="#"><m:message bundle="common" key="${fns:getDictLabel(cookie.language.value,'language','locales.zh_CN')}" locale="zh_CN" /><b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <c:forEach items="${fns:getDictList('language')}" var="dict">
                            <li><a href="#"
                                   onclick="location='${pageContext.request.contextPath}/language/${dict.value}?url='+location.href"><m:message bundle="common" key="${dict.label}" locale="zh_CN"/></a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>

                <div id="themeSwitch" class="dropdown pull-left">
                    <a class="dropdown-toggle" data-toggle="dropdown"
                       href="#"><m:message bundle="common" key="${fns:getDictLabel(cookie.theme.value,'theme','theme.default')}"/><b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <c:forEach items="${fns:getDictList('theme')}" var="dict">
                            <li><a href="#"
                                   onclick="location='${pageContext.request.contextPath}/theme/${dict.value}?url='+location.href"><m:message bundle="common" key="${dict.label}" /></a>
                            </li>
                        </c:forEach>
                    </ul>

                    <!--[if lte IE 6]>
                    <script type="text/javascript">$('#themeSwitch').hide();</script><![endif]-->
                </div>
            </div>
        </form>
    </div>
</div>
<footer class="white navbar-fixed-bottom">
    Copyright &copy; 2014-${fns:getConfig('copyrightYear')} ${fns:getConfig('productName')}</a> - Powered By <a
        href="http://www.mokylin.com/" target="_blank">Mokylin</a> ${fns:getConfig('version')}
</footer>
</body>
</html>