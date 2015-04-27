<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>活动配置</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#gameId").change(function(){
                $("#searchForm").attr("action", "${ctx}/system/activitysetting");
                $("#searchForm").submit();
                return false;
            });
        });
    </script>
</head>
<body>
    <form:form id="searchForm" action="${ctx}/system/activitysetting" modelAttribute="condition" method="post" class="breadcrumb form-search">
        <div>
            <label>所属游戏:</label>
            <form:select id="gameId" path="gameId" class="input-small">
                <option value="0">All</option>
                <c:forEach items="${fns:getGameList()}" var="game">
                    <option value="${game.id}" <c:if test="${condition.gameId eq game.id}">selected="true"</c:if>><m:message bundle="common" key="${game.gameName}"/></option>
                </c:forEach>
            </form:select>
            &nbsp;&nbsp;<input class="btn btn-primary" type="submit" value="<m:message bundle="common" key="common.query"/>"/>
            <shiro:hasPermission name="activitysetting_add">
            <a class="btn btn-primary pull-right" href="${ctx}/system/activitysetting/addForm"><m:message bundle="common" key="common.add"/></a>
            </shiro:hasPermission>
        </div>
    </form:form>
    <tags:message message="${message}"/>
    <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <tr><th>所属游戏</th><th>游戏版本</th><th>特殊代理商</th><th>活动父类型</th><th>活动类型</th><th>活动名称</th><th>游戏接口地址</th><th>操作</th></tr>
        <c:forEach items="${list}" var="activityInfo">
            <tr>
                <td>${activityInfo.gameName}</td>
                <td>${activityInfo.gameVersion}</td>
                <td>${activityInfo.hasSite}</td>
                <td>${activityInfo.parentType}</td>
                <td>${activityInfo.type}</td>
                <td>${activityInfo.name}</td>
                <td>${activityInfo.url}</td>
                <td>
                    <shiro:hasPermission name="activitysetting_update">
                    <a href="${ctx}/system/activitysetting/updateForm?id=${activityInfo.id}"><m:message bundle="common" key="common.modify"/></a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="activitysetting_delete">
                    <a href="${ctx}/system/activitysetting/delete?id=${activityInfo.id}" onclick="return confirmx('<m:message bundle="common" key="common.deleteall.confirm"/>?', this.href)"><m:message bundle="common" key="common.delete"/></a>
                    </shiro:hasPermission>
                    <a href="${ctx}/system/activitysetting/listParam?id=${activityInfo.id}&activityName=${activityInfo.name}" >参数管理</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
