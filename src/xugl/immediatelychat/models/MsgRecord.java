package xugl.immediatelychat.models;

public class MsgRecord {
	private String msgSenderObjectID;
	private String msgSenderName;
	private String msgContent;
	private String msgRecipientObjectID;
	private String msgRecipientGroupID;
	private int msgType;
	private String sendTime;
	public String getMsgSenderObjectID() {
		return msgSenderObjectID;
	}
	public void setMsgSenderObjectID(String msgSenderObjectID) {
		this.msgSenderObjectID = msgSenderObjectID;
	}
	public String getMsgSenderName() {
		return msgSenderName;
	}
	public void setMsgSenderName(String msgSenderName) {
		this.msgSenderName = msgSenderName;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getMsgRecipientObjectID() {
		return msgRecipientObjectID;
	}
	public void setMsgRecipientObjectID(String msgRecipientObjectID) {
		this.msgRecipientObjectID = msgRecipientObjectID;
	}
	public String getMsgRecipientGroupID() {
		return msgRecipientGroupID;
	}
	public void setMsgRecipientGroupID(String msgRecipientGroupID) {
		this.msgRecipientGroupID = msgRecipientGroupID;
	}
	public int getMsgType() {
		return msgType;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
}
