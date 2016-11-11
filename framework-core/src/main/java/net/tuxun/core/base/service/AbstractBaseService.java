package net.tuxun.core.base.service;

import java.util.List;

import net.tuxun.core.mybatis.page.PageNav;
import net.tuxun.core.mybatis.page.PageQuery;
import net.tuxun.core.util.IDGenerator;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * 业务层抽象类，实现了增删改查方法
 * 
 * @author liuqiang
 * 
 */
public abstract class AbstractBaseService implements IbaseService {

  protected Logger log = LoggerFactory.getLogger(this.getClass());

  @Override
  public <E> PageNav<E> pageResult(PageQuery query) {
    List<E> list = getDao().listPage(query.getAttr(), query.getCurSearch(), query.getCurCollates());
    return new PageNav<E>(query, list);
  }

  @Override
  @Transactional
  public <T> void save(T bean) {
    try {
      PropertyUtils.setProperty(bean, "id", IDGenerator.generateId());
    } catch (Exception e) {
      throw new RuntimeException("保存对象, 初始化主键出错" + e.getMessage());
    }
    getDao().insert(bean);
  }

  @Override
  @Transactional
  public <T> void saveOrModify(T bean) {
    try {
      Object id = PropertyUtils.getProperty(bean, "id");
      if (id == null || String.valueOf(id).equals("")) {
        save(bean);
      } else {
        modifyNotNull(bean);
      }
    } catch (Exception e) {
      throw new RuntimeException("保存或修改对象,取得主键出错" + e.getMessage());
    }
  }

  @Override
  public <T> T get(String id) {
    return getDao().select(id);
  }

  @Override
  @Transactional
  public <T> void modify(T bean) {
    getDao().update(bean);
  }

  @Override
  @Transactional
  public <T> void modifyNotNull(T bean) {
    getDao().updateNotNull(bean);
  }

  @Override
  @Transactional
  public <T> void remove(String id) {
    getDao().delete(id);
  }

}
