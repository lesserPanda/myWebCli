package net.tuxun.core.query;

import java.util.List;

import net.tuxun.core.mybatis.page.PageAttr;

/**
 * 提供一个统一的对外查询接口,一个实现对应一张数据表。 实现的作用就设置查询条件
 * 
 * @author Administrator
 * 
 * @param <T>
 */
public interface Query<T> {

  /**
   * 根据条件查询总记录数
   * 
   * @return
   */
  long count();

  /**
   * 返回第一条记录
   * 
   * @return
   */
  T singleResult();

  /**
   * 返回全部数据
   * 
   * @return
   */
  List<T> list();

  /**
   * 按分页返回数据
   * 
   * @param attr
   * @return
   */
  List<T> listPage(PageAttr attr);

  void clearParam();
}
