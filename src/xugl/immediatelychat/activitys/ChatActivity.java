package xugl.immediatelychat.activitys;

import android.content.Intent;
import xugl.immediatelychat.R;
import xugl.immediatelychat.common.CommonVariables;
import xugl.immediatelychat.models.ChatModel;

public class ChatActivity extends BaseActivity {
	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_chat);
	}


	@Override
	protected void init() {
		// TODO Auto-generated method stub
		Intent intent=getIntent();
		
		ChatModel chatModel= intent.getParcelableExtra("ChatModel");
		
		CommonVariables.getMsgRecordOperate().GetMsgRecord(chatModel.getChatType(), 
				chatModel.getChatType()==0? chatModel.getDestinationObjectID():chatModel.getGroupID());
		
		
		
	} 
}
