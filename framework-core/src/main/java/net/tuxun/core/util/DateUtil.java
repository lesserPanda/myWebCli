package net.tuxun.core.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * 常用工具
 * 
 * <p>
 * 字段串的操作,时间操作,MD5加密,上传文件
 */
public class DateUtil {
  // 用来全局控制 上一周，本周，下一周的周数变化
  private int weeks = 0;
  private int MaxDate;// 一月最大天数
  private int MaxYear;// 一年最大天数

  /**
   * 格式化时间
   * 
   * @param dateTime 要格式化的时间
   * @param pattern 格式化的样式
   * @return 已格式化的时间
   */
  public static String formatDateTime(Date dateTime, String pattern) {
    SimpleDateFormat dateFmt = new SimpleDateFormat(pattern);
    return dateTime == null ? "" : dateFmt.format(dateTime);
  }

  public static Date parseDateTime(String datetime, String pattern) {
    SimpleDateFormat dateFmt = new SimpleDateFormat(pattern);
    try {
      return dateFmt.parse(datetime);
    } catch (ParseException e) {
      return null;
    }
  }

  /**
   * 取得时间
   * 
   * @param dateTime
   * @return 返回"2007"数据格式的字符串
   */
  public static String miniTime(Date dateTime) {
    return formatDateTime(dateTime, "yyyy");
  }

  /**
   * 取得时间
   * 
   * @param dateTime
   * @return 返回"2007-09-10"数据格式的字符串
   */
  public static String shortTime(Date dateTime) {
    return formatDateTime(dateTime, "yyyy-MM-dd");
  }

  /**
   * 取得时间
   * 
   * @param dateTime
   * @return 返回"2007-09-10 16:09"数据格式的字符串
   */
  public static String middleTime(Date dateTime) {
    return formatDateTime(dateTime, "yyyy-MM-dd HH:mm");
  }

  /**
   * 取得时间
   * 
   * @param dateTime
   * @return 返回"2007-09-10 16:09:00"数据格式的字符串
   */
  public static String longTime(Date dateTime) {
    return formatDateTime(dateTime, "yyyy-MM-dd HH:mm:ss");
  }

  /**
   * 取得时间
   * 
   * @param dateTime
   * @return 返回"20070910160900"数据格式的字符串
   */
  public static String minLongTime(Date dateTime) {
    return formatDateTime(dateTime, "yyyyMMddHHmmss");
  }

  /**
   * 取得时间
   * 
   * @param dateTime
   * @return 返回"2007年09月10号 16点09分00秒"数据格式的字符串
   */
  public static String longZhTime(Date dateTime) {
    return formatDateTime(dateTime, "yyyy年MM月dd号 HH点mm分ss秒");
  }

  /**
   * 取得时间
   * 
   * @param dateTime
   * @return 返回"2007/09/10 16:09:00.000"数据格式的字符串
   */
  public static String bigLongTime(Date dateTime) {
    return formatDateTime(dateTime, "yyyy/MM/dd HH:mm:ss.SSS");
  }

