<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title></title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/dialog.jsp"%>
    <script type="text/javascript">

    </script>
</head>
<body>
<h1>${user.name}  <a href="${ctx}/userMgr/list"><img  src="${ctxStatic}/mokylin/img/return_48.png" title="返回"/></a> </h1>
<div class="row-fluid">
    <div class="span3">
        <table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
            <thead><tr><th>ID</th><th>系统名字</th><th>操作</th></tr></thead>
            <tbody>
            <c:forEach items="${sysList}" var="sys">
                <tr>
                    <td>${sys.id}</td>
                    <td>${sys.system_name}</td>
                    <td>
                        <a href="${ctx}/userMgr/assignPmsItem4User?userId=${user.id}&sysId=${sys.id}" target="content">权限</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div id="right" class="span7" style="height: 100%">
        <iframe   name="content" style="overflow:visible;"
                  scrolling="yes" frameborder="no" width="100%" height="100%"></iframe>
    </div>


</div>
<script type="text/javascript">
    function wSize(){
        var sizes = ["Height", "Width"].map(function(name){
            return window["inner" + name] ||
                    document.compatMode === "CSS1Compat" && document.documentElement[ "client" + name ] || document.body[ "client" + name ];
        });
        var strs = sizes.toString().split(",");
        $("#right, #content").height(strs[0]-5);
    }

    $(window).resize(function(){
        wSize();
    });
    wSize();
</script>
</body>
</html>
