package net.tuxun.customer.module.myWeb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.tuxun.core.base.dao.IBaseDao;
import net.tuxun.core.base.service.AbstractBaseService;
import net.tuxun.customer.module.myWeb.bean.CmsComment;
import net.tuxun.customer.module.myWeb.dao.ICmsCommentDao;
import net.tuxun.customer.module.myWeb.service.ICmsCommentService;
/**
 * 
 * @author pand
 *
 */
@Service
public class CmsCommentServiceImpl extends AbstractBaseService implements ICmsCommentService {
  @Autowired
  ICmsCommentDao dao;

  @Override
  public IBaseDao getDao() {
    return dao;
  } 
  
  @Override
  @Transactional
  public void removeByPrimaryTableId(String primaryTableId) {
    dao.deleteByPrimaryTableId(primaryTableId);
  }

  @Override
  public List<CmsComment> listByPrimaryTableId(String primaryTableId) {
    return dao.listByPrimaryTableId(primaryTableId);
  } 
  
}
