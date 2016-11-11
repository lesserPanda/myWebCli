package com.lesserPanda.module.myWeb.bean;

import java.util.List;

import com.lesserPanda.module.myWeb.bean.CmsComment;

/**
 * 数据库表[article]的数据模型
 * 
 * 
 * 
 * @author pand
 * 
 */
@SuppressWarnings("serial")
public class Article implements java.io.Serializable {

    /** 默认构造函数 */
    public Article() {
    }
    
    /** 全参构造函数 */
    public Article(java.lang.String id, java.lang.String categoryId, java.lang.String title, java.lang.String content, java.lang.String link, java.lang.String color, java.lang.String image, java.lang.String keywords, java.lang.String description, java.lang.Integer weight, java.util.Date weightDate, java.lang.Integer pushParise, java.lang.Integer hits, java.lang.String posid, java.lang.String customContentView, java.lang.String viewConfig, java.lang.String createBy, java.util.Date createDate, java.lang.String updateBy, java.util.Date updateDate, java.lang.String remarks, java.lang.String delFlag, java.lang.String zzFlag) {
      this.id = id;
      this.categoryId = categoryId;
      this.title = title;
      this.content = content;
      this.link = link;
      this.color = color;
      this.image = image;
      this.keywords = keywords;
      this.description = description;
      this.weight = weight;
      this.weightDate = weightDate;
      this.pushParise = pushParise;
      this.hits = hits;
      this.posid = posid;
      this.customContentView = customContentView;
      this.viewConfig = viewConfig;
      this.createBy = createBy;
      this.createDate = createDate;
      this.updateBy = updateBy;
      this.updateDate = updateDate;
      this.remarks = remarks;
      this.delFlag = delFlag;
      this.zzFlag = zzFlag;
    }
    
    // 编号 
    private java.lang.String id;
    // 栏目编号 
    private java.lang.String categoryId;
    // 标题 
    private java.lang.String title;
    // 文章内容 
    private java.lang.String content;
    // 文章链接 
    private java.lang.String link;
    // 标题颜色 
    private java.lang.String color;
    // 文章图片 
    private java.lang.String image;
    // 关键字 
    private java.lang.String keywords;
    // 描述、摘要 
    private java.lang.String description;
    // 权重，越大越靠前 
    private java.lang.Integer weight;
    // 权重期限 
    private java.util.Date weightDate;
    // 点赞数量 
    private java.lang.Integer pushParise;
    // 点击数 
    private java.lang.Integer hits;
    // 推荐位，多选 
    private java.lang.String posid;
    // 自定义内容视图 
    private java.lang.String customContentView;
    // 视图配置 
    private java.lang.String viewConfig;
    // 创建者 
    private java.lang.String createBy;
    // 创建时间 
    private java.util.Date createDate;
    // 更新者 
    private java.lang.String updateBy;
    // 更新时间 
    private java.util.Date updateDate;
    // 备注信息 
    private java.lang.String remarks;
    // 删除标记 
    private java.lang.String delFlag;
    //伤不起点击数
    private java.lang.Integer vulnerableNum;
    //喜欢点击数
    private java.lang.Integer likeNum;
    //感兴趣点击数
    private java.lang.Integer interestedNum;
    //转载收藏003标记
    private java.lang.String zzFlag;
    // 
    private java.util.List<CmsComment> cmsComments;
     
    
    public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(java.lang.String categoryId) {
		this.categoryId = categoryId;
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

	public java.lang.String getLink() {
		return link;
	}

	public void setLink(java.lang.String link) {
		this.link = link;
	}

	public java.lang.String getColor() {
		return color;
	}

	public void setColor(java.lang.String color) {
		this.color = color;
	}

	public java.lang.String getImage() {
		return image;
	}

	public void setImage(java.lang.String image) {
		this.image = image;
	}

	public java.lang.String getKeywords() {
		return keywords;
	}

	public void setKeywords(java.lang.String keywords) {
		this.keywords = keywords;
	}

	public java.lang.String getDescription() {
		return description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public java.lang.Integer getWeight() {
		return weight;
	}

	public void setWeight(java.lang.Integer weight) {
		this.weight = weight;
	}

	public java.util.Date getWeightDate() {
		return weightDate;
	}

	public void setWeightDate(java.util.Date weightDate) {
		this.weightDate = weightDate;
	}

	public java.lang.Integer getPushParise() {
		return pushParise;
	}

	public void setPushParise(java.lang.Integer pushParise) {
		this.pushParise = pushParise;
	}

	public java.lang.Integer getHits() {
		return hits;
	}

	public void setHits(java.lang.Integer hits) {
		this.hits = hits;
	}

	public java.lang.String getPosid() {
		return posid;
	}

	public void setPosid(java.lang.String posid) {
		this.posid = posid;
	}

	public java.lang.String getCustomContentView() {
		return customContentView;
	}

	public void setCustomContentView(java.lang.String customContentView) {
		this.customContentView = customContentView;
	}

	public java.lang.String getViewConfig() {
		return viewConfig;
	}

	public void setViewConfig(java.lang.String viewConfig) {
		this.viewConfig = viewConfig;
	}

	public java.lang.String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(java.lang.String createBy) {
		this.createBy = createBy;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public java.lang.String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(java.lang.String updateBy) {
		this.updateBy = updateBy;
	}

	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	public java.lang.String getRemarks() {
		return remarks;
	}

	public void setRemarks(java.lang.String remarks) {
		this.remarks = remarks;
	}

	public java.lang.String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(java.lang.String delFlag) {
		this.delFlag = delFlag;
	}

	public java.util.List<CmsComment> getCmsComments() {
		return cmsComments;
	}
	public void setCmsComments(List<CmsComment> cmsComments) {
		this.cmsComments = cmsComments;
	}

	public java.lang.Integer getVulnerableNum() {
		return vulnerableNum;
	}

	public void setVulnerableNum(java.lang.Integer vulnerableNum) {
		this.vulnerableNum = vulnerableNum;
	}

	public java.lang.Integer getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(java.lang.Integer likeNum) {
		this.likeNum = likeNum;
	}

	public java.lang.Integer getInterestedNum() {
		return interestedNum;
	}

	public void setInterestedNum(java.lang.Integer interestedNum) {
		this.interestedNum = interestedNum;
	}

	public java.lang.String getZzFlag() {
		return zzFlag;
	}

	public void setZzFlag(java.lang.String zzFlag) {
		this.zzFlag = zzFlag;
	}
	
}


