package net.tuxun.core.mybatis.plugin;

import java.util.Properties;

import net.tuxun.core.mybatis.util.PageUtil;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

/**
 * 分页拦截器 <br/>
 * 将Mybatis自带的内存分页改写成物理分页，并提供缓存的支持 <br/>
 * 支持注解SQL及Mapper两种分页查询
 * 
 * @author liuqiang
 * 
 */
@Intercepts(@Signature(type = Executor.class, method = "query", args = {MappedStatement.class,
    Object.class, RowBounds.class, ResultHandler.class}))
public class PageInterceptor implements Interceptor {
  // sql工具类
  private PageUtil pageUtil;

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    return pageUtil.process(invocation);
  }

  @Override
  public Object plugin(Object target) {
    if (target instanceof Executor) {
      return Plugin.wrap(target, this);
    } else {
      return target;
    }
  }

  @Override
  public void setProperties(Properties p) {
    // 数据库方言
    pageUtil = new PageUtil(p);
  }

}
