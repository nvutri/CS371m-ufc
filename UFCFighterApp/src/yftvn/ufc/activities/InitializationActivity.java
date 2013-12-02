package yftvn.ufc.activities;

import java.util.HashMap;

import yftvn.ufc.R;
import yftvn.ufc.data.FighterBasicData;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.parse.Parse;

public class InitializationActivity extends Activity {
	public static boolean isConnected;

	private static HashMap<String, Integer> fighterEspnId;
	private static String[] fighterNames;
	private ArrayAdapter<String> listAdapter;

	private static final String PARSE_APPLICATION_ID = "AJ0JAEbsMNs3pRi9poiROGLxopvwD9Y44aXs8rkz";
	private static final String PARSE_CLIENT_KEY = "ia1k06D9lHgWncELjHm49xsrbREVWUCn7flMc0ic";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.initialization);

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

			Intent intent = new Intent(InitializationActivity.this,
					FightEventListActivity.class);
			startActivity(intent);
		} else {
			displayNetworkAlert(this);
		}

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
