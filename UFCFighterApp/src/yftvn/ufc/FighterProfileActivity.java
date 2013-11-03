package yftvn.ufc;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.parse.Parse;

public class FighterProfileActivity extends Activity {

	// is this the route to go?
	private static TextView mNameTextView;
	private static TextView mWinsTextView;
	private static TextView mWKOTextView;
	private static TextView mWSubTextView;
	private static TextView mWDTextView;
	private static TextView mLossesTextView;
	private static TextView mTitlesTextView;

	// Fighter Profile View
	private FighterProfileView mFPView;

	// Hard coded Fighter id for now.
	private static final int FIGHTER_ESPN_ID = 2335447;

	private static final String PARSE_APPLICATION_ID = "AJ0JAEbsMNs3pRi9poiROGLxopvwD9Y44aXs8rkz";
	private static final String PARSE_CLIENT_KEY = "ia1k06D9lHgWncELjHm49xsrbREVWUCn7flMc0ic";

	@Override
	/**
	 * When the activity is onCreate:
	 * 1) Initialize Parse connection.
	 * 2) Display Fighter Profile data.
	 * 3) Display Fighter Picture.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);
		setContentView(R.layout.fighter_profile);

		initTextViewInfo();
		displayFighterProfile(FIGHTER_ESPN_ID);

		mFPView = (FighterProfileView) findViewById(R.id.pic);
		// TODO(yfang): Fix below initialize to display picture from espnId.
		mFPView.initialize("FIGHTER_ESPN_ID");
	}

	/**
	 * Associate all the TextView fields with an object.
	 */
	private void initTextViewInfo() {
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

	/**
	 * Display a Fighter Profile page given his espnId.
	 * 
	 * @param espnId
	 */
	private void displayFighterProfile(int espnId) {
		Fighter profile = FighterData.getFighter(espnId);
		Record rec = profile.getRecord();
		mNameTextView.setText(profile.getFullName());
		mWinsTextView.setText(rec.getWins());
		mWKOTextView.setText(rec.getKnockout());
		mWSubTextView.setText(rec.getSubmission());
		mWDTextView.setText(rec.getDecisionWins());
		mLossesTextView.setText(rec.getLosses());
		mTitlesTextView.setText(profile.getTitles());
	}

}
