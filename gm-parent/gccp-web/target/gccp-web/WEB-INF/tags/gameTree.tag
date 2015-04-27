<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/treeview.jsp"%>
<%@ attribute name="showStatus" type="java.lang.String" required="false" description="显示对应状态节点,多个,分隔" %>
<style type="text/css">
    .accordion-inner{padding:2px;}
</style>
<div id="treeContainer" class="accordion-group" style="height: 100%">
    <div class="accordion-heading">
        <a class="accordion-toggle"><m:message bundle="common" key="common.gamezone"/></a>
    </div>
    <div class="accordion-body">
        <div class="accordion-inner">
            <div id="search" class="hide" style="z-index:10;">
                <input class="empty" type="text" id="gameZoneName" name="gameZoneName" maxlength="30" placeholder="<m:message bundle="common" key="common.search"/>" style="margin-bottom: 0px;">
            </div>
            <div id="gameTree" class="ztree" style="height: 100%"></div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var key, lastValue = "", nodeList = [];
    var tree;

    key = $("#gameZoneName");
    key.bind("focus", focusKey).bind("blur", blurKey).bind("change keydown cut input propertychange", searchNode);

    var setting = {
        view:{
            selectedMulti:true,
            showLine:false,
            showIcon:false,
            fontCss: setFontCss
        },
        data:{
            simpleData:{enable:true}
        },
        check:{
            enable: true,
            chkStyle: "checkbox",
            chkboxType: { "Y": "ps", "N": "ps" },
            autoCheckTrigger: true
        },
        callback:{
            beforeClick: beforeClickLabel,
            onClick: onClickLabel
        }
    };

    function focusKey(e) {
        if (key.hasClass("empty")) {
            key.removeClass("empty");
        }
    }
    function blurKey(e) {
        if (key.get(0).value === "") {
            key.addClass("empty");
        }
        searchNode(e);
    }
    function searchNode(e) {
        // 取得输入的关键字的值
        var value = $.trim(key.get(0).value);

        // 按名字查询
        var keyType = "name";
        if (key.hasClass("empty")) {
            value = "";
        }

        // 如果和上次一次，就退出不查了。
        if (lastValue === value) {
            return;
        }

        // 保存最后一次
        lastValue = value;

        // 如果要查空字串，就退出不查了。
        if (value === "") {
            return;
        }
        updateNodes(false);
        nodeList = tree.getNodesByParamFuzzy(keyType, value, null);
        updateNodes(true);
    }
    function updateNodes(highlight) {
        for(var i=0, l=nodeList.length; i<l; i++) {
            nodeList[i].highlight = highlight;
            tree.updateNode(nodeList[i]);
            tree.expandNode(nodeList[i].getParentNode(), true, false, false);
        }
    }

    // 设置颜色
    function setFontCss(treeId, treeNode){
        var fontCss = {};
        if(treeNode.status == 0){ //未开服
            fontCss = {color:"red"};
        }
        else if(treeNode.status == 1){ //正常
            fontCss = {};
        }
        else if(treeNode.status == 2){ //合服
            fontCss = {color:"gray"};
        }
        else if(treeNode.status == 3){
            fontCss = {color:"green"};
        }
        fontCss.fontWeight = (!!treeNode.highlight) ? "bold":"normal";
        return fontCss;
    }

    function beforeClickLabel(treeId, treeNode, clickFlag){
        return (treeNode.isParent != true);
    }

    function onClickLabel(event, treeId, treeNode, clickFlag) {
        var zTree = $.fn.zTree.getZTreeObj("gameTree");
        //先取消勾选
        zTree.checkAllNodes(false);
        //勾选对应节点
        zTree.checkNode(treeNode, true, true, false);

        $("#treeContainer").trigger("hello");
    }

    // 数据内容
    var zNodes = [
            <c:forEach items="${fns:getGameZoneList(showStatus)}" var="gameZone">
                {id:'${gameZone.id}', pId:'${gameZone.parentId}', name:"${gameZone.name}", status:'${gameZone.status}'},
            </c:forEach>
    ];

    // 初始化树结构
    tree = $.fn.zTree.init($("#gameTree"), setting, zNodes);
    $(".ztree").css({"overflow":"auto","overflow-x":"auto","overflow-y":"auto"});
    // 默认展开一级节点
    var nodes = tree.getNodesByParam("level", 1);
    for(var i=0; i<nodes.length; i++) {
        tree.expandNode(nodes[i], true, false, false);
    }

    $(".accordion-toggle").click(function(){
        $("#search").slideToggle();
        $("#gameZoneName").focus();
        //重新设置树的高度
        if($("#search").is(":hidden")){
            $(".accordion-inner").height($(".accordion-inner").height() + 30);
        }else{
            $(".accordion-inner").height($(".accordion-inner").height() - 30);
        }
    });

    function wGameTreeSize(){
        $(".accordion-inner").width($("#treeContainer").width()-8).height($("#treeContainer").height()-47);
        $(".ztree").width($("#treeContainer").width()-8).height($("#treeContainer").height()-77);
    }
</script>