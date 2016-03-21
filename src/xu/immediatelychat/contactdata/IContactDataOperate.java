package xu.immediatelychat.contactdata;

import org.json.JSONObject;

import xugl.immediatelychat.models.ContactGroup;
import xugl.immediatelychat.models.ContactGroupSub;
import xugl.immediatelychat.models.ContactPersonList;
import android.content.Context;

public interface IContactDataOperate {
	public String SaveContactData(String objectID,JSONObject contactData,Context packageContext);
	
	public ContactPersonList[] LoadContactPersonList(String objectID,Context packageContext);
	
	public ContactGroup[] LoadContactGroup(String objectID,Context packageContext);
	
	public ContactGroupSub[] LoadContactGroupSub(String objectID,Context packageContext);
	
	public void InitContactPersonInfo(String objectID,Context packageContext);
}
