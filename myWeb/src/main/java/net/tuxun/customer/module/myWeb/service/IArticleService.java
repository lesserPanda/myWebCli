package net.tuxun.customer.module.myWeb.service;

import net.tuxun.core.base.service.IbaseService;
import net.tuxun.customer.module.myWeb.bean.Article;
/**
 * 
 * @author pand
 *
 */
public interface IArticleService extends IbaseService{
  
  void saveFull(Article bean);

  Article getFull(String id);

  void modifyFull(Article bean);

  void removeFull(String id);
  
}
