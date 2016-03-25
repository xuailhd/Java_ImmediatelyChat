package xugl.immediatelychat.activitys;

import org.json.JSONArray;
import org.json.JSONException;

import xugl.immediatelychat.R;
import xugl.immediatelychat.common.CommonVariables;
import xugl.immediatelychat.models.ContactPerson;
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

public class AddPersonActivity extends  BaseActivity  {
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
        	ContactPerson contactPerson=null;
        	ContactPerson[] contactPersons=null;
            //得到广播中得到的数据，并显示出来
            final String message = intent.getStringExtra("SearchResult");
            Log.e("Test", "get search message:" + message);

            try {
            	if(!message.equals("No Result") && !message.equals("No Connect"))
                {
            		jsonArray=new JSONArray(message);
            		
            		if(jsonArray!=null && jsonArray.length()>0)
            		{
            			contactPersons=new ContactPerson[jsonArray.length()];
            			for(int i=0;i<jsonArray.length();i++)
                		{
            				contactPerson=new ContactPerson();
            				contactPerson.setContactName(jsonArray.getJSONObject(i).getString("ContactName"));
            				contactPerson.setObjectID(jsonArray.getJSONObject(i).getString("ObjectID"));
            				contactPersons[i]=contactPerson;
                		}
            		}
                }
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            final ContactPerson[] contactPersonsnew=contactPersons;

        	mHandler.post(new Runnable() {  
				public void run() {  
		            if(contactPersonsnew!=null && contactPersonsnew.length>0)
		            {
    					for(int i=0;i<contactPersonsnew.length;i++)
    	            	{
    						addPersonIntoView(contactPersonsnew[i]);
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
					CommonVariables.getSendMsg().sendSearchRequest(key, 1, AddPersonActivity.this);
					search.setEnabled(false);
				}
			}
		);
		
		// 注册广播接收
        receiveBroadCast = new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("SearchPerson");    
        registerReceiver(receiveBroadCast, filter);
	}
	
	private void addPersonIntoView(final ContactPerson contactPerson)
	{
		ImageView pic=new ImageView(AddPersonActivity.this);
		pic.setImageResource(R.drawable.ic_launcher);
		
		TextView name=new TextView(AddPersonActivity.this);
		name.setText(contactPerson.getContactName());
		
		LinearLayout linearLayout=new LinearLayout(AddPersonActivity.this);
		linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout.addView(pic);
		linearLayout.addView(name);
		
		linearLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.putExtra("ObjectID", contactPerson.getObjectID());
				intent.putExtra(name, value)
				intent.setClass(packageContext, cls)
			}
		});

		searchlayout.addView(linearLayout);
	}

}
