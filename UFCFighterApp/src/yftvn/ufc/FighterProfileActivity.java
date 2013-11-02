package yftvn.ufc;

import java.util.ArrayList;
import java.util.HashMap;

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
	HashMap<String, String> fighterProfile = new HashMap<String, String>();
	
	//Fighter Profile View
	private FighterProfileView mFPView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fighter_profile);
		
		setTextViewInfo();
		
		//fill in the text info here
		testDataFighterProfile();
		
		mFPView = (FighterProfileView) findViewById(R.id.pic);
		mFPView.initialize(fighterProfile.get("name"));
		
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
		fighterProfile.put("name", "Anderson Silva");
		fighterProfile.put("wins", "33");
		fighterProfile.put("wko", "20");
		fighterProfile.put("wsub", "6");
		fighterProfile.put("wd", "7");
		fighterProfile.put("losses", "5");
		fighterProfile.put("titles", "Ex-Champion");
		
		mNameTextView.setText(fighterProfile.get("name"));
		mWinsTextView.setText(fighterProfile.get("wins"));
		mWKOTextView.setText(fighterProfile.get("wko"));
		mWSubTextView.setText(fighterProfile.get("wsub"));
		mWDTextView.setText(fighterProfile.get("wd"));
		mLossesTextView.setText(fighterProfile.get("losses"));
		mTitlesTextView.setText(fighterProfile.get("titles"));
	}

}
