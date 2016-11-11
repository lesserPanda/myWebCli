package net.tuxun.core.mybatis.db;

import java.util.List;
import java.util.Map;

import net.tuxun.core.mybatis.page.PageAttr;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.Configuration;

/**
 * 数据库接口 因为是对JDBC直接操作，数据库分页SQL是不一样的 主要有两个方法getPageSql, getCountSql
 * 
 * @author liuqiang
 */
public interface Parser {

  /**
   * 获取查询信息总数的sql
   * 
   * @param sql 原始查询的sql
   * @return 返回总数查询的sql
   */
  String getCountSql(String sql);



  /**
   * 是否支持MappedStatement全局缓存
   * 
   * @return
   */
  boolean isSupportedMappedStatementCache();

  /**
   * 获取分页sql - 如果要支持其他数据库，修改这里就可以
   * 
   * @param sql 原查询sql
   * @return 返回分页sql
   */
  String getPageSql(String sql);

  /**
   * 获取分页参数映射
   * 
   * @param configuration
   * @param boundSql
   * @return
   */
  List<ParameterMapping> getPageParameterMapping(Configuration configuration, BoundSql boundSql);

  /**
   * 设置分页参数
   * 
   * @param ms
   * @param parameterObject
   * @param boundSql
   * @param page
   * @return
   */
  Map setPageParameter(MappedStatement ms, Object parameterObject, BoundSql boundSql, PageAttr attr);
}
