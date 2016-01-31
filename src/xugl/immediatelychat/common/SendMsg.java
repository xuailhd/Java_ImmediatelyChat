package xugl.immediatelychat.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
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
			msgObject.put(CommonFlag.getF_RecivedGroupID(), CommonVariables.getGroupID());
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
				/* 建立HTTP Get对象 */
				HttpGet httpRequest = new HttpGet("http://"
						+ CommonVariables.getPSIP() + ":"
						+ CommonVariables.getPSPort()
						+ "/ContactPerson/LoginForAPI?"
						+ CommonFlag.getF_Account() + "=" + account + "&"
						+ CommonFlag.getF_Password() + "=" + password);
				try {
					/* 发送请求并等待响应 */
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(httpRequest);
					/* 若状态码为200 ok */
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						/* 读 */
						String strResult = EntityUtils.toString(httpResponse
								.getEntity());
						JSONObject psresultjsonObject = new JSONObject(
								strResult);
						CommonVariables.setMMSIP(psresultjsonObject
								.getString("IP"));
						CommonVariables.setMMSPort(psresultjsonObject
								.getInt("Port"));
						CommonVariables.setObjectID(psresultjsonObject
								.getString("ObjectID"));
						CommonVariables.setAccount(account);
						CommonVariables.setGroupID("Group1");

						// connect MMS
						try {
							char[] charbuffer = new char[1024];
							int charcount;
							Socket sockettoServer = new Socket();
							sockettoServer.connect(
									new InetSocketAddress(CommonVariables
											.getMMSIP(), CommonVariables
											.getMMSPort()), 5000);

							OutputStream ou = sockettoServer.getOutputStream();
							InputStream in = sockettoServer.getInputStream();

							JSONObject jsonObject = new JSONObject();
							jsonObject.put(CommonFlag.getF_ObjectID(),
									CommonVariables.getObjectID());
							String msg = CommonFlag.getF_MMSVerifyUA() + jsonObject.toString();
							ou.write(msg.getBytes("UTF-8"));
							ou.flush();

							BufferedReader bff = new BufferedReader(
									new InputStreamReader(in));

							charcount=bff.read(charbuffer);
							// 获取客户端的信息
							UnServerBox(String.valueOf(charbuffer, 0, charcount));
							ou.close();
							in.close();
							sockettoServer.close();
							// connect MCS
							try {
								Socket serivce = new Socket();
								serivce.connect(new InetSocketAddress(
										CommonVariables.getMCSIP(),
										CommonVariables.getMCSPort()), 5000);

								ou = serivce.getOutputStream();
								jsonObject = new JSONObject();
								jsonObject.put(CommonFlag.getF_ObjectID(),
										CommonVariables.getObjectID());
								msg = CommonFlag.getF_MCSVerifyUA() + jsonObject.toString();
								Log.e("Test", "Send MCS message" + msg);
								ou.write(msg.getBytes("UTF-8"));
								ou.flush();

								in = serivce.getInputStream();
								bff = new BufferedReader(new InputStreamReader(
										in, "UTF-8"));
								
								charcount = bff.read(charbuffer);
								Log.e("Test", "get message count:" + charcount);
								String.valueOf(charbuffer, 0, charcount);

								Intent intent = new Intent(); // Itent就是我们要发送的内容
								intent.putExtra("MSG", "Success");
								intent.setAction("LoginActivity"); // 设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
								packageContext.sendBroadcast(intent); // 发送广播
								serivce.close();
							} catch (Exception ex) {
								Log.e("Test",
										"Can not connect MCS:"
												+ ex.getMessage());
								Intent intent = new Intent(); // Itent就是我们要发送的内容
								intent.putExtra("MSG", "Can not connect MCS");
								intent.setAction("LoginActivity"); // 设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
								packageContext.sendBroadcast(intent); // 发送广播
							}

						} catch (Exception ex) {
							Log.e("Test",
									"Can not connect MMS:" + ex.getMessage());
							Intent intent = new Intent(); // Itent就是我们要发送的内容
							intent.putExtra("MSG", "Can not connect MMS");
							intent.setAction("LoginActivity"); // 设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
							packageContext.sendBroadcast(intent); // 发送广播
						}

					} else {
						Log.e("Test", "Post failure:"
								+ httpResponse.getStatusLine().getStatusCode());
						Intent intent = new Intent(); // Itent就是我们要发送的内容
						intent.putExtra("MSG", "Can not connect PS");
						intent.setAction("LoginActivity"); // 设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
						packageContext.sendBroadcast(intent); // 发送广播
					}
				}  catch (Exception e) {
					e.printStackTrace();
					Intent intent = new Intent(); // Itent就是我们要发送的内容
					intent.putExtra("MSG", "Can not connect PS");
					intent.setAction("LoginActivity"); // 设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
					packageContext.sendBroadcast(intent); // 发送广播
				}

			}
		}).start();

	}

	private void UnServerBox(String serverMsg) {
		try {
			JSONObject jsonObject = new JSONObject(serverMsg);
			CommonVariables.setMCSIP(jsonObject.getString("MCS_IP"));
			CommonVariables.setMCSPort(jsonObject.getInt("MCS_Port"));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
