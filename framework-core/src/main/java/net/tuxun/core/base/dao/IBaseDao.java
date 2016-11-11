package net.tuxun.core.base.dao;

import java.util.List;
import java.util.Map;

import net.tuxun.core.mybatis.page.Collate;
import net.tuxun.core.mybatis.page.PageAttr;

import org.apache.ibatis.annotations.Param;

/**
 * 持久层基类
 * 
 * @author liuqiang
 */
public interface IBaseDao {

  /**
   * 查询分页数据
   * 
   * @param attr 分页属性
   * @param search 搜索参数
   * @param collates 排序参数
   * @return 查询的分页数据
   */
  public <E> List<E> listPage(@Param("attr") PageAttr attr,
      @Param("search") Map<String, String> search, @Param("collates") List<Collate> collates);

  /**
   * 保存一条数据
   * 
   * @param bean 数据对象
   */
  public <T> void insert(T bean);

  /**
   * 根据数据的ID, 取得一条数据
   * 
   * @param id 数据的ID
   * @return　数据
   */
  public <T> T select(String id);

  /**
   * 更新数据
   * 
   * @param bean 需要更新的数据对象
   */
  public <T> void update(T bean);

  /**
   * 更新数据, 如果数据对象的属性为NULL, 那么这个属性不进行更新操作
   * 
   * @param bean 南要更新的数据对象
   */
  public <T> void updateNotNull(T bean);

  /**
   * 根据数据对象的ID, 删除数据
   * 
   * @param id 数据的ID
   */
  public <T> void delete(String id);

}
