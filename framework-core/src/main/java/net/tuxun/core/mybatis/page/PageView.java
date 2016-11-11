package net.tuxun.core.mybatis.page;

public class PageView {

  /**
   * 分页的类型
   */
  public static enum Style {
    BACK, FORE, CMS, SEARCH, AJAX
  }


  /**
   * 根据分页属性及样式，组装成分页显示页面
   * 
   * @param attr
   * @param style
   * @return
   */
  public static String assemble(PageAttr attr, Style style) {
    attr.comb();
    String view = "";
    switch (style) {
      case BACK:
        view = back(attr);
        break;
      case FORE:
        view = fore(attr);
        break;
      case CMS:
        view = cms(attr);
        break;
      case SEARCH:
        view = search(attr);
        break;
      case AJAX:
        view = null;
        break;
      default:
        break;
    }
    return view;
  }

  private static String cms(PageAttr attr) {
    StringBuffer sb = new StringBuffer();
    sb.append("<div class='ls-page'>");
    sb.append("     <input type='button' onclick='javascript:$.turnPage(1);' value='&lt;&lt;'>");
    sb.append("     <input type='button' onclick='javascript:$.turnPage(" + attr.getPagePrev()
        + ");' value='&lt;'>");
    sb.append("     <input type='text' value='" + attr.getPageCur()
        + "' name='attr.pageCur' style='width:40px;text-align:right;'> / " + attr.getPageTotal());
    sb.append("     <input type='button' onclick='javascript:$.turnPage(" + attr.getPageNext()
        + ");' value='&gt;'>");
    sb.append("     <input type='button' onclick='javascript:$.turnPage(" + attr.getPageTotal()
        + ");' value='&gt;&gt;'>");
    sb.append("     每页<input style='width:30px;text-align:center;' type='text' onchange='javascript:$.submitForm();' value='"
        + attr.getInfoSize() + "' name='attr.infoSize'>条");
    sb.append("</div>");
    return sb.toString();
  }

  private static String back(PageAttr attr) {
    StringBuffer sb = new StringBuffer();
    sb.append("<div class='pageView'>");
    sb.append("<ul>");
    sb.append("    <li>每页<input type='text' name='attr.infoSize' value='" + attr.getInfoSize()
        + "' onchange='javascript:$.submitForm();'/>条</li>");
    sb.append("    <li>共" + attr.getInfoTotal() + "条</li>");
    sb.append("    <li><a href='javascript:$.turnPage(1);'>首页</a></li>");
    sb.append("    <li><a href='javascript:$.turnPage(" + attr.getPagePrev() + ");'>上一页</a></li>");
    sb.append("    <li><a href='javascript:$.turnPage(" + attr.getPageNext() + ");'>下一页</a></li>");
    sb.append("    <li><a href='javascript:$.turnPage(" + attr.getPageTotal() + ");'>尾页</a></li>");
    sb.append("    <li>第" + attr.getPageCur() + "/" + attr.getPageTotal() + "页</li>");
    sb.append("    <li>");
    sb.append("        <input type='text' name='attr.pageCur' value='" + attr.getPageCur() + "'/>");
    sb.append("        <input type='button' onclick='javascript:$.submitForm();' value='跳转'/>");
    sb.append("    </li>");
    sb.append("</ul>");
    sb.append("</div>");
    return sb.toString();
  }

