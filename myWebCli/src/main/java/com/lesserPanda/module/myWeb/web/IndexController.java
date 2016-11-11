package com.lesserPanda.module.myWeb.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lesserPanda.module.myWeb.bean.Article;
import com.lesserPanda.module.myWeb.bean.MessagePerple;
import com.lesserPanda.module.myWeb.bean.MusicInfo;
import com.lesserPanda.module.myWeb.bean.SkillInfo;
import com.lesserPanda.module.myWeb.service.IArticleService;
import com.lesserPanda.module.myWeb.service.IMessagePerpleService;
import com.lesserPanda.module.myWeb.service.IMusicInfoService;
import com.lesserPanda.module.myWeb.service.ISkillInfoService;

import net.tuxun.core.mybatis.page.PageNav;
import net.tuxun.core.mybatis.page.PageQuery;

@Controller
@RequestMapping("/index")
public class IndexController {

	//文章表服务
	 @Autowired
	 IArticleService iArticleService;
	 //音乐服务
	 @Autowired
	 IMusicInfoService iMusicInfoService;
	 //留言服务
	 @Autowired
	 IMessagePerpleService iMessagePerpleService;
	 //技能展示（报表）
	 @Autowired
	 ISkillInfoService iSkillInfoService;
	 
	 @RequestMapping("toFengMian")
	 public String toFengMian(){
		 return "myWeb/fengmian";
	 }
	 
	 /**
	  * 各个模块详细列表
	  * @param categoryId
	  * @param query
	  * @param model
	  * @return
	  */
	 @RequestMapping("/toList")
	 public String toList(String categoryId, PageQuery query, Model model){
		 query.search("categoryId", categoryId);
		 query.order("createDate", "desc");
		 PageNav<Article> pageNav = iArticleService.pageResult(query);
		 model.addAttribute("pageNav", pageNav);
		 return "myWeb/list";
	 }
	
	/**
	 * 微博首页
	 * @param query
	 * @param model
	 * @return
	 */
	@RequestMapping("/toIndex")
	public String toIndex(PageQuery query,Model model){
		query.search("categoryId", "001");
		query.order("createDate", "desc");
		PageNav<Article> pageNav = iArticleService.pageResult(query);
		model.addAttribute("pageNav", pageNav);
		return "myWeb/index";
	}
	
	/**
	 * 获取生活琐事
	 * @param query
	 * @param model
	 * @return
	 */
	@RequestMapping("/getSHSH")
	@ResponseBody
	public List<Article> getSHSH(PageQuery query,Model model){
		query.search("categoryId", "001");
		query.order("createDate", "desc");
		PageNav<Article> pageNav = iArticleService.pageResult(query);
		return pageNav.getList();
	}
	
	
	/**
	 * 加载右半边公共部分，
	 * @param query
	 * @param model
	 * @return
	 */
	@RequestMapping("/toRightModel")
	public String toRightModil(PageQuery query, Model model){
		return "myWeb/rightModel";
	}
	
	/**
	 * 加载音乐
	 * @param query
	 * @param model
	 * @return
	 */
	@RequestMapping("/toMusic")
	public String toMusic(PageQuery query, Model model){
		PageNav<MusicInfo> pageNav = iMusicInfoService.pageResult(query);
		model.addAttribute("pageNav", pageNav);
		return "myWeb/music/music";
	}
	
	/**
	 * 弹出留言板
	 * @return
	 */
	@RequestMapping("/toLiveMessage")
	public String toLiveMessage(){
		return "myWeb/liveMessage";
	}
	
