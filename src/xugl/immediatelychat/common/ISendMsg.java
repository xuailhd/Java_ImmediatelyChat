package xugl.immediatelychat.common;

import org.json.JSONObject;

import xugl.immediatelychat.activitys.LoginActivity;
import android.content.Context;
import android.os.Handler;

public interface ISendMsg {
	void doSend(String Msgstr);
	
	void postAccount(String account,String password, Context packageContext);
	
	void sendSearchRequest(String key,int type,Context packageContext);
	
	void sendAddPersonRequest(String objectID);
}
