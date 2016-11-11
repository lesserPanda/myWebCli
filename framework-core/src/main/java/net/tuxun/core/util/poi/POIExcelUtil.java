package net.tuxun.core.util.poi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import net.tuxun.core.util.DateUtil;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 开源POI的操作工具类 <br>
 * Excel只有一个Worbook工作簿对象，有多个Sheet工作表组成 <br>
 * 封装了对Excel的常用操作
 * 
 * @author liuqiang
 * 
 */
public class POIExcelUtil {

  /**
   * 创建Excel工作簿
   * 
   * @param flag true:2007版本,false:2003及以前的版本
   * @return 返回Excel工作簿
   */
  public static Workbook createWorkbook(boolean flag) {
    Workbook wb;
    if (flag) {
      // XSSFWorkbook:是操作Excel2007的版本，扩展名是.xlsx
      wb = new XSSFWorkbook();
    } else {
      // HSSFWorkbook:是操作Excel2003以前（包括2003）的版本，扩展名是.xls
      wb = new HSSFWorkbook();
    }
    return wb;
  }

  /**
   * 读取Excel文件，生成Excel工作簿
   * 
   * @param filePathName 文件名称
   * @return 返回Excel工作簿
   */
  public static Workbook readExcel(String filePathName) {
    InputStream inp = null;
    Workbook wb = null;
    try {
      inp = new FileInputStream(filePathName);
      wb = WorkbookFactory.create(inp);
      inp.close();
    } catch (Exception e) {
      try {
        if (null != inp) {
          inp.close();
        }
      } catch (IOException ie) {
        ie.printStackTrace();
      }
      e.printStackTrace();
    }
    return wb;
  }

