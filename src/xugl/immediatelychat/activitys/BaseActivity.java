package xugl.immediatelychat.activitys;

import xugl.immediatelychat.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public abstract class BaseActivity extends Activity {

	private TextView charsView;
	private TextView personsView;
	private TextView groupsView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setView();
		initBottom();
		setBottomListner();
		init();
	}
	
	protected abstract void setView();
	protected abstract void init();

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
	}
	
	private void initBottom()
	{
		charsView= (TextView)findViewById(R.id.chatsView);
		personsView=(TextView)findViewById(R.id.personsView);
		groupsView=(TextView)findViewById(R.id.groupsView);
	}
	
	private void setBottomListner()
	{
		charsView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(BaseActivity.this, HomeActivity.class);
	            startActivity(intent);
			}
		});
		
		personsView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(BaseActivity.this, PersonsActivity.class);
	            startActivity(intent);
			}
		});
		
		groupsView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(BaseActivity.this, GroupsActivity.class);
	            startActivity(intent);
			}
		});
	}
}
