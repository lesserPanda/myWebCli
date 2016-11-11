package net.tuxun.core.security;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.tuxun.core.util.IDGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

public class TokenInterceptor extends HandlerInterceptorAdapter {
  
  public static final String TOKEN = "token";
  private Logger log = LoggerFactory.getLogger(TokenInterceptor.class);

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    if (handler instanceof HandlerMethod) {
      HandlerMethod handlerMethod = (HandlerMethod) handler;
      Method method = handlerMethod.getMethod();
      Token annotation = method.getAnnotation(Token.class);
      if (annotation != null) {
        log.debug("重复提交拦截器处理开始");
        boolean isCreate = annotation.create();
        if (isCreate) {
          String uuid = IDGenerator.generateId();
          WebUtils.setSessionAttribute(request, TOKEN, uuid);
          log.debug("创建token，将[key={}, value={}]放入session中", TOKEN, uuid);
        }
        boolean isDesdory = annotation.validate();
        if (isDesdory) {
          if (isRepeatSubmit(request)) {
            return false;
          }
          WebUtils.setSessionAttribute(request, TOKEN, null);
          log.debug("销毁session中的token[key={}]", TOKEN);
        }
      }
      return true;
    } else {
      return super.preHandle(request, response, handler);
    }
  }

  private boolean isRepeatSubmit(HttpServletRequest request) {
    String serverToken = (String) request.getSession(false).getAttribute("token");
    if (serverToken == null) {
      return true;
    }
    String clinetToken = request.getParameter("token");
    if (clinetToken == null) {
      return true;
    }
    if (!serverToken.equals(clinetToken)) {
      return true;
    }
    return false;
  }
}
