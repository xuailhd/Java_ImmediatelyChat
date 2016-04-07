package xugl.immediatelychat.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ChatModel implements Parcelable  {
	private int chatType;
	private String chatID;
	private String groupID;
	private String DestinationObjectID;
	private String LatestMsg;
	private String LatestTime;
	private String groupName;
	private String contactPersonName;
	/**
     * 1: single chat, 2: group chat
     */
	public int getChatType() {
		return chatType;
	}
	public void setChatType(int chatType) {
		this.chatType = chatType;
	}
	public String getChatID() {
		return chatID;
	}
	public void setChatID(String chatID) {
		this.chatID = chatID;
	}
	public String getGroupID() {
		return groupID;
	}
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	public String getDestinationObjectID() {
		return DestinationObjectID;
	}
	public void setDestinationObjectID(String destinationObjectID) {
		DestinationObjectID = destinationObjectID;
	}
	public String getLatestMsg() {
		return LatestMsg;
	}
	public void setLatestMsg(String latestMsg) {
		LatestMsg = latestMsg;
	}
	public String getLatestTime() {
		return LatestTime;
	}
	public void setLatestTime(String latestTime) {
		LatestTime = latestTime;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getContactPersonName() {
		return contactPersonName;
	}
	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(chatType);
		dest.writeString(groupID);
		dest.writeString(DestinationObjectID);
		dest.writeString(LatestMsg);
		dest.writeString(LatestTime);
		dest.writeString(LatestMsg);
		dest.writeString(groupName);
		dest.writeString(contactPersonName);
		
	}
	
//	private ChatModel(Parcel in) {
//		chatType = in.readInt();
//		groupID=in.readString();
//		DestinationObjectID=in.readString();
//		LatestMsg=in.readString();
//		LatestTime=in.readString();
//		LatestMsg=in.readString();
//		groupName=in.readString();
//		contactPersonName=in.readString();
//    }
}
