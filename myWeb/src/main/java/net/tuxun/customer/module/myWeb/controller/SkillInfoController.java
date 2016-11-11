package net.tuxun.customer.module.myWeb.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.tuxun.core.base.controller.BaseController;
import net.tuxun.core.mybatis.page.PageNav;
import net.tuxun.core.mybatis.page.PageQuery;
import net.tuxun.customer.module.myWeb.bean.SkillInfo;
import net.tuxun.customer.module.myWeb.service.ISkillInfoService;
/**
 * 
 * @author pand
 *
 */
@Controller
@RequestMapping("/SkillInfo/skillInfo")
public class SkillInfoController extends BaseController {
 
  // 技能表列表
  @RequiresPermissions("SkillInfo:skillInfo:list")
  @RequestMapping("list.do")
  public String list(PageQuery query,Model model) {
    PageNav<SkillInfo> pageNav = service.pageResult(query);
    model.addAttribute("pageNav", pageNav);
    model.addAttribute("query", query);
    return "SkillInfo/skillInfo/list";
  }
  
  // 查看技能表
  @RequiresPermissions("SkillInfo:skillInfo:view")
  @RequestMapping("view.do")
  public String view(String id, Model model) {
    SkillInfo bean = service.get(id);
    model.addAttribute("bean", bean);
    return "SkillInfo/skillInfo/view";
  }
  
  // 添加页面
  @RequiresPermissions("SkillInfo:skillInfo:add")
  @RequestMapping(value = "add.do", method = RequestMethod.GET)
  public String toAdd(String id, Model model) {
    SkillInfo bean = new SkillInfo();
    if(id != null){
      bean = service.get(id);
    }
    model.addAttribute("bean", bean);
    model.addAttribute(OPERATION, ADD);
    return "SkillInfo/skillInfo/form";
  }

  // 添加技能表
  @RequiresPermissions("SkillInfo:skillInfo:add")
  @RequestMapping(value = "add.do", method = RequestMethod.POST)
  public String add(SkillInfo bean, String redirect, RedirectAttributes ra) {
    service.save(bean);
    ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
    if(redirect.equals(ALTER)){
      return "redirect:alter.do?id=" + bean.getId();
    }else if(redirect.equals(ADD)){
      return "redirect:add.do";
    }else{      
      return "redirect:list.do";
    }
  }
  
  // 技能表修改页面
  @RequiresPermissions("SkillInfo:skillInfo:alter")
  @RequestMapping(value = "alter.do", method = RequestMethod.GET)
  public String toAlter(String id, Model model) {
    SkillInfo bean = service.get(id);
    model.addAttribute("bean", bean);
    model.addAttribute(OPERATION, ALTER);
    return "SkillInfo/skillInfo/form";
  }

  // 修改技能表
  @RequestMapping(value = "alter.do", method = RequestMethod.POST)
  @RequiresPermissions("SkillInfo:skillInfo:alter")
  public String alter(SkillInfo bean, String redirect, RedirectAttributes ra) {
    service.modifyNotNull(bean);
    ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
    if(redirect.equals(ALTER)){
      return "redirect:alter.do?id=" + bean.getId();
    }else if(redirect.equals(ADD)){
      return "redirect:add.do";
    }else{      
      return "redirect:list.do";
    }
  }
  
  // 删除技能表
  @RequiresPermissions("SkillInfo:skillInfo:remove")
  @RequestMapping(value = "remove.do")
  public String remove(String id, RedirectAttributes ra) {
    service.remove(id);
    ra.addFlashAttribute(MESSAGE, REMOVE_SUCCESS);
    return "redirect:list.do";
  }
  
  @Autowired
  ISkillInfoService service;
}
