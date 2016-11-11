package cache;

import org.springframework.cache.annotation.Cacheable;

/**
 * 用来测试缓存注解的调用
 * 
 * 有关注释缓存的详细用户请搜索这两个注解的用法@Cacheable @CacheEvict
 * 
 */
public class CacheService {


  @Cacheable(value = "cacheBuild", key = "#key")
  public Student find(Object key, Student student) {
    return student;
  }

  @Cacheable(value = "cacheBuild", key = "#key")
  public Student get(Object key) {
    return null;
  }

}
