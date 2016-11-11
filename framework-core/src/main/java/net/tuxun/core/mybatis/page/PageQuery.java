package net.tuxun.core.mybatis.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 封装页面查询参数 <br>
 * 包括列表分页查询参数、列表内容查询参数、列表内容排序参数
 * 
 * @author liuqiang
 * 
 */
@SuppressWarnings("serial")
public class PageQuery implements Serializable {

  // 分页查询参数
  public PageAttr attr;

  // 内容查询参数
  public Map<String, String> search;

  // 内容整理排序参数
  public List<Collate> collates;

  // 分页样式参数
  public PageView.Style style;

  // 默认搜索内容
  public Map<String, String> searchDefault;
  // 默认排序规则
  public List<Collate> collatesDefault;

  /**
   * 添加搜索参数
   * 
   * @param key
   * @param value
   */
  public void search(String key, String value) {
    this.getSearch().put(key, value);
  }

  /**
   * 添加默认的搜索参数,会被用户的搜索参数替换
   * 
   * @param key
   * @param value
   */
  public void searchDefault(String key, String value) {
    this.getSearchDefault().put(key, value);
  }

  /**
   * 添加排序
   * 
   * @param sortField 排序字段
   * @param sortType 排序方式
   */
  public void order(String sortField, String sortType) {
    this.getCollates().add(new Collate(sortField, sortType));
  }

  /**
   * 添加默认的排序方法,会被用户的排序规则替换
   * 
   * @param sortField 排序字段
   * @param sortType 排序方式
   */
  public void orderDefault(String sortField, String sortType) {
    this.getCollatesDefault().add(new Collate(sortField, sortType));
  }

  /**
   * 取得当前的搜索内容
   * 
   * @return
   */
  public Map<String, String> getCurSearch() {
    this.getSearchDefault().putAll(getSearch());
    this.setSearch(getSearchDefault());
    // this.setSearchDefault(null);
    return this.getSearch();
  }

  /**
   * 取得当前的排序规则
   * 
   * @return
   */
  public List<Collate> getCurCollates() {
    if (collates.isEmpty()) {
      this.setCollates(this.getCollatesDefault());
    }
    return collates;
  }

  public PageQuery() {
    super();
    this.attr = new PageAttr();
    this.search = new HashMap<String, String>();
    this.collates = new ArrayList<Collate>();
    this.style = PageView.Style.FORE;

    this.searchDefault = new HashMap<String, String>();
    this.collatesDefault = new ArrayList<Collate>();
  }

  public PageQuery(PageAttr attr, Map<String, String> search) {
    super();
    this.attr = attr;
    this.search = search;
    this.collates = new ArrayList<Collate>();
    this.style = PageView.Style.FORE;
    this.searchDefault = new HashMap<String, String>();
    this.collatesDefault = new ArrayList<Collate>();
  }

  public PageAttr getAttr() {
    return attr;
  }

  public void setAttr(PageAttr attr) {
    this.attr = attr;
  }

  public Map<String, String> getSearch() {
    return search;
  }

  public void setSearch(Map<String, String> search) {
    this.search = search;
  }

  public List<Collate> getCollates() {
    return collates;
  }

  public void setCollates(List<Collate> collates) {
    this.collates = collates;
  }

  public List<Collate> getCollatesDefault() {
    return collatesDefault;
  }

  public void setCollatesDefault(List<Collate> collatesDefault) {
    this.collatesDefault = collatesDefault;
  }

  public Map<String, String> getSearchDefault() {
    return searchDefault;
  }

  public void setSearchDefault(Map<String, String> searchDefault) {
    this.searchDefault = searchDefault;
  }

  public PageView.Style getStyle() {
    return style;
  }

  public void setStyle(PageView.Style style) {
    this.style = style;
  }

}
