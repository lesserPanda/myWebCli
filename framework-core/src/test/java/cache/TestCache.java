package cache;

import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.tuxun.core.util.CacheUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * 缓存测试
 * 
 * @author liuqiang
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/cache/application.xml")
public class TestCache {

  protected Logger log = LoggerFactory.getLogger(TestCache.class);

  @Autowired
  private CacheUtil cacheUtil;

  @Autowired
  private CacheService cacheService;

  // 使用默认的缓存区cacheBuild
  @Test
  public void testCache() {
    String key = "key";
    Object value = "李四";
    cacheUtil.put(key, value);
    String cacheValue = cacheUtil.get(key);
    Assert.notNull(value);
    cacheUtil.remove(key);
    log.info("取出的缓存值={}", cacheValue);
  }

  // 测试对象的存取
  // 缓存的对象必需是可序列化的
  // 没有序列化的对象会抛出异常
  @Test
  public void testBeanCache() {
    Object key = "zhangshan";
    Student student = new Student("张三", "35215522552255555", "男");
    cacheUtil.put(key, student);
    Student cacheValue = cacheUtil.get(key);
    Assert.notNull(cacheValue);
    cacheUtil.remove(key);
    // 打印maxEntriesLocalHeap可以确认使用的缓存区
    log.info("默认的 maxEntriesLocalHeap={}", cacheUtil.cacheBuffer.getCacheConfiguration()
        .getMaxEntriesLocalHeap());// 2000
    log.info("缓存中学生姓名={}, 身份证={}, 性别={}", cacheValue.name, cacheValue.idcard, cacheValue.sex);
  }

  // 指定一个缓存区，进行读写操作
  // 这个缓存区不存在就创建一个, 属性使用cacheBuild的一致
  // cacheUtil默认的是使用cacheBuild名称的缓存,详见ehcache-build.xml
  @Test
  public void testMeCache() {
    CacheUtil meCacheUtil = cacheUtil.assign("mexxcache");

    Object key = "lishi";
    Student student = new Student("李四", "55821111545121212", "男");
    meCacheUtil.put(key, student);
    Student cacheValue = meCacheUtil.get(key);
    Assert.notNull(cacheValue);
    meCacheUtil.remove(key);
    log.info("mexxcache maxEntriesLocalHeap={}", meCacheUtil.cacheBuffer.getCacheConfiguration()
        .getMaxEntriesLocalHeap());// 2000
    log.info("缓存中学生姓名={}, 身份证={}, 性别={}", cacheValue.name, cacheValue.idcard, cacheValue.sex);
  }

  // 自定义缓存区及属性
  // 不会继续默认的属性设置，完全自己定义
  // 熟悉的也可以在Spring xml配置使用
  @Test
  public void testMeProCache() {
    CacheConfiguration cacheConfiguration = new CacheConfiguration();
    // 定义缓存名称
    cacheConfiguration.setName("meprocache");
    cacheConfiguration.setMaxEntriesLocalHeap(8888);
    PersistenceConfiguration persistenceConfiguration = new PersistenceConfiguration();
    // localTempSwap 不持久对象，只是将内存中的对象保存在磁盘上，项目重启时动删除
    persistenceConfiguration.setStrategy("localTempSwap");
    cacheConfiguration.persistence(persistenceConfiguration);

    CacheUtil meProCacheUtil = cacheUtil.create(cacheConfiguration);

    Object key = "wanger";
    Student student = new Student("王二", "55821111545121212", "男");
    meProCacheUtil.put(key, student);
    Student cacheValue = meProCacheUtil.get(key);
    Assert.notNull(cacheValue);
    meProCacheUtil.remove(key);
    log.info("meprocache maxEntriesLocalHeap={}", meProCacheUtil.cacheBuffer
        .getCacheConfiguration().getMaxEntriesLocalHeap());// 8888
    log.info("缓存中学生姓名={}, 身份证={}, 性别={}", cacheValue.name, cacheValue.idcard, cacheValue.sex);
  }

  // 测试同时操作缓存，会不会出问题
  @Test
  public void twoCacheOper() {
    Object key = "lishi";
    Student student = new Student("李四", "55821111545121212", "男");

    CacheUtil one = cacheUtil.assign("oneCache");
    one.put(key, student);

    CacheUtil two = cacheUtil;
    two.put(key, student);


    Student oneValue = one.get(key);
    log.info("oneCache maxEntriesLocalHeap={}", one.cacheBuffer.getCacheConfiguration()
        .getMaxEntriesLocalHeap());// 2000
    log.info("oneCache缓存中学生姓名={}, 身份证={}, 性别={}", oneValue.name, oneValue.idcard, oneValue.sex);

    Student twoValue = two.get(key);
    log.info("twoCache maxEntriesLocalHeap={}", one.cacheBuffer.getCacheConfiguration()
        .getMaxEntriesLocalHeap());// 2000
    log.info("twoCache 缓存中学生姓名={}, 身份证={}, 性别={}", twoValue.name, twoValue.idcard, twoValue.sex);
  }

  // 测试注解缓存
  // 注解保存的值，手动取出来
  @Test
  public void testCacheable() {
    // 直接调用方式, 往默认的cacheBuild缓存中增加一条记录
    // Object key1 = "zhangshan";
    // Student student1 = new Student("张三", "35215522552255555", "男");
    // cacheUtil.put(key1, student1);

    Object key2 = "lishi";
    Student student2 = new Student("李四", "35215522552255555", "男");
    // 注解的方式, 往cacheBuild增加一条记录
    cacheService.find(key2, student2);

    // 用程序将key2的值取出来
    Student studentCache2 = cacheUtil.get(key2);
    log.info("用程序将key2的值取出来, key2=[姓名={}, 身份证={}, 性别={}]", studentCache2.name,
        studentCache2.idcard, studentCache2.sex);


    // 用注解将key1的值取出来
    // Student studentCache1 = cacheService.get(key1);
    // log.info("用程序将key1的值取出来, key1=[姓名={}, 身份证={}, 性别={}]", studentCache1.name,
    // studentCache1.idcard, studentCache1.sex);

  }

  // 测试手动缓存
  // 手动保存的值，用注解把他取出来
  // 测试注解形式与手动形式的缓存区的通用性
  @Test
  public void testHander() {
    // 直接调用方式, 往默认的cacheBuild缓存中增加一条记录
    Object key1 = "zhangshan";
    Student student1 = new Student("张三", "35215522552255555", "男");
    cacheUtil.put(key1, student1);

    // 用注解将key1的值取出来
    Student studentCache1 = cacheService.get(key1);
    log.info("用程序将key1的值取出来, key1=[姓名={}, 身份证={}, 性别={}]", studentCache1.name,
        studentCache1.idcard, studentCache1.sex);

  }

}
