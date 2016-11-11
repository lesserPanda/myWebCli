package net.tuxun.core.security;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.tuxun.core.base.controller.BaseController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;

/**
 * URL拦截器　<br>
 * 这个类的主要作用是增加，修改或删除后返回列表页面时,操作前的查询参数，排序及分页属性不变
 * 
 * @author liuqiang
 */
@SuppressWarnings("rawtypes")
public class ListUrlInterceptor extends HandlerInterceptorAdapter {
  
  private static final String MESSAGE = BaseController.MESSAGE;
  private static final String CHARSET = "utf-8";
  private Logger log = LoggerFactory.getLogger(ListUrlInterceptor.class);

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    
    String cacheKey = request.getRequestURI();
    log.debug("-------------------------------------------------------------------");
    log.debug("开始拦截处理{}请求", cacheKey);
    
    // POST请求直接放行
    if ("POST".equalsIgnoreCase(request.getMethod())) {
      log.debug("POST请求，直接放行");
      return true;
    }
    
    // 如果存在提示信息，将提示信息写入cookie中
    Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
    if (map != null) {
      Object message = map.get(MESSAGE);
      String messageCookie = getValueFromCookie(request, MESSAGE);
      if (message != null && messageCookie == null) {        
        setToCookie(response, MESSAGE, message.toString());
        log.debug("保存cookie(key={}, value={})", MESSAGE, message.toString());
      }
    }
    
    // 没有对应的缓存cookie直接放行
    String paramStr = getValueFromCookie(request, cacheKey);
    if (paramStr == null) {
      log.debug("没有对应的缓存cookie(key={})直接放行", cacheKey);
      return true;
    }
    
    // 跳转的URL与缓存的参数一致，直接放行
    if (getParamsTypeGet(request).equals(paramStr)) {
      log.debug("跳转的URL参数与缓存的参数一致，直接放行");
      return true;
    }
    
    // 再次重定向保证提示信息的不丢失
    if (map != null) {
      FlashMap flashMap = new FlashMap();
      flashMap.putAll(map);
      RequestContextUtils.getFlashMapManager(request).saveOutputFlashMap(flashMap, request, response);
      log.debug("传递提示信息的到新的重定向中，保证提示信息={}不丢失", flashMap);
    }
    
    // 重定向
    response.sendRedirect(cacheKey + paramStr);
    return false;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    
    String cacheKey = request.getRequestURI();
    
    // 成功搜索后，将搜索相关的参数保存到cookie中
    if ("POST".equalsIgnoreCase(request.getMethod())) {
      String paramStr = getParamsTypePost(request);
      if (paramStr.equals("")) {
        // 一般不会发生, 列表查询总会有参数，例分页参数
        removeCookie(request, response, cacheKey);
        log.debug("删除cookie=(key={})中", cacheKey);
      } else {
        setToCookie(response, cacheKey, paramStr);
        log.debug("将post请求的搜索参数保存到cookie=(key={}, value={})中", cacheKey, paramStr);
      }
    }
    log.debug("拦截处理{}请求结束", cacheKey);
    super.postHandle(request, response, handler, modelAndView);
  }

  /**
   * 删除cookie
   * @param response
   * @param cacheKey
   */
  private void removeCookie(HttpServletRequest request, HttpServletResponse response, String cacheKey) {
    Cookie cookie = WebUtils.getCookie(request, cacheKey);
    if (cookie != null) {      
      cookie.setPath("/");
      cookie.setMaxAge(0);
      response.addCookie(cookie);
    }
  }

  /**
   * 保存URL到cookie中
   * @param request 请求对象
   * @param cacheKey 缓存的key
   * @param paramStr 参数字符串
   * @throws UnsupportedEncodingException 编码异常
   */
  private void setToCookie(HttpServletResponse response, String cacheKey, String paramStr) throws UnsupportedEncodingException {
    Cookie cookie = new Cookie(cacheKey, URLEncoder.encode(paramStr, CHARSET));   
    cookie.setPath("/");
    response.addCookie(cookie);
  }

  /**
   * 取得cookie的值
   * @param request 请求对象
   * @param cacheKey 缓存的key
   * @return cookie的值
   * @throws UnsupportedEncodingException 编码异常
   */
  private String getValueFromCookie(HttpServletRequest request, String cacheKey) throws UnsupportedEncodingException {
    Cookie cookie = WebUtils.getCookie(request, cacheKey);
    return cookie != null? URLDecoder.decode(cookie.getValue(), CHARSET) : null;
  }
  
  /**
   * 取得POST请求的参数字符串
   * @param request 请求对象
   * @return 请求参数字符串形式
   * @throws UnsupportedEncodingException 编码异常
   */
  private String getParamsTypePost(HttpServletRequest request) throws UnsupportedEncodingException {
    Enumeration paramNames = request.getParameterNames();
    StringBuffer param = new StringBuffer();
    String paramName = null;
    String paramValue = null;
    while (paramNames.hasMoreElements()) {
      paramName = (String) paramNames.nextElement();
      paramValue = WebUtils.findParameterValue(request, paramName);
      if (paramValue != null && !paramValue.equals("")) {   
        param.append("&");
        param.append(URLEncoder.encode(paramName, CHARSET));
        param.append("=");
        // 对参数的值进行编码(主要用于中文)
        param.append(URLEncoder.encode(paramValue, CHARSET));
      }
    }
    return param.toString().replaceFirst("&", "?");
  }
  
  /**
   * 取得GET请求的参数字符串
   * @param request 请求对象
   * @return 请求参数字符串形式
   * @throws UnsupportedEncodingException 编码异常
   */
  private String getParamsTypeGet(HttpServletRequest request) throws UnsupportedEncodingException {
    String paramStr = "";
    String queryString = request.getQueryString();
    if (queryString != null && !queryString.equals("")) {
      paramStr = "?" + queryString;
    }
    return paramStr;
  }
}
