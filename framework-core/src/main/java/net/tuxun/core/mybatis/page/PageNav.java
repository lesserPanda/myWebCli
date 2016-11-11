package net.tuxun.core.mybatis.page;

import java.io.Serializable;
import java.util.List;

/**
 * 分页组件
 * 
 * @author liuqiang
 */
@SuppressWarnings("serial")
public class PageNav<E> extends PageQuery implements Serializable {

  // 分页数据
  private List<E> list;

  // html分页控制元素
  private String view;

  public PageNav() {
    super();
  }

  public PageNav(PageQuery query, List<E> list) {
    super();
    this.attr = query.getAttr();
    this.search = query.getSearch();
    this.list = list;
    this.style = query.getStyle();
    this.view = PageView.assemble(query.getAttr(), this.style);
  }

  public List<E> getList() {
    return list;
  }

  public void setList(List<E> list) {
    this.list = list;
  }

  public String getView() {
    return view;
  }

  public void setView(String view) {
    this.view = view;
  }

}
