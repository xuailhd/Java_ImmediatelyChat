package xugl.immediatelychar.chat;

import android.content.Context;
import xugl.immediatelychat.models.ChatModel;

public interface IChatOperate {
	ChatModel[] GetChats();
	
	void AddChat(String destinationObjectID,String destinationName,Context packageContext);
}
