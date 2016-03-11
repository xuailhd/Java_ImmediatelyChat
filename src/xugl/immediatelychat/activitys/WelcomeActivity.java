package xugl.immediatelychat.activitys;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import xugl.immediatelychat.R;
import xugl.immediatelychat.common.CommonVariables;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class WelcomeActivity extends Activity {

	private Handler mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			if (isServiceWork(WelcomeActivity.this,"xugl.immediatelychat.services.ReciveMsgService")){
				
				intent.setClass(WelcomeActivity.this, MainActivity.class);
	            startActivity(intent);
			}
			else{
				intent.setClass(WelcomeActivity.this, LoginActivity.class);
	            startActivity(intent);
			}
			
			finish();
		}}; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_welcome);
		
		
		
		
		try {
			//读取本地配置文件
			SharedPreferences settings = getSharedPreferences("PSConfig", Activity.MODE_PRIVATE);  
			SimpleDateFormat sdf = new SimpleDateFormat(CommonVariables.getDateFormat());  
//			if(settings.getString("LatestTime", "").length()<=0)
//			{
				SharedPreferences.Editor editor = settings.edit();  
				editor.putString("LatestTime","1900-01-01 00:00:00.000"); 
				editor.commit();
//			}
			
			CommonVariables.setLatestTime(sdf.parse(settings.getString("LatestTime", new Date().toString())));

//			if(settings.getString("UpdateTime", "").length()<=0)
//			{
//				SharedPreferences.Editor editor = settings.edit();  
				editor.putString("UpdateTime","1900-01-01 00:00:00.000"); 
				editor.commit();
//			}
			
			CommonVariables.setUpdateTime(sdf.parse(settings.getString("UpdateTime", new Date().toString())));
			
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		new Thread(){

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					mHandler.sendEmptyMessage(0);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}}.start();
	}
	
	public boolean isServiceWork(Context mContext, String serviceName) {  
        boolean isWork = false;  
        ActivityManager myAM = (ActivityManager) mContext  
                .getSystemService(Context.ACTIVITY_SERVICE);  
        List<RunningServiceInfo> myList = myAM.getRunningServices(40);  
        if (myList.size() <= 0) {  
            return false;  
        }  
        for (int i = 0; i < myList.size(); i++) {  
            String mName = myList.get(i).service.getClassName().toString();  
            if (mName.equals(serviceName)) {  
                isWork = true;  
                break;  
            }  
        }  
        return isWork;  
    }  
	
}
