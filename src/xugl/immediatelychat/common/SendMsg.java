package xugl.immediatelychat.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import xu.immediatelychat.contactdata.ContactDataOperate;
import xu.immediatelychat.contactdata.IContactDataOperate;
import xugl.immediatelychat.activitys.LoginActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class SendMsg implements ISendMsg {
	public void doSend(final String msg) {
		new Thread(new Runnable() {
			Socket socket;

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Log.e("Test", "Begin Send Message");
					socket = new Socket();
					socket.connect(
							new InetSocketAddress(CommonVariables.getMCSIP(),
									CommonVariables.getMCSPort()), 5000);

					OutputStream ou = socket.getOutputStream();

					ou.write(SetMSGBox(msg).getBytes("UTF-8"));

					ou.flush();
					ou.close();
					socket.close();
					Log.e("Test", "finished Send Message");
				} catch (IOException e) {
					e.printStackTrace();
					Log.e("Test", "Send Message Failure:" + e.getMessage());
				}

			}
		}).start();

	}

	private String SetMSGBox(String msg) {
		JSONObject msgObject = new JSONObject();
		try {
			msgObject.put(CommonFlag.getF_ObjectID(),
					CommonVariables.getObjectID());
			msgObject.put(CommonFlag.getF_Account(),
					CommonVariables.getAccount());
			msgObject.put(CommonFlag.getF_Content(), msg);
			msgObject.put(CommonFlag.getF_RecivedGroupID(),
					CommonVariables.getGroupID());
			msgObject.put(CommonFlag.getF_MsgType(), 0);
			msgObject.put(CommonFlag.getF_SendType(), 1);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CommonFlag.getF_MCSVerifyUAMSG() + msgObject.toString();
	}

	@Override
	public void postAccount(final String account, final String password,
			final Context packageContext) {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (ConnectPS(account, password, packageContext)) {
					// connect MMS
					if (ConnectMMS(packageContext)) {
						// connect MCS
						ConnectMCS(packageContext);
					}
				}
			}
		}).start();

	}

	private Boolean ConnectPS(String account, String password,
			Context packageContext) {
		Intent intent;
		HttpGet httpRequest = new HttpGet("http://" + CommonVariables.getPSIP()
				+ ":" + CommonVariables.getPSPort()
				+ "/ContactPerson/LoginForAPI?" + CommonFlag.getF_Account()
				+ "=" + account + "&" + CommonFlag.getF_Password() + "="
				+ password);
		try {
			/* �������󲢵ȴ���Ӧ */
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);
			/* ��״̬��Ϊ200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* �� */
				String strResult = EntityUtils.toString(httpResponse
						.getEntity());
				JSONObject psresultjsonObject = new JSONObject(strResult);
				
				int status=psresultjsonObject.getInt("Status");
				Log.e("Test", "PS return:"
						+ strResult);
				if(status==1)
				{
					intent = new Intent(); // Itent��������Ҫ���͵�����
					intent.putExtra("MSG", "Account or Password incorrect");
					intent.setAction("LoginActivity"); // ����������㲥��action��ֻ�к����actionһ���Ľ����߲��ܽ����߲��ܽ��չ㲥
					packageContext.sendBroadcast(intent); // ���͹㲥
					return false;
				}
				
				if(status==2)
				{
					intent = new Intent(); // Itent��������Ҫ���͵�����
					intent.putExtra("MSG", "MMS server have not start");
					intent.setAction("LoginActivity"); // ����������㲥��action��ֻ�к����actionһ���Ľ����߲��ܽ����߲��ܽ��չ㲥
					packageContext.sendBroadcast(intent); // ���͹㲥
					return false;
				}
				
				CommonVariables.setMMSIP(psresultjsonObject.getString("IP"));
				CommonVariables.setMMSPort(psresultjsonObject.getInt("Port"));
				CommonVariables.setObjectID(psresultjsonObject.getString("ObjectID"));
				CommonVariables.setAccount(account);
				return true;

			} else {
				Log.e("Test", "Post failure:"
						+ httpResponse.getStatusLine().getStatusCode());
				intent = new Intent(); // Itent��������Ҫ���͵�����
				intent.putExtra("MSG", "Can not connect PS");
				intent.setAction("LoginActivity"); // ����������㲥��action��ֻ�к����actionһ���Ľ����߲��ܽ����߲��ܽ��չ㲥
				packageContext.sendBroadcast(intent); // ���͹㲥

			}
		} catch (Exception e) {
			e.printStackTrace();
			intent = new Intent(); // Itent��������Ҫ���͵�����
			intent.putExtra("MSG", "Can not connect PS");
			intent.setAction("LoginActivity"); // ����������㲥��action��ֻ�к����actionһ���Ľ����߲��ܽ����߲��ܽ��չ㲥
			packageContext.sendBroadcast(intent); // ���͹㲥
		}
		return false;
	}

	private boolean ConnectMMS(Context packageContext) {
		try {
			IContactDataOperate contactDataOperate=null;
			char[] charbuffer = new char[1024];
//			String tempStr;
			int charcount;
			Socket sockettoServer = new Socket();
			sockettoServer.connect(
					new InetSocketAddress(CommonVariables.getMMSIP(),
							CommonVariables.getMMSPort()), 5000);

			OutputStream ou = sockettoServer.getOutputStream();
			InputStream in = sockettoServer.getInputStream();
			
			contactDataOperate=new ContactDataOperate();
			contactDataOperate.InitContactPersonInfo(CommonVariables.getObjectID(),packageContext);
			Log.e("Test", "init UA info:" + CommonVariables.getLatestTime() + "  " + CommonVariables.getUpdateTime());
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(CommonFlag.getF_ObjectID(), CommonVariables.getObjectID());
			jsonObject.put(CommonFlag.getF_LatestTime(),CommonVariables.getLatestTime());
			jsonObject.put(CommonFlag.getF_UpdateTime(),CommonVariables.getUpdateTime());
			String msg = CommonFlag.getF_MMSVerifyUA() + jsonObject.toString();
			ou.write(msg.getBytes("UTF-8"));
			ou.flush();

			BufferedReader bff = new BufferedReader(new InputStreamReader(in));

			charcount = bff.read(charbuffer);
			// ��ȡ�ͻ��˵���Ϣ
			msg=String.valueOf(charbuffer, 0, charcount);
			jsonObject=null;
			jsonObject = new JSONObject(msg);
			CommonVariables.setMCSIP(jsonObject.getString("MCS_IP"));
			CommonVariables.setMCSPort(jsonObject.getInt("MCS_Port"));
			
			String mmsUpdateTime= jsonObject.getString("UpdateTime");
			
			if(mmsUpdateTime.compareTo(CommonVariables.getUpdateTime())>0)
			{
				jsonObject = new JSONObject();
				jsonObject.put(CommonFlag.getF_ObjectID(), CommonVariables.getObjectID());
				jsonObject.put(CommonFlag.getF_UpdateTime(),CommonVariables.getUpdateTime());
				msg=CommonFlag.getF_MMSVerifyUAGetUAInfo() + jsonObject.toString();
				ou.write(msg.getBytes("UTF-8"));
				ou.flush();
				charcount = bff.read(charbuffer);
				while(charcount>0)
				{
					msg=String.valueOf(charbuffer, 0, charcount);
					Log.e("Test", "MMS UA info:" + msg);
					jsonObject = new JSONObject(msg);
					ou.write((CommonFlag.getF_MMSVerifyFBUAGetUAInfo()+
							contactDataOperate.SaveContactData(CommonVariables.getObjectID(), jsonObject, packageContext)).getBytes("UTF-8"));
					ou.flush();
					charcount = bff.read(charbuffer);
				}
			}
			jsonObject=null;
			ou.close();
			in.close();
			sockettoServer.close();
			return true;
		} catch (Exception ex) {
			Log.e("Test", "Can not connect MMS:" + ex.getMessage());
			Intent intent = new Intent(); // Itent��������Ҫ���͵�����
			intent.putExtra("MSG", "Can not connect MMS");
			intent.setAction("LoginActivity"); // ����������㲥��action��ֻ�к����actionһ���Ľ����߲��ܽ����߲��ܽ��չ㲥
			packageContext.sendBroadcast(intent); // ���͹㲥
		}
		return false;
	}

	private void ConnectMCS(Context packageContext) {
		try {
			char[] charbuffer = new char[1024];
			int charcount;
			Socket serivce = new Socket();
			serivce.connect(new InetSocketAddress(CommonVariables.getMCSIP(),
					CommonVariables.getMCSPort()), 5000);

			OutputStream ou = serivce.getOutputStream();
			InputStream in = serivce.getInputStream();
			BufferedReader bff = new BufferedReader(new InputStreamReader(in,
					"UTF-8"));
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(CommonFlag.getF_ObjectID(),CommonVariables.getObjectID());
			jsonObject.put(CommonFlag.getF_UpdateTime(),CommonVariables.getUpdateTime());
			String msg = CommonFlag.getF_MCSVerifyUA() + jsonObject.toString();
			
			String mcsRereturn=null;
			int retrytimes=3;
			while(retrytimes>0)
			{
				ou.write(msg.getBytes("UTF-8"));
				ou.flush();
				charcount = bff.read(charbuffer);
				mcsRereturn= String.valueOf(charbuffer, 0, charcount);
				Log.e("Test", "MCS Return:" + mcsRereturn);
				if(mcsRereturn.equalsIgnoreCase("ok"))
				{
					Intent intent = new Intent(); // Itent��������Ҫ���͵�����
					intent.putExtra("MSG", "Success");
					intent.setAction("LoginActivity"); // ����������㲥��action��ֻ�к����actionһ���Ľ����߲��ܽ����߲��ܽ��չ㲥
					packageContext.sendBroadcast(intent); // ���͹㲥
					break;
				}
				else if(mcsRereturn.equalsIgnoreCase("wait"))
				{
					retrytimes--;
					Thread.sleep(500);
					continue;
				}
				else
				{
					Intent intent = new Intent(); // Itent��������Ҫ���͵�����
					intent.putExtra("MSG", "Can not connect MCS");
					intent.setAction("LoginActivity"); // ����������㲥��action��ֻ�к����actionһ���Ľ����߲��ܽ����߲��ܽ��չ㲥
					packageContext.sendBroadcast(intent); // ���͹㲥
					break;
				}
			}
			serivce.close();
		} catch (Exception ex) {
			Log.e("Test", "Can not connect MCS:" + ex.getMessage());
			Intent intent = new Intent(); // Itent��������Ҫ���͵�����
			intent.putExtra("MSG", "Can not connect MCS");
			intent.setAction("LoginActivity"); // ����������㲥��action��ֻ�к����actionһ���Ľ����߲��ܽ����߲��ܽ��չ㲥
			packageContext.sendBroadcast(intent); // ���͹㲥
		}
	}

//	private void UnServerBox(String serverMsg) {
//		try {
//			Log.e("Test", "MMS Return:" + serverMsg);
//			JSONObject jsonObject = new JSONObject(serverMsg);
//			CommonVariables.setMCSIP(jsonObject.getString("MCS_IP"));
//			CommonVariables.setMCSPort(jsonObject.getInt("MCS_Port"));
//			DateFormat df = new SimpleDateFormat(CommonVariables.getDateFormat());
//			CommonVariables.setLatestTime(df.parse(jsonObject
//					.getString("LatestTime")));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

}
