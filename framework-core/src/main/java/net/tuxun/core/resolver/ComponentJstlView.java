package net.tuxun.core.resolver;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.tuxun.core.util.ApplicationContextUtil;

import org.apache.poi.util.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.view.JstlView;

public class ComponentJstlView extends JstlView {
  private boolean covercjsp = false;
  private Resource res = null;// 组件中jsp资源

  public boolean isCovercjsp() {
    return covercjsp;
  }

  public void setCovercjsp(boolean covercjsp) {
    this.covercjsp = covercjsp;
  }

  public Resource getRes() {
    return res;
  }

  public void setRes(Resource res) {
    this.res = res;
  }

  @Override
  protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    if (covercjsp && res != null) {
      // 根据需要将组件的视图覆盖项目中视图
      String webpath = ApplicationContextUtil.getServletContext().getRealPath("/");
      File jspfile = new File(webpath, this.getUrl());
      // 如果有修改就覆盖
      if (res.lastModified() > jspfile.lastModified()) {
        FileOutputStream out = null;
        try {
          out = new FileOutputStream(jspfile);
          IOUtils.copy(res.getInputStream(), out);
        } catch (Exception e) {
          logger.error("复制组件JSP异常", e);
        } finally {
          IOUtils.closeQuietly(out);
        }
      }
    }

    super.renderMergedOutputModel(model, request, response);
  }

}
