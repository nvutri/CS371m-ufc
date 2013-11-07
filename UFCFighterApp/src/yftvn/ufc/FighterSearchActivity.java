package yftvn.ufc;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.Parse;

/**
 * Class to display a list of all fighters. It will initialize connection to
 * server, and download core information. Assuming users to have Internet
 * connection.
 */
public class FighterSearchActivity extends Activity {
	public static boolean isConnected;

	private static HashMap<String, Integer> fighterEspnId;
	private static String[] fighterNames;
	private ArrayAdapter<String> listAdapter;

	private static final String PARSE_APPLICATION_ID = "AJ0JAEbsMNs3pRi9poiROGLxopvwD9Y44aXs8rkz";
	private static final String PARSE_CLIENT_KEY = "ia1k06D9lHgWncELjHm49xsrbREVWUCn7flMc0ic";

	// User input to be searched with
	EditText inputSearch;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fighter_search);

		ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		isConnected = activeNetwork != null
				&& activeNetwork.isConnectedOrConnecting();
		if (isConnected) {
			// Initialize Parse Connection.
			Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);
			// Download the fighter basic info.
			FighterBasicData.initialize();
			fighterEspnId = FighterBasicData.getEspnId();
			fighterNames = FighterBasicData.getFighterNames();
			// Display the list of fighters.
			displayListView();
		} else {
			displayNetworkAlert(this);
		}

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
				int espnId = fighterEspnId.get(fighterName);
				Intent intent = new Intent(FighterSearchActivity.this,
						FighterProfileActivity.class);
				intent.putExtra("espnId", espnId);
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
				FighterSearchActivity.this.listAdapter.getFilter().filter(cs);
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

	/**
	 * Display No network activity alert.
	 */
	private void displayNetworkAlert(Context context) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle("No Network Activity");
		alertDialog
				.setMessage("This app requires network activity to work properly");
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.show();
	}
}