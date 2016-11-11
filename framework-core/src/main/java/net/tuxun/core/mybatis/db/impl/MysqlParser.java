package net.tuxun.core.mybatis.db.impl;

import java.util.Map;

import net.tuxun.core.mybatis.page.PageAttr;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * mysql 数据分页
 * 
 * @author liuqiang
 */
public class MysqlParser extends AbstractParser {
  @Override
  public String getPageSql(String sql) {
    StringBuilder sqlBuilder = new StringBuilder(sql.length() + 14);
    sqlBuilder.append(sql);
    sqlBuilder.append(" limit ?,?");
    return sqlBuilder.toString();
  }

  @Override
  @SuppressWarnings({"rawtypes", "unchecked"})
  public Map setPageParameter(MappedStatement ms, Object parameterObject, BoundSql boundSql,
      PageAttr attr) {
    Map paramMap = super.setPageParameter(ms, parameterObject, boundSql, attr);
    paramMap
        .put(PAGEPARAMETER_FIRST, Integer.valueOf((attr.getPageCur() - 1) * attr.getInfoSize()));
    paramMap.put(PAGEPARAMETER_SECOND, Integer.valueOf(attr.getInfoSize()));
    return paramMap;
  }
}
