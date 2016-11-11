package net.tuxun.customer.module.myWeb.controller;

import java.util.Date;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.tuxun.component.admin.bean.UserInfo;
import net.tuxun.core.base.controller.BaseController;
import net.tuxun.core.mybatis.page.PageNav;
import net.tuxun.core.mybatis.page.PageQuery;
import net.tuxun.customer.module.myWeb.bean.MusicInfo;
import net.tuxun.customer.module.myWeb.service.IMusicInfoService;
/**
 * 
 * @author pand
 *
 */
@Controller
@RequestMapping("/MusicInfo/musicInfo")
public class MusicInfoController extends BaseController {
 
  // 音乐信息列表
  @RequiresPermissions("MusicInfo:musicInfo:list")
  @RequestMapping("list.do")
  public String list(PageQuery query,Model model) {
    PageNav<MusicInfo> pageNav = service.pageResult(query);
    model.addAttribute("pageNav", pageNav);
    model.addAttribute("query", query);
    return "MusicInfo/musicInfo/list";
  }
  
  // 查看音乐信息
  @RequiresPermissions("MusicInfo:musicInfo:view")
  @RequestMapping("view.do")
  public String view(String id, Model model) {
    MusicInfo bean = service.get(id);
    model.addAttribute("bean", bean);
    return "MusicInfo/musicInfo/view";
  }
  
  // 添加页面
  @RequiresPermissions("MusicInfo:musicInfo:add")
  @RequestMapping(value = "add.do", method = RequestMethod.GET)
  public String toAdd(String id, Model model) {
    MusicInfo bean = new MusicInfo();
    if(id != null){
      bean = service.get(id);
    }
    model.addAttribute("bean", bean);
    model.addAttribute(OPERATION, ADD);
    return "MusicInfo/musicInfo/form";
  }

  // 添加音乐信息
  @RequiresPermissions("MusicInfo:musicInfo:add")
  @RequestMapping(value = "add.do", method = RequestMethod.POST)
  public String add(MusicInfo bean, String redirect, RedirectAttributes ra, UserInfo user) {
	 bean.setCreateBy(user.getUsername());
	 bean.setCreateDate(new Date());
	 bean.setDelFlag("1");
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
  
  // 音乐信息修改页面
  @RequiresPermissions("MusicInfo:musicInfo:alter")
  @RequestMapping(value = "alter.do", method = RequestMethod.GET)
  public String toAlter(String id, Model model) {
    MusicInfo bean = service.get(id);
    model.addAttribute("bean", bean);
    model.addAttribute(OPERATION, ALTER);
    return "MusicInfo/musicInfo/form";
  }

  // 修改音乐信息
  @RequestMapping(value = "alter.do", method = RequestMethod.POST)
  @RequiresPermissions("MusicInfo:musicInfo:alter")
  public String alter(MusicInfo bean, String redirect, RedirectAttributes ra, UserInfo user) {
	  bean.setUpdateBy(user.getUsername());
	  bean.setUpdateDate(new Date());
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
  
  // 删除音乐信息
  @RequiresPermissions("MusicInfo:musicInfo:remove")
  @RequestMapping(value = "remove.do")
  public String remove(String id, RedirectAttributes ra) {
    service.remove(id);
    ra.addFlashAttribute(MESSAGE, REMOVE_SUCCESS);
    return "redirect:list.do";
  }
  
  @Autowired
  IMusicInfoService service;
}
