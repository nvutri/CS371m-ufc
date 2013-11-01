package yftvn.ufc;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.Menu;
import android.view.Window;
import android.widget.TextView;

public class FighterProfileActivity extends Activity {
	
	//is this the route to go?
	private TextView mNameTextView;
	private TextView mWinsTextView;
	private TextView mWKOTextView;
	private TextView mWSubTextView;
	private TextView mWDTextView;
	private TextView mLossesTextView;
	private TextView mTitlesTextView;
	
	//temporary until back-end is ready
	ArrayList<String> fighterProfile = new ArrayList<String>();
	
	//Fighter Profile View

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fighter_profile);
		
		setTextViewInfo();
		
		//fill in the text info here
		testDataFighterProfile();
		
	}
	
	// helper method for setting the text views
	private void setTextViewInfo() 
	{
		mNameTextView = (TextView) findViewById(R.id.name);
		mWinsTextView = (TextView) findViewById(R.id.wins);
		mWKOTextView = (TextView) findViewById(R.id.wko);
		mWSubTextView = (TextView) findViewById(R.id.wsub);
		mWDTextView = (TextView) findViewById(R.id.wd);
		mLossesTextView = (TextView) findViewById(R.id.losses);
		mTitlesTextView = (TextView) findViewById(R.id.titles);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fighter_profile, menu);
		return true;
	}
	
	//temporary until back-end is ready
	private void testDataFighterProfile()
	{
		fighterProfile.add("Anderson Silva");
		fighterProfile.add("33");
		fighterProfile.add("20");
		fighterProfile.add("6");
		fighterProfile.add("7");
		fighterProfile.add("5");
		fighterProfile.add("Ex-Champion");
		
		mNameTextView.setText(fighterProfile.get(0));
		mWinsTextView.setText(fighterProfile.get(1));
		mWKOTextView.setText(fighterProfile.get(2));
		mWSubTextView.setText(fighterProfile.get(3));
		mWDTextView.setText(fighterProfile.get(4));
		mLossesTextView.setText(fighterProfile.get(5));
		mTitlesTextView.setText(fighterProfile.get(6));
	}

}
