package yftvn.ufc.activities;

import yftvn.ufc.R;
import yftvn.ufc.data.FighterData;
import yftvn.ufc.models.Fighter;
import yftvn.ufc.models.Record;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class FighterProfileActivity extends Activity {
	
	// A tag for the cat log
	private static final String TAG = "UFC Fighter App";
	
	private static final int DIALOG_ONE_ID = 1;
	private static final int DIALOG_TWO_ID = 2;
	
	/*
	 * for determining at what point this activity was called
	 * 0 = First time this activity has been called. A fighter/fighters has yet to be selected
	 * 1 = The user has already selected one fighter previously before navigating back to here
	 * 2 = The user has selected 2 fighters previously, before navigating back to here
	 */
	private int mode = 0;
	
	/**
	 * The ESPN ID for the fighter this profile will display. This value will
	 * also be sent to comparison search so the comparison search can display a
	 * mini profile for the same fighter.
	 */
	private int espnId1, espnId2;	

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
		if (bundle.getInt("espnId2") != 0)
		{
			mode = 2;
			espnId1 = bundle.getInt("espnId1");
			espnId2 = bundle.getInt("espnId2");
		}
		else if (bundle.getInt("espnId1") != 0)
		{
			mode = 1;
			espnId1 = bundle.getInt("espnId1");
			espnId2 = 0;
		}
		else
		{
			espnId1 = 0;
			espnId2 = 0;
		}
		
		if (espnId1 > 0) {
			// Config ImageLoader.
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
					getApplicationContext()).build();
			mImgLoader = ImageLoader.getInstance();
			mImgLoader.init(config);
			// Display fighter profile.
			displayFighterProfile(espnId1);
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
		inflater.inflate(R.menu.options_menu, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
			case R.id.event_menu:
				eventMenu();
				return true;
				
			case R.id.fighter_search_menu: 
				fighterSearchMenu();   	
				return true;
				
			case R.id.fighter_profile_menu:
				fighterProfileMenu();
				return true;
				
			case R.id.comparison_search_menu:
				comparisonSearchMenu();
				return true;
				
			case R.id.comparison_profile_menu:
				comparisonProfileMenu();
				return true;
		}
		return false;
	}
	
	public void eventMenu()
	{
		Intent intent = new Intent(FighterProfileActivity.this,
				FightEventActivity.class);
		intent.putExtra("espnId1", espnId1);
		intent.putExtra("espnId2", espnId2);
		startActivity(intent);
	}
	
	public void fighterSearchMenu()
	{
		Intent intent = new Intent(FighterProfileActivity.this,
				FighterSearchActivity.class);
		intent.putExtra("espnId1", espnId1);
		intent.putExtra("espnId2", espnId2);
		startActivity(intent);
	}
	
	public void fighterProfileMenu()
	{
	}
	
	public void comparisonSearchMenu()
	{
		if (mode == 0)
		{
			showDialog(DIALOG_ONE_ID);
		}
		else
		{
			Intent intent = new Intent(FighterProfileActivity.this,
					ComparisonSearchActivity.class);
			intent.putExtra("espnId1", espnId1);
			intent.putExtra("espnId2", espnId2);
			startActivity(intent);
		}
	}
	
	public void comparisonProfileMenu()
	{
		if (mode != 2)
		{
			showDialog(DIALOG_TWO_ID);
		}
		else
		{
			Intent intent = new Intent(FighterProfileActivity.this,
					ComparisonProfileActivity.class);
			intent.putExtra("espnId1", espnId1);
			intent.putExtra("espnId2", espnId2);
			startActivity(intent);
		}
	}
	
	protected Dialog onCreateDialog(int id) 
	{
		Dialog dialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		switch(id) {
			case DIALOG_ONE_ID:
				dialog = createMissingDialog(builder, 1);
				break;
			case DIALOG_TWO_ID:
				dialog = createMissingDialog(builder, 2);
				break;
		}
 
		if(dialog == null)
			Log.d(TAG, "Dialog has a null value");
		else
			Log.d(TAG, "Dialog created: " + id + ", dialog: " + dialog);
		return dialog;   
	}
	
	// helper method for creating dialog
	private Dialog createMissingDialog(Builder builder, int count) 
	{
		if (count == 1)
		{
			builder.setMessage(R.string.one); 
		}
		else
		{
			builder.setMessage(R.string.two); 
		}
		
		builder.setPositiveButton("OK", null);	
		return builder.create();
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
