package net.tuxun.customer.module.myWeb.bean;

/**
 * 数据库表[message_perple]的数据模型
 * 
 * 
 * 
 * @author pand
 * 
 */

@SuppressWarnings("serial")
public class MessagePerple implements java.io.Serializable {

    /** 默认构造函数 */
    public MessagePerple() {
    }
    
    /** 全参构造函数 */
    public MessagePerple(java.lang.String id, java.lang.String messagePerple, java.lang.String messageEmail, java.lang.String messagePerpleIp, java.util.Date createTime, java.lang.String messageContent, java.lang.String messageTitle) {
      this.id = id;
      this.messagePerple = messagePerple;
      this.messageEmail = messageEmail;
      this.messagePerpleIp = messagePerpleIp;
      this.createTime = createTime;
      this.messageContent = messageContent;
      this.messageTitle = messageTitle;
    }
    
    // 编号 
    private java.lang.String id;
    // 留言人姓名 
    private java.lang.String messagePerple;
    //留言人email
    private java.lang.String messageEmail;
    // 留言人ip 
    private java.lang.String messagePerpleIp;
    // 留言时间 
    private java.util.Date createTime;
    // 留言内容 
    private java.lang.String messageContent;
    //留言主题
    private java.lang.String messageTitle;
    
	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getMessagePerple() {
		return messagePerple;
	}

	public void setMessagePerple(java.lang.String messagePerple) {
		this.messagePerple = messagePerple;
	}

	public java.lang.String getMessageEmail() {
		return messageEmail;
	}

	public void setMessageEmail(java.lang.String messageEmail) {
		this.messageEmail = messageEmail;
	}

	public java.lang.String getMessagePerpleIp() {
		return messagePerpleIp;
	}

	public void setMessagePerpleIp(java.lang.String messagePerpleIp) {
		this.messagePerpleIp = messagePerpleIp;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(java.lang.String messageContent) {
		this.messageContent = messageContent;
	}

	public java.lang.String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(java.lang.String messageTitle) {
		this.messageTitle = messageTitle;
	}
     
   
}


