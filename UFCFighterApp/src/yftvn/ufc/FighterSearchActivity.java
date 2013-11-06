package yftvn.ufc;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.Parse;

/**
 * Class to display a list of all fighters. It will initialize connection to
 * server, and download core information. Assuming users to have Internet
 * connection.
 */
public class FighterSearchActivity extends Activity {
	public static boolean isConnected;

	private static ArrayList<Integer> fighterEspnId;
	private static String[] fighterNames;
	private static ArrayAdapter<String> listAdapter;

	private static final String PARSE_APPLICATION_ID = "AJ0JAEbsMNs3pRi9poiROGLxopvwD9Y44aXs8rkz";
	private static final String PARSE_CLIENT_KEY = "ia1k06D9lHgWncELjHm49xsrbREVWUCn7flMc0ic";

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
			// TODO(nvutri): displayNetworkAlert(getApplicationContext());
		}

	}

	/**
	 * Display the list view of all fighters.
	 */
	private void displayListView() {
		ListView lv = (ListView) findViewById(R.id.listView);
		listAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, fighterNames);
		lv.setAdapter(listAdapter);
		// React to user clicks on item
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentAdapter, View view,
					int position, long id) {
				int espnId = fighterEspnId.get(position);
				Intent intent = new Intent(FighterSearchActivity.this,
						FighterProfileActivity.class);
				intent.putExtra("espnId", espnId);
				startActivity(intent);
			}
		});
		// we register for the contextmneu
		registerForContextMenu(lv);
	}
}