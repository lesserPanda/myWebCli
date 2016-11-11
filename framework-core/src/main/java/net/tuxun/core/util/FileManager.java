package net.tuxun.core.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.regex.Pattern;

import net.tuxun.core.exception.BaseException;

import org.apache.commons.io.IOUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

public class FileManager {

  /**
   * 删除文件，可以是单个文件或文件夹
   * 
   * @param fileName 待删除的文件名
   * @return 文件删除成功返回true,否则返回false
   */
  public static boolean delete(String fileName) {
    File file = new File(fileName);
    if (!file.exists()) {
      // System.out.println("删除文件失败："+fileName+"文件不存在");
      return false;
    } else {
      if (file.isFile()) {

        return deleteFile(fileName);
      } else {
        return deleteDirectory(fileName);
      }
    }
  }

  /**
   * 删除单个文件
   * 
   * @param fileName 被删除文件的文件名
   * @return 单个文件删除成功返回true,否则返回false
   */
  public static boolean deleteFile(String fileName) {
    File file = new File(fileName);
    if (file.isFile() && file.exists()) {
      file.delete();
      // System.out.println("删除单个文件"+fileName+"成功！");
      return true;
    } else {
      // System.out.println("删除单个文件"+fileName+"失败！");
      return false;
    }
  }

  /**
   * 删除目录（文件夹）以及目录下的文件
   * 
   * @param dir 被删除目录的文件路径
   * @return 目录删除成功返回true,否则返回false
   */
  public static boolean deleteDirectory(String dir) {
    // 如果dir不以文件分隔符结尾，自动添加文件分隔符
    if (!dir.endsWith(File.separator)) {
      dir = dir + File.separator;
    }
    File dirFile = new File(dir);
    // 如果dir对应的文件不存在，或者不是一个目录，则退出
    if (!dirFile.exists() || !dirFile.isDirectory()) {
      return false;
    }

    return deleteDirectory(dirFile);

  }

  public static boolean deleteDirectory(File dir) {
    if (!dir.isDirectory()) {
      return false;
    }

    boolean flag = true;
    // 删除文件夹下的所有文件(包括子目录)
    File[] files = dir.listFiles();
    for (int i = 0; i < files.length; i++) {
      // 删除子文件
      if (files[i].isFile()) {

        flag = deleteFile(files[i].getAbsolutePath());
        if (!flag) {
          break;
        }

      } else {

        flag = deleteDirectory(files[i]); // 删除子目录
        if (!flag) {
          break;
        }
      }
    }

    if (!flag) {
      return false;
    }

    // 删除当前目录
    if (dir.delete()) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 复制文件夹
   * 
   * @param src 源文件夹
   * @param dst 目标文件夹
   */
  public static void copyFolder(File src, File dst) {

    if (!src.isDirectory())
      return;

    if (!dst.isDirectory())
      dst.mkdirs();

    byte[] bytes = new byte[4096];
    int len = -1;
    File[] srcfiles = src.listFiles();
    for (File f : srcfiles) {

      if (f.isFile()) {
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
          in = new FileInputStream(f);
          out = new FileOutputStream(new File(dst, f.getName()));
          while ((len = in.read(bytes, 0, 4096)) > 0) {
            out.write(bytes, 0, len);
          }
          out.flush();
        } catch (Exception e) {
        } finally {
          try {
            if (in != null)
              in.close();
            if (out != null)
              out.close();
          } catch (Exception e) {
          }
        }
      } else if (f.isDirectory()) {
        File dest = new File(dst.getAbsolutePath() + File.separator + f.getName());
        copyFolder(f, dest);
      }

    }
  }

  /**
   * 复制文件到目录
   * 
   * @param file
   * @param dir
   * @param rename 是否重命名
   * @return
   */
  public static File corpFile2Dir(File file, File dir, boolean rename) {
    if (!file.isFile()) {
      return null;
    }

    if (!dir.isDirectory()) {
      dir.mkdirs();
    }
    File newfile = null;
    if (rename) {
      newfile = new File(dir, System.currentTimeMillis() + "_" + file.getName());
    } else {
      newfile = new File(dir, file.getName());
    }
    FileInputStream in = null;
    FileOutputStream out = null;
    try {
      in = new FileInputStream(file);
      out = new FileOutputStream(newfile);
      IOUtils.copy(in, out);

    } catch (Exception e) {
      throw new BaseException(e);
    } finally {
      IOUtils.closeQuietly(in);
      IOUtils.closeQuietly(out);
    }
    return newfile;

  }


