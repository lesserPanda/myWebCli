package net.tuxun.component.config.bean;

import java.io.Serializable;


@SuppressWarnings("serial")
public class Perm implements Comparable<Perm>, Serializable {
  // 排序
  private int sort;
  // 名称
  private String name;
  // 权限
  private String code;

  public Perm(String sort, String name, String code) {
    if (sort == null || name == null || code == null) {
      throw new RuntimeException("<perm />元素必需要同时指定sort,name,code属性");
    }
    try {
      this.sort = Integer.parseInt(sort);
    } catch (Exception e) {
      throw new RuntimeException("排序sort请使用数字表示");
    }
    this.name = name;
    this.code = code;
  }

  // 排序比较
  @Override
  public int compareTo(Perm menu) {
    return this.getSort() - menu.getSort();
  }


  public int getSort() {
    return sort;
  }

  public void setSort(int sort) {
    this.sort = sort;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}
