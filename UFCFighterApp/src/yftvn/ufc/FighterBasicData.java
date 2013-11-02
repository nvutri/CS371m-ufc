package yftvn.ufc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class FighterBasicData {
	public static ArrayList<Fighter> fighterBasicData;

	// Fighter data table.
	private static final String FIGHTER_DATA_TABLE = "FighterData";
	private static final String FIGHTER_DATA_TABLE_NAME = "name";
	private static final String FIGHTER_DATA_TABLE_LOCATION = "location";
	private static final String BASIC_INFO_NAME = "basicInfo";
	private static final int BUFFER_SIZE = 1000;

	/**
	 * @return True if the fighterBasicData is ready. False otherwise.
	 */
	public static boolean isReady() {
		return !fighterBasicData.isEmpty();
	}

	/**
	 * Initialize/Cache basic fighter data: espnId, first name, last name.
	 */
	public static void initialize() {
		// Query the basicInfo file form Parse.
		ParseQuery<ParseObject> query = ParseQuery.getQuery(FIGHTER_DATA_TABLE);
		query.whereEqualTo(FIGHTER_DATA_TABLE_NAME, BASIC_INFO_NAME);
		ParseObject fighterParse = new ParseObject(FIGHTER_DATA_TABLE);
		try {
			List<ParseObject> fighterList = query.find();
			assert 1 == fighterList.size();
			fighterParse = fighterList.get(0);
			// Fetch the Fighter data from Parse.
			fetchFighterDataTable(fighterParse);
		} catch (ParseException e) {
			Log.d("Error Parse:", e.toString());
		}
	}

	/**
	 * Fetch the fighter basic data from Parse. Once done, deserialize JSON into
	 * the array list.
	 * 
	 * @param fighterParse
	 */
	private static void fetchFighterDataTable(ParseObject fighterParse) {
		// Retrieve the File from Parse.
		byte[] data = new byte[BUFFER_SIZE];
		ParseFile basicInfo = new ParseFile(data);
		basicInfo = fighterParse.getParseFile(FIGHTER_DATA_TABLE_LOCATION);

		// Convert the Json files into an ArrayList<Fighter>.
		assert basicInfo != null;
		basicInfo.getDataInBackground(new GetDataCallback() {
			public void done(byte[] data, ParseException e) {
				assert e == null;
				try {
					fillInFighterBasicData(new String(data));
				} catch (JSONException jsonE) {
					jsonE.printStackTrace();
				}
			}
		});
	}

	/**
	 * Fill the jsonData into the fighterBasicData.
	 * 
	 * @param jsonData
	 */
	private static void fillInFighterBasicData(String jsonData)
			throws JSONException {
		// Recreate the fighterBasicData ArrayList
		fighterBasicData = new ArrayList<Fighter>();
		JSONArray arrInfo;
		arrInfo = new JSONArray(jsonData);
		for (int index = 0; index < arrInfo.length(); ++index) {
			JSONArray row = arrInfo.getJSONArray(index);
			fighterBasicData.add(new Fighter(row.getInt(0), row.getString(1),
					row.getString(2)));
		}
	}

}
