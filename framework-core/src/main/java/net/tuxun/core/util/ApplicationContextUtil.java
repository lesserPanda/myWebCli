package net.tuxun.core.util;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

/**
 * 
 * Spring上下文、Servlet上下文的工具类。
 * 
 * @author wangchengxiang
 * 
 */

@Component("applicationContextUtil")
public class ApplicationContextUtil implements ApplicationContextAware, ServletContextAware {

  private static ApplicationContext CONTEXT = null;
  private static ServletContext sc = null;

  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    CONTEXT = applicationContext;
  }

  public void setServletContext(ServletContext servletContext) {
    sc = servletContext;
  }

  public static ApplicationContext getContext() {
    return CONTEXT;
  }

  public static ServletContext getServletContext() {
    return sc;
  }

  public static Object getBean(String name) {
    return CONTEXT.getBean(name);
  }
  
  public static Object getBean(Class<?> clazz) {
    return CONTEXT.getBean(clazz);
  }

  public static Object getServletContextAttr(String name) {
    return sc.getAttribute(name);
  }

  public static String getWebAppPath() {
    return getServletContext().getRealPath("/").replaceAll("\\\\", "/");
  }

}
