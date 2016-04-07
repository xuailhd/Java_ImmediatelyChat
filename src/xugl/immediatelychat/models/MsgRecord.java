package xugl.immediatelychat.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MsgRecord implements Parcelable {
	private String msgID;
	private String msgSenderObjectID;
	private String msgSenderName;
	private String msgContent;
	private String msgRecipientObjectID;
	private String msgRecipientGroupID;
	private int msgType;
	private String sendTime;
	
	public String getMsgID() {
		return msgID;
	}
	public void setMsgID(String msgID) {
		this.msgID = msgID;
	}
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
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(msgSenderObjectID);
		dest.writeString(msgSenderName);
		dest.writeString(msgContent);
		dest.writeString(msgRecipientObjectID);
		dest.writeString(msgRecipientGroupID);
		dest.writeInt(msgType);
		dest.writeString(sendTime);
	}
	
}
