<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>${fns:getConfig('productName')}</title>
    <%@include file="/WEB-INF/views/include/dialog.jsp" %>
    <meta name="decorator" content="default"/>
    <style type="text/css">
        #main {
            padding: 0;
            margin: 0;
        }

        #main .container-fluid {
            padding: 0 7px 0 10px;
        }

        #header {
            margin: 0 0 10px;
            position: static;
        }

        #header li {
            font-size: 14px;
            _font-size: 12px;
        }

        #header .brand {
            font-family: Helvetica, Georgia, Arial, sans-serif, 黑 体;
            font-size: 26px;
            padding-left: 33px;
        }

        #footer {
            margin: 8px 0 0 0;
            padding: 3px 0 0 0;
            font-size: 11px;
            text-align: center;
            border-top: 2px solid #0663A2;
        }

        #footer, #footer a {
            color: #999;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#menu a.menu").click(function () {
                $("#menu li.menu").removeClass("active");
                $(this).parent().addClass("active");
                if (!$("#openClose").hasClass("close")) {
                    $("#openClose").click();
                }
            });
        });

        function removeDiv(obj){
            var ob = document.getElementById(obj);
            ob.parentNode.removeChild(ob);
            var obDiv = document.getElementById("div_" + obj);
            obDiv.removeAttribute('src');
            obDiv.parentNode.removeChild(obDiv);
            var tabList = document.getElementById("div_tab").getElementsByTagName('li');
            var panelList = document.getElementById("div_panel").getElementsByTagName('iframe');
            for (var i = 1; i < tabList.length; i++)
            {
                tabList[i].className = "";
                panelList[i].style.display = "none";
            }

            if(tabList.length>0){
                tabList[0].className = 'active';
                panelList[0].style.display = 'block';
            }
            return false;
        }

        function showMainFrame(){
            var tabList = document.getElementById("div_tab").getElementsByTagName('li');
            var panelList = document.getElementById("div_panel").getElementsByTagName('iframe');
            for (var i = 1; i < tabList.length; i++)
            {
                tabList[i].className = "";
                panelList[i].style.display = "none";
            }
            tabList[0].className = "active";
            document.getElementById("mainFrame").style.display = 'block';
        }
    </script>
