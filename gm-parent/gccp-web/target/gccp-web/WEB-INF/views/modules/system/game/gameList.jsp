<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title><m:message bundle="common" key="common.menu.gameversion"/></title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/treetable.jsp" %>
</head>
<body>
    <table class="table table-striped table-bordered table-condensed table-hover">
        <tr><th>游戏编号</th><th>游戏名</th><th>游戏版本</th></tr>
        <c:forEach items="${list}" var="game">
            <tr>
                <td>${game.id}</td>
                <td><m:message bundle="common" key="${game.gameName}"/></td>
                <td>${game.versions}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
