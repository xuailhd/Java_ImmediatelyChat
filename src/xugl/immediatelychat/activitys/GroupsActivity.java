package xugl.immediatelychat.activitys;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import xugl.immediatelychat.R;
import xugl.immediatelychat.common.CommonVariables;
import xugl.immediatelychat.models.ContactGroup;

public class GroupsActivity extends BaseActivity {
	private LinearLayout mainLayout;
	private ScrollView scrollView;
	
	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_group);
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		ContactGroup[] contactGroups= CommonVariables.getContactDataOperate().LoadContactGroup(CommonVariables.getObjectID(), this);
		if(contactGroups==null)
		{
			return;
		}
		
		if(contactGroups.length<1)
		{
			return;
		}
		
		mainLayout=(LinearLayout)findViewById(R.id.groupslayout);
		scrollView=(ScrollView)findViewById(R.id.groupsScrollView);
		
		for(int i=0;i<contactGroups.length;i++)
		{
			addGroupIntoView(contactGroups[i]);
		}
	}
	private void addGroupIntoView(ContactGroup contactGroup)
	{
		ImageView pic=new ImageView(GroupsActivity.this);
		pic.setImageResource(R.drawable.ic_launcher);
		
		TextView name=new TextView(GroupsActivity.this);
		name.setText(contactGroup.getGroupName());
		
		LinearLayout linearLayout=new LinearLayout(GroupsActivity.this);
		linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout.addView(pic);
		linearLayout.addView(name);
		
		mainLayout.addView(linearLayout);
	}
	
}
