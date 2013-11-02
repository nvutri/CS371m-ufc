package yftvn.ufc;

import java.util.ArrayList;
import java.util.HashMap;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseAnalytics;

public class FighterProfileActivity extends Activity {

	// is this the route to go?
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

	// Fighter Profile View

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		final String PARSE_APPLICATION_ID = "AJ0JAEbsMNs3pRi9poiROGLxopvwD9Y44aXs8rkz";
		final String PARSE_CLIENT_KEY = "ia1k06D9lHgWncELjHm49xsrbREVWUCn7flMc0ic";
		Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);
		ParseAnalytics.trackAppOpened(getIntent());
		setContentView(R.layout.fighter_profile);

		setTextViewInfo();

		// fill in the text info here
		testDataFighterProfile();
		
		mFPView = (FighterProfileView) findViewById(R.id.pic);
		mFPView.initialize(fighterProfile.get("name"));
		
	}

	// helper method for setting the text views
	private void setTextViewInfo() {
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
