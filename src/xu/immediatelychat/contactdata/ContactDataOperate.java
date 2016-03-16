package xu.immediatelychat.contactdata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import xugl.immediatelychat.common.CommonVariables;
import xugl.immediatelychat.models.ContactGroup;

public class ContactDataOperate {
	public void SaveContactData(String objectID,JSONObject contactData,Context packageContext)
	{
		try {
			String localDataStr=null;
			JSONObject jsonObject=null;
			JSONArray jsonArray=null;
			int i=0;
			int findindex=-1;
			
			if(contactData.getInt("DataType")==1)
			{
				localDataStr=CommonVariables.getLocalDataManager().GetData(objectID, "ContactPersonLists", packageContext);
				jsonArray=new JSONArray(localDataStr);
				
				while(i<jsonArray.length())
				{
					if(jsonArray.getJSONObject(i).getString("DestinationObjectID").equalsIgnoreCase(contactData.getString("DestinationObjectID"))
							&& jsonArray.getJSONObject(i).getString("ObjectID").equalsIgnoreCase( contactData.getString("ObjectID")))
					{
						findindex=i;
						break;
					}
					i++;
				}
				
				if(findindex>=0)
				{
					jsonArray.getJSONObject(findindex).put("IsDelete", contactData.getBoolean("IsDelete"));
				}
				else
				{
					jsonObject=new JSONObject();

					jsonObject.put("ObjectID", contactData.getString("ObjectID"));				
					jsonObject.put("DestinationObjectID", contactData.getString("DestinationObjectID"));
					jsonObject.put("ContactPersonName", contactData.getString("ContactPersonName"));
					jsonObject.put("IsDelete", contactData.getBoolean("IsDelete"));
					jsonArray.put(jsonObject);
				}
			}

			CommonVariables.getLocalDataManager().SaveData(objectID,"ContactGroups", jsonArray.toString(), packageContext);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
