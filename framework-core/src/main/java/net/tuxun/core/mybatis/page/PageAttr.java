package net.tuxun.core.mybatis.page;

import java.io.Serializable;


/**
 * 分页属性类
 * 
 * @author liuqiang
 * 
 */
@SuppressWarnings("serial")
public class PageAttr implements Serializable {

  // 默认的每页的记录数
  public static final int DEFAULT_INFO_SIZE = 15;
  // 最大的每页记录数
  public static final int MAX_INFO_SIZE = 100;

  // 当前页
  private int pageCur;
  // 上一页
  private int pagePrev;
  // 下一页
  private int pageNext;
  // 总页数
  private int pageTotal;

  // 每页记录数
  private int infoSize;
  // 信息总数
  private int infoTotal;

  public PageAttr() {
    this.pageCur = 1;
    this.infoSize = DEFAULT_INFO_SIZE;
    this.pagePrev = pageCur;
    this.pageNext = pageCur;
  }

  public PageAttr(int pageCur, int infoSize) {
    this.pageCur = pageCur;
    this.infoSize = infoSize;
    this.pagePrev = pageCur;
    this.pageNext = pageCur;
  }

  /**
   * 梳理PageAttr对象的属性 对属性的值进行正确的纠正与修改 梳理的顺序不能变
   */
  public void comb() {
    // 梳理infoSize
    if (infoSize < 1) {
      setInfoSize(DEFAULT_INFO_SIZE);
    }
    if (infoSize > MAX_INFO_SIZE) {
      setInfoSize(MAX_INFO_SIZE);
    }
    if (infoTotal > 0) {
      // 梳理pageTotal
      setPageTotal((infoTotal / infoSize) + ((infoTotal % infoSize > 0) ? 1 : 0));
      // 梳理pageCur
      if (pageCur < 1) {
        setPageCur(1);
      } else {
        setPageCur(pageCur > pageTotal ? pageTotal : pageCur);
      }
      // 梳理pagePrev
      setPagePrev((pageCur - 1 < 1) ? (1) : (pageCur - 1));
      // 梳理pageNext
      setPageNext((pageCur + 1 > pageTotal) ? (pageTotal) : (pageCur + 1));
    }
  }

  public int getPagePrev() {
    return pagePrev;
  }

  public void setPagePrev(int pagePrev) {
    this.pagePrev = pagePrev;
  }

  public int getPageCur() {
    return pageCur;
  }

  public void setPageCur(int pageCur) {
    this.pageCur = pageCur;
  }

  public int getPageNext() {
    return pageNext;
  }

  public void setPageNext(int pageNext) {
    this.pageNext = pageNext;
  }

  public int getPageTotal() {
    return pageTotal;
  }

  public void setPageTotal(int pageTotal) {
    this.pageTotal = pageTotal;
  }

  public int getInfoSize() {
    return infoSize;
  }

  public void setInfoSize(int infoSize) {
    this.infoSize = infoSize;
  }

  public int getInfoTotal() {
    return infoTotal;
  }

  public void setInfoTotal(int infoTotal) {
    this.infoTotal = infoTotal;
  }

}
