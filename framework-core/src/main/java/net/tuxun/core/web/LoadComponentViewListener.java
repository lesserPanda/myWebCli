package net.tuxun.core.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.tuxun.core.util.FileManager;

import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class LoadComponentViewListener implements ServletContextListener {
  private static Logger log = LoggerFactory.getLogger(LoadComponentViewListener.class);
  // 开发模式、生产模式
  // develop produce
  private String model = "produce";

  private static final String MODE_PROCUCE = "produce";
  private static final String MODE_DEVELOP = "develop";

  private String viewpath = "back";
  private String dir = "";

  @Override
  public void contextDestroyed(ServletContextEvent arg0) {


  }

  @Override
  public void contextInitialized(ServletContextEvent arg0) {

    String mode = arg0.getServletContext().getInitParameter("LoadComponentViewMode");
    if (mode != null) {
      model = mode;
    }

    ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    // 将加载多个绝对匹配的所有Resource
    Resource[] resources = null;
    try {
      resources = resolver.getResources("classpath*:/componentviews/*.zip");
    } catch (IOException e) {
      log.error("查找视图资源异常", e);
      return;
    }
    dir = arg0.getServletContext().getRealPath("/WEB-INF/" + viewpath);
    for (Resource res : resources) {

      // 如果是生产模式下，视图资源已经存在就不做解压处理
      if (model.equals(MODE_PROCUCE)) {

        String filename = res.getFilename();
        filename = filename.substring(0, filename.lastIndexOf("."));
        File dir2 = new File(dir, filename);
        // 如果已经存在 就不自动解压
        if (dir2.exists()) {
          continue;
        }

      } else if (!model.equals(MODE_DEVELOP)) {
        log.error("model错误，只能是produce,develop模式");
        return;
      }

      log.info("正在解压组件视图【{}】", res.getFilename());
      unViewZip(res);



    }


  }

  private void unViewZip(Resource res) {

    File rootdir = new File(dir);
    if (!rootdir.isDirectory()) {
      rootdir.mkdirs();
    }

    ZipFile zipfile = null;
    try {
      File zf = res.getFile();
      if (zf.exists()) {
        zipfile = new ZipFile(zf);
      }
    } catch (IOException e) {
      log.error("res.getFile异常");
    }

    if (zipfile == null) {
      try {
        FileManager.unzip(res.getInputStream(), rootdir);
      } catch (IOException e) {
        log.error("解压组件视图 【{}】异常", res.getFilename(), e);
      }
    } else {
      FileManager.unzip(zipfile, rootdir);
    }

  }

}