  /**
   * 压缩文件或目录
   * 
   * @param zip 压缩后最终文件
   * @param src 源文件或目录
   * @throws Exception
   */
  public static void zip(File zip, File src) throws Exception {
    ZipOutputStream zipout = null;
    try {
      zipout = new ZipOutputStream(new FileOutputStream(zip));
      zip(zipout, src, src.getName(), null);
    } catch (Exception e) {
      throw e;
    } finally {
      IOUtils.closeQuietly(zipout);
    }
  }

  /**
   * 压缩文件或目录
   * 
   * @param zip 压缩后最终文件
   * @param src 源文件或目录
   * @param exincludeRegex 排除文件的正则表达式,例如：.+\\.pdf 表示排除所有pdf文件
   * @throws Exception
   */
  public static void zip(File zip, File src, String exincludeRegex) throws Exception {
    ZipOutputStream zipout = null;
    try {
      zipout = new ZipOutputStream(new FileOutputStream(zip));
      zip(zipout, src, src.getName(), exincludeRegex);
    } catch (Exception e) {
      throw e;
    } finally {
      IOUtils.closeQuietly(zipout);
    }
  }


  /**
   * 压缩文件或目录
   * 
   * @param out
   * @param f
   * @param basedir
   * @param exincludeRegex 排除文件的正则表达式,例如：.+\\.pdf 表示排除所有pdf文件
   * @throws Exception
   */
  public static void zip(ZipOutputStream out, File f, String basedir, String exincludeRegex)
      throws Exception {
    if (!f.exists()) {
      throw new BaseException("源文件或目录不存在");
    }

    if (exincludeRegex != null) {
      Pattern pat = Pattern.compile(exincludeRegex);
      if (pat.matcher(f.getName()).find()) {
        return;
      }
    }

    if (f.isDirectory()) {
      File[] fl = f.listFiles();
      if (fl.length == 0) {
        out.putNextEntry(new ZipEntry(basedir + "/"));
      }

      for (File file : fl) {
        zip(out, file, basedir + "/" + file.getName(), exincludeRegex);
      }

    } else {
      out.putNextEntry(new ZipEntry(basedir));
      FileInputStream in = null;
      try {
        in = new FileInputStream(f);
        IOUtils.copy(in, out);
      } catch (Exception e) {
        throw e;
      } finally {
        IOUtils.closeQuietly(in);
      }
    }
  }


  public static void unzip(InputStream in, File dir) {
    String tmpdir = System.getProperty("java.io.tmpdir");
    File file = new File(tmpdir, System.currentTimeMillis() + ".zip");
    FileOutputStream fout = null;
    try {
      fout = new FileOutputStream(file);
      IOUtils.copy(in, fout);
    } catch (Exception e) {
      throw new BaseException(e);
    } finally {
      IOUtils.closeQuietly(fout);
    }

    ZipFile zf = null;
    try {
      zf = new ZipFile(file);
    } catch (IOException e) {
      throw new BaseException(e);
    }
    unzip(zf, dir);
    file.delete();
  }


  public static void unzip(ZipFile Zin, File dir) {
    ZipEntry entry;

    File Fout = null;

    Enumeration<ZipEntry> enu = Zin.getEntries();
    while (enu.hasMoreElements()) {
      entry = enu.nextElement();

      if (entry.isDirectory()) {

        File filedir = new File(dir, entry.getName());
        if (!filedir.isDirectory()) {
          filedir.mkdirs();
        }
        continue;
      }

      Fout = new File(dir, entry.getName());

      File pdir = new File(Fout.getParent());
      if (!pdir.isDirectory()) {
        pdir.mkdirs();
      }

      BufferedOutputStream Bout = null;

      InputStream bin = null;
      try {
        bin = Zin.getInputStream(entry);
        Bout = new BufferedOutputStream(new FileOutputStream(Fout));
        IOUtils.copy(bin, Bout);
      } catch (Exception e) {
        continue;
      } finally {
        IOUtils.closeQuietly(Bout);
        IOUtils.closeQuietly(bin);
      }
    }


  }

}
