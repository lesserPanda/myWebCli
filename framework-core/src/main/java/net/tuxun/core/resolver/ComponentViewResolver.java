package net.tuxun.core.resolver;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;

import net.tuxun.core.util.ApplicationContextUtil;

import org.apache.poi.util.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * 组件视图的解析器 目的是让程序方便的访问组件中jsp视图
 * 
 * @author Administrator
 * 
 */
public class ComponentViewResolver extends InternalResourceViewResolver {

  // 是否覆盖组件jsp
  private boolean covercjsp = false;

  /*
   * @Override protected View loadView(String viewName, Locale locale) throws Exception { try{ View
   * view =super.loadView(viewName,locale); return view; }catch(Exception e){ return null; }
   * 
   * }
   */

  public boolean isCovercjsp() {
    return covercjsp;
  }

  public void setCovercjsp(boolean covercjsp) {
    this.covercjsp = covercjsp;
  }

  /*
   * 将组件视图根据需要复制到项目中 (non-Javadoc)
   * 
   * @see
   * org.springframework.web.servlet.view.InternalResourceViewResolver#buildView(java.lang.String)
   */
  @Override
  protected AbstractUrlBasedView buildView(String viewName) throws Exception {

    String webpath = ApplicationContextUtil.getServletContext().getRealPath("/");
    ComponentJstlView view = (ComponentJstlView) super.buildView(viewName);

    if (!covercjsp) {
      if (new File(webpath, view.getUrl()).isFile())
        return view;
    }

    Resource res = new ClassPathResource("/componentviews/" + viewName + ".jsp");
    if (res.exists()) {
      // 将组件视图复制到项目中
      File jspfile = new File(webpath, getPrefix() + viewName + getSuffix());
      if (!jspfile.getParentFile().isDirectory()) {
        jspfile.getParentFile().mkdirs();
      }
      FileOutputStream out = null;
      try {
        out = new FileOutputStream(jspfile);
        IOUtils.copy(res.getInputStream(), out);
      } catch (Exception e) {
        logger.error("复制组件JSP异常", e);
      } finally {
        IOUtils.closeQuietly(out);
      }
      view.setCovercjsp(covercjsp);
      view.setRes(res);

    }

    return view;
    // return super.buildView(viewName);
  }

  @Override
  protected Class getViewClass() {
    return ComponentJstlView.class;
  }

}
