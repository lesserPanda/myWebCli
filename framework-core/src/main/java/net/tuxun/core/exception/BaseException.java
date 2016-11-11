package net.tuxun.core.exception;

/**
 * 应用系统的根异常
 * 
 * @author Administrator
 * 
 */
public class BaseException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = -2774246936062283640L;

  public BaseException(String message) {
    super(message);
  }

  public BaseException(String message, Throwable e) {
    super(message, e);
  }

  public BaseException(Throwable e) {
    super(e);
  }

}
