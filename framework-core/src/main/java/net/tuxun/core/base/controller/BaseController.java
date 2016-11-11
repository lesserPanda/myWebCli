package net.tuxun.core.base.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.tuxun.core.exception.BaseException;
import net.tuxun.core.propertyeditor.AutoMappingDateEditor;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 控制器基类
 * 
 * @author liuqiang
 * 
 */
public class BaseController {
  protected Logger log = LoggerFactory.getLogger(this.getClass());

  /* 表单操作类型 */
  public static final String OPERATION = "operation";
  /* 表单添加操作 */
  public static final String ADD = "add";
  /* 表单修改操作 */
  public static final String ALTER = "alter";

  /* 信息提示 */
  public static final String MESSAGE = "message";

  /* 刷新菜单上的记录数 */
  public static final String KEEPCOUNT = "keepcount";

  /* 操作成功提示信息 */
  public static final String OPERATION_SUCCESS = "操作成功";
  /* 操作失败提示信息 */
  public static final String OPERATION_FAILURE = "操作失败";
  /* 保存成功提示信息 */
  public static final String SAVE_SUCCESS = "保存成功";
  /* 删除成功提示信息 */
  public static final String REMOVE_SUCCESS = "删除成功";

  @RequestMapping("{page:[A-Za-z]+}.do")
  public String jump(@PathVariable("page") String page) {
    return page;
  }

  // private static final String[] FOMATS = {"yyyy-MM-dd","yyyy-MM-dd HH:mm","yyyy-MM-dd HH:mm:ss"};
  @InitBinder
  protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
      throws Exception {
    binder.registerCustomEditor(Date.class, new AutoMappingDateEditor(true));
  }

  protected Map<String, Boolean> status(boolean bool) {
    Map<String, Boolean> map = new HashMap<String, Boolean>();
    map.put("status", bool);
    return map;
  }

  /**
   * 附件下载
   * 
   * @param file
   * @param showname
   * @return
   * @throws FileNotFoundException
   * @throws IOException
   */
  public ResponseEntity<byte[]> download(File file, String showname) throws FileNotFoundException,
      IOException {
    if (file == null || !file.exists()) {
      return null;
    }
    if (showname == null) {
      showname = file.getName();
    }
    byte[] bytes = new byte[(int) file.length()];
    IOUtils.read(new FileInputStream(file), bytes);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentLength(bytes.length);
    headers.setContentDispositionFormData("attachment", new String(showname.getBytes("utf-8"),
        "ISO8859-1"));
    /*
     * try {
     * 
     * headers.add("Content-disposition", "attachment; filename=" + new
     * String(showname.getBytes("utf-8"), "ISO8859-1")); } catch (UnsupportedEncodingException e) {
     * throw new BaseException(e); }
     */
    return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
  }

}
