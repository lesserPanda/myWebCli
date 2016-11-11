package net.tuxun.core.util;

import java.io.Serializable;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.tuxun.core.security.GlobalException;

import org.springframework.stereotype.Component;

/**
 * 缓存操作工具类, 用法如下：<br>
 * 1.你可以直接使用，操作提系统默认的cacheBuild缓存区 <br>
 * 2.你可以创建一个{@link #assign(String)} 相关缓存属性的配置参照cacheBuild属性 <br>
 * 3.你完全自已定义一个缓存区{@link #create(CacheConfiguration)}, 属性完全由自己定义 <br>
 * 你需要对Ehcache 相关的API使用要有所了解
 * 
 * @author liuqiang
 */
@Component("cacheUtil")
public class CacheUtil {

  /**
   * 注入一个名为cacheBuild缓存区
   */
  @Resource(name = "cacheBuild")
  public Ehcache cacheBuffer;

  /**
   * 放入缓存
   * 
   * @param key
   * @param value
   */
  public void put(Object key, Object value) {
    if (value instanceof Serializable) {
      cacheBuffer.put(new Element(key, value));
    } else {
      throw new GlobalException("CacheUtil#put加入缓存的对象必需是可序列化的");
    }
  }

  /**
   * 取得缓存
   * 
   * @param key
   * @return
   */
  @SuppressWarnings("unchecked")
  public <T> T get(Object key) {
    Element element = cacheBuffer.get(key);
    if (element != null) {
      return (T) element.getObjectValue();
    } else {
      return null;
    }
  }

  /**
   * 指定使用的缓存，如果没有则创建一个 <br>
   * 创建的缓存使用默认的规则
   * 
   * @param cacheName 缓存区名称
   * @return 操作这个缓存区的缓存工具类
   */
  public CacheUtil assign(String cacheName) {
    CacheManager cacheMangager = cacheBuffer.getCacheManager();
    if (!cacheMangager.cacheExists(cacheName)) {
      synchronized (cacheMangager) {
        if (!cacheMangager.cacheExists(cacheName)) {          
          CacheConfiguration cacheConfiguration = cacheBuffer.getCacheConfiguration();
          cacheConfiguration.setName(cacheName);
          cacheMangager.addCache(new Cache(cacheConfiguration));
        }
      }
    }
    return new CacheUtil(cacheMangager.getEhcache(cacheName));
  }

  /**
   * 创建使用的缓存, 如果存在则将其删除, 重新创建一个<br>
   * 创建的cache属性完全由自己定义，不会继承ehcache-build.xml中默认的配置
   * 
   * @param cacheConfiguration 缓存配置
   * @return 操作这个缓存区的缓存工具类
   */
  public CacheUtil create(CacheConfiguration cacheConfiguration) {
    CacheManager cacheMangager = cacheBuffer.getCacheManager();
    String cacheName = cacheConfiguration.getName();
    if (cacheName == null) {
      throw new GlobalException("CacheUtil#assign没有指定缓存区名称");
    }
    if (cacheMangager.cacheExists(cacheName)) {
      // 如果存在即删除
      cacheMangager.removeCache(cacheName);
    }
    cacheMangager.addCache(new Cache(cacheConfiguration));
    return new CacheUtil(cacheMangager.getEhcache(cacheName));
  }
  
  /**
   * 创建使用的缓存, 如果存在则直接返回<br>
   * 创建的cache属性完全由自己定义，不会继承ehcache-build.xml中默认的配置
   * 
   * @param cacheConfiguration 缓存配置
   * @return 操作这个缓存区的缓存工具类
   */
  public CacheUtil assign(CacheConfiguration cacheConfiguration) {
    CacheManager cacheMangager = cacheBuffer.getCacheManager();
    String cacheName = cacheConfiguration.getName();
    if (cacheName == null) {
      throw new GlobalException("CacheUtil#assign#CacheConfiguration没有指定缓存区名称");
    }
    if (!cacheMangager.cacheExists(cacheName)) {
      cacheMangager.addCache(new Cache(cacheConfiguration));
    }
    return new CacheUtil(cacheMangager.getEhcache(cacheName));
  }

  /**
   * 删除 key 相关的缓存
   * 
   * @param key 缓存key
   */
  public void remove(Object key) {
    cacheBuffer.remove(key);
  }

  /**
   * 删除缓存区的全部内容
   */
  public void removeAll() {
    cacheBuffer.removeAll();
  }

  /**
   * 有参构造
   * 
   * @param ehcache 缓存区
   */
  public CacheUtil(Ehcache cacheBuffer) {
    super();
    this.cacheBuffer = cacheBuffer;
  }

  /**
   * 无参构造
   */
  public CacheUtil() {
    super();
  }

}