  private static String fore(PageAttr attr) {
    StringBuffer sb = new StringBuffer();
    sb.append("<div class='pageView'>");
    sb.append("<ul class='items'>");
    // 当前页前面部分
    sb.append("    <li><a href='javascript:$.turnPage(" + attr.getPagePrev() + ");'>&lt;</a></li>");
    sb.append("    <li class='item" + ((attr.getPageCur() == 1) ? " active" : "")
        + "'><a href='javascript:$.turnPage(1);'>1</a></li>");
    if (attr.getPageCur() <= 4) {
      for (int i = 2; i < attr.getPageCur(); i++) {
        sb.append("    <li class='item'><a href='javascript:$.turnPage(" + i + ");'>" + i
            + "</a></li>");
      }
    } else {
      sb.append("    <li class='item dot'>...</li>");
      sb.append("    <li class='item'><a href='javascript:$.turnPage(" + (attr.getPageCur() - 1)
          + ");'>" + (attr.getPageCur() - 1) + "</a></li>");
    }
    // 当前页
    if (attr.getPageCur() > 1) {
      sb.append("    <li class='item active'><a href='javascript:$.turnPage(" + attr.getPageCur()
          + ");'>" + attr.getPageCur() + "</a></li>");
    }
    // 当前页后面部分
    if (attr.getPageTotal() - attr.getPageCur() <= 3) {
      for (int i = attr.getPageCur() + 1; i <= attr.getPageTotal(); i++) {
        sb.append("    <li class='item'><a href='javascript:$.turnPage(" + i + ");'>" + i
            + "</a></li>");
      }
    } else {
      sb.append("    <li class='item'><a href='javascript:$.turnPage(" + (attr.getPageCur() + 1)
          + ");'>" + (attr.getPageCur() + 1) + "</a></li>");
      sb.append("    <li class='item'><a href='javascript:$.turnPage(" + (attr.getPageCur() + 2)
          + ");'>" + (attr.getPageCur() + 2) + "</a></li>");
      sb.append("    <li class='item dot'>...</li>");
    }
    sb.append("    <li><a href='javascript:$.turnPage(" + attr.getPageNext() + ");'>&gt;</a></li>");
    sb.append("</ul>");
    sb.append("<div class='total'>共" + attr.getPageTotal() + "页" + attr.getInfoTotal() + "条</div>");
    sb.append("<div class='form'>");
    sb.append("每页<input type='text' name='attr.infoSize' value='" + attr.getInfoSize()
        + "' onchange='javascript:$.submitForm();'/>条");
    sb.append("到第<input type='text' name='attr.pageCur' value='" + attr.getPageCur() + "'/>页 ");
    sb.append("    <input type='button' class='button' value='确定' onclick='javascript:$.submitForm();'/>");
    sb.append("</div>");
    sb.append("</div>");
    return sb.toString();
  }

  /**
   * 搜索引擎风格的分页
   * 
   * @param attr
   * @return
   */
  private static String search(PageAttr attr) {
    StringBuffer sb = new StringBuffer();
    sb.append("<div class=\"pagewhole\">");
    sb.append("<div class=\"page\">");
    sb.append("<input type='hidden' name='attr.infoSize' value='" + attr.getInfoSize() + "'/>");
    sb.append("<input type='hidden' name='attr.pageCur' value='" + attr.getPageCur() + "'/>");
    if (attr.getPageCur() > 1) {
      sb.append("<a href='javascript:$.turnPage(" + (attr.getPageCur() - 1) + ");'>上一页</a>");
    }

    int begin = 1; // 开始页
    if (attr.getPageCur() > 5) {
      begin = attr.getPageCur() - 5;
    }

    // 结束页
    int end =
        attr.getPageTotal() <= 10 ? attr.getPageTotal() : (begin + 9) > attr.getPageTotal() ? attr
            .getPageTotal() : (begin + 9);

    for (int i = begin; i <= end; i++) {
      if (i == attr.getPageCur()) {
        sb.append("<a href='javascript:$.turnPage(" + i + ");' class=\"change\">" + i + "</a>");
      } else {
        sb.append("<a href='javascript:$.turnPage(" + i + ");'>" + i + "</a>");
      }
    }

    if (attr.getPageCur() < attr.getPageTotal()) {
      sb.append("<a href='javascript:$.turnPage(" + (attr.getPageCur() + 1) + ");'>下一页</a>");
    }

    sb.append("</div>");
    sb.append("<div class=\"pageright\">");
    sb.append("找到相关结果约" + attr.getInfoTotal() + "个");
    sb.append("</div>");
    sb.append("</div>");
    return sb.toString();
  }

}
