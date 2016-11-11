package net.tuxun.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;

public class ToolUtil {

  // private DaFjDefine daFj;

  /**
   * 根据cla中的字段，对请求参数进行封装并生成cla类的对象
   * 
   * @author Frank
   * @param request
   * @param cla
   * @return
   * @throws Exception
   */
  public static Object encapsulationRequestDate(HttpServletRequest request, Class cla)
      throws Exception {
    Object obj;
    try {
      obj = cla.newInstance();
    } catch (Exception e) {
      throw new Exception("生成对象异常", e);
    }
    Field[] fields = cla.getDeclaredFields();
    Method setM = null;
    for (int i = 0; i < fields.length; i++) {
      String fn = fields[i].getName();
      String setf = "set" + fn.substring(0, 1).toUpperCase() + fn.substring(1);// set方法
      Object tmpdate = request.getParameter(fn);
      String typename = fields[i].getType().getName();
      if (tmpdate != null) {
        try {
          setM = cla.getMethod(setf, new Class[] {fields[i].getType()});
          if ((typename.endsWith("Integer") || typename.equals("int")) && !tmpdate.equals("")) {
            try {
              tmpdate = Integer.valueOf(String.valueOf(tmpdate));
            } catch (Exception e) {
            }
          } else if (typename.endsWith("Double") && !tmpdate.equals("")) {
            try {
              tmpdate = Double.valueOf((String) tmpdate);
            } catch (Exception e) {
            }
          } else if (typename.endsWith("Date") && !tmpdate.equals("")) {
            try {
              tmpdate = UncDate.parseDate((String) tmpdate);
            } catch (Exception e) {
            }
          } else if (!typename.endsWith("String")) {
            // Log.error("封装请求数据时，"+cla.getName()+"的"+fn+"字段的数据没有载入");
            continue;
          }
          setM.invoke(obj, new Object[] {tmpdate});

        } catch (NoSuchMethodException e) {
          throw new Exception("没有这样的方法", e);
        } catch (Exception ie) {
          throw new Exception("封装出现异常", ie);
        }
      }
    }
    return obj;
  }

  /**
   * 用于在上传附件时，把相关的表单数据封装成由cla指定的对象
   * 
   * @param fileList 是由ServletFileUpload解析请求返回的列表。包含了所有的文件、表单数据
   * @param cla 用于封装成某对象的类型
   * @return 类型为cla的对象
   * @throws Exception
   */
  public static Object encapsulationRequestDate(List<FileItem> fileList, Class cla)
      throws Exception {
    Object obj;
    try {
      obj = cla.newInstance();
    } catch (Exception e) {
      throw new Exception("生成对象异常", e);
    }
    // 得到所有上传的文件
    Iterator<FileItem> fileItr = fileList.iterator();
    Method setM = null;
    while (fileItr.hasNext()) {
      FileItem fileItem = fileItr.next();
      if (!fileItem.isFormField())
        continue;

      String fn = fileItem.getFieldName();

      Object value = null;
      Field field = null;
      try {
        value = fileItem.getString("GBK");
        field = cla.getDeclaredField(fn);
      } catch (NoSuchFieldException e) {
        continue;
      } catch (Exception e) {
        continue;
      }
      String setf = "set" + fn.substring(0, 1).toUpperCase() + fn.substring(1);// set方法
      String typename = field.getType().getName();
      if (value != null) {
        try {
          setM = cla.getMethod(setf, new Class[] {field.getType()});
          if ((typename.endsWith("Integer") || typename.equals("int")) && !value.equals("")) {
            try {
              value = Integer.valueOf(String.valueOf(value));
            } catch (Exception e) {
            }
          } else if (typename.endsWith("Double") && !value.equals("")) {
            try {
              value = Double.valueOf((String) value);
            } catch (Exception e) {
            }
          } else if (typename.endsWith("Date") && !value.equals("")) {
            try {
              value = UncDate.parseDate((String) value);
            } catch (Exception e) {
            }
          } else if (!typename.endsWith("String")) {
            // Log.error("封装请求数据时，"+cla.getName()+"的"+fn+"字段的数据没有载入");
            continue;
          }
          setM.invoke(obj, new Object[] {value});

        } catch (NoSuchMethodException e) {
          throw new Exception("没有这样的方法", e);
        } catch (Exception ie) {
          throw new Exception("封装出现异常", ie);
        }
      }
    }

    return obj;

  }

