package yftvn.ufc;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.Parse;

public class FighterProfileActivity extends Activity {

	public static boolean isConnected;

	// is this the route to go?
	private static TextView mNameTextView;
	private static TextView mWinsTextView;
	private static TextView mWKOTextView;
	private static TextView mWSubTextView;
	private static TextView mWDTextView;
	private static TextView mLossesTextView;
	private static TextView mTitlesTextView;
	private static ImageView mImgView;
	private static ImageLoader mImgLoader;

	/**
	 * The URL Format to get the photo of the fighter based on his espnId. The
	 * format request 3 integers: espnId, width, height.
	 */
	private static final String PHOTO_URL_FORMAT = "http://a.espncdn.com/combiner/i?img=/i/headshots/mma/players/full/%d.png&w=%d&h=%d";
	private static final int PHOTO_DEFAULT_WIDTH = 250;
	private static final int PHOTO_DEFAULT_HEIGHT = 181;

	// Hard coded Fighter id for now.
	private static final int FIGHTER_ESPN_ID = 2335447;

	private static final String PARSE_APPLICATION_ID = "AJ0JAEbsMNs3pRi9poiROGLxopvwD9Y44aXs8rkz";
	private static final String PARSE_CLIENT_KEY = "ia1k06D9lHgWncELjHm49xsrbREVWUCn7flMc0ic";

	@Override
	/**
	 * Activities run when the application first created.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set Content View.
		setContentView(R.layout.fighter_profile);
		// Detect if network is available.
		ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		isConnected = activeNetwork != null
				&& activeNetwork.isConnectedOrConnecting();
		if (isConnected) {
			// 1. Init Parse Connection.
			Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);

			// 2. Associate TextFields and display info.
			initFighterViewInfo();

			// 3. Config ImageLoader.
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
					getApplicationContext()).build();
			mImgLoader = ImageLoader.getInstance();
			mImgLoader.init(config);

			// 4. Display fighter profile.
			displayFighterProfile(FIGHTER_ESPN_ID);
		} else {
			// TODO(nvutri): displayNetworkAlert(getApplicationContext());
		}

	}

	/**
	 * Associate all the TextView fields with an object.
	 */
	private void initFighterViewInfo() {
		mNameTextView = (TextView) findViewById(R.id.name);
		mWinsTextView = (TextView) findViewById(R.id.wins);
		mWKOTextView = (TextView) findViewById(R.id.wko);
		mWSubTextView = (TextView) findViewById(R.id.wsub);
		mWDTextView = (TextView) findViewById(R.id.wd);
		mLossesTextView = (TextView) findViewById(R.id.losses);
		mTitlesTextView = (TextView) findViewById(R.id.titles);
		mImgView = (ImageView) findViewById(R.id.fighterPic);
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
		String imageUri = getPhotoURL(espnId);
		Fighter profile = FighterData.getFighter(espnId);
		Record rec = profile.getRecord();
		mNameTextView.setText(profile.getFullName());
		mWinsTextView.setText(String.valueOf(rec.getWins()));
		mWKOTextView.setText(String.valueOf(rec.getKnockout()));
		mWSubTextView.setText(String.valueOf(rec.getSubmission()));
		mWDTextView.setText(String.valueOf(rec.getDecisionWins()));
		mLossesTextView.setText(String.valueOf(rec.getLosses()));
		mTitlesTextView.setText(profile.getTitles());
		mImgLoader.displayImage(imageUri, mImgView);
	}

	/**
	 * @param espnId
	 * @return String of the correct Photo URL to be displayed.
	 */
	private static String getPhotoURL(int espnId) {
		return String.format(PHOTO_URL_FORMAT, espnId, PHOTO_DEFAULT_WIDTH,
				PHOTO_DEFAULT_HEIGHT);
	}

}
