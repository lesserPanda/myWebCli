package net.tuxun.component.config.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 生成tree
 * 
 * @author liuqiang
 * 
 */
@SuppressWarnings("serial")
public class TreeNode implements Serializable {

  // 树节点标识
  private String id;
  // 树节点名称
  private String text;
  // 树节点状状态closed,open默认为closed
  private String state;
  // 树的子节点
  private List<TreeNode> children;

  public TreeNode() {
    super();
  }

  public TreeNode(String id, String text, String state, List<TreeNode> children) {
    super();
    this.id = id;
    this.text = text;
    this.state = state;
    this.children = children;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public List<TreeNode> getChildren() {
    return children;
  }

  public void setChildren(List<TreeNode> children) {
    this.children = children;
  }
}
