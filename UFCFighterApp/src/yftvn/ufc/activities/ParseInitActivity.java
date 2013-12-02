package yftvn.ufc.activities;

import yftvn.ufc.data.FighterBasicData;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.parse.Parse;

/**
 * Intialize Parse connection and start request and cache required info.
 * 
 */
public class ParseInitActivity extends Activity {
	private static final String PARSE_APPLICATION_ID = "AJ0JAEbsMNs3pRi9poiROGLxopvwD9Y44aXs8rkz";
	private static final String PARSE_CLIENT_KEY = "ia1k06D9lHgWncELjHm49xsrbREVWUCn7flMc0ic";

	/**
	 * Initialization Required to create Parse connection and cache data.
	 * 
	 * @param context
	 * @return if succesfully connected to Parse.
	 */
	public static Boolean initialize(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		Boolean isConnected = activeNetwork != null
				&& activeNetwork.isConnectedOrConnecting();
		if (isConnected) {
			// Initialize Parse Connection.
			Parse.initialize(context, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);
			// Download the fighter basic info.
			FighterBasicData.initialize();
		}
		return isConnected;
	}

}
