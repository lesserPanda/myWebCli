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
import net.tuxun.customer.module.myWeb.bean.MessagePerple;
import net.tuxun.customer.module.myWeb.service.IMessagePerpleService;
/**
 * 
 * @author pand
 *
 */
@Controller
@RequestMapping("/MessagePerple/messagePerple")
public class MessagePerpleController extends BaseController {
 
  // 留言板列表
  @RequiresPermissions("MessagePerple:messagePerple:list")
  @RequestMapping("list.do")
  public String list(PageQuery query,Model model) {
    PageNav<MessagePerple> pageNav = service.pageResult(query);
    model.addAttribute("pageNav", pageNav);
    model.addAttribute("query", query);
    return "MessagePerple/messagePerple/list";
  }
  
  // 查看留言板
  @RequiresPermissions("MessagePerple:messagePerple:view")
  @RequestMapping("view.do")
  public String view(String id, Model model) {
    MessagePerple bean = service.get(id);
    model.addAttribute("bean", bean);
    return "MessagePerple/messagePerple/view";
  }
  
  // 添加页面
  @RequiresPermissions("MessagePerple:messagePerple:add")
  @RequestMapping(value = "add.do", method = RequestMethod.GET)
  public String toAdd(String id, Model model) {
    MessagePerple bean = new MessagePerple();
    if(id != null){
      bean = service.get(id);
    }
    model.addAttribute("bean", bean);
    model.addAttribute(OPERATION, ADD);
    return "MessagePerple/messagePerple/form";
  }

  // 添加留言板
  @RequiresPermissions("MessagePerple:messagePerple:add")
  @RequestMapping(value = "add.do", method = RequestMethod.POST)
  public String add(MessagePerple bean, String redirect, RedirectAttributes ra) {
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
  
  // 留言板修改页面
  @RequiresPermissions("MessagePerple:messagePerple:alter")
  @RequestMapping(value = "alter.do", method = RequestMethod.GET)
  public String toAlter(String id, Model model) {
    MessagePerple bean = service.get(id);
    model.addAttribute("bean", bean);
    model.addAttribute(OPERATION, ALTER);
    return "MessagePerple/messagePerple/form";
  }

  // 修改留言板
  @RequestMapping(value = "alter.do", method = RequestMethod.POST)
  @RequiresPermissions("MessagePerple:messagePerple:alter")
  public String alter(MessagePerple bean, String redirect, RedirectAttributes ra) {
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
  
  // 删除留言板
  @RequiresPermissions("MessagePerple:messagePerple:remove")
  @RequestMapping(value = "remove.do")
  public String remove(String id, RedirectAttributes ra) {
    service.remove(id);
    ra.addFlashAttribute(MESSAGE, REMOVE_SUCCESS);
    return "redirect:list.do";
  }
  
  @Autowired
  IMessagePerpleService service;
}
