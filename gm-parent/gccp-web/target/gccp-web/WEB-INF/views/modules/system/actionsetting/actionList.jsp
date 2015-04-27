<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title><m:message bundle="common" key="common.menu.actionsetting"/></title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#gameId").change(function(){
                $("#searchForm").attr("action", "${ctx}/system/actionsetting");
                $("#searchForm").submit();
                return false;
            });
        });
    </script>
</head>
<body>
    <form:form id="searchForm" action="${ctx}/system/actionsetting" modelAttribute="condition" method="post" class="breadcrumb form-search">
        <div>
            <label>所属游戏:</label>
            <form:select id="gameId" path="gameId" class="input-small">
                <option value="0">All</option>
                <c:forEach items="${fns:getGameList()}" var="game">
                    <option value="${game.id}" <c:if test="${condition.gameId eq game.id}">selected="true"</c:if>><m:message bundle="common" key="${game.gameName}"/></option>
                </c:forEach>
            </form:select>
            &nbsp;&nbsp;<input class="btn btn-primary" type="submit" value="<m:message bundle="common" key="common.query"/>"/>
            <shiro:hasPermission name="actionsetting_add">
            <a class="btn btn-primary pull-right" href="${ctx}/system/actionsetting/addForm"><m:message bundle="common" key="common.add"/></a>
            </shiro:hasPermission>
        </div>
    </form:form>
    <tags:message message="${message}"/>
    <table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
        <tr><th>所属菜单</th><th>所属游戏</th><th>所属版本</th><th>功能名称</th><th>功能别称</th><th>接口地址</th><th>数据适配类</th><th>操作</th></tr>
        <c:forEach items="${list}" var="action">
            <tr>
                <td><m:message bundle="common" key="${action.menu.name}"/></td>
                <td>${action.gameName}</td>
                <td>${action.gameVersion}</td>
                <td>${action.name}</td>
                <td>${action.keyName}</td>
                <td>${action.url}</td>
                <td>${action.adapterClass}</td>
                <td>
                    <shiro:hasPermission name="actionsetting_update">
                    <a href="${ctx}/system/actionsetting/updateForm?id=${action.id}"><m:message bundle="common" key="common.modify"/></a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="actionsetting_delete">
                    <a href="${ctx}/system/actionsetting/delete?id=${action.id}" onclick="return confirmx('<m:message bundle="common" key="common.deleteall.confirm"/>?', this.href)"><m:message bundle="common" key="common.delete"/></a>
                    </shiro:hasPermission>
                    <a href="${ctx}/system/actionsetting/listParam?id=${action.id}&actionName=${action.name}">参数管理</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