  /**
   * 将Excel工作簿对象写入到文件中
   * 
   * @param wb Excel工作簿对象
   * @param fileName 写入的文件名称
   * @return flag true:写入成功，false写入失败
   */
  public static boolean writeExcel(Workbook wb, String fileName) {
    boolean flag = true;
    FileOutputStream fileOut = null;
    try {
      fileOut = new FileOutputStream(fileName);
      wb.write(fileOut);
      fileOut.close();
    } catch (Exception e) {
      flag = false;
      if (fileOut != null) {
        try {
          fileOut.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
      e.printStackTrace();
    }
    return flag;
  }

  /**
   * 添加图片到单元格中
   * 
   * @param wb Excel工作簿
   * @param sheet 工作表
   * @param picFileName 图片名称（完整的图片路径+图片名称）
   * @param picType 图片类型,Workbook类中定义了常见的图片类型
   * @param row 单元格所在的行
   * @param col 单元格所在的列
   */
  public static void addPicture(Workbook wb, Sheet sheet, String picFileName, int picType, int row,
      int col) {
    InputStream is = null;
    try {
      // 读取图片
      is = new FileInputStream(picFileName);
      byte[] bytes = IOUtils.toByteArray(is);
      int pictureIdx = wb.addPicture(bytes, picType);
      is.close();
      // 写入图片
      CreationHelper helper = wb.getCreationHelper();
      Drawing drawing = sheet.createDrawingPatriarch();
      ClientAnchor anchor = helper.createClientAnchor();
      // 设置图片的位置
      anchor.setCol1(col);
      anchor.setRow1(row);
      Picture pict = drawing.createPicture(anchor, pictureIdx);

      pict.resize();
    } catch (Exception e) {
      try {
        if (is != null) {
          is.close();
        }
      } catch (IOException e1) {
        e1.printStackTrace();
      }
      e.printStackTrace();
    }
  }

  /**
   * 创建单元格cell,并设置样式
   * 
   * @param style 单元格式样式
   * @param row 单元格式位于的行对象
   * @param column 单元格位于的列数
   * @return 新创建的单于格
   */
  public static Cell createCell(CellStyle style, Row row, int column) {
    Cell cell = row.createCell(column);
    cell.setCellStyle(style);
    return cell;
  }

  /**
   * 合并单元格
   * 
   * @param sheet 工作表
   * @param firstRow 起始行
   * @param lastRow 结束行
   * @param firstCol 起始列
   * @param lastCol 结束列
   */
  public static void mergeCell(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
    sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
  }

  /**
   * 设置单元格对齐方式
   * 
   * @param style
   * @param halign
   * @param valign
   * @return
   */
  public static CellStyle setAlign(CellStyle style, short halign, short valign) {
    style.setAlignment(halign);
    style.setVerticalAlignment(valign);
    return style;
  }

  /**
   * 设置单元格边框的样式(四个方向的颜色一样)
   * 
   * @param style 单元格样式
   * @param borderStyle 单元格边框的类型，详见CellStyle.BORDER_*<code>CellStyle</code>
   * @param borderColor 单元格边框的颜色，详见IndexedColors.*<code>IndexedColors</code>
   * @return 设置后的单元格样式
   */
  public static CellStyle setBorder(CellStyle style, short borderStyle, short borderColor) {
    // 设置底部格式（样式+颜色）
    style.setBorderBottom(borderStyle);
    style.setBottomBorderColor(borderColor);
    // 设置左边格式
    style.setBorderLeft(borderStyle);
    style.setLeftBorderColor(borderColor);
    // 设置右边格式
    style.setBorderRight(borderStyle);
    style.setRightBorderColor(borderColor);
    // 设置顶部格式
    style.setBorderTop(borderStyle);
    style.setTopBorderColor(borderColor);

    return style;
  }

  /**
   * 2007的版本 自定义设置单元格背景色（不使用POI提供的色彩）
   * 
   * @param style
   * @param red RGB red (0-255)
   * @param green RGB green (0-255)
   * @param blue RGB blue (0-255)
   * @return 单元格式的样式
   */
  public static CellStyle setBackColorByCustom(XSSFCellStyle style, int red, int green, int blue) {
    // 设置前端颜色
    style.setFillForegroundColor(new XSSFColor(new java.awt.Color(red, green, blue)));
    // 设置填充模式
    style.setFillPattern(CellStyle.SOLID_FOREGROUND);

    return style;
  }

  /**
   * 2003及以前的版本 自定义设置背景色（不使用POI提供的色彩）
   * 
   * @param wb 工作簿
   * @param style 样式
   * @param red
   * @param green
   * @param blue
   * @return 单元格式样式
   */
  public static CellStyle setBackColorByCustom(HSSFWorkbook wb, HSSFCellStyle style, int red,
      int green, int blue) {
    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    style.setFillForegroundColor(HSSFColor.WHITE.index);

    HSSFPalette palette = wb.getCustomPalette();
    palette.setColorAtIndex((short) 9, (byte) red, (byte) green, (byte) blue);
    style.setFillForegroundColor((short) 9);

    style.setFillBackgroundColor((short) 9);
    return style;
  }

  /**
   * 设置前景颜色
   * 
   * @param style 单元格样式
   * @param color 前景色
   * @return 单元格式样式
   */
  public static CellStyle setForegroundColor(CellStyle style, short color) {

    // 设置前端颜色
    style.setFillForegroundColor(color);
    // 设置填充模式
    style.setFillPattern(CellStyle.SOLID_FOREGROUND);

    return style;
  }

  /**
   * 设置背景颜色
   * 
   * @param style 单元格样式
   * @param backColor 背景色
   * @param fillPattern 填充模式
   * @return 单元格式样式
   */
  public static CellStyle setBackgroundColor(CellStyle style, short backColor, short fillPattern) {

    // 设置背景颜色
    style.setFillBackgroundColor(backColor);

    // 设置填充模式
    style.setFillPattern(fillPattern);

    return style;
  }


  /**
   * 设置日期的格式
   * 
   * @param createHelper createHelper对象
   * @param style CellStyle对象
   * @param formartData date:"m/d/yy h:mm"; int:"#,###.0000" ,"0.0"
   * @return 单元格式样式
   */
  public static CellStyle setDataFormat(CreationHelper createHelper, CellStyle style,
      String formartData) {

    style.setDataFormat(createHelper.createDataFormat().getFormat(formartData));

    return style;
  }

  /**
   * 取得cell的值
   * 
   * @param cell
   * @return cell的字符串值
   */
  public static String getCellValue(Cell cell) {
    String value = "";
    if (cell != null) {
      switch (cell.getCellType()) {
        // 字符串
        case Cell.CELL_TYPE_STRING:
          value = cell.getStringCellValue();
          break;
        // 数字
        case Cell.CELL_TYPE_NUMERIC:
          if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
            value = DateUtil.longTime(cell.getDateCellValue());
          } else {
            Double d = cell.getNumericCellValue();
            DecimalFormat df = new DecimalFormat("0.##");
            value = df.format(d);
          }
          break;
        // 公式
        case Cell.CELL_TYPE_FORMULA:
          value = cell.getCellFormula();
          break;
        // 布尔类型
        case Cell.CELL_TYPE_BOOLEAN:
          value = String.valueOf(cell.getBooleanCellValue());
          break;
        // 空值, 故障
        default:
          value = "";
          break;
      }
    }
    return value;
  }

  /**
   * 取得工作表正确的行数
   * 
   * @param sheet 工作表
   * @return 行数
   */
  public static int getRightRNum(Sheet sheet) {
    int num = 0;
    int begin = sheet.getFirstRowNum();
    int end = sheet.getLastRowNum();
    for (int i = begin; i <= end; i++) {
      if (null == sheet.getRow(i) || null == sheet.getRow(i).getCell(0)
          || null == POIExcelUtil.getCellValue(sheet.getRow(i).getCell(0))) {
        continue;
      }
      num++;
    }
    return num;
  }

  /**
   * 取得正确的列数
   * 
   * @param sheet 工作表
   * @return 列数
   */
  public static int getRightCNum(Sheet sheet) {
    Row headRow = sheet.getRow(0);
    int num = 0;
    int begin = headRow.getFirstCellNum();
    int end = headRow.getLastCellNum();
    for (int i = begin; i <= end; i++) {
      if (null == headRow.getCell(i)) {
        continue;
      }
      num++;
    }
    return num;
  }
  
}
