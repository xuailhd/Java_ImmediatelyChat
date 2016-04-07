package xugl.immediatelychar.chat;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import xugl.immediatelychat.models.MsgRecord;

public interface IMsgRecordOperate {
	ArrayList<MsgRecord> GetMsgRecord(int chatType,String chatObjectID,Context packageContext);
	
	void SaveMsgRecord(MsgRecord msgRecord,String chatID,Context packageContext);
	
	MsgRecord SaveMsgRecord(JSONObject msgRecord,String chatID,Context packageContext);
}
