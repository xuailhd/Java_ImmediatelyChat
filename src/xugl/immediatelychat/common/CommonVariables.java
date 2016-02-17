package xugl.immediatelychat.common;

import java.util.Date;

import org.json.JSONArray;

public class CommonVariables {
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
	
	private static Date latestTime;

	public static Date getLatestTime() {
		return latestTime;
	}
	public static void setLatestTime(Date latestTime) {
		CommonVariables.latestTime = latestTime;
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
