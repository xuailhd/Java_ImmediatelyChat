package xugl.immediatelychat.activitys;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import xugl.immediatelychat.R;
import xugl.immediatelychat.common.CommonVariables;
import xugl.immediatelychat.models.ContactPersonList;

public class PersonsActivity extends BaseActivity {
	private LinearLayout mainLayout;
	private ScrollView scrollView;
	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_person);
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		ContactPersonList[] contactPersonLists= CommonVariables.getContactDataOperate().LoadContactPersonList(CommonVariables.getObjectID(), this);
		if(contactPersonLists==null)
		{
			return;
		}
		
		if(contactPersonLists.length<1)
		{
			return;
		}
		
		mainLayout=(LinearLayout)findViewById(R.id.personslayout);
		scrollView=(ScrollView)findViewById(R.id.personsScrollView);
		
		for(int i=0;i<contactPersonLists.length;i++)
		{
			addPersonIntoView(contactPersonLists[i]);
		}
	}
	
	
	private void addPersonIntoView(ContactPersonList contactPersonList)
	{
		ImageView pic=new ImageView(PersonsActivity.this);
		pic.setImageResource(R.drawable.ic_launcher);
		
		TextView name=new TextView(PersonsActivity.this);
		name.setText(contactPersonList.getContactPersonName());
		
		LinearLayout linearLayout=new LinearLayout(PersonsActivity.this);
		linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout.addView(pic);
		linearLayout.addView(name);
		

		mainLayout.addView(linearLayout);
	}
}
