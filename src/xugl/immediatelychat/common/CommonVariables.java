package xugl.immediatelychat.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.JSONArray;

import xugl.immediatelychar.chat.ChatOperate;
import xugl.immediatelychar.chat.IChatOperate;
import xugl.immediatelychar.chat.IMsgRecordOperate;
import xugl.immediatelychar.chat.MsgRecordOperate;
import xugl.immediatelychat.contactdata.ContactDataOperate;
import xugl.immediatelychat.contactdata.IContactDataOperate;
import xugl.immediatelychat.models.MsgRecord;

public class CommonVariables {
	private static int baseActivityCount = 0;
	
	public static int getBaseActivityCount() {
		return baseActivityCount;
	}
	public static void setBaseActivityCount(int baseActivityCount) {
		CommonVariables.baseActivityCount = baseActivityCount;
	}

	private static String dateFormat="yyyy-MM-dd HH:mm:ss.SSS";
	private static String dateFormat2="yyyy-MM-dd HH:mm:ss";
	
	public static String getDateFormat2() {
		return dateFormat2;
	}

	private static SimpleDateFormat simpleDateFormat;
	private static SimpleDateFormat simpleDateFormat2;
	public static SimpleDateFormat getSimpleDateFormat() {
		if(simpleDateFormat==null)
		{
			simpleDateFormat = new SimpleDateFormat(dateFormat);
		}
		return simpleDateFormat;
	}
	
	public static SimpleDateFormat getSimpleDateFormat2() {
		if(simpleDateFormat2==null)
		{
			simpleDateFormat2 = new SimpleDateFormat(dateFormat2);
		}
		return simpleDateFormat2;
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
	
	private static IMsgRecordOperate msgRecordOperate;
	
	public static IMsgRecordOperate getMsgRecordOperate() {
		if(msgRecordOperate==null)
		{
			msgRecordOperate=new MsgRecordOperate();
		}
		return msgRecordOperate;
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
	private static ArrayList<String> groupIDs;
	
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
	public static ArrayList<String> getGroupIDs() {
		if(groupIDs==null)
		{
			groupIDs=new ArrayList<String>();
		}
		return groupIDs;
	}
	public static void setGroupIDs(ArrayList<String> groupIDs) {
		CommonVariables.groupIDs = groupIDs;
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
	
	private static ArrayList<MsgRecord> msgRecordBuffer;

	public static ArrayList<MsgRecord> getMsgRecordBuffer() {
		if(msgRecordBuffer==null)
		{
			msgRecordBuffer = new ArrayList<MsgRecord>();
		}
		return msgRecordBuffer;
	}
	
}
