package mybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试mybatis 别名包通配符的支持
 * 
 * @author liuqiang
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/mybatis/application.xml")
public class TestTypeAliasesPackage {

  @Test
  public void loadTypeAliasesPackage() {
    // None
  }
}
