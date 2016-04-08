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
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

import org.json.JSONArray;
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

			Log.e("Test", "Begin Get Mssage from MCS");
			char[] charbuffer = new char[1024];
			int charcount = 0;
			Socket sockettoServer = null;
			OutputStream ou = null;
			InputStream in = null;
			JSONObject jsonObject = null;
			Intent intent = null;
			String message = null;
			String reciverid = null;
			int i = 0;
			
			while (isGoonRunning) {
				try {
					i++;
					Log.e(TAG, "Begin the " + i + "st get");
					sockettoServer = new Socket();
					sockettoServer.connect(
							new InetSocketAddress(CommonVariables.getMCSIP(),
									CommonVariables.getMCSPort()), 5000);

					ou = sockettoServer.getOutputStream();
					in = sockettoServer.getInputStream();

					jsonObject = new JSONObject();
					jsonObject.put(CommonFlag.getF_ObjectID(),CommonVariables.getObjectID());
					jsonObject.put(CommonFlag.getF_LatestTime(),CommonVariables.getLatestTime());
					jsonObject.put(CommonFlag.getF_GroupIDs(), CommonVariables.getGroupIDs());

					String msg = CommonFlag.getF_MCSVerifyUAGetMSG()
							+ jsonObject.toString();

					ou.write(msg.getBytes("UTF-8"));
					ou.flush();

					BufferedReader bff = new BufferedReader(
							new InputStreamReader(in, "UTF-8"));
					charcount = bff.read(charbuffer);
//					Log.e("Test", "Get Message charcount:" + charcount);
					while (charcount > 0) {
						intent = new Intent(); 

						message = String.valueOf(charbuffer, 0, charcount);

						jsonObject = new JSONObject(message);
						reciverid = jsonObject.getString(CommonFlag.getF_GroupID());

						if (jsonObject.getString("LatestTime").compareTo(CommonVariables.getLatestTime())> 0) 
						{
							CommonVariables.setLatestTime(jsonObject.getString("LatestTime"));
						}

						intent.putExtra("MSG", message);
						intent.setAction(reciverid); // 设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
						sendBroadcast(intent); // 发送广播

						charcount = bff.read(charbuffer);
					}

					ou.close();
					in.close();
					sockettoServer.close();
					sockettoServer=null;
					Thread.sleep(1000);
				} catch (Exception ex) {
					Log.e("Test", "Get Message failure:" + ex.getMessage());

				}
			}

			stopSelf();
		}
	}

	public class SendMsgThread extends Thread
	{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
		}
		
	}
	
	
}