  /**
   * 时间+-天数 :要得到的时间
   * 
   * @param d 时间
   * @param offset 天数
   * @param bool true天数加false天数减
   * @return
   */
  public static Date changeDay(Date d, int offset, boolean bool) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(d);
    if (bool) {
      calendar.set(Calendar.DAY_OF_YEAR, (calendar.get(Calendar.DAY_OF_YEAR) + offset));
    } else {
      calendar.set(Calendar.DAY_OF_YEAR, (calendar.get(Calendar.DAY_OF_YEAR) - offset));
    }
    return calendar.getTime();
  }

  /**
   * 时间+-天数 :要得到的时间
   * 
   * @param d 时间
   * @param offset 天数
   * @param bool true天数加false天数减
   * @return
   */
  public static Timestamp changeDay(Timestamp d, int offset, boolean bool) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(d);
    if (bool) {
      calendar.set(Calendar.DAY_OF_YEAR, (calendar.get(Calendar.DAY_OF_YEAR) + offset));
    } else {
      calendar.set(Calendar.DAY_OF_YEAR, (calendar.get(Calendar.DAY_OF_YEAR) - offset));
    }
    return new Timestamp(calendar.getTimeInMillis());
  }

  /**
   * 时间+-多少年 :要得到的时间
   * 
   * @param d 时间
   * @param offset 年数
   * @param bool true年数加false年数减
   * @return
   */
  public static Date changeYear(Date d, int offset, boolean bool) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(d);
    if (bool) {
      calendar.set(Calendar.YEAR, (calendar.get(Calendar.YEAR) + offset));
    } else {
      calendar.set(Calendar.YEAR, (calendar.get(Calendar.YEAR) - offset));
    }
    return calendar.getTime();
  }

  /**
   * 时间+-多少年 :要得到的时间
   * 
   * @param d 时间
   * @param offset 年数
   * @param bool true年数加false年数减
   * @return
   */
  public static Timestamp changeYear(Timestamp d, int offset, boolean bool) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(d);
    if (bool) {
      calendar.set(Calendar.YEAR, (calendar.get(Calendar.YEAR) + offset));
    } else {
      calendar.set(Calendar.YEAR, (calendar.get(Calendar.YEAR) - offset));
    }
    return new Timestamp(calendar.getTimeInMillis());
  }

  /**
   * 取得结束的时间 如果参数为2007，则返回2007-12-31 23:59:59 如果参数为2007-08 ，则返回2007-08-31 23:59:59 如果参数为2007-09
   * ，则返回2007-09-30 23:59:59 如果参数为2007-09-09 ，则返回2007-09-09 23:59:59
   * 
   * @param time yyyy yyyy-MM yyyy-MM-dd形式
   * @return yyyy-MM-dd HH:mm:ss
   */
  public static String getTimeEnd(final String time) {
    Calendar timeEnd = Calendar.getInstance();
    if (time != null) {
      if (time.length() == 4) {
        timeEnd.set(Calendar.YEAR, Integer.parseInt(time));
        timeEnd.set(Calendar.MONTH, 11);
        timeEnd.set(Calendar.DATE, 1);
        timeEnd.roll(Calendar.DATE, -1);
        timeEnd.set(Calendar.HOUR_OF_DAY, 23);
        timeEnd.set(Calendar.MINUTE, 59);
        timeEnd.set(Calendar.SECOND, 59);
      }
      if (time.length() == 7) {
        timeEnd.set(Calendar.YEAR, Integer.parseInt((time.split("-"))[0]));
        timeEnd.set(Calendar.MONTH, Integer.parseInt((time.split("-"))[1]) - 1);
        timeEnd.set(Calendar.DATE, 1);
        timeEnd.roll(Calendar.DATE, -1);
        timeEnd.set(Calendar.HOUR_OF_DAY, 23);
        timeEnd.set(Calendar.MINUTE, 59);
        timeEnd.set(Calendar.SECOND, 59);
      }
      if (time.length() == 10) {
        timeEnd.set(Calendar.YEAR, Integer.parseInt((time.split("-"))[0]));
        timeEnd.set(Calendar.MONTH, Integer.parseInt((time.split("-"))[1]) - 1);
        timeEnd.set(Calendar.DATE, Integer.parseInt((time.split("-"))[2]));
        timeEnd.set(Calendar.HOUR_OF_DAY, 23);
        timeEnd.set(Calendar.MINUTE, 59);
        timeEnd.set(Calendar.SECOND, 59);
      }
    }
    return formatDateTime(timeEnd.getTime(), "yyyy-MM-dd HH:mm:ss");
  }

  /**
   * 取得起始的时间 如果参数为2007，则返回2007-01-01 00:00:00 如果参数为2007-09 ，则返回2007-09-01 00:00:00 如果参数为2007-09-09
   * ，则返回2007-09-09 00:00:00
   * 
   * @param time yyyy yyyy-MM yyyy-MM-dd形式
   * @return yyyy-MM-dd HH:mm:ss
   */
  public static String getTimeStart(final String time) {
    Calendar timeStart = Calendar.getInstance();
    if (time != null) {
      if (time.length() == 4) {
        timeStart.set(Calendar.YEAR, Integer.parseInt(time));
        timeStart.set(Calendar.MONTH, 0);
        timeStart.set(Calendar.DATE, 1);
        timeStart.set(Calendar.HOUR_OF_DAY, 0);
        timeStart.set(Calendar.MINUTE, 0);
        timeStart.set(Calendar.SECOND, 0);
      }
      if (time.length() == 7) {
        timeStart.set(Calendar.YEAR, Integer.parseInt((time.split("-"))[0]));
        timeStart.set(Calendar.MONTH, Integer.parseInt((time.split("-"))[1]) - 1);
        timeStart.set(Calendar.DATE, 1);
        timeStart.set(Calendar.HOUR_OF_DAY, 0);
        timeStart.set(Calendar.MINUTE, 0);
        timeStart.set(Calendar.SECOND, 0);
      }
      if (time.length() == 10) {
        timeStart.set(Calendar.YEAR, Integer.parseInt((time.split("-"))[0]));
        timeStart.set(Calendar.MONTH, Integer.parseInt((time.split("-"))[1]) - 1);
        timeStart.set(Calendar.DATE, Integer.parseInt((time.split("-"))[2]));
        timeStart.set(Calendar.HOUR_OF_DAY, 0);
        timeStart.set(Calendar.MINUTE, 0);
        timeStart.set(Calendar.SECOND, 0);
      }

    }
    return formatDateTime(timeStart.getTime(), "yyyy-MM-dd HH:mm:ss");
  }

  /** 取得当前的时间 */
  public static Timestamp getNowTime() {
    return new Timestamp(System.currentTimeMillis());
  }

  /**
   * yyyy-MM-dd hh:mm:ss转换为date类型
   * 
   * @param str
   */
  public static Date getShortDate(String str) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Date date = null;
    try {
      date = df.parse(str);
    } catch (Exception e) {
      System.out.println("Tools.getDate失败");
      return null;
    }
    return date;
  }

  /**
   * yyyy-MM-dd hh:mm:ss转换为date类型
   * 
   * @param str
   */
  public static Date getDate(String str) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    Date date = null;
    try {
      date = df.parse(str);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return date;
  }

  /**
   * 取得时间段内的年的日期集合
   * 
   * @param beginDateStr
   * @param endDateStr
   * @return
   */
  public static String[] getYearList(String dateFrom, String dateEnd) {
    dateFrom = dateFrom.substring(0, 4);
    dateEnd = dateEnd.substring(0, 4);
    int df = Integer.valueOf(dateFrom);
    int de = Integer.valueOf(dateEnd);
    List<String> dateList = new ArrayList<String>();
    for (int i = df; i <= de; i++) {
      dateList.add("" + i);
    }
    String[] dateArray = new String[dateList.size()];
    dateList.toArray(dateArray);
    return dateArray;
  }

  /**
   * 取得时间段内的月的日期集合
   * 
   * @param beginDateStr
   * @param endDateStr
   * @return
   */
  public static String[] getMonthList(String dateFrom, String dateEnd) {
    // 指定要解析的时间格式
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
    List<String> dateList = new ArrayList<String>();
    // 定义一些变量
    Date beginDate = null;
    Date endDate = null;
    GregorianCalendar beginGC = null;
    GregorianCalendar endGC = null;
    try {
      // 将字符串parse成日期
      beginDate = f.parse(dateFrom);
      endDate = f.parse(dateEnd);
      // 设置日历
      beginGC = new GregorianCalendar();
      beginGC.setTime(beginDate);
      endGC = new GregorianCalendar();
      endGC.setTime(endDate);
      // 直到两个时间相同
      while (beginGC.getTime().compareTo(endGC.getTime()) <= 0) {
        dateList.add(beginGC.get(Calendar.YEAR) + "-" + getM(beginGC.get(Calendar.MONTH) + 1));
        // 以月为单位，增加时间
        beginGC.add(Calendar.MONTH, 1);
      }
      dateList.add(beginGC.get(Calendar.YEAR) + "-" + getM(beginGC.get(Calendar.MONTH) + 1));
      String[] dateArray = new String[dateList.size()];
      dateList.toArray(dateArray);
      return dateArray;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  private static String getM(int i) {
    String st = "" + i;
    st = "00".substring(st.length()) + st;
    return st;
  }

  /**
   * 取得时间段内的日的日期集合
   * 
   * @param dateFrom
   * @param dateEnd
   * @return
   */
  public static String[] getDayList(String dateFrom, String dateEnd) {
    long time = 1l;
    long perDayMilSec = 24 * 60 * 60 * 1000;
    List<String> dateList = new ArrayList<String>();
    dateList.add(dateFrom);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    while (true) {
      try {
        time = sdf.parse(dateFrom).getTime();
        time = time + perDayMilSec;
        dateFrom = sdf.format(new Date(time));
        if (dateFrom.compareTo(dateEnd) < 0) {
          dateList.add(dateFrom);
        } else {
          break;
        }
      } catch (Exception e) {
        e.printStackTrace();
        break;
      }
    }
    dateList.add(dateEnd);
    String[] dateArray = new String[dateList.size()];
    dateList.toArray(dateArray);
    return dateArray;

  }

  /**
   * 返回常用的日期格式:yyyy-MM-dd
   * 
   * @param date
   * @return
   */
  public static String shortDate(Date date) {
    return formatDateTime(date, "yyyy-MM-dd");
  }

  /**
   * 返回中文日期格式：yyyy年MM月dd日
   * 
   * @param date
   * @return
   */
  public static String chineseDate(Date date) {
    return formatDateTime(date, "yyyy年MM月dd日");
  }

  /**
   * 将含有日期的字符串转化为Date型。
   * 
   * @param dateString 含有日期的字符串。格式为：yyyy-MM-dd
   * @return
   */
  public static Date parseDate(String dateString) {
    return parseDate(dateString, '-');
  }

  /**
   * 将含有日期的字符串转化为Date型。
   * 
   * @param dateString 含有日期的字符串。使用separator作为分隔符。
   * @param separator 年月日间分隔符
   * @return
   */

  public static Date parseDate(String dateString, char separator) {
    Date date = null;

    if (dateString == null || dateString.length() == 0)
      return null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + separator + "MM" + separator + "dd");
    try {
      date = sdf.parse(dateString);
    } catch (ParseException ex) {

    } finally {

    }
    return date;
  }

  /**
   * 得到二个日期间的间隔天数
   */
  public static String getTwoDay(String sj1, String sj2) {
    SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
    long day = 0;
    try {
      java.util.Date date = myFormatter.parse(sj1);
      java.util.Date mydate = myFormatter.parse(sj2);
      day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
    } catch (Exception e) {
      return "";
    }
    return day + "";
  }

  /**
   * 根据一个日期，返回是星期几的字符串
   * 
   * @param sdate
   * @return
   */
  public static String getWeek(String sdate) {
    // 再转换为时间
    Date date = DateUtil.strToDate(sdate);
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    // int hour=c.get(Calendar.DAY_OF_WEEK);
    // hour中存的就是星期几了，其范围 1~7
    // 1=星期日 7=星期六，其他类推
    return new SimpleDateFormat("EEEE").format(c.getTime());
  }

  /**
   * 将短时间格式字符串转换为时间 yyyy-MM-dd
   * 
   * @param strDate
   * @return
   */
  public static Date strToDate(String strDate) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    ParsePosition pos = new ParsePosition(0);
    Date strtodate = formatter.parse(strDate, pos);
    return strtodate;
  }

  /**
   * 两个时间之间的天数
   * 
   * @param date1
   * @param date2
   * @return
   */
  public static long getDays(String date1, String date2) {
    if (date1 == null || date1.equals(""))
      return 0;
    if (date2 == null || date2.equals(""))
      return 0;
    // 转换为标准时间
    SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date date = null;
    java.util.Date mydate = null;
    try {
      date = myFormatter.parse(date1);
      mydate = myFormatter.parse(date2);
    } catch (Exception e) {
    }
    long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
    return day;
  }

  // 计算当月最后一天,返回字符串
  public String getDefaultDay() {
    String str = "";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Calendar lastDate = Calendar.getInstance();
    lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
    lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
    lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

    str = sdf.format(lastDate.getTime());
    return str;
  }

  // 上月第一天
  public String getPreviousMonthFirst() {
    String str = "";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Calendar lastDate = Calendar.getInstance();
    lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
    lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
    // lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天

    str = sdf.format(lastDate.getTime());
    return str;
  }

  // 获取当月第一天
  public String getFirstDayOfMonth() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar lastDate = Calendar.getInstance();
    lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
    String str = sdf.format(lastDate.getTime());
    return str;
  }

  // 获得本周星期日的日期
  public String getCurrentWeekday() {
    weeks = 0;
    int mondayPlus = this.getMondayPlus();
    GregorianCalendar currentDate = new GregorianCalendar();
    currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
    Date monday = currentDate.getTime();

    DateFormat df = DateFormat.getDateInstance();
    String preMonday = df.format(monday);
    return preMonday;
  }

  // 获取当天时间
  public static String getNowTime(String dateformat) {
    Date now = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
    String hehe = dateFormat.format(now);
    return hehe;
  }

  // 获得当前日期与本周日相差的天数
  private static int getMondayPlus() {
    Calendar cd = Calendar.getInstance();
    // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
    int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
    // System.out.println("相差天数:"+dayOfWeek);
    if (dayOfWeek == 1) {
      return 0;
    } else {
      return 1 - dayOfWeek;
    }
  }

  // 获得本周一的日期
  public String getMondayOFWeek() {
    weeks = 0;
    int mondayPlus = this.getMondayPlus();
    GregorianCalendar currentDate = new GregorianCalendar();
    currentDate.add(GregorianCalendar.DATE, mondayPlus);
    Date monday = currentDate.getTime();

    DateFormat df = DateFormat.getDateInstance();
    String preMonday = df.format(monday);
    return preMonday;
  }

  // 获得相应周的周六的日期
  public String getSaturday() {
    int mondayPlus = this.getMondayPlus();
    GregorianCalendar currentDate = new GregorianCalendar();
    currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);
    Date monday = currentDate.getTime();
    DateFormat df = DateFormat.getDateInstance();
    String preMonday = df.format(monday);
    return preMonday;
  }

  // 获得上周星期日的日期
  public String getPreviousWeekSunday() {
    weeks = 0;
    weeks--;
    int mondayPlus = this.getMondayPlus();
    GregorianCalendar currentDate = new GregorianCalendar();
    currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
    Date monday = currentDate.getTime();
    DateFormat df = DateFormat.getDateInstance();
    String preMonday = df.format(monday);
    return preMonday;
  }

  // 获得上周星期一的日期
  public String getPreviousWeekday() {
    weeks--;
    int mondayPlus = this.getMondayPlus();
    GregorianCalendar currentDate = new GregorianCalendar();
    currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
    Date monday = currentDate.getTime();
    DateFormat df = DateFormat.getDateInstance();
    String preMonday = df.format(monday);
    return preMonday;
  }

  // 获得下周星期一的日期
  public String getNextMonday() {
    weeks++;
    int mondayPlus = this.getMondayPlus();
    GregorianCalendar currentDate = new GregorianCalendar();
    currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
    Date monday = currentDate.getTime();
    DateFormat df = DateFormat.getDateInstance();
    String preMonday = df.format(monday);
    return preMonday;
  }

  // 获得下周星期日的日期
  public String getNextSunday() {

    int mondayPlus = this.getMondayPlus();
    GregorianCalendar currentDate = new GregorianCalendar();
    currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
    Date monday = currentDate.getTime();
    DateFormat df = DateFormat.getDateInstance();
    String preMonday = df.format(monday);
    return preMonday;
  }

  private int getMonthPlus() {
    Calendar cd = Calendar.getInstance();
    int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);
    cd.set(Calendar.DATE, 1);// 把日期设置为当月第一天
    cd.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
    MaxDate = cd.get(Calendar.DATE);
    if (monthOfNumber == 1) {
      return -MaxDate;
    } else {
      return 1 - monthOfNumber;
    }
  }

  // 获得上月最后一天的日期
  public String getPreviousMonthEnd() {
    String str = "";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Calendar lastDate = Calendar.getInstance();
    lastDate.add(Calendar.MONTH, -1);// 减一个月
    lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
    lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
    str = sdf.format(lastDate.getTime());
    return str;
  }

  // 获得下个月第一天的日期
  public String getNextMonthFirst() {
    String str = "";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Calendar lastDate = Calendar.getInstance();
    lastDate.add(Calendar.MONTH, 1);// 减一个月
    lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
    str = sdf.format(lastDate.getTime());
    return str;
  }

  // 获得下个月最后一天的日期
  public String getNextMonthEnd() {
    String str = "";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Calendar lastDate = Calendar.getInstance();
    lastDate.add(Calendar.MONTH, 1);// 加一个月
    lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
    lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
    str = sdf.format(lastDate.getTime());
    return str;
  }

  // 获得明年最后一天的日期
  public String getNextYearEnd() {
    String str = "";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Calendar lastDate = Calendar.getInstance();
    lastDate.add(Calendar.YEAR, 1);// 加一个年
    lastDate.set(Calendar.DAY_OF_YEAR, 1);
    lastDate.roll(Calendar.DAY_OF_YEAR, -1);
    str = sdf.format(lastDate.getTime());
    return str;
  }

  // 获得明年第一天的日期
  public String getNextYearFirst() {
    weeks--;
    Calendar cd = Calendar.getInstance();
    cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
    cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
    int MaxYear = cd.get(Calendar.DAY_OF_YEAR); // 获得本年有多少天
    int yearPlus = this.getYearPlus();
    GregorianCalendar currentDate = new GregorianCalendar();
    currentDate.add(GregorianCalendar.DATE, MaxYear - (yearPlus * weeks) + 2);
    Date yearDay = currentDate.getTime();
    DateFormat df = DateFormat.getDateInstance();
    String preYearDay = df.format(yearDay);
    return preYearDay;

  }

  // 获得本年有多少天
  private int getMaxYear() {
    Calendar cd = Calendar.getInstance();
    cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
    cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
    int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
    return MaxYear;
  }

  private int getYearPlus() {
    Calendar cd = Calendar.getInstance();
    int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
    cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
    cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
    int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
    if (yearOfNumber == 1) {
      return -MaxYear;
    } else {
      return 1 - yearOfNumber;
    }
  }

  // 获得本年第一天的日期
  public String getCurrentYearFirst() {
    int yearPlus = this.getYearPlus();
    GregorianCalendar currentDate = new GregorianCalendar();
    currentDate.add(GregorianCalendar.DATE, yearPlus);
    Date yearDay = currentDate.getTime();
    DateFormat df = DateFormat.getDateInstance();
    String preYearDay = df.format(yearDay);
    return preYearDay;
  }

  // 获得本年最后一天的日期 *
  public String getCurrentYearEnd() {
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
    String years = dateFormat.format(date);
    return years + "-12-31";
  }

  // 获得上年第一天的日期 *
  public String getPreviousYearFirst() {
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
    String years = dateFormat.format(date);
    int years_value = Integer.parseInt(years);
    years_value--;
    return years_value + "-1-1";
  }

  // 获得上年最后一天的日期
  public String getPreviousYearEnd() {
    weeks--;
    int yearPlus = this.getYearPlus();
    GregorianCalendar currentDate = new GregorianCalendar();
    currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks + (MaxYear - 1));
    Date yearDay = currentDate.getTime();
    DateFormat df = DateFormat.getDateInstance();
    String preYearDay = df.format(yearDay);
    getThisSeasonTime(11);
    return preYearDay;
  }

  // 获得本季度
  public String getThisSeasonTime(int month) {
    int array[][] = { {1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
    int season = 1;
    if (month >= 1 && month <= 3) {
      season = 1;
    }
    if (month >= 4 && month <= 6) {
      season = 2;
    }
    if (month >= 7 && month <= 9) {
      season = 3;
    }
    if (month >= 10 && month <= 12) {
      season = 4;
    }
    int start_month = array[season - 1][0];
    int end_month = array[season - 1][2];

    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
    String years = dateFormat.format(date);
    int years_value = Integer.parseInt(years);

    int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
    int end_days = getLastDayOfMonth(years_value, end_month);
    String seasonDate =
        years_value + "-" + start_month + "-" + start_days + ";" + years_value + "-" + end_month
            + "-" + end_days;
    return seasonDate;

  }

  /**
   * 获取某年某月的最后一天
   * 
   * @param year 年
   * @param month 月
   * @return 最后一天
   */
  private int getLastDayOfMonth(int year, int month) {
    if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10
        || month == 12) {
      return 31;
    }
    if (month == 4 || month == 6 || month == 9 || month == 11) {
      return 30;
    }
    if (month == 2) {
      if (isLeapYear(year)) {
        return 29;
      } else {
        return 28;
      }
    }
    return 0;
  }

  /**
   * 是否闰年
   * 
   * @param year 年
   * @return
   */
  public boolean isLeapYear(int year) {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
  }

  /**
   * 返回美国时间格式 26 Apr 2006
   * 
   * @param str
   * @return
   */
  public static String getEDate(String str) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    ParsePosition pos = new ParsePosition(0);
    Date strtodate = formatter.parse(str, pos);
    String j = strtodate.toString();
    String[] k = j.split(" ");
    return k[1] + "." + k[2] + "," + k[5].substring(0, 4);
  }

  /**
   * 获得当前时间一小时前的时间，格式化成yyyy-MM-dd HH:mm:ss:SS<br>
   * 
   * @return 当前时间一小时前的时间
   */
  public static String getHoursAgoTime(int advanceHours) {
    String oneHoursAgoTime = "";
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) - advanceHours);
    oneHoursAgoTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
    return oneHoursAgoTime;
  }

  public static String getLongTime(Date date) {
    String longTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    return longTime;
  }

  /**
   * 获得当前时间，格式化成yyyy-MM-dd HH:mm:ss<br>
   * 
   * @return 当前时间格式化成
   */
  public static Date parseFormattedTimestamp(String timeString) throws ParseException {
    Date date = null;
    if (timeString == null)
      return null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    date = sdf.parse(timeString);
    return (date == null) ? null : date;
  };

  /**
   * 计算时间
   * 
   * @param date 日期
   * @param amount 计算量
   * @return
   * @throws Exception 例子：计算当前时间的前10秒的时间？ TimeCalculate(new Date(), Calendar.SECOND, -10)
   *         例子：计算当前时间的后10秒的时间？ TimeCalculate(new Date(), Calendar.SECOND, 10)
   */
  public static Date TimeCalculate(Date date, int amount) throws Exception {
    Calendar cal = new GregorianCalendar();
    cal.setTime(date);
    cal.add(Calendar.SECOND, amount);
    return cal.getTime();
  }

  /**
   * 计算时间
   * 
   * @param date 日期
   * @param amount 计算量
   * @return
   * @throws Exception 例子：计算当前时间的前10秒的时间？ TimeCalculate(new Date(), Calendar.SECOND, -10)
   *         例子：计算当前时间的后10秒的时间？ TimeCalculate(new Date(), Calendar.SECOND, 10)
   */
  public static Date MinuteCalculate(Date date, int amount) throws Exception {
    Calendar cal = new GregorianCalendar();
    cal.setTime(date);
    cal.add(Calendar.MINUTE, amount);
    return cal.getTime();
  }

  public static String Differ(Date beginDate, Date endDate) {
    // 默认为毫秒，除以1000是为了转换成秒
    long interval = (endDate.getTime() - beginDate.getTime()) / 1000;// 秒
    long day = interval / (24 * 3600);// 天
    long hour = interval % (24 * 3600) / 3600;// 小时
    long minute = interval % 3600 / 60;// 分钟
    long second = interval % 60;// 秒
    return day + "天" + hour + "小时" + minute + "分" + second + "秒";
  }

  public static Long DifferDay(Date beginDate, Date endDate) {
    // 默认为毫秒，除以1000是为了转换成秒
    long interval = (endDate.getTime() - beginDate.getTime()) / 1000;// 秒
    long day = interval / (24 * 3600);// 天
    return day;
  }

  public static Long DifferHour(Date beginDate, Date endDate) {
    // 默认为毫秒，除以1000是为了转换成秒
    long interval = (endDate.getTime() - beginDate.getTime()) / 1000;// 秒
    long hour = interval / 3600;// 小时
    return hour;
  }

  public static Long DifferMinute(Date beginDate, Date endDate) {
    // 默认为毫秒，除以1000是为了转换成秒
    long interval = (endDate.getTime() - beginDate.getTime()) / 1000;// 秒
    long minute = interval / 60;// 分钟
    return minute;
  }

  public static Long DifferSecond(Date beginDate, Date endDate) {
    // 默认为毫秒，除以1000是为了转换成秒
    long interval = (endDate.getTime() - beginDate.getTime()) / 1000;// 秒
    return interval;
  }

  public static String chineseDate(Date date, String ormatDateTimeType) {
    SimpleDateFormat sdf = new SimpleDateFormat(ormatDateTimeType);
    return sdf.format(date);
  }

  // 方法名称：isSameDate(String date1,String date2)
  // 功能描述：判断date1和date2是否在同一周
  // 输入参数：date1,date2
  // 输出参数：
  // 返 回 值：false 或 true
  // 其它说明：主要用到Calendar类中的一些方法
  public static boolean isSameDate(String date1, String date2) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date d1 = null;
    Date d2 = null;
    try {
      d1 = format.parse(date1);
      d2 = format.parse(date2);
    } catch (Exception e) {
      e.printStackTrace();
    }
    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    cal1.setTime(d1);
    cal2.setTime(d2);
    int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
    // subYear==0,说明是同一年
    if (subYear == 0) {
      if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
        return true;
    }
    // 例子:cal1是"2005-1-1"，cal2是"2004-12-25"
    // java对"2004-12-25"处理成第52周
    // "2004-12-26"它处理成了第1周，和"2005-1-1"相同了
    // 大家可以查一下自己的日历
    // 处理的比较好
    // 说明:java的一月用"0"标识，那么12月用"11"
    else if (subYear == 1 && cal2.get(Calendar.MONTH) == 11) {
      if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
        return true;
    }
    // 例子:cal1是"2004-12-31"，cal2是"2005-1-1"
    else if (subYear == -1 && cal1.get(Calendar.MONTH) == 11) {
      if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
        return true;
    }
    return false;
  }

  public static boolean daysBetween(String date1, String date2) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    if (date1 != null && !"".equals(date1) && date2 != null && !"".equals(date2)) {
      try {
        Date dt1 = df.parse(date1);
        Date dt2 = df.parse(date2);
        if (dt1.getTime() > dt2.getTime()) {
          return true;
        } else if (dt1.getTime() < dt2.getTime()) {
          return false;
        } else {
          return false;
        }
      } catch (Exception exception) {
        exception.printStackTrace();
      }
      return false;
    } else {
      return false;
    }

  }

  /**
   * 返回英文格式时间格式化
   * 
   * @param date
   * @return
   */

  public static String getCustomDateString(Date date) {
    SimpleDateFormat format = new SimpleDateFormat("d");
    String dateStr = format.format(date);
    if (dateStr.endsWith("1") && !dateStr.endsWith("11"))
      format = new SimpleDateFormat("MMM d'st', yyyy", Locale.ENGLISH);
    else if (dateStr.endsWith("2") && !dateStr.endsWith("12"))
      format = new SimpleDateFormat("MMM d'nd', yyyy", Locale.ENGLISH);
    else if (dateStr.endsWith("3") && !dateStr.endsWith("13"))
      format = new SimpleDateFormat("MMM d'rd', yyyy", Locale.ENGLISH);
    else
      format = new SimpleDateFormat("MMM d'th', yyyy", Locale.ENGLISH);

    String yourDate = format.format(date);
    return yourDate;
  }

  /**
   * 将含有日期时间的字符串转化为Timestamp型。
   * 
   * @param timeString 含有日期时间的字符串。格式为：yyyy-MM-dd HH:mm:ss
   * @return
   */
  public static Timestamp parseTimestamp(String timeString) {
    Timestamp time = null;
    String format1 = "yyyy-MM-dd HH:mm:ss";
    String format2 = "yyyy-MM-dd HH:mm";
    String format3 = "yyyy-MM-dd";

    try {
      time = parseFormattedTimestamp(timeString, format1);
    } catch (ParseException ex) {
      try {
        time = parseFormattedTimestamp(timeString, format2);
      } catch (ParseException ex2) {
        try {
          time = parseFormattedTimestamp(timeString, format3);
        } catch (ParseException ex3) {
          //
        }

      }
    }

    return time;
  }

  private static Timestamp parseFormattedTimestamp(String timeString, String format)
      throws ParseException {
    Date date = null;

    if (timeString == null)
      return null;
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    date = sdf.parse(timeString);
    return (date == null) ? null : (new Timestamp(date.getTime()));
  }

  /**
   * 设置时间
   * 
   * @param dateTime 需要设置的日期类
   * @param year 年
   * @param month 月（1-12）
   * @param day 日（1-31）
   * @param hour 小时（0-23）
   * @param minute 分（0-59）
   * @param second 秒（0-59）
   * @param millisecond 毫秒（0-999）
   */
  public static void setTime(Calendar dateTime, int year, int month, int day, int hour, int minute,
      int second) {
    dateTime.set(Calendar.YEAR, year);
    dateTime.set(Calendar.MONTH, month - 1);
    dateTime.set(Calendar.DAY_OF_MONTH, day);
    dateTime.set(Calendar.HOUR_OF_DAY, hour);
    dateTime.set(Calendar.MINUTE, minute);
    dateTime.set(Calendar.SECOND, second);
    dateTime.set(Calendar.MILLISECOND, 0);
  }

  /**
   * 设置时间
   * 
   * @param dateTime 需要设置的日期类
   * @param hour 小时（0-23）
   * @param minute 分（0-59）
   * @param second 秒（0-59）
   * @param millisecond 毫秒（0-999）
   */
  public static void setTime(Calendar dateTime, int hour, int minute, int second, int millisecond) {
    dateTime.set(Calendar.HOUR_OF_DAY, hour);
    dateTime.set(Calendar.MINUTE, minute);
    dateTime.set(Calendar.SECOND, second);
    dateTime.set(Calendar.MILLISECOND, millisecond);
  }

  /**
   * 截掉日期的小时分秒部分
   * 
   * @param dateTime
   */
  public static void trim(Calendar dateTime) {
    dateTime.set(Calendar.HOUR_OF_DAY, 0);
    dateTime.set(Calendar.MINUTE, 0);
    dateTime.set(Calendar.SECOND, 0);
    dateTime.set(Calendar.MILLISECOND, 0);
  }
}
