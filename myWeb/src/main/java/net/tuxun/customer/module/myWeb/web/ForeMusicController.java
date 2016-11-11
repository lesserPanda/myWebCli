package net.tuxun.customer.module.myWeb.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.tuxun.core.mybatis.page.PageNav;
import net.tuxun.core.mybatis.page.PageQuery;
import net.tuxun.customer.module.myWeb.bean.MusicInfo;
import net.tuxun.customer.module.myWeb.service.IMusicInfoService;

@Controller
@RequestMapping("/music")
public class ForeMusicController {

	//音乐服务
	 @Autowired
	 IMusicInfoService iMusicInfoService;
	 
	 @RequestMapping("/getMusic")
	 @ResponseBody
	 public List<MusicInfo> getMusic(PageQuery query, Model model){
		 PageNav<MusicInfo> pageNav = iMusicInfoService.pageResult(query);
		return pageNav.getList(); 
	 }
}