</head>
<body>
<div id="main">
    <div id="header" class="navbar navbar-fixed-top">
        <div class="navbar-inner">
            <div class="brand">${fns:getConfig('productName')}</div>
            <div class="nav-collapse">
                <ul id="menu" class="nav">
                    <%--游戏列表--%>
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#"><m:message bundle="common" key="${fns:getCurrentGame()}"/></a>
                        <ul class="dropdown-menu">
                            <c:forEach items="${fns:getGameList()}" var="game">
                                <li><a href="#" onclick="location='${pageContext.request.contextPath}/system/user/gameSelect?id=${game.gameName}&url=' + location.href" ><m:message bundle="common" key="${game.gameName}"/></a></li>
                            </c:forEach>
                        </ul>
                    </li>
                    <c:set var="firstMenu" value="true"/>
                    <c:forEach items="${fns:getMenuList()}" var="menu" varStatus="idxStatus">
                        <c:if test="${menu.parent.id eq '1' && menu.show && fn:length(menu.childList)>0 }">
                            <li class="menu ${firstMenu ? ' active' : ''}"><a class="menu"
                                                                              href="${ctx}/system/menu/tree?parentId=${menu.id}"
                                                                              target="menuFrame"><m:message bundle="common" key="${menu.name}"/></a></li>
                            <c:if test="${firstMenu}">
                                <c:set var="firstMenuId" value="${menu.id}"/>
                            </c:if>
                            <c:set var="firstMenu" value="false"/>
                        </c:if>
                    </c:forEach>
                </ul>
                <ul class="nav pull-right">
                    <li><a href="${pageContext.request.contextPath}${fns:getAdminPath()}" target="_self"
                           title="<m:message bundle="common" key="common.home"/>">
                        <i class="icon-home"></i></a></li>
                    <li id="themeSwitch" class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#" title="<m:message bundle="common" key="theme.select" />"><i
                                class="icon-th-large"></i></a>
                        <ul class="dropdown-menu">
                            <c:forEach items="${fns:getDictList('theme')}" var="dict">
                                <li><a href="#"
                                       onclick="location='${pageContext.request.contextPath}/theme/${dict.value}?url='+location.href"><m:message bundle="common" key="${dict.label}" /></a>
                                </li>
                            </c:forEach>
                        </ul>
                        <!--[if lte IE 6]>
                        <script type="text/javascript">$('#themeSwitch').hide();</script><![endif]-->
                    </li>
                    <%-- 语言选择 --%>
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#" title="<m:message bundle="common" key="language.select"/>">
                            <i class="icon-globe"></i>
                        </a>
                        <ul class="dropdown-menu">
                            <c:forEach items="${fns:getDictList('language')}" var="dict">
                                <li>
                                    <a href="#" onclick="location='${pageContext.request.contextPath}/language/${dict.value}?url=' + location.href">
                                        <m:message bundle="common" key="${dict.label}" locale="zh_CN"/>
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </li>

                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#" title="<m:message bundle="common" key="common.privateinfo"/>"><m:message bundle="common" key="common.hello"/>, <shiro:principal
                                property="userName"/></a>
                        <%--<ul class="dropdown-menu">--%>
                            <%--<li><a href="${ctx}/system/user/info" target="mainFrame"><i class="icon-user"></i>&nbsp;--%>
                                <%--<m:message bundle="common" key="common.privateinfo"/></a></li>--%>
                            <%--<li><a href="${ctx}/system/user/modifyPwd" target="mainFrame"><i class="icon-lock"></i>&nbsp;--%>
                                <%--<m:message bundle="common" key="common.modify.password"/></a></li>--%>
                        <%--</ul>--%>
                    </li>
                    <li><a href="${ctx}/logout" title="<m:message bundle="common" key="common.logout.offline"/>"><m:message bundle="common" key="common.logout"/></a></li>
                    <li>&nbsp;</li>
                </ul>
            </div>
            <!--/.nav-collapse -->
        </div>
    </div>
    <div class="container-fluid">
        <div id="content" class="row-fluid">
            <div id="left">
                <iframe id="menuFrame" name="menuFrame" src="${ctx}/system/menu/tree?parentId=${firstMenuId}"
                        style="overflow:visible;"
                        scrolling="yes" frameborder="no" width="100%" ></iframe>
            </div>
            <div id="openClose" class="close">&nbsp;</div>
            <div id="right">
                <ul id="div_tab" class="nav nav-tabs">
                    <li class="active"><a href="javascript:;" onclick="showMainFrame()" data-toggle="tab"><m:message bundle="common" key="common.home"/></a></li>
                </ul>
                <div id="div_panel">
                    <iframe id="mainFrame" name="mainFrame" src="${ctx}/system/user/home" style="overflow:visible;"
                            scrolling="yes" frameborder="no" width="100%" height="650">
                            </iframe>
                </div>
            </div>
        </div>
        <div id="footer" class="row-fluid">
            Copyright &copy; 2014-${fns:getConfig('copyrightYear')} ${fns:getConfig('productName')} - Powered By <a
                href="http://www.mokylin.com/" target="_blank">Mokylin</a> ${fns:getConfig('version')}
        </div>
    </div>
</div>
<script type="text/javascript">
    var leftWidth = "160"; // 左侧窗口大小
    function wSize() {
        var minHeight = 500, minWidth = 980;
        var strs = getWindowSize().toString().split(",");
        $("#menuFrame, #openClose").height((strs[0] < minHeight ? minHeight : strs[0]) - $("#header").height() - $("#footer").height() - 32);
//        $("#menuFrame, #mainFrame, #openClose").height((strs[0] < minHeight ? minHeight : strs[0]) - $("#header").height() - $("#footer").height() - 32);
        $("#openClose").height($("#openClose").height() - 5);
        $("#div_panel").height($("#openClose").height()-$("#div_tab").height());
        if (strs[1] < minWidth) {
            $("#main").css("width", minWidth - 10);
            $("html,body").css({"overflow": "auto", "overflow-x": "auto", "overflow-y": "auto"});
        } else {
            $("#main").css("width", "auto");
            $("html,body").css({"overflow": "hidden", "overflow-x": "hidden", "overflow-y": "hidden"});
        }
        $("#right").width($("#content").width() - $("#left").width() - $("#openClose").width() - 5);
    }
</script>
<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>