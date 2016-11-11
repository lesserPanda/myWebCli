package net.tuxun.customer.module.myWeb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.tuxun.core.base.dao.IBaseDao;
import net.tuxun.core.base.service.AbstractBaseService;
import net.tuxun.customer.module.myWeb.dao.IMusicInfoDao;
import net.tuxun.customer.module.myWeb.service.IMusicInfoService;
/**
 * 
 * @author pand
 *
 */
@Service
public class MusicInfoServiceImpl extends AbstractBaseService implements IMusicInfoService {
  @Autowired
  IMusicInfoDao dao;

  @Override
  public IBaseDao getDao() {
    return dao;
  } 
  
  
}
