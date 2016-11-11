package net.tuxun.core.mybatis.util;


/**
 * 定义了分页拦截器用到的一些常量
 * 
 * @author liuqiang
 */
public interface Constant {

  // 分页的id后缀
  String SUFFIX_PAGE = "_List";

  // count查询的id后缀
  String SUFFIX_COUNT = "_Count";

  // 第一个分页参数
  String PAGEPARAMETER_FIRST = "First" + SUFFIX_PAGE;

  // 第二个分页参数
  String PAGEPARAMETER_SECOND = "Second" + SUFFIX_PAGE;

  // 用于保存注解BoundSql的key
  String PROVIDER_OBJECT = "_provider_object";

  // 存储原始的参数
  String ORIGINAL_PARAMETER_OBJECT = "_ORIGINAL_PARAMETER_OBJECT";

  // 分页ID规则
  String PAGE_PATTERN = "Page";

}
