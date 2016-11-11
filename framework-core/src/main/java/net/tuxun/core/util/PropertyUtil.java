package net.tuxun.core.util;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.support.PropertiesLoaderSupport;
import org.springframework.web.context.ServletContextAware;

/**
 * 通过静态方法{@link #get(String)}取得webapp.properties与user.properties文件中的值 <br>
 * 文件中包含的key=${another key}形式的值可以在任何位置
 * 
 * @author liuqiang
 * 
 */
public class PropertyUtil extends PropertiesLoaderSupport implements FactoryBean<Properties>,
    InitializingBean, ServletContextAware {

  public static final String ROOT_PATH = "rootPath"; // 附件存放的根路径
  public static final String HOST_PREFIX = "${";
  public static final String HOST_SUFFIX = "}";
  public static final String HOST_SEPARATOR = ":";

  private static Logger log = LoggerFactory.getLogger(PropertyUtil.class);
  private static Properties RROP = new Properties();
  private String webapppath;

  /**
   * 将配置文件key-value存放到静态常量PROP中
   * 
   * @param prop
   * @throws IOException
   */
  public synchronized void init() throws IOException {
    Properties prop = mergeProperties();
    if (!prop.containsKey(ROOT_PATH) && webapppath != null) {
      prop.put(ROOT_PATH, webapppath);
    }
    Enumeration<Object> em = prop.keys();
    while (em.hasMoreElements()) {
      String key = (String) em.nextElement();
      String value = replace(prop, prop.getProperty(key));
      RROP.put(key, value);
      log.debug("{}={}", key, value);
    }
  }


  /**
   * 取得属性文件的对应的值
   * 
   * @param key
   * @return
   */
  public static String get(String key) {
    if (key != null) {
      Object obj = RROP.get(key);
      if (obj != null) {
        String value = (String) RROP.get(key);
        return value;
      }
    }
    return null;
  }


  /**
   * 取得以指定属性名开始的所有属性名。<br>
   * .e.g 如果想获得以client.host开头的属性名<br>
   * 则可以调用getKeyStartWith("client.host")
   * 
   * @param key 属性名前缀
   * @return 以指定属性名开头的属性集合
   */
  public static Set<String> getKeyStartsWith(String key) {
    Set<String> set = new HashSet<String>();
    Enumeration<Object> em = RROP.keys();
    while (em.hasMoreElements()) {
      String name = (String) em.nextElement();
      if (name.startsWith(key)) {
        set.add(name);
      }
    }
    return set;

  }


  /**
   * 获取值为中文的属性。例如：appName (系统名称)<br>
   * {@link #get(String)}已可以正确取到中文
   * 
   * @param key
   * @return
   */
  @Deprecated
  public static String getUTF8(String key) {
    return get(key);
  }

  /**
   * 替换value中的${}
   * 
   * @param value 包含${}点位符的字符串
   * @return 返回已经替换过正确的值
   */
  public static String replace(String value) {
    return replace(RROP, value);
  }

  /**
   * 替换value中的${}
   * 
   * @param prop 键值对集合
   * @param value 包含${}点位符的字符串
   * @return 返回已经替换过正确的值
   */
  public static String replace(Properties prop, String value) {
    if (value == null) {
      return null;
    }
    if (value.indexOf(HOST_PREFIX) == -1 || value.indexOf(HOST_SUFFIX) == -1) {
      return value;
    }
    try {
      StringBuffer sb = new StringBuffer();
      String vaeHead;
      String vaeFoot;
      String tempKey;
      for (String s : value.split(HOST_SUFFIX)) {
        int i = s.indexOf(HOST_PREFIX);
        if (i > -1) {
          vaeHead = s.substring(0, s.indexOf(HOST_PREFIX));
          tempKey = s.substring(s.indexOf(HOST_PREFIX) + 2);
          vaeFoot = prop.getProperty(tempKey);
          if (vaeFoot == null) {
            throw new RuntimeException("${" + tempKey + "}取值为空值");
          }
          sb.append(vaeHead);
          sb.append(vaeFoot);
        } else {
          sb.append(s);
        }
      }
      return replace(prop, sb.toString());
    } catch (Exception e) {
      throw new RuntimeException(value + "${}取值异常");
    }
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    log.debug("[webapp.properties, user.properties]初始化开始");
    long startTime = System.currentTimeMillis();
    init();
    log.debug("[webapp.properties, user.properties]初始化完成, 用时{}毫秒", System.currentTimeMillis()
        - startTime);
  }

  @Override
  public Properties getObject() throws Exception {
    return RROP;
  }

  @Override
  public Class<Properties> getObjectType() {
    return Properties.class;
  }

  @Override
  public boolean isSingleton() {
    return false;
  }

  @Override
  public void setServletContext(ServletContext servletContext) {
    if (servletContext != null) {
      webapppath = servletContext.getRealPath("/").replaceAll("\\\\", "/");
    }
  }

}
