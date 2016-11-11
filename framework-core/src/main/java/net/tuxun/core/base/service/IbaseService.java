package net.tuxun.core.base.service;

import net.tuxun.core.base.dao.IBaseDao;
import net.tuxun.core.mybatis.page.PageNav;
import net.tuxun.core.mybatis.page.PageQuery;

/**
 * 业务层基类
 * 
 * @author liuqiang
 */
public interface IbaseService {

  /**
   * 取得分页数据
   * 
   * @param query 封装了所有分页相关查询参数的对象
   * @return 分页对象(包含了分页数据及分页信息)
   */
  public <P> PageNav<P> pageResult(PageQuery query);

  /**
   * 保存对象
   * 
   * @param bean 对象
   */
  public <T> void save(T bean);

  /**
   * 保存(bean.id==null)或修改(bean.id!=null)对象
   * 
   * @param bean 对象
   */
  public <T> void saveOrModify(T bean);

  /**
   * 根据对象的ID, 取得对象
   * 
   * @param id 对象的ID
   * @return 对象, 如果不存在返回NULL
   */
  public <T> T get(String id);

  /**
   * 更新对象
   * 
   * @param bean 对象
   */
  public <T> void modify(T bean);

  /**
   * 更新对象，如果数据对象的属性为NULL, 那么这个属性不进行更新操作
   * 
   * @param bean　需要更新的对象
   */
  public <T> void modifyNotNull(T bean);

  /**
   * 删除数据
   * 
   * @param id　数据的ID
   */
  public <T> void remove(String id);

  /**
   * 取得持久层的对象
   * 
   * @return 持久层的对象
   */
  public IBaseDao getDao();

}
