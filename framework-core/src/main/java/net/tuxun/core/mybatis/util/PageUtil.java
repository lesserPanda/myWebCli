package net.tuxun.core.mybatis.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.tuxun.core.mybatis.SystemMetaObject;
import net.tuxun.core.mybatis.db.Dialect;
import net.tuxun.core.mybatis.db.Parser;
import net.tuxun.core.mybatis.db.impl.AbstractParser;
import net.tuxun.core.mybatis.page.PageAttr;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.RowBounds;



/**
 * Mybatis 分页工具 <br/>
 * 更改Mybatis内存分页改成物理分页
 * 
 * @author liuqiang
 */
public class PageUtil implements Constant {

  // 本地线程
  private static final ThreadLocal<PageAttr> LOCAL_PAGE = new ThreadLocal<PageAttr>();

  // 数据库的parser
  private Parser parser;
  // 处理MappedStatement
  private MSUtils msUtils;
  // 数据库方言
  private Dialect dialect;

  /**
   * 构造方法
   * 
   * @param strDialect
   */
  public PageUtil(Properties p) {
    String strDialect = p.getProperty("dialect");
    if (strDialect == null || "".equals(strDialect)) {
      throw new IllegalArgumentException("Mybatis分页插件无法获取dialect参数!");
    }
    dialect = Dialect.of(strDialect);
    parser = AbstractParser.newParser(dialect);
    msUtils = new MSUtils(parser);
  }

  /**
   * 本地线程保存分页属性
   * 
   * @param attr 分页属性
   */
  public static void setLocalPageAttr(PageAttr attr) {
    LOCAL_PAGE.set(attr);
  }

  /**
   * 从本地线程中取得分页属性
   * 
   * @return PageAttr 分页属性
   */
  public static PageAttr getLocalPageAttr() {
    return LOCAL_PAGE.get();
  }

  /**
   * 移除本地变量
   */
  public static void clearLocalPage() {
    LOCAL_PAGE.remove();
  }

  /**
   * Mybatis拦截器方法
   * 
   * @param invocation 拦截器入参
   * @return 返回执行结果
   * @throws Throwable 抛出异常
   */
  @SuppressWarnings("rawtypes")
  public Object process(Invocation invocation) throws Throwable {
    final Object[] args = invocation.getArgs();
    MappedStatement ms = (MappedStatement) args[0];
    if (!ms.getId().endsWith(PAGE_PATTERN)) {
      DebugUitl.logPrepare(ms, args[1]);
      return invocation.proceed();
    } else {
      // 忽略RowBounds-否则会进行Mybatis自带的内存分页
      args[2] = RowBounds.DEFAULT;
      // 分页信息
      PageAttr attr = getPageAttr(invocation);

      // 获取原始的sqlSource
      SqlSource sqlSource = ms.getSqlSource();
      // 将参数中的MappedStatement替换为新的qs
      msUtils.processCountMappedStatement(ms, sqlSource, args);
      // 查询总数
      Object result = invocation.proceed();
      // 设置总数
      attr.setInfoTotal((Integer) ((List) result).get(0));
      attr.comb();
      List list;
      if (attr.getInfoTotal() == 0) {
        list = new ArrayList();
      } else {
        // 将参数中的MappedStatement替换为新的qs
        msUtils.processPageMappedStatement(ms, sqlSource, attr, args);
        result = invocation.proceed();
        // 得到处理结果
        list = (List) result;
      }
      DebugUitl.logPrepare(ms, args[1], attr, list);
      return list;
    }
  }

  /**
   * 取得分页查询的pageAttr参数
   * 
   * @param invocation 拦截器入参
   * @return 返回分页对象
   */
  private PageAttr getPageAttr(Invocation invocation) {
    Object parameterObject = invocation.getArgs()[1];
    MetaObject metaObject = SystemMetaObject.forObject(parameterObject);
    PageAttr attr = (PageAttr) metaObject.getValue("attr");

    if (!parser.isSupportedMappedStatementCache()) {// 主要针对sql server 数据库
      setLocalPageAttr(attr);
    }
    return attr;
  }

}
