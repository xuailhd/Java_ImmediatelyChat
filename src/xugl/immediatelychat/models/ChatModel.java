package xugl.immediatelychat.models;

public class ChatModel {
	private int chatType;
	private int groupID;
	private int DestinationObjectID;
	private String LatestMsg;
	private String LatestTime;
	private String groupName;
	private String contactPersonName;
	
	/**
     * 0: single chat, 1: group chat
     */
	public int getChatType() {
		return chatType;
	}
	public void setChatType(int chatType) {
		this.chatType = chatType;
	}
	public int getGroupID() {
		return groupID;
	}
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
	public int getDestinationObjectID() {
		return DestinationObjectID;
	}
	public void setDestinationObjectID(int destinationObjectID) {
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
	
}
