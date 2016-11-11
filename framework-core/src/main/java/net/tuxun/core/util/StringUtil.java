package net.tuxun.core.util;

import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用工具
 * 
 * <p>字段串的操作,时间操作,MD5加密,上传文件
 */
public class StringUtil {

  /**
   * md5加密
   * @param x
   * @return 加密后的字符串
   */
  public static String md5(String x) {
    try {
      MessageDigest m = MessageDigest.getInstance("MD5");
      m.update(x.getBytes("UTF8"));
      byte s[] = m.digest();
      String result = "";
      for (int i = 0; i < s.length; i++) {
        result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00)
            .substring(6);
      }
      return result;
    } catch (Exception e) {
      System.out.println("Tools.md5 加密[" + x + "]失败");
      return null;
    }
  }

  /**
   * 字符串是否可以转化成Double形式
   * @param str
   * @return
   */
  public static boolean isDouble(String str){
    Pattern pattern = Pattern.compile("^[-\\+]?\\d+(\\.\\d*)?|\\.\\d+$");  
    return pattern.matcher(str).matches();
  }

  /**
   * 是否可以转化为整数
   * @param str
   * @return
   */
  public static boolean isInteger(String str){
    Pattern pattern = Pattern.compile("[0-9]*");
    return pattern.matcher(str).matches();  
  } 

  /**
   * 是否可以转化为数字
   * @param str
   * @return
   */
  public static boolean isNumber(String str){
    return isInteger(str) || isDouble(str);  
  } 


  /**
   * 判断str是否为空或为空字符串,成立返回false,反之返回true
   * @param str
   * @return
   */
  public static boolean isNullChar(String str){
    return str!=null && !str.trim().equals("");
  }

  /** 
   * 获得随机数字符串 
   * 
   * @param length 
   *            需要获得随机数的长度 
   * @param type 
   *            随机数的类型：'0':表示仅获得数字随机数；'1'：表示仅获得字符随机数；'2'：表示获得数字字符混合随机数 
   * @return 
   */
  public static String getRandomStr(int length, int type) {
    String strRandom = "";

    Random rnd = new Random();

    if (length < 0) {
      length = 5;
    }

    if ((type > 2) || (type < 0)) {
      type = 2;
    }

    switch (type) {
      case 0:
        for (int iLoop = 0; iLoop < length; iLoop++) {
          strRandom += Integer.toString(rnd.nextInt(10));
        }
        break;
      case 1:
        for (int iLoop = 0; iLoop < length; iLoop++) {
          strRandom += Integer.toString((35 - rnd.nextInt(10)), 36);
        }
        break;
      case 2:
        for (int iLoop = 0; iLoop < length; iLoop++) {
          strRandom += Integer.toString(rnd.nextInt(36), 36);
        }
        break;
    }
    return strRandom;
  }


  /**
   * 验证输入字符串是否符合既定的规则
   * @param inputString 输入的字符串
   * @param regexString 格式正则表达式
   * @return true | false
   */
  public static boolean chkInputByRegex(String inputString,String regexString){
    Pattern p=Pattern.compile(regexString);
    Matcher m=p.matcher(inputString);
    return m.matches();		
  }

  /**
   * 检测参数的合法性
   * @param str
   * @return 如果正确返回true
   */
  public static boolean isActive(String param){
    if(param == null){			
      return false;
    }
    param = param.trim();

    if(param.equals("") || param.equalsIgnoreCase("all")){			
      return false;
    }
    return true;
  }
  /**
   * 截取机构代码前八位,然后再去掉后面的“00”
   * @param orgCode
   * @return
   */
  public static String trimAreaCode(String orgCode) {
    if(orgCode == null){
      return null;
    }
    if(orgCode.length() > 8){
      orgCode = orgCode.substring(0,8);
    }
    if (orgCode.endsWith("000000")) {
      return orgCode.substring(0,2);
    }
    if (orgCode.endsWith("0000")) {
      return orgCode.substring(0,4);
    }
    if (orgCode.endsWith("00")) {
      return orgCode.substring(0,6);
    }
    return orgCode;
  }
  /**
   * 截取地区代码,然后再去掉后面的“00”
   * @param areaCode
   * @return
   */
  public static String subAreaCode(String areaCode) {
    if(areaCode == null){
      return null;
    }
    return areaCode.replaceAll("(00)+$", "");
   /* if (areaCode.endsWith("00000000")) {
      return areaCode.substring(0,2);
    }else if (areaCode.endsWith("000000")) {
      return areaCode.substring(0,4);
    }else if (areaCode.endsWith("0000")) {
      return areaCode.substring(0,6);
    }else if (areaCode.endsWith("00")) {
      return areaCode.substring(0,8);
    }*/
    //return areaCode;
  }

  public static String trimProfessionTypeCode(String code) {
    if(code == null){
      return null;
    }
    if(code.length() > 8){
      code = code.substring(0,8);
    }
    if (code.endsWith("000000")) {
      return code.substring(0,2);
    }
    if (code.endsWith("000")) {
      return code.substring(0,5);
    }
    return code;
  }

  public static Map<String,String> strConvertToMap(String strs){
    Map<String, String> map = new LinkedHashMap<String, String>();
    String s[];
    String k;
    String v;
    if(strs.indexOf(":") != -1){			
      for (String str : strs.split(",")) {
        s = str.split(":");
        k = s[0];
        v = s[1];
        map.put(k.substring(k.indexOf("'")+1,k.lastIndexOf("'")), v.substring(v.indexOf("'")+1,v.lastIndexOf("'")));
      }	
    }else{
      for (String str : strs.split(",")) {
        k = str.substring(str.indexOf("'")+1,str.lastIndexOf("'"));
        map.put(k, k);
      }
    }
    return map;
  }

  public static void main(String args[]){
    System.out.println(StringUtil.md5("123456"));
  }
}
