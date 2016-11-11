package net.tuxun.core.mybatis.db.impl;

import java.util.List;
import java.util.Map;

import net.tuxun.core.mybatis.page.PageAttr;
import net.tuxun.core.mybatis.util.PageUtil;
import net.tuxun.core.mybatis.util.SqlServer;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.Configuration;

/**
 * sqlserver 分页查询
 * 
 * @author liuqiang
 */
public class SqlServerParser extends AbstractParser {

  private static final SqlServer pageSql = new SqlServer();

  @Override
  public boolean isSupportedMappedStatementCache() {
    // 由于sqlserver每次分页参数都是直接写入到sql语句中，因此不能缓存MS
    return false;
  }

  @Override
  public List<ParameterMapping> getPageParameterMapping(Configuration configuration,
      BoundSql boundSql) {
    return boundSql.getParameterMappings();
  }

  @Override
  public String getPageSql(String sql) {
    PageAttr attr = PageUtil.getLocalPageAttr();
    PageUtil.clearLocalPage();
    return pageSql.convertToPageSql(sql, (attr.getPageCur() - 1) * attr.getInfoSize(),
        attr.getInfoSize());
  }

  @Override
  @SuppressWarnings("rawtypes")
  public Map setPageParameter(MappedStatement ms, Object parameterObject, BoundSql boundSql,
      PageAttr attr) {
    return super.setPageParameter(ms, parameterObject, boundSql, attr);
  }
}
