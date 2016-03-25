package xugl.immediatelychat.activitys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import xugl.immediatelychat.R;
import xugl.immediatelychat.common.CommonVariables;
import xugl.immediatelychat.models.ContactGroup;
import xugl.immediatelychat.models.ContactPerson;

public class AddGroupActivity extends BaseActivity {
	private Button search;
	private EditText searchinput;
	private LinearLayout searchlayout;
	private Handler mHandler=new Handler();
	private ReceiveBroadCast receiveBroadCast;
	
	
	private class ReceiveBroadCast extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
        	JSONArray jsonArray =null;
        	ContactGroup contactGroup=null;
        	ContactGroup[] contactGroups=null;
            //得到广播中得到的数据，并显示出来
            final String message = intent.getStringExtra("SearchResult");
            Log.e("Test", "get search message:" + message);

            try {
            	if(!message.equals("No Result") && !message.equals("No Connect"))
                {
            		jsonArray=new JSONArray(message);
            		
            		if(jsonArray!=null && jsonArray.length()>0)
            		{
            			contactGroups=new ContactGroup[jsonArray.length()];
            			for(int i=0;i<jsonArray.length();i++)
                		{
            				contactGroup=new ContactGroup();
            				contactGroup.setGroupName(jsonArray.getJSONObject(i).getString("GroupName"));
            				contactGroup.setGroupObjectID(jsonArray.getJSONObject(i).getString("GroupObjectID"));
            				contactGroups[i]=contactGroup;
                		}
            		}
                }
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            final ContactGroup[] contactGroupsnew=contactGroups;

        	mHandler.post(new Runnable() {  
				public void run() {  
		            if(contactGroupsnew!=null && contactGroupsnew.length>0)
		            {
    					for(int i=0;i<contactGroupsnew.length;i++)
    	            	{
    	            		addGroupIntoView(contactGroupsnew[i]);
    	            	}
		            }
		            search.setEnabled(true);
				}	
			}); 
		}
	}
	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_findcontact);
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		search=(Button)findViewById(R.id.search);
		searchinput=(EditText)findViewById(R.id.searchinput);
		searchlayout=(LinearLayout)findViewById(R.id.searchlayout);
		
		search.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String key=searchinput.getText().toString();
					CommonVariables.getSendMsg().sendSearchRequest(key, 2, AddGroupActivity.this);
					search.setEnabled(false);
				}
			}
		);
		
		// 注册广播接收
        receiveBroadCast = new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("SearchGroup");    
        registerReceiver(receiveBroadCast, filter);
	}
	
	private void addGroupIntoView(ContactGroup contactGroup)
	{
		ImageView pic=new ImageView(AddGroupActivity.this);
		pic.setImageResource(R.drawable.ic_launcher);
		
		TextView name=new TextView(AddGroupActivity.this);
		name.setText(contactGroup.getGroupName());
		
		LinearLayout linearLayout=new LinearLayout(AddGroupActivity.this);
		linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout.addView(pic);
		linearLayout.addView(name);
		
		searchlayout.addView(linearLayout);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(receiveBroadCast!=null)
		{
			unregisterReceiver(receiveBroadCast);
		}
		super.onDestroy();
	}
	
}
