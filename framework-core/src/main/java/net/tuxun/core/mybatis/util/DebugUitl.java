package net.tuxun.core.mybatis.util;

import java.util.List;
import java.util.StringTokenizer;

import net.tuxun.core.mybatis.page.PageAttr;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 打印分页查询有关的信息 <br/>
 * 查询语句 <br/>
 * 查询参数 <br/>
 * 分页信息 <br/>
 * 在log4j.properties配置log4j.logger.net.tuxun.core.mybatis.util.DebugUitl=debug
 * 
 * @author liuqiang
 * 
 */
public class DebugUitl {

  protected static Logger log = LoggerFactory.getLogger(DebugUitl.class);

  /**
   * 打印分页查询有关的信息
   * 
   * @param ms MappedStatement对象
   * @param parameterObject 参数实体,包含SQL查询参数，还包括其它的一些设置，如排序
   * @param attr　分页属性
   * @param list　查询返回的数据
   */
  public static void logPrepare(MappedStatement ms, Object parameterObject, PageAttr attr,
      List<?> list) {
    if (log.isDebugEnabled()) {
      SqlSource sqlSource = ms.getSqlSource();
      BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
      log.debug("分页语句 [ sql={}]", removeBreakingWhitespace(boundSql.getSql()));
      log.debug("查询参数 [ {} ]", getParameters(ms, boundSql));
      log.debug("分页信息 [ 当前页={}, 每页记录数={}, 信息总数={}, 总页数={} ]", attr.getPageCur(),
          attr.getInfoSize(), attr.getInfoTotal(), attr.getPageTotal());
      log.debug("返回数据 [ 记录数={} ]", list.size());
      logApendList(ms, list);
    }
  }
  
  public static void logPrepare(MappedStatement ms, Object parameterObject) {
    if (log.isDebugEnabled()) {
      SqlSource sqlSource = ms.getSqlSource();
      BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
      log.debug("-------------------------------------------------------------------");
      log.debug("执行语句 [ sql={}]", removeBreakingWhitespace(boundSql.getSql()));
      log.debug("相关参数 [ {} ]", getParameters(ms, boundSql));
    }
    
  }

  /**
   * 打印查询返回的所有数据
   * 
   * @param ms MappedStatement对象
   * @param list 查询的数据集合
   */
  private static void logApendList(MappedStatement ms, List<?> list) {
    // TODO 日志打印查询的数据集合
  }

  /**
   * SQL语句整理,删除多余的空格, 换行, 缩进
   * 
   * @param original
   * @return
   */
  protected static String removeBreakingWhitespace(String sql) {
    StringTokenizer whitespaceStripper = new StringTokenizer(sql);
    StringBuilder builder = new StringBuilder();
    while (whitespaceStripper.hasMoreTokens()) {
      builder.append(whitespaceStripper.nextToken());
      builder.append(" ");
    }
    return builder.toString();
  }

  /**
   * 取得查询参数 参照{@link org.apache.ibatis.scripting.defaults.DefaultParameterHandler#setParameter}
   * 
   * @param ms MappedStatement对象
   * @param boundSql　BoundSql对象
   */
  public static String getParameters(MappedStatement ms, BoundSql boundSql) {
    List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
    Object parameterObject = boundSql.getParameterObject();
    Configuration configuration = ms.getConfiguration();
    TypeHandlerRegistry typeHandlerRegistry = ms.getConfiguration().getTypeHandlerRegistry();
    StringBuffer sb = new StringBuffer();
    if (parameterMappings != null) {
      for (int i = 0; i < parameterMappings.size(); i++) {
        ParameterMapping parameterMapping = parameterMappings.get(i);
        if (parameterMapping.getMode() != ParameterMode.OUT) {
          Object value;
          String propertyName = parameterMapping.getProperty();
          if (boundSql.hasAdditionalParameter(propertyName)) { // issue #448 ask first for
                                                               // additional params
            value = boundSql.getAdditionalParameter(propertyName);
          } else if (parameterObject == null) {
            value = null;
          } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
            value = parameterObject;
          } else {
            MetaObject metaObject = configuration.newMetaObject(parameterObject);
            value = metaObject.getValue(propertyName);
          }
          sb.append(propertyName).append("=").append(value);
          if (i != parameterMappings.size() - 1)
            sb.append(" ,");
        }
      }
    }
    return sb.toString();
  }

}
