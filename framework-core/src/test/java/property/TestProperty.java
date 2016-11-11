package property;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * *.properties 配置文件读取的三种方式 <br>
 * 1.通过{@link net.tuxun.core.util.PropertyUtil#get(String)}工具类静态读取 <br>
 * 2.通过{@Value("${key}")}注解 <br>
 * 3.在JSP页面中通过${conf['key']}
 * 
 * @author Administrator
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/property/application.xml")
public class TestProperty {

  protected Logger log = LoggerFactory.getLogger(TestProperty.class);


  // PropertyUtil 取得.properties中的值
  @Test
  public void testPropertyUtil() {

    String v1 = PropertyUtil.get("editor.attached.path");
    Assert.notNull(v1);
    log.info("webapp.properties文件中editor.attached.path={}", v1);

    String v2 = PropertyUtil.get("login.online.user");
    Assert.notNull(v2);
    log.info("user.properties文件中login.online.user={}", v2);
  }

  // PropertyUtil 测试值重复的地方
  // 测试结果：覆盖前面的值
  @Test
  public void testPropertyUtilRepeat() {
    String v1 = PropertyUtil.get("test.same");
    Assert.notNull(v1);
    log.info("webapp.properties文件中test.same={}", v1);

    String v2 = PropertyUtil.get("test.same");
    Assert.notNull(v2);
    log.info("user.properties文件中test.same={}", v2);

    Assert.isTrue(v1.equals(v2));
  }



  // 使用@Value进行取值
  @Value("${editor.attached.path}")
  private String v11;
  @Value("${login.online.user}")
  private String v22;

  @Test
  public void testValue() {
    Assert.notNull(v11);
    log.info("webapp.properties文件中editor.attached.path={}", v11);

    Assert.notNull(v22);
    log.info("user.properties文件中login.online.user={}", v22);
  }

  // 使用@Value进行取值,测试重复的值
  // 这里需保证取值结果与PropertyUtil取值结果保持一致
  @Value("${test.same}")
  private String vx;
  @Value("${test.same}")
  private String vy;

  @Test
  public void testValueRepeat() {
    Assert.notNull(vx);
    log.info("webapp.properties文件中test.same={}", vx);

    Assert.notNull(vy);
    log.info("user.properties文件中test.same={}", vy);

    Assert.isTrue(vx.equals(vy));
  }

  // PropertyUtil测试中文
  @SuppressWarnings("deprecation")
  @Test
  public void testPropertyUtilCh() {
    String ch1 = PropertyUtil.get("test.ch");
    Assert.notNull(ch1);
    log.info("test.ch={}", ch1);

    String ch2 = PropertyUtil.getUTF8("test.ch");
    Assert.notNull(ch2);
    log.info("test.ch={}", ch2);
  }

  // @Value测试中文
  @Value("${test.ch}")
  private String ch;

  @Test
  public void testValueCh() {
    Assert.notNull(ch);
    log.info("test.ch={}", ch);
  }

  // PropertyUtil
  // 测试.properties中含有的值是否正确${key}
  @Test
  public void testPropertyUtilHybrid() {
    String loginUrl = PropertyUtil.get("login.url");
    Assert.notNull(loginUrl);
    log.info("login.url={}", loginUrl);
  }

  // @Value
  // 测试.properties中含有的值是否正确${key}
  @Value("${login.url}")
  private String loginUrl;

  @Test
  public void testValueHybrid() {
    Assert.notNull(loginUrl);
    log.info("login.url={}", loginUrl);
  }
}
