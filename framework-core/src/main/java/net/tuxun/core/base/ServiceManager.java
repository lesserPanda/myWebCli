package net.tuxun.core.base;

import java.util.HashMap;
import java.util.Map;

import net.tuxun.core.util.ApplicationContextUtil;

/**
 * 接口查询服务 组件A可以通过此服务查询组件B指定接口的实现。
 * 
 * @author Administrator
 * 
 */
public class ServiceManager {

  private static Map<String, Object> objmap = null;
  static {
    objmap = new HashMap<String, Object>();
  }

  public synchronized static <T> void addService(Class<T> cla, T obj) {
    objmap.put(cla.getName(), obj);
  }

  public synchronized static <T> void addService(String objname, T obj) {
    objmap.put(objname, obj);
  }

  /**
   * @param objname
   * @param beanname 对应spring容器中的Bean名称
   */
  public synchronized static <T> void addService(String objname, String beanname) {
    objmap.put(objname, beanname);
  }


  /**
   * 查询接口实现
   * 
   * @param cla 接口class
   * @return 接口实现，不存在则返回null。
   */
  public static <T> T findService(Class<T> cla) {

    T obj = null;
    try {
      obj = (T) objmap.get(cla.getName());
      if (obj == null) {
        obj = ApplicationContextUtil.getContext().getBean(cla);
      }
    } catch (Exception e) {
      return null;
    }

    return obj;
  }

  public static Object findService(String name) {
    try {
      Object obj = objmap.get(name);
      if (obj == null) {
        obj = ApplicationContextUtil.getBean(name);
      } else if (obj instanceof String) {
        obj = ApplicationContextUtil.getBean((String) obj);
      }
      return obj;
    } catch (Exception e) {
      return null;
    }
  }


}
