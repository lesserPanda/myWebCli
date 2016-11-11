package net.tuxun.customer.module.myWeb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.tuxun.core.base.dao.IBaseDao;
import net.tuxun.core.base.service.AbstractBaseService;
import net.tuxun.customer.module.myWeb.dao.ISkillInfoDao;
import net.tuxun.customer.module.myWeb.service.ISkillInfoService;
/**
 * 
 * @author pand
 *
 */
@Service
public class SkillInfoServiceImpl extends AbstractBaseService implements ISkillInfoService {
  @Autowired
  ISkillInfoDao dao;

  @Override
  public IBaseDao getDao() {
    return dao;
  } 
  
  
}
