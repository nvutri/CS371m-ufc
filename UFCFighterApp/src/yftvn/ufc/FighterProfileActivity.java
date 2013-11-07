package yftvn.ufc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class FighterProfileActivity extends Activity {

	/**
	 * TextView and ImageView fields.
	 */
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
	
	/**
	 * The ESPN ID for the fighter this profile will display.
	 * This value will also be sent to comparison search so the comparison search
	 * can display a mini profile for the same fighter.
	 */
	private int espnId;

	@Override
	/**
	 * Activities run when viewing a profile page.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set Content View.
		setContentView(R.layout.fighter_profile);
		// Associate TextFields and display info.
		initFighterViewInfo();
		// Get Fighter ESPN Id.
		Bundle bundle = getIntent().getExtras();
		espnId = bundle.getInt("espnId");
		if (espnId > 0) {
			// Config ImageLoader.
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
					getApplicationContext()).build();
			mImgLoader = ImageLoader.getInstance();
			mImgLoader.init(config);
			// Display fighter profile.
			displayFighterProfile(espnId);
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
	public boolean onCreateOptionsMenu(Menu menu) 
	{ 
		super.onCreateOptionsMenu(menu); 

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.fighter_profile, menu);
		return true;

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		super.onOptionsItemSelected(item);
		
		switch (item.getItemId()) 
		{
			case R.id.comparison_search:
				Intent intent = new Intent(FighterProfileActivity.this,
						ComparisonSearchActivity.class);
				intent.putExtra("espnId", espnId);
				startActivity(intent);
				return true;
		}
		return false;
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
		mImgLoader.displayImage(imageUri, mImgView);
		mNameTextView.setText(profile.getFullName());
		mWinsTextView.setText(String.valueOf(rec.getWins()));
		mWKOTextView.setText(String.valueOf(rec.getKnockout()));
		mWSubTextView.setText(String.valueOf(rec.getSubmission()));
		mWDTextView.setText(String.valueOf(rec.getDecisionWins()));
		mLossesTextView.setText(String.valueOf(rec.getLosses()));
		mTitlesTextView.setText(profile.getTitles());
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
