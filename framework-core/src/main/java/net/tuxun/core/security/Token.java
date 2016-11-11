package net.tuxun.core.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注释在controller方法上，用于防止表单的重复提交
 * 由于多标签的机制,采用session不太现实，这里将token保存到cookie中
 * @author liuqiang
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {
  
  /**
   * 创建一个token, 在浏览器中写一个cookie
   * @return 是否创建一个token
   */
  boolean create() default true;
  
  /**
   * 验证
   * @return true 即销毁
   */
  boolean validate() default false;
  
}
