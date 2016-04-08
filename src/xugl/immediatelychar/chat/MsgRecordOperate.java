package xugl.immediatelychar.chat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import xugl.immediatelychat.common.CommonFlag;
import xugl.immediatelychat.common.CommonVariables;
import xugl.immediatelychat.models.MsgRecord;

public class MsgRecordOperate implements IMsgRecordOperate {

	@Override
	public ArrayList<MsgRecord> GetMsgRecord(int chatType,String chatID,Context packageContext) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = null;
		ArrayList<MsgRecord> msgRecords= null;
		MsgRecord msgRecord = null;
		try {
			Map<String, ?> map=CommonVariables.getLocalDataManager().GetAllData("MSG" + chatID,packageContext);
			if(map!=null)
			{				
				msgRecords = new ArrayList<MsgRecord>();
				Iterator iter = map.entrySet().iterator();  
			    while (iter.hasNext()) {  
			        Map.Entry entry = (Map.Entry) iter.next();   
			        String val = (String) entry.getValue();
			        jsonObject = new JSONObject(val);
			        msgRecord = new MsgRecord();
			        msgRecord.setMsgContent(jsonObject.getString("MsgContent"));
			        msgRecord.setMsgID(jsonObject.getString("MsgID"));
			        msgRecord.setMsgRecipientGroupID(jsonObject.getString("MsgRecipientGroupID"));
			        msgRecord.setMsgRecipientObjectID(jsonObject.getString("MsgRecipientObjectID"));
			        msgRecord.setMsgSenderName(jsonObject.getString("MsgSenderName"));
			        msgRecord.setMsgSenderObjectID(jsonObject.getString("MsgSenderObjectID"));
			        msgRecord.setMsgType(jsonObject.getInt("MsgType"));
			        msgRecord.setSendTime(jsonObject.getString("SendTime"));
			        msgRecords.add(msgRecord);
			    }
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return msgRecords;
	}


	@Override
	public void SaveMsgRecord(MsgRecord msgRecord,String chatID, Context packageContext) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = null;
		
		try {
			jsonObject =new JSONObject();
			jsonObject.put(CommonFlag.getF_MsgID(), msgRecord.getMsgID());
			jsonObject.put(CommonFlag.getF_MsgSenderObjectID(),msgRecord.getMsgSenderObjectID());
			jsonObject.put(CommonFlag.getF_MsgSenderName(), msgRecord.getMsgSenderName());
			jsonObject.put(CommonFlag.getF_MsgContent(), msgRecord.getMsgContent());
			jsonObject.put(CommonFlag.getF_MsgRecipientObjectID(),msgRecord.getMsgRecipientObjectID());
			jsonObject.put(CommonFlag.getF_MsgRecipientGroupID(),msgRecord.getMsgRecipientGroupID());
			jsonObject.put(CommonFlag.getF_MsgType(), msgRecord.getMsgType());
			jsonObject.put(CommonFlag.getF_SendTime(), msgRecord.getSendTime());
			
			CommonVariables.getLocalDataManager().SaveData("MSG" + chatID,msgRecord.getMsgID(),jsonObject.toString(), packageContext);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public MsgRecord SaveMsgRecord(JSONObject jsonObject,String chatID,Context packageContext) {
		// TODO Auto-generated method stub
		MsgRecord msgRecord=null;
		try {
			msgRecord=new MsgRecord();
			msgRecord.setMsgID(jsonObject.getString(CommonFlag.getF_MsgID()));
			msgRecord.setMsgSenderObjectID(jsonObject.getString(CommonFlag.getF_MsgSenderObjectID()));
			msgRecord.setMsgSenderName(jsonObject.getString(CommonFlag.getF_MsgSenderName()));
			msgRecord.setMsgContent(jsonObject.getString(CommonFlag.getF_MsgContent()));
			msgRecord.setMsgRecipientObjectID(jsonObject.getString(CommonFlag.getF_MsgRecipientObjectID()));
			msgRecord.setMsgRecipientGroupID(jsonObject.getString(CommonFlag.getF_MsgRecipientGroupID()));
			msgRecord.setMsgType(jsonObject.getInt(CommonFlag.getF_MsgType()));
			msgRecord.setSendTime(jsonObject.getString(CommonFlag.getF_SendTime()));
			
			CommonVariables.getLocalDataManager().SaveData("MSG" + chatID,msgRecord.getMsgID(),jsonObject.toString(), packageContext);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msgRecord;
	}
}
