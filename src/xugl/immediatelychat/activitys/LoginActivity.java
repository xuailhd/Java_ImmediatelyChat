package xugl.immediatelychat.activitys;




import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import xugl.immediatelychat.R;
import xugl.immediatelychat.common.CommonVariables;
import xugl.immediatelychat.common.ISendMsg;
import xugl.immediatelychat.common.SendMsg;
import xugl.immediatelychat.services.ReciveMsgService;
import android.content.BroadcastReceiver;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.SharedPreferences;

public class LoginActivity extends Activity {
	private Button login;
	private Button cancel;
	private EditText serverIP;
	private EditText serverPort;
	private EditText account;
	private EditText password;
	private TextView errorMsg;
	private Handler mHandler; 
	private ReceiveBroadCast receiveBroadCast;
	
	private class ReceiveBroadCast extends BroadcastReceiver
	{
        @Override
        public void onReceive(Context context, Intent intent)
        {
        	//得到广播中得到的数据，并显示出来
            final String message = intent.getStringExtra("MSG");
            if(message.equals("Success"))
            {
            	
//            	startService(new Intent().setClass(LoginActivity.this, ReciveMsgService.class));
//            	
//            	Intent intent2 = new Intent();
//            	intent2.setClass(LoginActivity.this, MainActivity.class);
//	            startActivity(intent2);
//	            finish();
            }
            else
            {
            	mHandler.post(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						errorMsg.setText(message);
					}
            		
            	});
            }
        }

    }
	
	
//	private String GetHostIP()
//	{
//		String ip="1";
//		//获取wifi服务  
//        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);  
//        //判断wifi是否开启  
//        if (wifiManager.isWifiEnabled()) {  
//        	WifiInfo wifiInfo = wifiManager.getConnectionInfo();       
//            int ipAddress = wifiInfo.getIpAddress();   
//            ip = intToIp(ipAddress);   
//        }  
//        else
//        {
//        	try
//        	{
//	        	for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)  
//	            {  
//	               NetworkInterface intf = en.nextElement();  
//	               for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)  
//	               {  
//	                   InetAddress inetAddress = enumIpAddr.nextElement();  
//	                   if (!inetAddress.isLoopbackAddress())  
//	                   {  
//	                       return inetAddress.getHostAddress().toString();  
//	                   }  
//	               }  
//	           }  
//        	}
//        	catch(SocketException ex)
//        	{
//        		
//        	}
//        }
//         
//        return ip;
//	}
//	
//	private String intToIp(int i) {       
//        
//        return (i & 0xFF ) + "." +       
//      ((i >> 8 ) & 0xFF) + "." +       
//      ((i >> 16 ) & 0xFF) + "." +       
//      ( i >> 24 & 0xFF) ;  
//   } 
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiveBroadCast);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.login_activity);
		
		mHandler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				Bundle data = msg.getData();  
				JSONObject resultjsonObject=null;
				try {
					resultjsonObject=new JSONObject(data.getString("data"));
					CommonVariables.setMMSIP(resultjsonObject.getString("IP"));
					CommonVariables.setMMSPort(Integer.parseInt(resultjsonObject.getString("Port")));
					CommonVariables.setObjectID(resultjsonObject.getString("ObjectID"));
					CommonVariables.setAccount(account.getText().toString());
					CommonVariables.setGroupID("Group1");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
				
				if(resultjsonObject!=null)
				{
					if(CommonVariables.getObjectID()==null)
					{
						errorMsg.setText(R.string.accountsame);
						return;
					}
					startService(new Intent(LoginActivity.this,ReciveMsgService.class));

				}
				else
				{
					errorMsg.setText(R.string.servererror);
				}

			}
			
		};
		
		
		// 注册广播接收
        receiveBroadCast = new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("LoginActivity");    //只有持有相同的action的接受者才能接收此广播
        registerReceiver(receiveBroadCast, filter);
		
		login=(Button)findViewById(R.id.login);
		cancel=(Button)findViewById(R.id.cancel);
		serverIP=(EditText)findViewById(R.id.serverip);
		serverPort=(EditText)findViewById(R.id.port);
		account=(EditText)findViewById(R.id.account);
		password=(EditText)findViewById(R.id.password);
		errorMsg=(TextView)findViewById(R.id.errorMsg);
		
		//读取本地配置文件
		SharedPreferences settings = getSharedPreferences("PSConfig", Activity.MODE_PRIVATE);  
		serverIP.setText(settings.getString("PSIP", ""));
		serverPort.setText(settings.getString("PSPort", ""));
		
		
		login.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				ISendMsg sendMsg=new SendMsg();
				
				//存储本地配置文件
				SharedPreferences settings = getSharedPreferences("PSConfig", Activity.MODE_PRIVATE);  
				SharedPreferences.Editor editor = settings.edit();  
				editor.putString("PSIP", serverIP.getText().toString()); 
				editor.putString("PSPort", serverPort.getText().toString()); 
				editor.commit();  
				
				CommonVariables.setPSIP(serverIP.getText().toString());
				CommonVariables.setPSPort(Integer.parseInt(serverPort.getText().toString()) );
				sendMsg.postAccount(account.getText().toString(),password.getText().toString(),LoginActivity.this);
				
				errorMsg.setText(R.string.conneting);
				
			}
			
		});
		
	}
	

}
