package net.tuxun.core.security;

/**
 * 异常封装类 <br>
 * 封装各种系统级别异常， <br>
 * 由<code>org.springframework.web.servlet.handler.SimpleMappingExceptionResolver</code>提供统一的展示页面
 * 
 * @author liuqiang
 */
@SuppressWarnings("serial")
public class GlobalException extends RuntimeException {

  public GlobalException(String message) {
    super(message);
  }

  public GlobalException(Throwable throwable) {
    super(throwable);
  }

  public GlobalException(Throwable throwable, String frdMessage) {
    super(throwable);
  }

}
