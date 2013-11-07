package yftvn.ufc;

import java.util.HashMap;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

/**
 * Class to display a list of all fighters. It will initialize connection to
 * server, and download core information. Assuming users to have Internet
 * connection.
 */
public class ComparisonSearchActivity extends Activity {
	
	// A tag for the cat log
	private static final String TAG = "UFC Fighter App";
	
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
	
	/**
	 * These are the 2 fighter espn ID's we are concerned with.
	 * The first one is for the profile we are displaying on the left, from the profile activity
	 * The second one is for the user selection to compare to.
	 * Both espn IDs will be sent to the ComparisonProfileActivity
	 */
	private int espnId1, espnId2;

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
		espnId1 = bundle.getInt("espnId");
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
				ComparisonSearchActivity.this.listAdapter.getFilter().filter(cs);
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