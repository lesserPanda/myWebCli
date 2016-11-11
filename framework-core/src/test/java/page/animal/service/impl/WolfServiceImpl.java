package page.animal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import page.animal.dao.IWolfDao;
import page.animal.service.IWolfService;
import net.tuxun.core.base.dao.IBaseDao;
import net.tuxun.core.base.service.AbstractBaseService;

/**
 * 
 * @author liuqiang
 * 
 */
@Service
public class WolfServiceImpl extends AbstractBaseService implements IWolfService {
  @Autowired
  IWolfDao dao;

  @Override
  public IBaseDao getDao() {
    return dao;
  }


}
