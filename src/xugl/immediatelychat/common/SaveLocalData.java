package xugl.immediatelychat.common;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

public class SaveLocalData implements ISaveLocalData {
	private String commonFileName="PSConfig";
	@Override
	public void SaveData(String fieldName, String fieldValue,Context packageContext) {
		// TODO Auto-generated method stub
		SharedPreferences settings = packageContext.getSharedPreferences(commonFileName, Activity.MODE_PRIVATE);  
		SharedPreferences.Editor editor = settings.edit();  
		editor.putString(fieldName,fieldValue); 
		editor.commit();
	}

	@Override
	public void SaveData(String objectID, String fieldName, String fieldValue,Context packageContext) {
		// TODO Auto-generated method stub
		SharedPreferences settings = packageContext.getSharedPreferences(objectID, Activity.MODE_PRIVATE);  
		SharedPreferences.Editor editor = settings.edit();  
		editor.putString(fieldName,fieldValue); 
		editor.commit();
	}

	@Override
	public String GetData(String objectID, String fieldName,Context packageContext) {
		// TODO Auto-generated method stub
		SharedPreferences settings = packageContext.getSharedPreferences(objectID, Activity.MODE_PRIVATE);  
		return settings.getString(fieldName, null);
	}

	@Override
	public String GetData(String fieldName,Context packageContext) {
		// TODO Auto-generated method stub
		SharedPreferences settings = packageContext.getSharedPreferences(commonFileName, Activity.MODE_PRIVATE);  
		
		return settings.getString(fieldName, null);
	}
	
	public 

}
