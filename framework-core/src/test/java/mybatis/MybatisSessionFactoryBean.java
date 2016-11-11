package mybatis;

import static org.springframework.util.StringUtils.arrayToCommaDelimitedString;
import static org.springframework.util.StringUtils.hasLength;
import static org.springframework.util.StringUtils.tokenizeToStringArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.AntPathMatcher;

/**
 * 让别名包支持通配符, 提高加载效率
 * 
 * @author liuqiang
 * 
 */
public class MybatisSessionFactoryBean implements InitializingBean {

  protected static Logger log = LoggerFactory.getLogger(MybatisSessionFactoryBean.class);
  private PathMatchingResourcePatternResolver resourcePatternResolver =
      new PathMatchingResourcePatternResolver();
  private static String CLASSPATH_ALL_URL_PREFIX = "classpath*:";
  private String typeAliasesPackage;

  public void afterPropertiesSet() {
    log.debug("Mybatis别名包通配符过滤开始");
    log.debug("oldTypeAliasesPackage=[{}]", typeAliasesPackage);
    long startTime = System.currentTimeMillis();
    String newTypeAliasesPackage = wildcardFilter(typeAliasesPackage);
    log.debug("newTypeAliasesPackage=[{}]", newTypeAliasesPackage);
    log.debug("Mybatis别名包通配符过滤结束,  用时{}毫秒", System.currentTimeMillis() - startTime);
    // super.setTypeAliasesPackage(newTypeAliasesPackage);
  }

  /**
   * 别名包通配符过滤
   * 
   * @param typeAliasesPackage 别名包名称
   * @return 已过滤完毕的别名包
   */
  private String wildcardFilter(String typeAliasesPackage) {
    if (hasLength(typeAliasesPackage) && typeAliasesPackage.indexOf("*") != -1) {
      List<String> list = new ArrayList<String>();
      String[] typeAliasesPackageArray = tokenizeToStringArray(typeAliasesPackage, ",; \t\n");
      for (String aliasesPackage : typeAliasesPackageArray) {
        addAliasesPackageToList(aliasesPackage, list);
      }
      return arrayToCommaDelimitedString(list.toArray());
    }
    return typeAliasesPackage;
  }

  /**
   * 通配符路径解析成正确的相对路径保存到list中<br>
   * 例：net.tuxun.**.bean -> [net.tuxun.xx.yy.bean,net.tuxun.xx.zz.bean]
   * 
   * @param aliasesPackage 包别名
   * @param list 存放的集合
   */
  private void addAliasesPackageToList(String aliasesPackage, List<String> list) {
    try {
      String prefix = determinePrefix(aliasesPackage);
      aliasesPackage = CLASSPATH_ALL_URL_PREFIX + aliasesPackage.replace('.', '/');
      Resource resourceArray[] = this.resourcePatternResolver.getResources(aliasesPackage);
      for (Resource resource : resourceArray) {
        list.add(parseToPackage(resource, prefix));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 资源转换成相对路径
   * 
   * @param resource 资源
   * @return 资源对应的相对路径
   * @throws IOException IO异常信息
   */
  private String parseToPackage(Resource resource, String prefix) throws IOException {
    String aliasesPackage = resource.getURL().toString();
    aliasesPackage = aliasesPackage.replace("/", ".");
    aliasesPackage =
        aliasesPackage.substring(aliasesPackage.lastIndexOf(prefix), aliasesPackage.length() - 1);
    return aliasesPackage;
  }

  /**
   * 取得前缀
   * 
   * @param location
   * @return
   */
  private String determinePrefix(String location) {
    int prefixEnd = 0;
    int rootDirEnd = location.length();
    while (rootDirEnd > prefixEnd
        && new AntPathMatcher().isPattern(location.substring(prefixEnd, rootDirEnd))) {
      rootDirEnd = location.lastIndexOf('.', rootDirEnd - 2) + 1;
    }
    if (rootDirEnd == 0) {
      rootDirEnd = prefixEnd;
    }
    return location.substring(0, rootDirEnd);
  }

  public String getTypeAliasesPackage() {
    return typeAliasesPackage;
  }

  public void setTypeAliasesPackage(String typeAliasesPackage) {
    this.typeAliasesPackage = typeAliasesPackage;
  }

}