  /**
   * 使用DiskFileItemFactory 来上传文件
   * 
   * @param request
   * @param path 上传的文件存放路径
   * @param maxsize 文件允许的最大尺寸
   * @return 包含了文件信息和由ServletFileUpload返回的解析列表： 文件信息的key为文件类型表单名称，Object为上传后的绝对路径；
   *         解析列表的key为FileItemList,Object为ServletFileUpload .parseRequest(request)返回值;
   * @throws Exception
   */
  public static Map<String, Object> uploadfile(HttpServletRequest request, String path, int maxsize)
      throws Exception {
    Map<String, Object> map = new HashMap<String, Object>();
    // try{
    // 实例化一个硬盘文件工厂,用来配置上传组件ServletFileUpload
    DiskFileItemFactory dfif = new DiskFileItemFactory();
    dfif.setSizeThreshold(4096);// 设置上传文件时用于临时存放文件的内存大小,这里是4K.多于的部分将临时存在硬盘

    // 设置存放临时文件的目录,web根目录下的ImagesUploadTemp目录
    File tmpdir =
        new File(request.getSession().getServletContext().getRealPath("/") + "UploadTemp");
    if (!tmpdir.isDirectory())
      tmpdir.mkdirs();
    dfif.setRepository(tmpdir);

    // 用以上工厂实例化上传组件
    ServletFileUpload sfu = new ServletFileUpload(dfif);
    // 设置最大上传尺寸
    sfu.setSizeMax(maxsize);
    sfu.setHeaderEncoding("GBK");
    List fileList = sfu.parseRequest(request);
    /*
     * } catch (FileUploadException e) {// 处理文件尺寸过大异常 log.error("上传文件异常",e);
     * 
     * if (e instanceof SizeLimitExceededException) { //out.println("文件尺寸超过规定大小<p />");
     * //out.println("<a href=\"upload.html\" target=\"_top\">返回</a>"); throw new
     * Exception("文件太大！"); }
     * 
     * }
     */
    // 没有文件上传
    if (fileList == null || fileList.size() == 0) {
      // throw new Exception("请选择附件");
      return map;
    }
    map.put("FileItemList", fileList);
    // 得到所有上传的文件
    Iterator fileItr = fileList.iterator();
    // 循环处理所有文件
    while (fileItr.hasNext()) {
      FileItem fileItem = (FileItem) fileItr.next();
      if (fileItem == null) {
        continue;
      }
      if (!fileItem.isFormField()) {
        // 得到文件的完整路径
        String tmppath = fileItem.getName();

        String name = tmppath.substring(tmppath.lastIndexOf("\\") + 1);
        if (name != null && !name.equals("")) {
          File file = new File(path + File.separator + name);
          fileItem.write(file);
          map.put(fileItem.getFieldName(), file.getAbsolutePath());
        }

      }
    }
    /*
     * }catch(Exception e){ throw e; //new Exception("上传文件出现异常",e); }
     */

    return map;
  }

  public static Map<String, Object> uploadfileArchive(HttpServletRequest request, String path,
      int maxsize) throws Exception {
    Map<String, Object> map = new HashMap<String, Object>();
    // try{
    // 实例化一个硬盘文件工厂,用来配置上传组件ServletFileUpload
    DiskFileItemFactory dfif = new DiskFileItemFactory();
    dfif.setSizeThreshold(4096);// 设置上传文件时用于临时存放文件的内存大小,这里是4K.多于的部分将临时存在硬盘

    // 设置存放临时文件的目录,web根目录下的ImagesUploadTemp目录
    File tmpdir =
        new File(request.getSession().getServletContext().getRealPath("/") + "UploadTemp");
    if (!tmpdir.isDirectory())
      tmpdir.mkdirs();
    dfif.setRepository(tmpdir);

    // 用以上工厂实例化上传组件
    ServletFileUpload sfu = new ServletFileUpload(dfif);
    // 设置最大上传尺寸
    sfu.setSizeMax(maxsize);
    sfu.setHeaderEncoding("GBK");
    List fileList = sfu.parseRequest(request);
    // 没有文件上传
    if (fileList == null || fileList.size() == 0) {
      // throw new Exception("请选择附件");
      return map;
    }
    map.put("FileItemList", fileList);
    // 得到所有上传的文件
    Iterator fileItr = fileList.iterator();
    // 循环处理所有文件
    while (fileItr.hasNext()) {
      FileItem fileItem = (FileItem) fileItr.next();
      if (fileItem == null) {
        continue;
      }
      if (!fileItem.isFormField()) {
        // 得到文件的完整路径
        String tmppath = fileItem.getName();

        String name = tmppath.substring(tmppath.lastIndexOf("\\") + 1);
        if (name != null && !name.equals("")) {
          String fieldName = fileItem.getFieldName();
          String[] str = fieldName.split("\\.");
          if (str == null || str.length <= 0) {
            File dir = new File(path);
            if (!dir.isDirectory())
              dir.mkdirs();
            File file = new File(path + File.separator + name);
            fileItem.write(file);
            map.put(fileItem.getFieldName(), file.getAbsolutePath());
          } else {
            String pat1 = "";
            String pat2 = "";
            for (int j = 0; j < str.length; j++) {
              pat1 += str[j] + ".";
              pat2 += "/" + pat1.substring(0, pat1.length() - 1);
            }
            File dir = new File(path + pat2);
            if (!dir.isDirectory())
              dir.mkdirs();
            File file = new File(path + pat2 + File.separator + name);
            fileItem.write(file);
            map.put(fileItem.getFieldName(), file.getAbsolutePath());
          }

        }

      }
    }
    /*
     * }catch(Exception e){ throw e; //new Exception("上传文件出现异常",e); }
     */

    return map;
  }

