package net.tuxun.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 身份证检测与转换工具 多线程安全
 */
public class IDCardUtil {
  // 15位身份证
  private final static int OLD_LENGTH = 15;
  // 18位身份证
  private final static int NEW_LENGTH = 18;
  // 18位身份证中最后一位校验码
  private final static char[] VERIFY_CODE = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
  // 18位身份证中，各个数字的生成校验码时的权值
  private final static int[] VERIFY_CODE_WEIGHT = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8,
      4, 2};
  // 身份证号码中的出生日期的格式
  private final static String BIRTH_DATE_FORMAT = "yyyyMMdd";
  // 身份证的最小出生日期,1900年1月1日
  private final static Date MINIMAL_BIRTH_DATE = new Date(-2209017600000L);

  /**
   * 用于查询验证 身份证的过滤修补，不详细验证,不合规范的身份证全部返回""空字符串 1.如果是15位,则自动转换成18位 2.x大写X
   * 
   * @param idCardNumber
   * @return
   */
  public static String filterFix(String idCardNumber) {
    String str = "";
    if (idCardNumber != null) {
      idCardNumber = idCardNumber.trim();
      final int lgh = idCardNumber.length();
      if (lgh == OLD_LENGTH) {
        str = change(idCardNumber);
      }
      if (lgh == NEW_LENGTH) {
        str = idCardNumber.toUpperCase();
      }
      str = ToDBC(str);
    }
    return str;
  }

  /**
   * 用于保存或修改 身份证的过滤修补验证,不会规范的身份证全部返回null 如果是15位,则自动转换成18位
   * 
   * @param idCardNumber 如果返回值不为null，则是正确的18位身份证信息，否则全返回null
   * @return
   */
  public static String filterFixCheck(String idCardNumber) {
    String str = null;
    if (idCardNumber != null) {
      idCardNumber = idCardNumber.trim();
      final int lgh = idCardNumber.length();
      if (lgh != OLD_LENGTH && lgh != NEW_LENGTH) {
        return null;
      }
      if (lgh == OLD_LENGTH) {
        str = change(idCardNumber);
      }
      if (lgh == NEW_LENGTH) {
        str = idCardNumber.toUpperCase();
      }
      if (!validate(str)) {
        return null;
      }
      str = ToDBC(str);
    }
    return str;
  }

  /**
   * 对18位身份证的验证
   * 
   * @param idCardNumberNew 18位身份证
   * @return
   */
  public static boolean validate(String idCardNumber) {
    boolean result = true;
    // 1.身份证号不能为空
    result = result && (null != idCardNumber);
    // 2.如果是全角字符串，转半角
    idCardNumber = ToDBC(idCardNumber);
    // 3.身份证号长度是18(新证)
    result = result && NEW_LENGTH == idCardNumber.length();
    // 4.身份证号的前17位必须是阿拉伯数字
    for (int i = 0; result && i < NEW_LENGTH - 1; i++) {
      char ch = idCardNumber.charAt(i);
      result = result && ch >= '0' && ch <= '9';
    }
    // 5.身份证号的第18位校验正确
    result = result && (calculateVerifyCode(idCardNumber) == idCardNumber.charAt(NEW_LENGTH - 1));

    // 6.出生日期不能晚于当前时间，并且不能早于1900年
    try {
      Date birthDate = getBirthDate(idCardNumber);
      result = result && null != birthDate;
      result = result && birthDate.before(new Date());
      result = result && birthDate.after(MINIMAL_BIRTH_DATE);
      /**
       * 出生日期中的年、月、日必须正确,比如月份范围是[1,12],日期范围是[1,31]，还需要校验闰年、大月、小月的情况时， 月份和日期相符合
       */
      String birthdayPart = getBirthDayPart(idCardNumber);
      String realBirthdayPart = createBirthDateParser().format(birthDate);
      result = result && (birthdayPart.equals(realBirthdayPart));
    } catch (Exception e) {
      result = false;
    }
    return result;
  }

  // 15 转18
  private static String change(String idCardNumber) {
    StringBuilder buf = new StringBuilder(NEW_LENGTH);
    buf.append(idCardNumber.substring(0, 6));
    buf.append("19");
    buf.append(idCardNumber.substring(6));
    buf.append(calculateVerifyCode(buf));
    return buf.toString();
  }

  // 取得校验码（18位身份证的最后一位）
  private static char calculateVerifyCode(CharSequence cs) {
    int sum = 0;
    for (int i = 0; i < NEW_LENGTH - 1; i++) {
      char ch = cs.charAt(i);
      sum += ((int) (ch - '0')) * VERIFY_CODE_WEIGHT[i];
    }
    return VERIFY_CODE[sum % 11];
  }

  // 取得生日
  public static Date getBirthDate(String idCardNumber) {
    DateFormat df = createBirthDateParser();
    Date date = null;
    try {
      date = df.parse(getBirthDayPart(idCardNumber));
    } catch (Exception e) {
      // e.printStackTrace();
    }
    return date;
  }

  // 截取身份证中的生日
  public static String getBirthDayPart(String idCardNumber) {
    if (idCardNumber != null) {
      return idCardNumber.substring(6, 14);
    } else {
      return "";
    }
  }

  // 格式化生日
  public static SimpleDateFormat createBirthDateParser() {
    return new SimpleDateFormat(BIRTH_DATE_FORMAT);
  }

  /**
   * 转半角的函数(DBC case)
   * 
   * 任意字符串 半角字符串
   * 
   * 全角空格为12288，半角空格为32 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
   */
  public static String ToDBC(String input) {
    char[] c = input.toCharArray();
    for (int i = 0; i < c.length; i++) {
      if (c[i] == 12288) {
        c[i] = (char) 32;
        continue;
      }
      if (c[i] > 65280 && c[i] < 65375)
        c[i] = (char) (c[i] - 65248);
    }
    return new String(c);
  }

  public static void main(String args[]) {
    String str = "330324197112213429";
    System.out.println(filterFixCheck(str));
  }
}
