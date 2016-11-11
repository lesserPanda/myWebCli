package net.tuxun.customer.module.myWeb.service;

import java.util.List;

import net.tuxun.core.base.service.IbaseService;
import net.tuxun.customer.module.myWeb.bean.CmsComment;
/**
 * 
 * @author pand
 *
 */
public interface ICmsCommentService extends IbaseService{
  
  void removeByPrimaryTableId(String primaryTableId);

  List<CmsComment> listByPrimaryTableId(String primaryTableId);
  
}
