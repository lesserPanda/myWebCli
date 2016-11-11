package page.animal.bean;

/**
 * 数据库表[WOLF]的数据模型 狼
 * 
 * 
 * @author liuqiang
 * 
 */

@SuppressWarnings("serial")
public class Wolf implements java.io.Serializable {

  /** 默认构造函数 */
  public Wolf() {}

  /** 全参构造函数 */
  public Wolf(java.lang.String id, java.lang.String variety, java.lang.String color,
      java.lang.Integer weight) {
    this.id = id;
    this.variety = variety;
    this.color = color;
    this.weight = weight;
  }

  // 主键
  private java.lang.String id;
  // 品种
  private java.lang.String variety;
  // 毛色
  private java.lang.String color;
  // 体重
  private java.lang.Integer weight;

  public java.lang.String getId() {
    return this.id;
  }

  public void setId(java.lang.String id) {
    this.id = id;
  }

  public java.lang.String getVariety() {
    return this.variety;
  }

  public void setVariety(java.lang.String variety) {
    this.variety = variety;
  }

  public java.lang.String getColor() {
    return this.color;
  }

  public void setColor(java.lang.String color) {
    this.color = color;
  }

  public java.lang.Integer getWeight() {
    return this.weight;
  }

  public void setWeight(java.lang.Integer weight) {
    this.weight = weight;
  }
}
