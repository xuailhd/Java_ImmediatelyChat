package xugl.immediatelychat.common;

import org.json.JSONArray;

import xugl.immediatelychar.chat.ChatOperate;
import xugl.immediatelychar.chat.IChatOperate;
import xugl.immediatelychat.contactdata.ContactDataOperate;
import xugl.immediatelychat.contactdata.IContactDataOperate;

public class CommonVariables {
	private static String dateFormat="yyyy-MM-dd HH:mm:ss.SSS";
	
	public static String getDateFormat() {
		return dateFormat;
	}
	
	private static String minDate="1900-01-01 00:00:00.000";

	public static String getMinDate() {
		return minDate;
	}

	private static ISaveLocalData saveLocalDataManager;
	public static ISaveLocalData getLocalDataManager() {
		if(saveLocalDataManager==null)
		{
			saveLocalDataManager=new SaveLocalData();
		}
		return saveLocalDataManager;
	}
	
	private static IContactDataOperate contactDataOperate;
	public static IContactDataOperate getContactDataOperate() {
		if(contactDataOperate==null)
		{
			contactDataOperate=new ContactDataOperate();
		}
		return contactDataOperate;
	}
	
	private static IChatOperate chatOperate;
	public static IChatOperate getChatOperate() {
		if(chatOperate==null)
		{
			chatOperate=new ChatOperate();
		}
		return chatOperate;
	}
	
	private static ISendMsg sendMsg;
	public static ISendMsg getSendMsg() {
		if(sendMsg==null)
		{
			sendMsg=new SendMsg();
		}
		return sendMsg;
	}

	private static String PSIP;
	private static int PSPort;
	private static String MMSIP;
	private static int MMSPort;
	private static String MCSIP;
	private static int MCSPort;


	private static String objectID;
	private static String account;
	private static String password;
	private static String groupID;
	
	public static String getPSIP() {
		return PSIP;
	}
	public static void setPSIP(String pSIP) {
		PSIP = pSIP;
	}
	public static int getPSPort() {
		return PSPort;
	}
	public static void setPSPort(int pSPort) {
		PSPort = pSPort;
	}
	public static String getMMSIP() {
		return MMSIP;
	}
	public static void setMMSIP(String mMSIP) {
		MMSIP = mMSIP;
	}
	public static int getMMSPort() {
		return MMSPort;
	}
	public static void setMMSPort(int mMSPort) {
		MMSPort = mMSPort;
	}
	public static String getMCSIP() {
		return MCSIP;
	}
	public static void setMCSIP(String mCSIP) {
		MCSIP = mCSIP;
	}
	public static int getMCSPort() {
		return MCSPort;
	}
	public static void setMCSPort(int mCSPort) {
		MCSPort = mCSPort;
	}
	public static String getObjectID() {
		return objectID;
	}
	public static void setObjectID(String objectID) {
		CommonVariables.objectID = objectID;
	}
	public static String getAccount() {
		return account;
	}
	public static void setAccount(String account) {
		CommonVariables.account = account;
	}
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		CommonVariables.password = password;
	}
	public static String getGroupID() {
		return groupID;
	}
	public static void setGroupID(String groupID) {
		CommonVariables.groupID = groupID;
	}
	
	private static String latestTime;

	public static String getLatestTime() {
		return latestTime;
	}
	public static void setLatestTime(String latestTime) {
		CommonVariables.latestTime = latestTime;
	}
	
	private static String updateTime;
	
	public static String getUpdateTime() {
		return updateTime;
	}
	public static void setUpdateTime(String updateTime) {
		CommonVariables.updateTime = updateTime;
	}

	private static JSONArray jsonMsgArray;

	public static JSONArray getJsonMsgArray() {
		if(jsonMsgArray==null)
		{
			jsonMsgArray=new JSONArray();
		}
		return jsonMsgArray;
	}

}
