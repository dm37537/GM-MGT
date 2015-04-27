<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title><m:message bundle="common" key="common.menu.switchmanage"/></title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $("#treeContainer").live("hello",function(){
            alert("dfd");
        });
    </script>
</head>
<body>
    <div id="content" class="row-fluid">
        <div id="left">
            <tags:gameTree showStatus="0,1"/>
        </div>
        <div id="openClose" class="close">&nbsp;</div>
        <div id="right">

        </div>
    </div>

    <script type="text/javascript">
        var leftWidth = "230"; //左侧宽度
        function wSize(){
            var strs = getWindowSize().toString().split(",");
            $("#left, #openClose, #right").height(strs[0]-5);
            $("#right").width($("body").width() - $("#left").width() - $("#openClose").width()-20);
            wGameTreeSize();
        }
    </script>
    <script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>
