package net.tuxun.component.config.service;

import java.util.List;

import net.tuxun.component.config.bean.Menu;
import net.tuxun.component.config.bean.TreeNode;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

public interface IMenuService extends InitializingBean {

  public static final String SHIRO_MENU = "menu";
  public static final String SHIRO_CHILD = "child";
  public static final String SHIRO_PARENT_KEY = "parentKey";
  public static final String SHIRO_PERM = "perm";
  public static final String SHIRO_SORT = "sort";
  public static final String SHIRO_KEY = "key";
  public static final String SHIRO_NAME = "name";
  public static final String SHIRO_URL = "url";
  public static final String SHIRO_IMG = "img";
  public static final String SHIRO_CODE = "code";
  public static final String COUNT = "count";
  public static final String DEFAULT_PAGE = "defaultPage";
  public static final String HOST = "host";

  public static final String EXCLUDE = "exclude";
  public static final String INCLUDE = "include";

  public abstract List<Menu> getMenus();

  public abstract void setMenus(List<Menu> menus);

  public abstract Resource[] getPermsLocations();

  public abstract void setPermsLocations(Resource[] permsLocations);

  public abstract List<Menu> getChildMenus(String key);

  /**
   * 将menus转换成树形菜单要求的格式返回
   * 
   * @return
   */
  public abstract List<TreeNode> getTreeNodes();

  /**
   * 加入子菜单
   * 
   * @param menus
   * @param childs
   */
  public void addToMenus(List<Menu> childs);

  /**
   * 删除指定的菜单
   * 
   * @param parentKey 指定菜单的上级菜单,目的是提高效率。些参数可以为空。
   * @param key 指定的菜单
   */
  public void removeMenus(String parentKey, String key);
}
