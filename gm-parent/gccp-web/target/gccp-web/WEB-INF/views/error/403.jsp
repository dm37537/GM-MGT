<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>403 - <m:message bundle="common" key="common.user.permission.notenough"/></title>
    <%@include file="/WEB-INF/views/include/head.jsp" %>
</head>
<body>
<div class="container-fluid">
    <div class="page-header"><h1><m:message bundle="common" key="common.user.permission.notenough"/>.</h1></div>
    <div><a href="javascript:" onclick="history.go(-1);" class="btn"><m:message bundle="common" key="common.back.previous"/></a></div>
    <script>try {
        top.$.jBox.closeTip();
    } catch (e) {
    }</script>
</div>
</body>
</html>