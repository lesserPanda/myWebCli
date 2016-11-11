package net.tuxun.core.mybatis.db.impl;

import java.util.Map;

import net.tuxun.core.mybatis.page.PageAttr;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * oracle 数据分页
 * 
 * @author liuqiang
 */
public class OracleParser extends AbstractParser {

  @Override
  public String getPageSql(String sql) {
    StringBuilder sqlBuilder = new StringBuilder(sql.length() + 120);
    sqlBuilder.append("select * from ( select tmp_page.*, rownum row_id from ( ");
    sqlBuilder.append(sql);
    sqlBuilder.append(" ) tmp_page where rownum <= ? ) where row_id > ?");
    return sqlBuilder.toString();
  }

  @Override
  @SuppressWarnings({"rawtypes", "unchecked"})
  public Map setPageParameter(MappedStatement ms, Object parameterObject, BoundSql boundSql,
      PageAttr attr) {
    Map paramMap = super.setPageParameter(ms, parameterObject, boundSql, attr);
    Integer beginrow = Integer.valueOf(attr.getPageCur() * attr.getInfoSize());
    Integer endrow = Integer.valueOf((attr.getPageCur() - 1) * attr.getInfoSize());
    paramMap.put(PAGEPARAMETER_FIRST, beginrow);
    paramMap.put(PAGEPARAMETER_SECOND, endrow);
    return paramMap;
  }
}
