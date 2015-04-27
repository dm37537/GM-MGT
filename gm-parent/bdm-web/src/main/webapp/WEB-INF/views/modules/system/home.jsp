<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title><m:message bundle="common" key="common.home"/></title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            $(".control-group a").click(function(){
                $(".nav li").removeClass("active");
                $(".nav li i").removeClass("icon-white");
                $(this).parent().addClass("active");
                $(this).children("i").addClass("icon-white");
            });

            $("#inputForm").validate({
                submitHandler: function(form){
                    loading('<m:message bundle="common" key="common.commiting"/>...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function(error, element) {
                    $("#messageBox").text("<m:message bundle="common" key="common.inputerror.pleaseconfirm"/>");
                    if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
    </script>
</head>
<body>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span10">
                <div class="row-fluid">
                    <c:set var="menuList" value="${fns:getMenuList()}"/>
                    <c:forEach items="${menuList}" var="menu" varStatus="idxStatus">
                        <c:if test="${menu.parent.id eq '1' && menu.show}">
                            <div class="span2">
                                <ul class="breadcrumb">
                                    <li><m:message bundle="common" key="${menu.name}"/></li>
                                </ul>
                                <c:forEach items="${menuList}" var="menuChild">
                                    <c:if test="${menuChild.parent.id eq  menu.id && menuChild.show }">
                                        <div class="control-group">
                                            <label class="control-label"><m:message bundle="common" key="${menuChild.name}"/></label>
                                            <ul class="nav nav-list">
                                                <c:forEach items="${menuList}" var="menuLeaf">
                                                    <c:if test="${menuLeaf.parent.id eq menuChild.id && menuLeaf.show}">
                                                        <li><a href="#" onclick="createDivParent('tab${menuLeaf.id}','${fn:indexOf(menuLeaf.href, '://') eq -1?ctx:''}${not empty menuLeaf.href?menuLeaf.href:'/404'}','<m:message bundle="common" key="${menuLeaf.name}"/>',true)">&nbsp;&nbsp;<i class="icon-${not empty menuLeaf.icon?menuLeaf.icon:'circle-arrow-right'}"></i>&nbsp;<m:message bundle="common" key="${menuLeaf.name}"/></a></li>
                                                    </c:if>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
            <div class="span2">
                <ul class="breadcrumb">
                    <li><a><m:message bundle="common" key="common.notice"/></a></li>
                </ul>
                <div>
                    暂无
                </div>
            </div>
        </div>
    </div>
</body>
</html>
