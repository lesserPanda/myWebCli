package net.tuxun.component.config.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.tuxun.component.config.bean.Menu;
import net.tuxun.component.config.bean.Perm;
import net.tuxun.component.config.bean.TreeNode;
import net.tuxun.component.config.service.IMenuService;
import net.tuxun.core.util.PropertyUtil;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.Resource;

@SuppressWarnings("unchecked")
public class MenuServiceImpl implements IMenuService {

  // xml特性配置
  private Resource[] menuConfigLocation;

  // 权限配置xml文件
  private Resource[] permsLocations;
  // xml文件转化为menu集合
  private List<Menu> menus;
  // menuConfig.xml设置的exclude, includes属性
  // exclude, includes属性不能同时存在,同时存在优先赢取exclude,如果exclude不包含元素,再读取includes
  private List<String> excludes;
  private List<String> includes;

  /**
   * permsLocations注入后执行的方法,实现了spring的InitializingBean接口
   * 
   * @see <code>org.springframework.beans.factory.InitializingBean</code>
   */
  @Override
  public void afterPropertiesSet() {
    try {
      convert();
      readMenuConfig();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 重新设置menu的值
   * 
   * @param newMenus
   * @param menus
   * @param configs
   * @param b
   */
  private void resetMenus(List<Menu> newMenus, List<Menu> menus, List<String> configs, boolean b) {
    if (menus != null && !menus.isEmpty()) {
      Menu newMenu;
      for (Menu oldMenu : menus) {
        newMenu = getMenuFilter(oldMenu, configs, b);
        if (newMenu.getKey() != null) {
          newMenu = treeWalkFilter(newMenu, oldMenu, configs, b);
          newMenus.add(newMenu);
        }
      }
    }
  }

  private Menu treeWalkFilter(Menu newMenu, Menu oldMenu, List<String> configs, boolean b) {
    List<Menu> oldChilds = oldMenu.getChild();
    if (oldChilds != null && oldChilds.size() > 0) {
      Menu newChild;
      List<Menu> newChilds = new ArrayList<Menu>();
      for (Menu oldChild : oldChilds) {
        newChild = getMenuFilter(oldChild, configs, b);
        if (newChild.getKey() != null) {
          newChild = treeWalkFilter(newChild, oldChild, configs, b);
          newChilds.add(newChild);
        }
      }
      newMenu.setChild(newChilds);
    }
    return newMenu;
  }

  private Menu getMenuFilter(Menu oldMenu, List<String> configs, boolean b) {
    Menu newMenu = new Menu();
    boolean c = configs.contains(oldMenu.getKey());
    if ((b && !c) || (!b && c)) {
      newMenu.setChild(oldMenu.getChild());
      newMenu.setCount(oldMenu.getCount());
      newMenu.setDefaultPage(oldMenu.isDefaultPage());
      newMenu.setImg(oldMenu.getImg());
      newMenu.setKey(oldMenu.getKey());
      newMenu.setName(oldMenu.getName());
      newMenu.setParentKey(oldMenu.getParentKey());
      newMenu.setPerms(oldMenu.getPerms());
      newMenu.setSort(oldMenu.getSort());
      newMenu.setUrl(oldMenu.getUrl());
    }
    newMenu.setChild(null);
    return newMenu;
  }

  // 读取封装menu设置
  private void readMenuConfig() throws Exception {
    if (menuConfigLocation == null || menuConfigLocation.length == 0) {
      return;
    }
    SAXReader reader = new SAXReader();
    Document doc = reader.read(menuConfigLocation[0].getInputStream());
    Element root = doc.getRootElement();
    if (root == null) {
      throw new RuntimeException("menuConfig.xml文件没有根目录");
    }
    excludes = readConvert(root, EXCLUDE);
    List<Menu> newMenus = new ArrayList<Menu>();
    if (!excludes.isEmpty()) {
      resetMenus(newMenus, menus, excludes, true);
    } else {
      includes = readConvert(root, INCLUDE);
      if (!includes.isEmpty()) {
        resetMenus(newMenus, menus, includes, false);
      }
    }
    if (newMenus != null && !newMenus.isEmpty()) {
      menus = newMenus;
    }
  }

  private List<String> readConvert(Element root, String mark) throws Exception {
    List<String> list = new ArrayList<String>();
    Element element = root.element(mark);
    if (element != null) {
      List<Element> elements = element.elements(SHIRO_MENU);
      if (elements != null) {
        String key;
        for (Element emt : elements) {
          key = emt.attributeValue(SHIRO_KEY);
          if (key != null) {
            list.add(key);
          }
        }
      }
    }
    return list;
  }

  /**
   * 从xml菜单配置文件中解析数据、封装数据
   * 
   * @return 菜单集合
   * @throws IOException
   * @throws DocumentException
   */
  private void convert() throws Exception {
    SAXReader reader = new SAXReader();
    List<Menu> childs = new ArrayList<Menu>();
    menus = new ArrayList<Menu>();
    Document doc;
    Menu menu;
    Element root;
    for (Resource permsLocation : permsLocations) {
      doc = reader.read(permsLocation.getInputStream());
      root = doc.getRootElement();
      if (root == null) {
        throw new RuntimeException(permsLocation.getFilename() + "文件没有根目录");
      }
      // 父菜单
      List<Element> elements = root.elements(SHIRO_MENU);
      if (elements == null) {
        throw new RuntimeException(permsLocation.getFilename() + "文件没有配置菜单");
      }
      for (Element element : elements) {
        menu = getMenu(element);
        menu = treeWalk(menu, element);
        menus.add(menu);
      }
      // 子菜单
      List<Element> childElements = root.elements(SHIRO_CHILD);
      if (childElements != null && !childElements.isEmpty()) {
        for (Element element : childElements) {
          menu = getMenu(element);
          menu = treeWalk(menu, element);
          childs.add(menu);
        }
      }
    }
    // 排序
    Collections.sort(menus);
    Collections.sort(childs);
    // 整合
    together(menus, childs);
  }

  private void together(List<Menu> menus, List<Menu> childs) {
    Menu parentMenu;
    List<Menu> temps;
    for (Menu child : childs) {
      parentMenu = this.getMenuByKey(getMenus(), child.getParentKey());
      if (parentMenu != null) {
        if (parentMenu.getChild() != null) {
          parentMenu.getChild().add(child);
        } else {
          temps = new ArrayList<Menu>();
          temps.add(child);
          parentMenu.setChild(temps);
        }
        Collections.sort(parentMenu.getChild());
      }
    }
  }

  @Override
  public void addToMenus(List<Menu> childs) {
    this.together(menus, childs);
  }



  @Override
  public void removeMenus(String parentKey, String key) {
    List<Menu> _menus = menus;
    if (parentKey != null) {
      Menu parentMenu = getMenuByKey(menus, parentKey);
      if (parentMenu == null) {
        return;
      }
      _menus = parentMenu.getChild();
    }
    removeMenus(_menus, key);
  }

  /**
   * 采用的递归的方式删除菜单表的指定菜单
   * 
   * @param menus
   * @param key
   */
  public void removeMenus(List<Menu> menus, String key) {
    if (menus == null || menus.isEmpty()) {
      return;
    }
    Iterator<Menu> it = menus.iterator();
    while (it.hasNext()) {
      Menu menu = it.next();
      if (menu.getKey().equals(key)) {
        it.remove();
        return;
      } else {
        List<Menu> childMenus = menu.getChild();
        removeMenus(childMenus, key);
      }
    }
  }

  /**
   * 根据元素取得菜单对象
   * 
   * @param element 元素
   * @return 菜单
   */
  private Menu getMenu(Element element) {
    Menu menu =
        new Menu(element.attributeValue(SHIRO_SORT), element.attributeValue(SHIRO_KEY),
            element.attributeValue(SHIRO_NAME));

    menu.setUrl(element.attributeValue(SHIRO_URL));
    menu.setImg(element.attributeValue(SHIRO_IMG));
    menu.setParentKey(element.attributeValue(SHIRO_PARENT_KEY));
    menu.setCount(element.attributeValue(COUNT));
    menu.setDefaultPage(element.attributeValue(DEFAULT_PAGE) != null);

    List<Element> elements = element.elements(SHIRO_PERM);

    if (element.getName().equals(SHIRO_CHILD) && menu.getParentKey() == null) {
      throw new RuntimeException("<child></child>标签没有配置parentKey这个属性");
    }

    if (menu.getUrl() != null) {
      /*
       * if (elements == null || elements.size() == 0) { throw new RuntimeException("<menu key=\"" +
       * menu.getKey() + "\" name=\"" + menu.getName() + "\" url=\"" + menu.getUrl() +
       * "\"></menu>下没有配置<perm/>"); }
       */
      // 配置权限
      if (elements != null && elements.size() > 0) {
        Perm perm;
        List<Perm> perms = new ArrayList<Perm>();
        for (Element permElement : elements) {
          perm =
              new Perm(permElement.attributeValue(SHIRO_SORT),
                  permElement.attributeValue(SHIRO_NAME), permElement.attributeValue(SHIRO_CODE));
          perms.add(perm);
        }
        Collections.sort(perms);
        menu.setPerms(perms);
      }
    } else {
      if (elements != null && elements.size() > 0) {
        throw new RuntimeException("<menu key=\"" + menu.getKey() + "\" name=\"" + menu.getName()
            + "\"></menu>请配置URL属性");
      }
    }
    // ${}替换
    menu.setUrl(PropertyUtil.replace(menu.getUrl()));
    menu.setCount(PropertyUtil.replace(menu.getCount()));
    return menu;
  }


  /**
   * 封装子元素菜单到本身菜单中
   * 
   * @param menu 本身菜单(不包含子元素菜单)
   * @param element 本身元素
   * @return 节点的菜单(包含子元素菜单)
   */
  private Menu treeWalk(Menu menu, Element element) {
    List<Element> elements = element.elements(SHIRO_MENU);
    if (elements != null && elements.size() > 0) {
      Menu sonMenu;
      List<Menu> child = new ArrayList<Menu>();
      for (Element sonElement : elements) {
        sonMenu = getMenu(sonElement);
        sonMenu = treeWalk(sonMenu, sonElement); // 内循环遍历
        child.add(sonMenu);
      }
      // 排序
      Collections.sort(child);
      menu.setChild(child);
    }
    return menu;
  }

  @Override
  public List<Menu> getMenus() {
    return menus;
  }

  @Override
  public void setMenus(List<Menu> menus) {
    this.menus = menus;
  }

  @Override
  public Resource[] getPermsLocations() {
    return permsLocations;
  }

  @Override
  public void setPermsLocations(Resource[] permsLocations) {
    this.permsLocations = permsLocations;
  }

  public Resource[] getMenuConfigLocation() {
    return menuConfigLocation;
  }

  public void setMenuConfigLocation(Resource[] menuConfigLocation) {
    this.menuConfigLocation = menuConfigLocation;
  }

  public List<String> getExcludes() {
    return excludes;
  }

  public void setExcludes(List<String> excludes) {
    this.excludes = excludes;
  }

  public List<String> getIncludes() {
    return includes;
  }

  public void setIncludes(List<String> includes) {
    this.includes = includes;
  }

  @Override
  public List<Menu> getChildMenus(String key) {
    List<Menu> menus = getMenus();
    if (menus != null && !menus.isEmpty()) {
      if (key != null) {
        for (Menu menu : menus) {
          if (menu.getKey().equals(key)) {
            return menu.getChild();
          }
        }
      } else {
        return menus.get(0).getChild();
      }
    }
    return null;
  }

  private Menu getMenuByKey(List<Menu> menus, String key) {
    Menu parent = null;
    if (menus != null && !menus.isEmpty()) {
      if (key != null) {
        for (Menu menu : menus) {
          if (menu.getKey().equals(key)) {
            return menu;
          } else {
            parent = getMenuByKey(menu.getChild(), key);
            if (parent != null) {
              return parent;
            }
          }
        }
      }
    }

    return parent;
  }

  @Override
  public List<TreeNode> getTreeNodes() {
    // 建一个根目录
    List<TreeNode> rootNodes = new ArrayList<TreeNode>();
    TreeNode rootNode = new TreeNode();
    rootNode.setId("root:menu:top");
    rootNode.setText("根目录");
    rootNode.setState("open");
    List<Menu> menus = getMenus();
    List<TreeNode> nodes = new ArrayList<TreeNode>();
    if (menus != null && !menus.isEmpty()) {
      TreeNode node;
      for (Menu menu : menus) {
        node = getTreeNode(menu);
        node = treeWalkMenu(node, menu);
        nodes.add(node);
      }
    }
    rootNode.setChildren(nodes);
    rootNodes.add(rootNode);
    return rootNodes;
  }

  private TreeNode treeWalkMenu(TreeNode node, Menu menu) {
    if (menu.getChild() != null && !menu.getChild().isEmpty()) {
      node.setState("closed");
      List<TreeNode> children = new ArrayList<TreeNode>();
      TreeNode sonNode;
      for (Menu sonMenu : menu.getChild()) {
        sonNode = getTreeNode(sonMenu);
        sonNode = treeWalkMenu(sonNode, sonMenu);
        children.add(sonNode);
      }
      node.setChildren(children);
    }
    return node;
  }

  private TreeNode getTreeNode(Menu menu) {
    TreeNode node = new TreeNode();
    node.setId(menu.getKey());
    node.setText(menu.getName());
    if (menu.getPerms() != null && !menu.getPerms().isEmpty()) {
      List<TreeNode> children = new ArrayList<TreeNode>();
      TreeNode sonNode;
      for (Perm perm : menu.getPerms()) {
        sonNode = new TreeNode();
        sonNode.setId(perm.getCode());
        sonNode.setText(perm.getName());
        children.add(sonNode);
      }
      node.setState("closed");
      node.setChildren(children);
    }
    return node;
  }
}
