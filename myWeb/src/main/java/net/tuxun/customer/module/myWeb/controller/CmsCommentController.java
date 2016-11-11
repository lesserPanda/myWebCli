package net.tuxun.customer.module.myWeb.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.tuxun.core.base.controller.BaseController;
import net.tuxun.customer.module.myWeb.bean.CmsComment;
import net.tuxun.customer.module.myWeb.service.ICmsCommentService;
/**
 * 
 * @author pand
 *
 */
@Controller
@RequestMapping("/Article/cmsComment")
public class CmsCommentController extends BaseController {
 
  // 列表
  @RequiresPermissions("Article:cmsComment:list")
  @RequestMapping("list.do")
  @ResponseBody
  public List<CmsComment> list(String primaryTableId) {
    return service.listByPrimaryTableId(primaryTableId);
  }
  
  // 查看
  @RequiresPermissions("Article:cmsComment:view")
  @RequestMapping("view.do")
  @ResponseBody
  public CmsComment view(String id) {
    return service.get(id);
  }
  
  // 添加
  @RequiresPermissions("Article:cmsComment:add")
  @RequestMapping(value = "add.do")
  @ResponseBody
  public Map<String, Boolean> add(CmsComment bean) {
    service.save(bean);
    return status(true);
  }


  // 修改
  @RequestMapping(value = "alter.do")
  @RequiresPermissions("Article:cmsComment:alter")
  @ResponseBody
  public Map<String, Boolean> alter(CmsComment bean) {
    service.modify(bean);
    return status(true);
  }
  
  // 删除
  @RequiresPermissions("Article:cmsComment:remove")
  @RequestMapping(value = "remove.do")
  @ResponseBody
  public Map<String, Boolean> remove(String id) {
    service.remove(id);
    return status(true);
  }
  
  @Autowired
  ICmsCommentService service;
}
