package xugl.immediatelychat.common;

import java.util.Map;

import android.content.Context;

public interface ISaveLocalData {
	void SaveData(String fieldName,String fieldValue,Context packageContext);
	
	void SaveData(String objectID, String fieldName,String fieldValue,Context packageContext);
	
	String GetData(String objectID,String fieldName,Context packageContext);
	
	String GetData(String fieldName,Context packageContext);
	
	Map<String, ?> GetAllData(String fieldName,Context packageContext);
}
