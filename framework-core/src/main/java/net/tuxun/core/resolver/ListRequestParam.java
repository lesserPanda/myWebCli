package net.tuxun.core.resolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ListRequestParam {

  /**
   * 需要解析的类
   * 
   * @return
   */
  Class<?> bean();

  /**
   * 当一条记录中有数组字段的情况 可以使用动态前缀
   * 
   * @return
   */
  String relevance() default "";

  /**
   * 是否查找父类字段
   * 
   * @return
   */
  boolean findSuperClass() default false;

}
