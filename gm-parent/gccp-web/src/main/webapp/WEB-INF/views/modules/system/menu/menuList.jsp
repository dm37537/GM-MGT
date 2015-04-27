<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title><m:message bundle="common" key="common.menu.menumanage"/></title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/treetable.jsp" %>
    <style type="text/css">.table td i{margin:0 2px;}</style>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#treeTable").treeTable({expandLevel : 3});
        });
        function updateSort() {
            loading('<m:message bundle="common" key="common.commiting"/>...');
            $("#listForm").attr("action", "${ctx}/system/menu/updateSort");
            $("#listForm").submit();
        }
    </script>
</head>
<body>
    <tags:message message="${message}"/>
    <form id="listForm" method="post">
        <table id="treeTable" class="table table-striped table-bordered table-condensed table-hover">
            <tr><th><m:message bundle="common" key="common.titlename"/></th><th><m:message bundle="common" key="common.link"/></th><th style="text-align:center;"><m:message bundle="common" key="common.sort"/></th><th><m:message bundle="common" key="common.visible"/></th><th><m:message bundle="common" key="common.permission.name"/></th><th><m:message bundle="common" key="common.operate"/></th></tr>
            <c:forEach items="${list}" var="menu">
                <tr id="${menu.id}" pId="${menu.parent.id ne '1' ? menu.parent.id : '0'}">
                    <td><i class="icon-${not empty menu.icon?menu.icon:' hide'}"></i><a href="${ctx}/system/menu/formThis?id=${menu.id}"><m:message bundle="common" key="${menu.name}"/></a></td>
                    <td>${menu.href}</td>
                    <td style="text-align:center;">
                        <shiro:hasPermission name="menu_update">
                            <input type="hidden" name="ids" value="${menu.id}"/>
                            <input name="sorts" type="text" value="${menu.sort}" style="width:50px;margin:0;padding:0;text-align:center;">
                        </shiro:hasPermission>
                        <shiro:lacksPermission name="menu_update">
                            ${menu.sort}
                        </shiro:lacksPermission>
                    </td>
                    <td>
                        <c:if test="${menu.show}"><m:message bundle="common" key="common.yes"/></c:if>
                        <c:if test="${!menu.show}"><m:message bundle="common" key="common.no"/></c:if>
                    </td>
                    <td>${menu.permission}</td>
                    <td>
                        <shiro:hasPermission name="menu_update">
                        <a href="${ctx}/system/menu/formThis?id=${menu.id}"><m:message bundle="common" key="common.modify"/></a>
                            </shiro:hasPermission>
                        <shiro:hasPermission name="menu_delete">
                        <a href="${ctx}/system/menu/delete?id=${menu.id}" onclick="return confirmx('<m:message bundle="common" key="common.delete.menuandchildmenu.confirm"/>？', this.href)"><m:message bundle="common" key="common.delete"/></a>
                        </shiro:hasPermission>
                        <%--<a href="${ctx}/system/menu/formChild?parent.id=${menu.id}"><m:message bundle="common" key="common.add.childmenu"/></a>--%>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <shiro:hasPermission name="menu_update">
        <div class="form-actions pagination-left">
            <input id="btnSubmit" class="btn btn-primary" type="button" value="<m:message bundle="common" key="common.save"/>" onclick="updateSort();"/>
        </div>
        </shiro:hasPermission>
    </form>
</body>
</html>
