package net.tuxun.customer.module.myWeb.bean;

/**
 * 数据库表[music_info]的数据模型
 * 
 * 
 * 
 * @author pand
 * 
 */

@SuppressWarnings("serial")
public class MusicInfo implements java.io.Serializable {

    /** 默认构造函数 */
    public MusicInfo() {
    }
    
    /** 全参构造函数 */
    public MusicInfo(java.lang.String id, java.lang.String musicName, java.lang.String musicAuthor, java.lang.String musicSrc, java.lang.String createBy, java.util.Date createDate, java.lang.String updateBy, java.util.Date updateDate, java.lang.String remarks, java.lang.String delFlag) {
      this.id = id;
      this.musicName = musicName;
      this.musicAuthor = musicAuthor;
      this.musicSrc = musicSrc;
      this.createBy = createBy;
      this.createDate = createDate;
      this.updateBy = updateBy;
      this.updateDate = updateDate;
      this.remarks = remarks;
      this.delFlag = delFlag;
    }
    
    // ID 
    private java.lang.String id;
    // 音乐名字 
    private java.lang.String musicName;
    // 音乐作者 
    private java.lang.String musicAuthor;
    // 音乐链接 
    private java.lang.String musicSrc;
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
	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getMusicName() {
		return musicName;
	}

	public void setMusicName(java.lang.String musicName) {
		this.musicName = musicName;
	}

	public java.lang.String getMusicAuthor() {
		return musicAuthor;
	}

	public void setMusicAuthor(java.lang.String musicAuthor) {
		this.musicAuthor = musicAuthor;
	}

	public java.lang.String getMusicSrc() {
		return musicSrc;
	}

	public void setMusicSrc(java.lang.String musicSrc) {
		this.musicSrc = musicSrc;
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
     
}


