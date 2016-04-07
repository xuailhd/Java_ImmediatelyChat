package xugl.immediatelychat.activitys;

import java.util.ArrayList;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import xugl.immediatelychat.R;
import xugl.immediatelychat.common.CommonVariables;
import xugl.immediatelychat.models.ChatModel;
import xugl.immediatelychat.models.MsgRecord;

public class ChatActivity extends Activity {
	
	private Button button;
	private EditText editText;
	private LinearLayout chatLayout;
	private ScrollView scrollView;
	private Handler mHandler;
	private ReceiveBroadCast receiveBroadCast;

	protected void setView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_chat);
	}
	
	
	private class ReceiveBroadCast extends BroadcastReceiver
	{
        @Override
        public void onReceive(Context context, Intent intent)
        {
            //得到广播中得到的数据，并显示出来
            final MsgRecord msgRecord = intent.getParcelableExtra("MsgRecord");
            Log.e("Test", "get message:" + msgRecord.getMsgContent());

            if(msgRecord.getMsgSenderObjectID().equals(CommonVariables.getObjectID()))
            {
            	return;
            }
           
            mHandler.post(new Runnable() {  
				public void run() {  
					AddChatIntoView(msgRecord);
					scrollView.fullScroll(ScrollView.FOCUS_DOWN);  
					//mHandler.post(this);
				}  
			}); 
        }
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setView();
		init();
	}


	protected void init() {
		// TODO Auto-generated method stub
		chatLayout= (LinearLayout)findViewById(R.id.mainchatLayout);
		button=(Button)findViewById(R.id.mainbuttonSend);
		editText=(EditText)findViewById(R.id.mainchatinput);
		scrollView=(ScrollView)findViewById(R.id.mainchatScrollView);
		
		Intent intent=getIntent();
		
		ChatModel chatModel= intent.getParcelableExtra("ChatModel");
		
		ArrayList<MsgRecord> msgRecords= CommonVariables.getMsgRecordOperate().GetMsgRecord(chatModel.getChatType(), 
				chatModel.getChatType()==0? chatModel.getDestinationObjectID():chatModel.getGroupID());
		
		for(int i=0;i<msgRecords.size();i++)
		{
			AddChatIntoView(msgRecords.get(i));
		}
		
		
        if(chatModel.getChatType()==1)
        {
        	receiveBroadCast = new ReceiveBroadCast();
            IntentFilter filter = new IntentFilter();
        	filter.addAction(chatModel.getDestinationObjectID());
        	registerReceiver(receiveBroadCast, filter);
        }
        else if (chatModel.getChatType()==2)
        {
        	receiveBroadCast = new ReceiveBroadCast();
            IntentFilter filter = new IntentFilter();
        	filter.addAction(chatModel.getGroupID() + "2");
        	registerReceiver(receiveBroadCast, filter);
        }
        
		
		button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String text=editText.getText().toString();
				if(!text.isEmpty())
				{
					MsgRecord msgRecord=CommonVariables.getMsgRecordOperate().SaveMsgRecord(text);
					AddChatIntoView(msgRecord);
					scrollView.fullScroll(ScrollView.FOCUS_DOWN);  
					CommonVariables.getSendMsg().doSend(text);
				}
			}});
	} 
	
	
	private void AddChatIntoView(MsgRecord msgRecord)
	{
		ImageView pic=new ImageView(ChatActivity.this);
		pic.setImageResource(R.drawable.ic_launcher);
		
		TextView name=new TextView(ChatActivity.this);
		name.setText(msgRecord.getMsgSenderName());
		
		LinearLayout linearLayout=new LinearLayout(ChatActivity.this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.addView(pic);
		linearLayout.addView(name);
		
		
		TextView textView=new TextView(ChatActivity.this);
		textView.setText(msgRecord.getMsgContent());

		LinearLayout linearLayout2=new LinearLayout(ChatActivity.this);
		linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout2.addView(textView);
		linearLayout2.addView(linearLayout);
		
		
		//此处相当于布局文件中的Android:layout_gravity属性  
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);  
		lp.gravity = Gravity.END;  
		linearLayout2.setLayoutParams(lp);

		chatLayout.addView(linearLayout2);
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		if(receiveBroadCast!=null)
		{
			unregisterReceiver(receiveBroadCast);
		}
	}
	
	
	
}
