package xugl.immediatelychat.activitys;

import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import xugl.immediatelychat.R;
import xugl.immediatelychat.common.CommonVariables;
import xugl.immediatelychat.models.ChatModel;

public class HomeActivity extends BaseActivity {
	private LinearLayout mainLayout;
	private ScrollView scrollView;
	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_home);
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub

		mainLayout=(LinearLayout)findViewById(R.id.charsLayout);
		scrollView=(ScrollView)findViewById(R.id.chartsScrollView);
		Log.e("Test", "123213");
		
		ChatModel[] chatModels=CommonVariables.getChatOperate().GetChats();
		
		if(chatModels==null || chatModels.length<1)
		{
			return;
		}
		
		for(int i=0;i<chatModels.length;i++)
		{
			addChatIntoView(chatModels[i]);
		}
	}
	
	private void addChatIntoView(ChatModel chatModel)
	{
		ImageView pic=new ImageView(HomeActivity.this);
		pic.setImageResource(R.drawable.ic_launcher);

		TextView name=new TextView(HomeActivity.this);
		if(chatModel.getChatType()==0)
		{
			name.setText(chatModel.getContactPersonName());
		}
		else if(chatModel.getChatType()==1)
		{
			name.setText(chatModel.getGroupName());
		}
		
		TextView content=new TextView(HomeActivity.this);
		content.setText(chatModel.getLatestMsg());
		
		LinearLayout line=new LinearLayout(HomeActivity.this);
		line.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,4));
		line.setOrientation(LinearLayout.VERTICAL);

		TextView contentTime=new TextView(HomeActivity.this);
		contentTime.setText(chatModel.getLatestTime());
		
		LinearLayout linearLayout=new LinearLayout(HomeActivity.this);
		linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		linearLayout.addView(pic);
		linearLayout.addView(line);
		linearLayout.addView(contentTime);
		

		mainLayout.addView(linearLayout);
	}

}