	/**
	 * 保存留言板信息
	 * @return
	 */
	@RequestMapping("/saveLiveMessage")
	@ResponseBody
	public Map<String, Object> saveLiveMessage(HttpServletRequest request, String messagePerple, String messageEmail, String messageContent, String messageTitle, Model model){
		String ip= request.getRemoteAddr();
		
		MessagePerple messageperple = new MessagePerple();
		messageperple.setCreateTime(new Date());
		messageperple.setMessageContent(messageContent);
		messageperple.setMessageEmail(messageEmail);
		messageperple.setMessagePerple(messagePerple);
		messageperple.setMessageTitle(messageTitle);
		messageperple.setMessagePerpleIp(ip);
	    
		iMessagePerpleService.save(messageperple);
		Map<String, Object> flag = new HashMap<String, Object>();
		flag.put("message", true);
		return flag;
	}
	
	/**
	 * 获取首页介绍
	 * @param categoryId
	 * @param model
	 * @param query
	 * @return
	 */
	@RequestMapping("/getSYJJ")
	@ResponseBody
	public List<Article> getSYJJ(String categoryId, Model model, PageQuery query){
		query.search("categoryId", categoryId);
		query.order("createDate", "desc");
		PageNav<Article> pageNav = iArticleService.pageResult(query);
		for(Article article : pageNav.getList()){
			article.setCmsComments(((Article) iArticleService.get(article.getId())).getCmsComments());
		}
		model.addAttribute("pageNav", pageNav);
		return pageNav.getList();
	}
	
	/**
	 * 获取个人技能文章
	 * @param categoryId
	 * @param model
	 * @param query
	 * @return
	 */
	@RequestMapping("/getGRJN")
	@ResponseBody
	public List<Article> getGRJN(String categoryId, Model model, PageQuery query){
		query.search("categoryId", categoryId);
		query.order("createDate", "desc");
		PageNav<Article> pageNav = iArticleService.pageResult(query);
		for(Article article : pageNav.getList()){
			article.setCmsComments(((Article) iArticleService.get(article.getId())).getCmsComments());
		}
		model.addAttribute("pageNav", pageNav);
		return pageNav.getList();
	}
	
	/**
	 * 获取转载收藏
	 * @param categoryId
	 * @param model
	 * @param query
	 * @return
	 */
	@RequestMapping("/getZZSC")
	@ResponseBody
	public List<Article> getZZSC(String categoryId, Model model, PageQuery query){
		query.search("categoryId", categoryId);
		query.order("createDate", "desc");
		PageNav<Article> pageNav = iArticleService.pageResult(query);
		model.addAttribute("pageNav", pageNav);
		return pageNav.getList();
	}
	
	/**
	 * 点击转载下的（伤不起，感兴趣，喜欢）更新数据
	 * @param id
	 * @param zzFlag
	 * @return
	 */
	@RequestMapping("/updateZZSC")
	@ResponseBody
	public Map<String, Object> updateZZSC(String id, String zzFlag){
		Map<String, Object> map = new HashMap<String, Object>();
		Article article = iArticleService.get(id);

		if("0031".equals(zzFlag)){
			article.setVulnerableNum(article.getVulnerableNum()+1);
		}else if("0032".equals(zzFlag)){
			article.setVulnerableNum(article.getLikeNum()+1);
		}else if("0033".equals(zzFlag)){
			article.setVulnerableNum(article.getInterestedNum()+1);
		}
		
		iArticleService.modifyNotNull(article);
		
		map.put("flag", true);
		return map;
	}
	
	/**
	 * 报表展示获取个人技能
	 * @param model
	 * @param query
	 * @return
	 */
	@RequestMapping("/getSkillInfo")
	@ResponseBody
	public Map<String, Object> getSkillInfo(Model model, PageQuery query){
		PageNav<SkillInfo> pageNav = iSkillInfoService.pageResult(query);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNav", pageNav);
		return map;
	}
	
	//查看页面
	@RequestMapping("/toView")
	public String toView(String id, Model model){
		Article article = iArticleService.get(id);
		article.setHits(article.getHits()+1);
		iArticleService.modifyNotNull(article);
		
		model.addAttribute("bean", article);
		return "myWeb/view";
	}
}
