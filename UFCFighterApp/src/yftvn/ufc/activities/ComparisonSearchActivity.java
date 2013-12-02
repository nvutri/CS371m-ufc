package yftvn.ufc.activities;

import java.util.HashMap;

import yftvn.ufc.R;
import yftvn.ufc.data.FighterBasicData;
import yftvn.ufc.data.FighterData;
import yftvn.ufc.models.Fighter;
import yftvn.ufc.models.Record;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Class to display a list of all fighters. It will initialize connection to
 * server, and download core information. Assuming users to have Internet
 * connection.
 */
public class ComparisonSearchActivity extends Activity {

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
	private static final int PHOTO_DEFAULT_WIDTH = 200;
	private static final int PHOTO_DEFAULT_HEIGHT = 145;

	private static HashMap<String, Integer> fighterEspnId;
	private static String[] fighterNames;
	private static ArrayAdapter<String> listAdapter;

	// User input to be searched with
	EditText inputSearch;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comparison_search);

		Log.d(TAG, "We have entered ComparisonSearchActivity");

		fighterEspnId = FighterBasicData.getEspnId();
		fighterNames = FighterBasicData.getFighterNames();

		// Display the list of fighters.
		displayListView();

		// Initialize the text view for the current fighter
		initFighterViewInfo();
		
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
		
		Log.d(TAG, "espnID is: "+espnId1);
		
		if (espnId1 > 0) {
			// Config ImageLoader.
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
					getApplicationContext()).build();
			mImgLoader = ImageLoader.getInstance();
			mImgLoader.init(config);
			// Display fighter profile.
			displayFighterProfile();
		}
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
		Intent intent = new Intent(ComparisonSearchActivity.this,
				FightEventActivity.class);
		intent.putExtra("espnId1", espnId1);
		intent.putExtra("espnId2", espnId2);
		startActivity(intent);
	}
	
	public void fighterSearchMenu()
	{
		Intent intent = new Intent(ComparisonSearchActivity.this,
				FighterSearchActivity.class);
		intent.putExtra("espnId1", espnId1);
		intent.putExtra("espnId2", espnId2);
		startActivity(intent);
	}
	
	public void fighterProfileMenu()
	{
		if (mode == 0)
		{
			showDialog(DIALOG_ONE_ID);
		}
		else
		{
			Intent intent = new Intent(ComparisonSearchActivity.this,
					FighterProfileActivity.class);
			intent.putExtra("espnId1", espnId1);
			intent.putExtra("espnId2", espnId2);
			startActivity(intent);
		}
	}
	
	public void comparisonSearchMenu()
	{
		
	}
	
	public void comparisonProfileMenu()
	{
		if (mode != 2)
		{
			showDialog(DIALOG_TWO_ID);
		}
		else
		{
			Intent intent = new Intent(ComparisonSearchActivity.this,
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

	/**
	 * Display a Fighter Profile page given his espnId.
	 * 
	 * @param espnId
	 */
	private void displayFighterProfile() {
		String imageUri = getPhotoURL(espnId1);
		Fighter profile = FighterData.getFighter(espnId1);
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

	/**
	 * Display the list view of all fighters.
	 */
	private void displayListView() {
		ListView lv = (ListView) findViewById(R.id.listView);
		inputSearch = (EditText) findViewById(R.id.inputSearch);
		listAdapter = new ArrayAdapter<String>(this, R.layout.list_item,
				R.id.fighter_name, fighterNames);

		lv.setAdapter(listAdapter);
		// React to user clicks on item
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentAdapter, View view,
					int position, long id) {
				TextView clickedView = (TextView) view
						.findViewById(R.id.fighter_name);
				String fighterName = clickedView.getText().toString();
				espnId2 = fighterEspnId.get(fighterName);
				Intent intent = new Intent(ComparisonSearchActivity.this,
						ComparisonProfileActivity.class);
				intent.putExtra("espnId1", espnId1);
				intent.putExtra("espnId2", espnId2);
				startActivity(intent);
			}
		});

		/**
		 * Add Search Filter to List View.
		 */
		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				// When user changed the Text
				ComparisonSearchActivity.this.listAdapter.getFilter()
						.filter(cs);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
			}
		});

		// we register for the contextmneu
		registerForContextMenu(lv);
	}
}