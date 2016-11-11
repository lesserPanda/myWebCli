package net.tuxun.core.propertyeditor;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

/**
 * 自适应日期映射
 * 
 * @author Administrator
 * 
 */
public class AutoMappingDateEditor extends PropertyEditorSupport {

  private DateFormat dateFormat = null;
  private final boolean allowEmpty;
  private String[] regx = null;
  private DateFormat[] dfs = null;

  public AutoMappingDateEditor(boolean allowEmpty) {
    this.allowEmpty = allowEmpty;
    regx =
        new String[] {"\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}",
            "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}", "\\d{4}-\\d{2}-\\d{2} \\d{2}",
            "\\d{4}-\\d{2}-\\d{2}"};

    dfs =
        new DateFormat[] {new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
            new SimpleDateFormat("yyyy-MM-dd HH:mm"), new SimpleDateFormat("yyyy-MM-dd HH"),
            new SimpleDateFormat("yyyy-MM-dd")};

  }

  /**
   * Parse the Date from the given text, using the specified DateFormat.
   */
  @Override
  public void setAsText(String text) throws IllegalArgumentException {
    if (this.allowEmpty && !StringUtils.hasText(text)) {
      // Treat empty String as null value.
      setValue(null);
      return;
    } else if (!this.allowEmpty && StringUtils.hasText(text)) {
      throw new IllegalArgumentException("日期为空！");
    }

    initDateFormat(text);

    if (dateFormat == null) {
      throw new IllegalArgumentException("没有对应的日期格式无法转换！");
    }

    try {
      setValue(this.dateFormat.parse(text));
    } catch (ParseException ex) {
      throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
    }

  }

  /**
   * 根据指定的日期值初始化日期格式
   * 
   * @param dateValue
   */
  protected void initDateFormat(String dateValue) {
    for (int i = 0; i < regx.length; i++) {
      if (dateValue.matches(regx[i])) {
        dateFormat = dfs[i];
        break;
      }
    }
  }

  /**
   * Format the Date as String, using the specified DateFormat.
   */
  @Override
  public String getAsText() {
    Date value = (Date) getValue();
    if (value == null) {
      return "";
    }
    // Calendar calendar=Calendar.getInstance();
    // calendar.setTime(value);
    dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String tmpvalue = dateFormat.format(value);
    while (tmpvalue.endsWith("00")) {
      tmpvalue = tmpvalue.substring(0, tmpvalue.length() - 2);
      if (tmpvalue.endsWith(":") || tmpvalue.endsWith(" ")) {
        tmpvalue = tmpvalue.substring(0, tmpvalue.length() - 1);
      }
    }
    tmpvalue = tmpvalue.trim();

    initDateFormat(tmpvalue);


    return (value != null ? this.dateFormat.format(value) : "");
  }

}
