package net.tuxun.customer.module.myWeb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.tuxun.core.base.dao.IBaseDao;
import net.tuxun.customer.module.myWeb.bean.CmsComment;
/**
 * 
 * @author pand
 *
 */
public interface ICmsCommentDao extends IBaseDao {
  
  void deleteByPrimaryTableId(String primaryTableId);

  List<CmsComment> listByPrimaryTableId(@Param("id")String primaryTableId);
}
