package net.tuxun.customer.module.myWeb.bean;

/**
 * 数据库表[skill_info]的数据模型
 * 
 * 
 * 
 * @author pand
 * 
 */

@SuppressWarnings("serial")
public class SkillInfo implements java.io.Serializable {

    /** 默认构造函数 */
    public SkillInfo() {
    }
    
    /** 全参构造函数 */
    public SkillInfo(java.lang.String id, java.lang.String skillType, java.lang.String skillName, java.lang.Integer skillPer) {
      this.id = id;
      this.skillType = skillType;
      this.skillName = skillName;
      this.skillPer = skillPer;
    }
    
    // 编号 
    private java.lang.String id;
    // 技能类型 
    private java.lang.String skillType;
    // 技能名称 
    private java.lang.String skillName;
    // 技能占比 
    private java.lang.Integer skillPer;
	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getSkillType() {
		return skillType;
	}

	public void setSkillType(java.lang.String skillType) {
		this.skillType = skillType;
	}

	public java.lang.String getSkillName() {
		return skillName;
	}

	public void setSkillName(java.lang.String skillName) {
		this.skillName = skillName;
	}

	public java.lang.Integer getSkillPer() {
		return skillPer;
	}

	public void setSkillPer(java.lang.Integer skillPer) {
		this.skillPer = skillPer;
	}
     
}


