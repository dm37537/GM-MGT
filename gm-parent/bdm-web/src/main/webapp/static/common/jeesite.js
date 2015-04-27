/*!
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

// 引入js和css文件
function include(id, path, file) {
    if (document.getElementById(id) == null) {
        var files = typeof file == "string" ? [file] : file;
        for (var i = 0; i < files.length; i++) {
            var name = files[i].replace(/^\s|\s$/g, "");
            var att = name.split('.');
            var ext = att[att.length - 1].toLowerCase();
            var isCSS = ext == "css";
            var tag = isCSS ? "link" : "script";
            var attr = isCSS ? " type='text/css' rel='stylesheet' " : " type='text/javascript' ";
            var link = (isCSS ? "href" : "src") + "='" + path + name + "'";
            document.write("<" + tag + (i == 0 ? " id=" + id : "") + attr + link + "></" + tag + ">");
        }
    }
}

// 打开一个窗体
function windowOpen(url, name, width, height) {
    var top = parseInt((window.screen.height - height) / 2, 10), left = parseInt((window.screen.width - width) / 2, 10),
        options = "location=no,menubar=no,toolbar=no,dependent=yes,minimizable=no,modal=yes,alwaysRaised=yes," +
            "resizable=yes,scrollbars=yes," + "width=" + width + ",height=" + height + ",top=" + top + ",left=" + left;
    window.open(url, name, options);
}

// 显示加载框
function loading(mess) {
    top.$.jBox.tip.mess = null;
    top.$.jBox.tip(mess, 'loading', {opacity: 0});
}

// 确认对话框
function confirmx(mess, href) {
    top.$.jBox.confirm(mess, '系统提示', function (v, h, f) {
        if (v == 'ok') {
            loading('正在提交，请稍等...');
            location = href;
        }
    }, {buttonsFocus: 1});
    top.$('.jbox-body .jbox-icon').css('top', '55px');
    return false;
}
function confirmxS(mess) {
    top.$.jBox.confirm(mess, '系统提示', function (v, h, f) {
        if (v == 'ok') {
            loading('正在提交，请稍等...');
            return true;
        }
    }, {buttonsFocus: 1});
    top.$('.jbox-body .jbox-icon').css('top', '55px');
    return false;
}
function createDivParent(tabId, tabUrl, tabName,isNew){
    //如果当前tabId存在直接显示已经打开的tab
    if(window.parent.document.getElementById("div_" + tabId) == null){
        if(!isNew)
            return false;
        // 创建iframe
        var box = window.parent.document.createElement("iframe");
        box.id = "div_" + tabId;
        box.src = tabUrl;
        box.width = "100%";
        box.height = "100%";
        box.frameBorder = "no";
        box.iframeScrolling = "yes";
        window.parent.document.getElementById("div_panel").appendChild(box);

        //遍历并清除开始存在的tab当前效果并隐藏其显示的div
        var tabList = window.parent.document.getElementById("div_tab").getElementsByTagName('li');
        var panellist = window.parent.document.getElementById("div_panel").getElementsByTagName('iframe');
        if (tabList.length > 0)
        {
            for (i = 0; i < tabList.length; i++)
            {
                tabList[i].className = "";
                panellist[i].style.display = "none";
            }
        }

        //创建li菜单
        var tab = window.parent.document.createElement("li");
        tab.className = "active";
        tab.id = tabId;
        var litxt = "<a href=\"javascript:;\" onclick=\"javascript:createDivThis('" + tabId + "','" + tabUrl + "','" + tabName + "',false)\" data-toggle=\""+tabName+"\" >" + tabName + "<i class=\"menu icon-remove pull-right\" style=\"cursor:pointer\" title=\"close\" onclick=\"removeDiv('" + tabId + "')\"></i></a>";
        tab.innerHTML = litxt;
        window.parent.document.getElementById("div_tab").appendChild(tab);
    }
    else{
        var tabList = window.parent.document.getElementById("div_tab").getElementsByTagName('li');
        var panelList = window.parent.document.getElementById("div_panel").getElementsByTagName('iframe');
        for (i = 0; i < tabList.length; i++)
        {
            tabList[i].className = "";
            panelList[i].style.display = "none";
        }
        window.parent.document.getElementById(tabId).className = 'active';
        window.parent.document.getElementById("div_" + tabId).style.display = 'block';
    }
    return false;
}

function createDivThis(tabId, tabUrl, tabName, isNew){
    //如果当前tabId存在直接显示已经打开的tab
    if(document.getElementById("div_" + tabId) == null){
        if(!isNew)
            return false;
        // 创建iframe
        var box = document.createElement("iframe");
        box.id = "div_" + tabId;
        box.src = tabUrl;
        box.width = "100%";
        box.height = "100%";
        box.frameBorder = "no";
        box.iframeScrolling = "yes";
        document.getElementById("div_panel").appendChild(box);

        //遍历并清除开始存在的tab当前效果并隐藏其显示的div
        var tabList = document.getElementById("div_tab").getElementsByTagName('li');
        var panellist = document.getElementById("div_panel").getElementsByTagName('iframe');
        if (tabList.length > 0)
        {
            for (i = 0; i < tabList.length; i++)
            {
                tabList[i].className = "";
                panellist[i].style.display = "none";
            }
        }

        //创建li菜单
        var tab = document.createElement("li");
        tab.className = "active";
        tab.id = tabId;
        var litxt = "<a href=\"javascript:;\" onclick=\"javascript:createDivThis('" + tabId + "','" + tabUrl + "','" + tabName + "',false)\" data-toggle=\""+tabName+"\" >" + tabName + "<i class=\"menu icon-remove pull-right\" style=\"cursor:pointer\" title=\"close\" onclick=\"removeDiv('" + tabId + "')\"></i></a>";
        tab.innerHTML = litxt;
        document.getElementById("div_tab").appendChild(tab);
    }
    else{
        var tabList = document.getElementById("div_tab").getElementsByTagName('li');
        var panelList = document.getElementById("div_panel").getElementsByTagName('iframe');
        for (i = 0; i < tabList.length; i++)
        {
            tabList[i].className = "";
            panelList[i].style.display = "none";
        }
        document.getElementById(tabId).className = 'active';
        document.getElementById("div_" + tabId).style.display = 'block';
    }
    return false;
}

$(document).ready(function () {
    //所有下拉框使用select2
    $("select").select2();
    $('.fancybox').fancybox();
});