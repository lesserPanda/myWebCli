package net.tuxun.core.base.dao;

import java.util.List;
import java.util.Map;

import net.tuxun.core.mybatis.page.Collate;
import net.tuxun.core.mybatis.page.PageAttr;

import org.apache.ibatis.annotations.Param;

public interface IQueryDao<T> extends IBaseDao {

  List<T> selectAll(@Param("search") Map<String, Object> search,
      @Param("collates") List<Collate> collates);

  List<T> selectAllPage(@Param("attr") PageAttr attr, @Param("search") Map<String, Object> search,
      @Param("collates") List<Collate> collates);

  int selectAllCount(@Param("search") Map<String, Object> search,
      @Param("collates") List<Collate> collates);
}
