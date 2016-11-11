package com.lesserPanda.module.myWeb.bean;

/**
 * 数据库表[cms_comment]的数据模型
 * 
 * 
 * 
 * @author pand
 * 
 */
@SuppressWarnings("serial")
public class CmsComment implements java.io.Serializable {

    /** 默认构造函数 */
    public CmsComment() {
    }
    
    /** 全参构造函数 */
    public CmsComment(java.lang.String id, java.lang.String articleId, java.lang.String title, java.lang.String content, java.lang.String name, java.lang.String ip, java.util.Date createDate, java.lang.String robackName, java.util.Date robackCreateDate, java.lang.String auditUserId, java.util.Date auditDate, java.lang.String delFlag) {
      this.id = id;
      this.articleId = articleId;
      this.title = title;
      this.content = content;
      this.name = name;
      this.ip = ip;
      this.createDate = createDate;
      this.robackName = robackName;
      this.robackCreateDate = robackCreateDate;
      this.auditUserId = auditUserId;
      this.auditDate = auditDate;
      this.delFlag = delFlag;
    }
    
    // 编号 
    private java.lang.String id;
    // 文章编号 
    private java.lang.String articleId;
    // 栏目内容的标题 
    private java.lang.String title;
    // 评论内容 
    private java.lang.String content;
    // 评论姓名 
    private java.lang.String name;
    // 评论IP 
    private java.lang.String ip;
    // 评论时间 
    private java.util.Date createDate;
    // 回复评论人 
    private java.lang.String robackName;
    // 评论时间 
    private java.util.Date robackCreateDate;
    // 审核人 
    private java.lang.String auditUserId;
    // 审核时间 
    private java.util.Date auditDate;
    // 删除标记 
    private java.lang.String delFlag;
	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getArticleId() {
		return articleId;
	}

	public void setArticleId(java.lang.String articleId) {
		this.articleId = articleId;
	}

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public java.lang.String getContent() {
		return content;
	}

	public void setContent(java.lang.String content) {
		this.content = content;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getIp() {
		return ip;
	}

	public void setIp(java.lang.String ip) {
		this.ip = ip;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public java.lang.String getRobackName() {
		return robackName;
	}

	public void setRobackName(java.lang.String robackName) {
		this.robackName = robackName;
	}

	public java.util.Date getRobackCreateDate() {
		return robackCreateDate;
	}

	public void setRobackCreateDate(java.util.Date robackCreateDate) {
		this.robackCreateDate = robackCreateDate;
	}

	public java.lang.String getAuditUserId() {
		return auditUserId;
	}

	public void setAuditUserId(java.lang.String auditUserId) {
		this.auditUserId = auditUserId;
	}

	public java.util.Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(java.util.Date auditDate) {
		this.auditDate = auditDate;
	}

	public java.lang.String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(java.lang.String delFlag) {
		this.delFlag = delFlag;
	}
     
    
}


