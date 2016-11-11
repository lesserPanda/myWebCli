package net.tuxun.core.mybatis.page;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Collate implements Serializable {

  // 排序字段
  private String sortField;
  // 排序类型
  private String sortType;

  public Collate() {
    super();
  }

  public Collate(String sortField, String sortType) {
    super();
    this.sortField = sortField;
    this.sortType = sortType;
  }

  public String getSortField() {
    return sortField;
  }

  public void setSortField(String sortField) {
    this.sortField = sortField;
  }

  public String getSortType() {
    return sortType;
  }

  public void setSortType(String sortType) {
    this.sortType = sortType;
  }
}
