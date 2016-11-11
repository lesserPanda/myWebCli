package net.tuxun.core.mybatis.sqlsource;


import java.util.Map;

import net.tuxun.core.mybatis.util.Constant;
import net.tuxun.core.mybatis.util.MSUtils;

import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.session.Configuration;

/**
 * 动态查询时用于取得BoundSql的实现(非注解)
 * 
 * @author liuqiang
 */
@SuppressWarnings("rawtypes")
public class PageDynamicSqlSource implements SqlSource, Constant {
  private Configuration configuration;
  private SqlNode rootSqlNode;
  /**
   * 用于区分动态的count查询或分页查询
   */
  private Boolean count;

  private MSUtils msUtils;

  public PageDynamicSqlSource(MSUtils msUtils, Configuration configuration, SqlNode rootSqlNode,
      Boolean count) {
    this.msUtils = msUtils;
    this.configuration = configuration;
    this.rootSqlNode = rootSqlNode;
    this.count = count;
  }

  public BoundSql getBoundSql(Object parameterObject) {
    DynamicContext context;
    // 由于增加分页参数后会修改parameterObject的值，因此在前面处理时备份该值
    // 如果发现参数是Map并且包含该KEY，就使用备份的该值
    if (parameterObject != null && parameterObject instanceof Map
        && ((Map) parameterObject).containsKey(ORIGINAL_PARAMETER_OBJECT)) {
      context =
          new DynamicContext(configuration, ((Map) parameterObject).get(ORIGINAL_PARAMETER_OBJECT));
    } else {
      context = new DynamicContext(configuration, parameterObject);
    }
    rootSqlNode.apply(context);
    SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
    Class<?> parameterType = parameterObject == null ? Object.class : parameterObject.getClass();
    SqlSource sqlSource =
        sqlSourceParser.parse(context.getSql(), parameterType, context.getBindings());
    if (count) {
      sqlSource = msUtils.getStaticCountSqlSource(configuration, sqlSource, parameterObject);
    } else {
      sqlSource = msUtils.getStaticPageSqlSource(configuration, sqlSource, parameterObject);
    }
    BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
    // 设置条件参数
    for (Map.Entry<String, Object> entry : context.getBindings().entrySet()) {
      boundSql.setAdditionalParameter(entry.getKey(), entry.getValue());
    }
    return boundSql;
  }
}
