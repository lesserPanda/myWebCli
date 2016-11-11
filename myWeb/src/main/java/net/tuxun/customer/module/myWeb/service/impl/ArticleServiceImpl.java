package net.tuxun.customer.module.myWeb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.tuxun.core.base.dao.IBaseDao;
import net.tuxun.core.base.service.AbstractBaseService;
import net.tuxun.customer.module.myWeb.bean.Article;
import net.tuxun.customer.module.myWeb.bean.CmsComment;
import net.tuxun.customer.module.myWeb.dao.IArticleDao;
import net.tuxun.customer.module.myWeb.service.IArticleService;
import net.tuxun.customer.module.myWeb.service.ICmsCommentService;
/**
 * 
 * @author pand
 *
 */
@Service
public class ArticleServiceImpl extends AbstractBaseService implements IArticleService {
  @Autowired
  IArticleDao dao;
  @Autowired
  ICmsCommentService cmsCommentService;
  @Override
  public IBaseDao getDao() {
    return dao;
  } 
  
  @Override
  public Article getFull(String id) {
    return dao.selectFull(id);
  }

  @Override
  @Transactional
  public void saveFull(Article bean) {
    this.save(bean);
    
    List<CmsComment> cmsComments = bean.getCmsComments();
    if(cmsComments != null && !cmsComments.isEmpty()){
      for (CmsComment cmsComment : cmsComments) {
        cmsComment.setArticleId(bean.getId());
        cmsCommentService.save(cmsComment);
      }
    }
  }

  @Override
  @Transactional
  public void modifyFull(Article bean) {
    this.modifyNotNull(bean);
    
    List<CmsComment> cmsComments = bean.getCmsComments();
    if(cmsComments != null && !cmsComments.isEmpty()){
      for (CmsComment cmsComment : cmsComments) {
        cmsComment.setArticleId(bean.getId());
        cmsCommentService.saveOrModify(cmsComment);
      }
    }
  }

  @Override
  @Transactional
  public void removeFull(String id) {
    this.remove(id);
    for (String primaryTableId  : id.split(",")) {
      cmsCommentService.removeByPrimaryTableId(primaryTableId);
    }
  }
  
}
