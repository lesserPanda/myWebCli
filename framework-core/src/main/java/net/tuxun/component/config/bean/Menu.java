package net.tuxun.component.config.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Menu implements Comparable<Menu>, Serializable {

  // 排序
  private int sort;
  // 菜单标识
  private String key;
  // 父菜单标识
  private String parentKey;
  // 菜单名称
  private String name;
  // 菜单的URL
  private String url;
  // 子菜单
  private List<Menu> child;
  // 拥有权限
  private List<Perm> perms;
  // 图片
  private String img;
  // 是否是默认的URL,当url!=null起作用，且只能有一个
  private boolean defaultPage;
  // 统计数量的URL
  private String count;


  public Menu() {
    super();
  }

  /**
   * 构造
   * 
   * @param sort
   * @param key
   * @param name
   */
  public Menu(String sort, String key, String name) {
    if (sort == null || key == null || name == null) {
      throw new RuntimeException("<menu></menu>元素必需要同时指定sort,key,name属性");
    }
    try {
      this.sort = Integer.parseInt(sort);
    } catch (Exception e) {
      throw new RuntimeException("排序sort请使用数字表示");
    }
    this.key = key;
    this.name = name;
  }

  // 排序比较
  @Override
  public int compareTo(Menu menu) {
    return this.getSort() - menu.getSort();
  }

  public int getSort() {
    return sort;
  }

  public void setSort(int sort) {
    this.sort = sort;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getParentKey() {
    return parentKey;
  }

  public void setParentKey(String parentKey) {
    this.parentKey = parentKey;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public List<Menu> getChild() {
    return child;
  }

  public void setChild(List<Menu> child) {
    this.child = child;
  }

  public List<Perm> getPerms() {
    return perms;
  }

  public void setPerms(List<Perm> perms) {
    this.perms = perms;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public boolean isDefaultPage() {
    return defaultPage;
  }

  public void setDefaultPage(boolean defaultPage) {
    this.defaultPage = defaultPage;
  }

  public String getCount() {
    return count;
  }

  public void setCount(String count) {
    this.count = count;
  }

  public static void main(String args[]) {
    List<String> list = new ArrayList<String>();
    list.add("1");
    list.add("2");
    list.add("3");
    list.add("4");
    list.add("5");
    list.add("6");
    list.add("7");
    for (String string : list) {
      if (string.equals("2")) {
        list.remove(string);
      } else {
        System.out.println(string);
      }
    }
  }
}
