package net.tuxun.core.util;

import java.util.Date;
import java.util.Random;
import java.util.UUID;


/**
 * @author Fantasy
 * 
 *         TODO To change the template for this generated type comment go to Window - Preferences -
 *         Java - Code Style - Code Templates
 */
public class IDGenerator {
  // private final static SimpleUUIDGenerator idGenerator = new SimpleUUIDGenerator();
  private static int counter = 0;
  private final static String baseStr = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ"; // 不生成0O1I，因为客户可能看不清楚0O1I的区别
                                                                            // 共32个字符 相当于32进制
  private final static long BASE_MILLI_SECONDS = 1104508800000L; // 2005-1-1的毫秒值

  /**
   * 
   * @return 32位随机数
   */
  public static String generateId() {

    UUID id = UUID.randomUUID();
    return id.toString().replace("-", "");
  }

  /**
   * <pre>
   * if (n &lt;= 0)
   *   throw new IllegalArgumentException(&quot;生成ID的位数不能为零&quot;);
   * int strlen = baseStr.length();
   * int s = new Long((System.currentTimeMillis() - BASE_MILLI_SECONDS) % strlen).intValue();
   * Random r = new Random();
   * StringBuffer id = new StringBuffer();
   * int k = 0;
   * for (int i = 0; i &lt; n; i++) {
   *   k = s % strlen;// 保证k为0&tilde;baseStr的长度之间的数
   *   s = s / (r.nextInt(k + 1) + 1) + (r.nextInt(strlen) * (k + 1));// 加1是为了不许不零
   *   id.append(baseStr.substring(k, k + 1));
   * }
   * return id.toString();
   * </pre>
   * 
   * @param n<=0
   * @return 生成N位随机数
   * @exception 当n小于等二零时出现异常: IllegalArgumentException
   */
  public static String generateId(int n) {

    if (n <= 0)
      throw new IllegalArgumentException("生成ID的位数不能为零");
    int strlen = baseStr.length();
    int s = new Long((System.currentTimeMillis() - BASE_MILLI_SECONDS) % strlen).intValue();
    Random r = new Random();
    StringBuffer id = new StringBuffer();
    int k = 0;
    for (int i = 0; i < n; i++) {
      k = Math.abs(s % strlen);// 保证k为0~baseStr的长度之间的数
      // s=s/(r.nextInt(k+1)+1)+(r.nextInt(strlen)*(k+1));//加1是为了不许不零
      s = r.nextInt(strlen) * (k + 1);
      id.append(baseStr.substring(k, k + 1));
    }
    return id.toString();
  }

  /**
   * 8位
   * 
   * @return
   */
  public static String generateVerify_code() {

    String verify_code = "";
    // 按自2005－-1-1日起的秒数 6位 (可够34年用)+ 2位顺序号（每秒申请32*32个以下不重复）
    long s = ((new Date()).getTime() - BASE_MILLI_SECONDS) / 1000;
    int k = 0;
    for (int i = 0; i < 6; i++) {
      // 取余
      k = (new Long(s % baseStr.length())).intValue();
      // 取模
      s = s / baseStr.length();
      //
      verify_code += baseStr.substring(k, k + 1);
    }
    int c = getCount();
    for (int i = 6; i < 8; i++) {
      // 取余
      k = c % baseStr.length();
      // 取模
      c = c / baseStr.length();
      //
      verify_code += baseStr.substring(k, k + 1);
    }
    return verify_code;
  }

  private static int getCount() {

    synchronized (IDGenerator.class) {
      if (counter > baseStr.length() * baseStr.length())
        counter = 0;
      return counter++;
    }
  }
}
