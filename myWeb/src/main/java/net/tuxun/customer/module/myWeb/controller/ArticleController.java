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
import net.tuxun.customer.module.myWeb.bean.Article;
import net.tuxun.customer.module.myWeb.service.IArticleService;

@Controller
@RequestMapping("/Article/article")
public class ArticleController extends BaseController {
 
  // 文章列表
  @RequiresPermissions("Article:article:list")
  @RequestMapping("list.do")
  public String list(PageQuery query,Model model) {
    PageNav<Article> pageNav = service.pageResult(query);
    model.addAttribute("pageNav", pageNav);
    model.addAttribute("query", query);
    return "myWeb/myweb/list";
  }
  
  // 查看文章
  @RequiresPermissions("Article:article:view")
  @RequestMapping("view.do")
  public String view(String id, Model model) {
    Article bean = service.get(id);
    model.addAttribute("bean", bean);
    return "myWeb/myweb/view";
  }
  
  // 添加页面
  @RequiresPermissions("Article:article:add")
  @RequestMapping(value = "add.do", method = RequestMethod.GET)
  public String toAdd(String id, Model model) {
    Article bean = new Article();
    if(id != null){
      bean = service.get(id);
    }
    model.addAttribute("bean", bean);
    model.addAttribute(OPERATION, ADD);
    return "myWeb/myweb/form";
  }

  // 添加文章
  @RequiresPermissions("Article:article:add")
  @RequestMapping(value = "add.do", method = RequestMethod.POST)
  public String add(Article bean, String redirect, RedirectAttributes ra, UserInfo user) {
	bean.setCreateBy(user.getUsername());
	bean.setCreateDate(new Date());
	bean.setHits(0 );
	bean.setVulnerableNum(0);
	bean.setLikeNum(0);
	bean.setInterestedNum(0);
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
  
  // 文章修改页面
  @RequiresPermissions("Article:article:alter")
  @RequestMapping(value = "alter.do", method = RequestMethod.GET)
  public String toAlter(String id, Model model) {
    Article bean = service.get(id);
    model.addAttribute("bean", bean);
    model.addAttribute(OPERATION, ALTER);
    return "myWeb/myweb/form";
  }

  // 修改文章
  @RequestMapping(value = "alter.do", method = RequestMethod.POST)
  @RequiresPermissions("Article:article:alter")
  public String alter(Article bean, String redirect, RedirectAttributes ra) {
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
  
  // 删除文章
  @RequiresPermissions("Article:article:remove")
  @RequestMapping(value = "remove.do")
  public String remove(String id, RedirectAttributes ra) {
    service.removeFull(id);
    ra.addFlashAttribute(MESSAGE, REMOVE_SUCCESS);
    return "redirect:list.do";
  }
  
  @Autowired
  IArticleService service;
}