  /**
   * 下载文件
   * 
   * @param response
   * @param file
   * @throws Exception
   */
  public static void downFile(HttpServletResponse response, File file) throws Exception {
    int bufferSize = 1024 * 5;
    if (file == null || !file.isFile()) {
      throw new Exception("不是有效文件！");
    }
    String filename = file.getName();
    int indexExt = filename.lastIndexOf(".");
    String ext = "";
    if (indexExt != -1) {
      ext = filename.substring(indexExt + 1).toLowerCase();
    }
    FileInputStream in = null;
    OutputStream out = null;
    byte[] bytes = new byte[bufferSize];
    int i = 0;
    try {
      in = new FileInputStream(file);
      response.setHeader("Content-disposition",
          "attachment; filename=" + new String(filename.getBytes(), "ISO-8859-1"));
      String contentType = "";
      if (ext.equals("xls")) {
        contentType = "application/vnd.ms-excel";
      } else if (ext.equals("doc")) {
        contentType = "application/msword";
      } else if (ext.equals("exe")) {
        contentType = "application/octet-stream";
      } else if (ext.equals("txt")) {
        contentType = "text/plain";
      } else if (ext.equals("pdf")) {
        contentType = "application/pdf";
      } else if (ext.equals("rar")) {
        contentType = "application/x-rar-compressed";
      } else if (ext.equals("zip")) {
        contentType = "application/zip";
      } else {
        contentType = "application/octet-stream";
      }
      response.setContentType(contentType);
      out = response.getOutputStream();
      while ((i = in.read(bytes)) != -1) {
        out.write(bytes, 0, i);
      }
      out.flush();
      out.close();
    } catch (IOException e) {
      // log.error(e.getMessage());
    } catch (Exception e) {
      throw new Exception("导出文件出现异常", e);
    } finally {
      try {
        if (in != null)
          in.close();

      } catch (Exception e) {
      }
    }
  }



  /**
   * 获得请求IP地址
   * 
   * @param request
   * @return
   */
  public static String getIpAddr(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }

  /***
   * 保存文件
   * 
   * @param file
   * @return
   */
  /*
   * public boolean saveFile(MultipartFile file,String xxlx,DaFjDefine fj) { // 判断文件是否为空 if
   * (!file.isEmpty()) { try { // 文件保存路径 Calendar cal = Calendar.getInstance(); int year =
   * cal.get(Calendar.YEAR); String filePath = Config.ROOTPATH +
   * "/gdml/"+String.valueOf(year)+"/"+xxlx+"/"; File dir = new File(filePath);
   * System.out.println(filePath); if(!dir.isDirectory()){ dir.mkdirs(); } fj.setFj_path(filePath);
   * fj.setFj_name(file.getOriginalFilename()); filePath +=file.getOriginalFilename();
   * 
   * // 转存文件 file.transferTo(new File(filePath)); setDaFj(fj); return true; } catch (Exception e) {
   * e.printStackTrace(); } } return false; }
   * 
   * public DaFjDefine getDaFj() { return daFj; }
   * 
   * public void setDaFj(DaFjDefine daFj) { this.daFj = daFj; }
   */
}
