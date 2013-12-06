package yftvn.ufc.activities;

import java.util.ArrayList;

import yftvn.ufc.R;
import yftvn.ufc.data.FightRecordData;
import yftvn.ufc.data.FighterData;
import yftvn.ufc.models.FightRecord;
import yftvn.ufc.models.Fighter;
import yftvn.ufc.models.Record;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ComparisonProfileActivity extends Activity {

	// A tag for the cat log
	private static final String TAG = "UFC Fighter App";

	/**
	 * The ESPN ID for the fighter this profile will display. This value will
	 * also be sent to comparison search so the comparison search can display a
	 * mini profile for the same fighter.
	 */

	private int espnId1, espnId2;

	/**
	 * This stores the results of the previous encounters between the two
	 * fighters
	 */
	// private ArrayList<FightRecord> prevFights;

	/**
	 * This stores the results of a third fighter these two fighters have fought
	 */
	// private ArrayList<FightRecord> compFights;

	/**
	 * TextView and ImageView fields.
	 */
	private static TextView mNameTextView1, mNameTextView2;
	private static TextView mWinsTextView1, mWinsTextView2;
	private static TextView mWKOTextView1, mWKOTextView2;
	private static TextView mWSubTextView1, mWSubTextView2;
	private static TextView mWDTextView1, mWDTextView2;
	private static TextView mLossesTextView1, mLossesTextView2;
	// private static TextView mTitlesTextView1, mTitlesTextView2;
	private static ImageView mImgView1, mImgView2;
	private static ImageLoader mImgLoader1, mImgLoader2;

	/**
	 * The URL Format to get the photo of the fighter based on his espnId. The
	 * format request 3 integers: espnId, width, height.
	 */
	private static final String PHOTO_URL_FORMAT = "http://a.espncdn.com/combiner/i?img=/i/headshots/mma/players/full/%d.png&w=%d&h=%d";
	private static final int PHOTO_DEFAULT_WIDTH = 250;
	private static final int PHOTO_DEFAULT_HEIGHT = 181;

	@Override
	/**
	 * Activities run when viewing a profile page.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set Content View.
		setContentView(R.layout.comparison_profile);
		// Associate TextFields and display info.
		initFighterViewInfo1();
		initFighterViewInfo2();

		// Get Fighter ESPN Id.
		Bundle bundle = getIntent().getExtras();
		espnId1 = bundle.getInt("espnId1");
		espnId2 = bundle.getInt("espnId2");

		Log.d(TAG, "We have entered ComparisonProfileActivity");
		Log.d(TAG, "espn ID 1: " + bundle.getInt("espnId1"));
		Log.d(TAG, "espn ID 2: " + bundle.getInt("espnId2"));

		// setup the intersection array lists
		// prevFights = FightRecordData.getPrevFightRecords(espnId1, espnId2);
		// compFights = FightRecordData.getComparisonFight(espnId1, espnId2);

		// Log.d(TAG, "size of prevFights is: " + prevFights.size());

		// Config ImageLoader.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).build();

		if (espnId1 > 0) {
			mImgLoader1 = ImageLoader.getInstance();
			mImgLoader1.init(config);
			// Display fighter profile.
			displayFighterProfile1(espnId1);
		}

		if (espnId2 > 0) {
			mImgLoader2 = ImageLoader.getInstance();
			mImgLoader2.init(config);
			// Display fighter profile.
			displayFighterProfile2(espnId2);
		}

		// PrevAdapter adapter = new PrevAdapter(this, R.layout.prev_row,
		// prevFights);
		// ListView prevListView = (ListView) findViewById(R.id.prevListView);
		// prevListView.setAdapter(adapter);
	}

	/**
	 * Associate all the TextView fields with an object. Duplicate due to
	 * laziness to create a class.
	 */
	private void initFighterViewInfo1() {
		mNameTextView1 = (TextView) findViewById(R.id.name1);
		mWinsTextView1 = (TextView) findViewById(R.id.wins1);
		mWKOTextView1 = (TextView) findViewById(R.id.wko1);
		mWSubTextView1 = (TextView) findViewById(R.id.wsub1);
		mWDTextView1 = (TextView) findViewById(R.id.wd1);
		mLossesTextView1 = (TextView) findViewById(R.id.losses1);
		// mTitlesTextView1 = (TextView) findViewById(R.id.titles1);
		mImgView1 = (ImageView) findViewById(R.id.fighterPic1);
	}

	private void initFighterViewInfo2() {
		mNameTextView2 = (TextView) findViewById(R.id.name2);
		mWinsTextView2 = (TextView) findViewById(R.id.wins2);
		mWKOTextView2 = (TextView) findViewById(R.id.wko2);
		mWSubTextView2 = (TextView) findViewById(R.id.wsub2);
		mWDTextView2 = (TextView) findViewById(R.id.wd2);
		mLossesTextView2 = (TextView) findViewById(R.id.losses2);
		// mTitlesTextView2 = (TextView) findViewById(R.id.titles2);
		mImgView2 = (ImageView) findViewById(R.id.fighterPic2);
	}

	/**
	 * Create a listener for click event.
	 * 
	 * @param espnId
	 * @return
	 */
	private OnClickListener createClickListener(final Integer espnId) {
		return new OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(ComparisonProfileActivity.this,
						FighterProfileActivity.class);
				intent.putExtra("espnId1", espnId);
				startActivity(intent);
			}
		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.event_menu:
			eventMenu();
			return true;

		case R.id.fighter_search_menu:
			fighterSearchMenu();
			return true;
		}
		return false;
	}

	public void eventMenu() {
		Intent intent = new Intent(ComparisonProfileActivity.this,
				FightEventListActivity.class);
		startActivity(intent);
	}

	public void fighterSearchMenu() {
		Intent intent = new Intent(ComparisonProfileActivity.this,
				FighterSearchActivity.class);
		startActivity(intent);
	}

	/**
	 * Display a Fighter Profile page given his espnId. Yes, I know this code is
	 * essentially duplicated. We are running out of time, and Yu-Chieh Fang was
	 * sick for a week. We'll make the code a lot cleaner, and less
	 * "duplicative" in the next release. We're very sorry!
	 * 
	 * @param espnId
	 */
	private void displayFighterProfile1(int espnId) {
		String imageUri = getPhotoURL(espnId);
		Fighter profile = FighterData.getFighter(espnId);
		Record rec = profile.getRecord();
		mImgLoader1.displayImage(imageUri, mImgView1);
		mNameTextView1.setText(profile.getFullName());
		mWinsTextView1.setText(String.valueOf(rec.getWins()));
		mWKOTextView1.setText(String.valueOf(rec.getKnockout()));
		mWSubTextView1.setText(String.valueOf(rec.getSubmission()));
		mWDTextView1.setText(String.valueOf(rec.getDecisionWins()));
		mLossesTextView1.setText(String.valueOf(rec.getLosses()));
		mNameTextView1.setOnClickListener(createClickListener(espnId));
		mImgView1.setOnClickListener(createClickListener(espnId));
		// mTitlesTextView1.setText(profile.getTitles());
	}

	private void displayFighterProfile2(int espnId) {
		String imageUri = getPhotoURL(espnId);
		Fighter profile = FighterData.getFighter(espnId);
		Record rec = profile.getRecord();
		mImgLoader2.displayImage(imageUri, mImgView2);
		mNameTextView2.setText(profile.getFullName());
		mWinsTextView2.setText(String.valueOf(rec.getWins()));
		mWKOTextView2.setText(String.valueOf(rec.getKnockout()));
		mWSubTextView2.setText(String.valueOf(rec.getSubmission()));
		mWDTextView2.setText(String.valueOf(rec.getDecisionWins()));
		mLossesTextView2.setText(String.valueOf(rec.getLosses()));
		mNameTextView2.setOnClickListener(createClickListener(espnId));
		mImgView2.setOnClickListener(createClickListener(espnId));
		// mTitlesTextView2.setText(profile.getTitles());
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
