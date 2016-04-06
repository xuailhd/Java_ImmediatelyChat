package xugl.immediatelychar.chat;

import java.util.ArrayList;

import xugl.immediatelychat.models.MsgRecord;

public interface IMsgRecordOperate {
	ArrayList<MsgRecord> GetMsgRecord(int chatType,String chatObjectID);
}
