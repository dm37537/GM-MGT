package com.mokylin.gm.modules.system.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mokylin.gm.config.Global;
import com.mokylin.gm.entity.MessageType;
import com.mokylin.gm.entity.ResultMsg;
import com.mokylin.gm.modules.system.entity.Dict;
import com.mokylin.gm.modules.system.service.DictService;
import com.mokylin.gm.utils.Page;
import com.mokylin.gm.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
/**
 * 字典Controller
 */
@Controller
@RequiresUser
@RequestMapping(value = "/system/dict")
public class DictController extends BaseController {

	@Resource
	private DictService dictService;

    @RequiresPermissions(value = "dictmanage")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute(value = "dict")Dict dict, HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("typeList", dictService.findTypeList());
        Page<Dict> page = dictService.find(new Page<Dict>(request, response), dict);
        model.addAttribute("page", page);
		return "modules/system/dict/dictList";
	}

    @RequiresPermissions(value = "dictmanage")
	@RequestMapping(value = "form")
	public String form(Dict dict, Model model) {
        if(dict.getId() != 0)
            dict = dictService.getDict(dict.getId());
		model.addAttribute("dict", dict);
		return "modules/system/dict/dictForm";
	}

    @RequiresPermissions(value = "dictmanage")
	@RequestMapping(value = "save")
	public String save(Dict dict, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, dict)){
			return form(dict, model);
		}
		if(dictService.update(dict))
		    addMessage(redirectAttributes, new ResultMsg(1, MessageType.SUCCESS, "保存字典'" + dict.getLabel() + "'成功"));
        else
            addMessage(redirectAttributes, new ResultMsg(1, MessageType.SUCCESS, "保存字典'" + dict.getLabel() + "'失败"));
		return "redirect:"+ Global.getAdminPath()+"/system/dict/?repage&type="+dict.getType();
	}

    @RequiresPermissions(value = "dictmanage")
	@RequestMapping(value = "delete")
	public String delete(int id, RedirectAttributes redirectAttributes) {
		if(dictService.delete(id))
		    addMessage(redirectAttributes, new ResultMsg(1, MessageType.SUCCESS, "删除字典成功"));
        else
            addMessage(redirectAttributes, new ResultMsg(0, MessageType.ERROR, "删除字典失败"));
		return "redirect:"+ Global.getAdminPath()+"/system/dict/?repage";
	}

}
