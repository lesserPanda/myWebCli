package net.tuxun.core.util;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class ServletUtil {

  private static final String NUKNOWN = "unknown";
  private static final String[] ADDR_HEADER = {"X-Forwarded-For", "Proxy-Client-IP",
      "WL-Proxy-Client-IP", "X-Real-IP"};

  /**
   * 获得真实IP地址。在使用了反向代理时，直接用HttpServletRequest.getRemoteAddr()无法获取客户真实的IP地址。
   * 
   * @param request
   * @return
   */
  public static String getRemoteAddr(HttpServletRequest request) {
    String addr = null;
    for (String header : ADDR_HEADER) {
      if (StringUtils.isBlank(addr) || NUKNOWN.equalsIgnoreCase(addr)) {
        addr = request.getHeader(header);
      } else {
        break;
      }
    }
    if (StringUtils.isBlank(addr) || NUKNOWN.equalsIgnoreCase(addr)) {
      addr = request.getRemoteAddr();
    } else {
      // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按','分割
      int i = addr.indexOf(",");
      if (i > 0) {
        addr = addr.substring(0, i);
      }
    }
    return addr;
  }

  public static String getRemoteAddr(ServletRequest request) {
    HttpServletRequest hsr = (HttpServletRequest) request;
    return getRemoteAddr(hsr);
  }
}
