package unzip;

import java.io.File;

import net.tuxun.core.util.FileManager;

import org.apache.tools.zip.ZipFile;
import org.junit.Assert;
import org.junit.Test;

public class TestUnZip {

  // 测试压缩
  @Test
  public void testZip() throws Exception {
    File f = new File("src/test/resources/unzip/zip.txt");
    File src = new File("src/test/resources/unzip/zip.zip");
    FileManager.zip(src, f);
    Assert.assertTrue(src.exists());
  }

  // 测试解压
  @Test
  public void testUnzip() throws Exception {
    File zip = new File("src/test/resources/unzip/zip.zip");
    File dir = new File("src/test/resources/unzip/zip");
    FileManager.unzip(new ZipFile(zip), dir);
    Assert.assertTrue(dir.listFiles().length > 0);
  }

}
