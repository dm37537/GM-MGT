<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ attribute name="message" type="com.mokylin.gm.entity.ResultMsg" description="返回信息" %>
<script type="text/javascript">top.$.jBox.closeTip();</script>
<c:if test="${not empty message}">
    <c:if test="${not empty message.type}">
        <c:set var="ctype" value="${message.type.type}"/>
    </c:if>
    <c:if test="${empty  message.type}">
        <c:set var="ctype" value="${message.status eq 1?'success':'error'}"/>
    </c:if>
    <div id="messageBox" class="alert alert-${ctype} hide">
        <button data-dismiss="alert" class="close">×</button>
            ${message.msg}</div>
    <script type="text/javascript">
        if (!top.$.jBox.tip.mess) {
            top.$.jBox.tip.mess = 1;
            top.$.jBox.tip("${message.msg}", "${ctype}", {persistent: true, opacity: 0});
            $("#messageBox").show();
        }
    </script>
</c:if>