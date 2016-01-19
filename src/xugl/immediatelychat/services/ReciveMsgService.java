package xugl.immediatelychat.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import xugl.immediatelychat.common.CommonFlag;
import xugl.immediatelychat.common.CommonVariables;
import xugl.immediatelychat.common.IUpdateChatContent;
import xugl.immediatelychat.models.ClientStatusModel;
import android.R.integer;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ReciveMsgService extends Service {

	private ReciveMsgThread reciveMsgThread;
	private static final String TAG = "ReciveMsgService";

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return new LocalBinder();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		super.onStartCommand(intent, flags, startId);

		reciveMsgThread = new ReciveMsgThread();
		reciveMsgThread.start();

		return 1;

	}

	// public void GetMSG()
	// {
	// ReciveMsgThread reciveMsgThread=new ReciveMsgThread();
	// reciveMsgThread.start();
	// }

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		reciveMsgThread.setGoonRunning(false);

		Log.e("Test", "ReciveMsgService Destroyed");
	}

	public class LocalBinder extends Binder {
		// 返回本地服务
		public ReciveMsgService getService() {
			return ReciveMsgService.this;
		}
	}

	public class ReciveMsgThread extends Thread {

		private boolean isGoonRunning;

		public void setGoonRunning(boolean isGoonRunning) {
			this.isGoonRunning = isGoonRunning;
		}

		public ReciveMsgThread() {
			isGoonRunning = true;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

			try {
				Log.e("Test", "Begin Get Mssage from MCS");

				while (isGoonRunning) {
					Socket sockettoServer = new Socket();
					sockettoServer.connect(
							new InetSocketAddress(CommonVariables.getMCSIP(),
									CommonVariables.getMCSPort()), 5000);

					OutputStream ou = sockettoServer.getOutputStream();
					InputStream in = sockettoServer.getInputStream();

					JSONObject jsonObject = new JSONObject();
					jsonObject.put("ObjectID", CommonVariables.getObjectID());
					String msg = "VerifyGetMSG" + jsonObject.toString();

					ou.write(msg.getBytes("UTF-8"));
					ou.flush();

					BufferedReader bff = new BufferedReader(
							new InputStreamReader(in));
					
					
					
					char[] buffer=new char[1024];
					bff.read(buffer, 0, 1024);
					
					
					String line = null;
					// 获取客户端的信息
					while ((line = bff.readLine()) != null) {
						if( !line.equals("No"))
						{
							new HandlerMsg(line).start();
						}
					}
					ou.close();
					in.close();
					sockettoServer.close();
					Thread.sleep(2000);
				}
				

			} catch (Exception ex) {
				Log.e("Test", "Get Message failure:" + ex.getMessage());

			}

		}
	}
	
	
	private class HandlerMsg extends Thread
	{
		private String msg;
		public HandlerMsg(String _msg)
		{
			this.msg=_msg;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			try {
				JSONObject msgJSONObject=new JSONObject(msg);
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
	}
}
