package yftvn.ufc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class FighterBasicData {
	private static ArrayList<Map<String, Integer>> fighterBasicData;

	// Fighter data table.
	private static final String FIGHTER_DATA_TABLE = "FighterData";
	private static final String FIGHTER_DATA_TABLE_NAME = "name";
	private static final String FIGHTER_DATA_TABLE_LOCATION = "location";
	private static final String BASIC_INFO_NAME = "basicInfo";
	private static final int BUFFER_SIZE = 1000;

	/**
	 * Initialize/Cache basic fighter data: espnId, first name, last name. The
	 * data is in a file loading from Parse.
	 */
	public static void initialize() {
		// Parse Query.
		ParseQuery<ParseObject> query = ParseQuery.getQuery(FIGHTER_DATA_TABLE);
		query.whereEqualTo(FIGHTER_DATA_TABLE_NAME, BASIC_INFO_NAME);
		ParseObject fighterParse = new ParseObject(FIGHTER_DATA_TABLE);
		// Fighter Basic Data.
		fighterBasicData = new ArrayList<Map<String, Integer>>();
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
		try {
			data = basicInfo.getData();
			fillInFighterBasicData(new String(data));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fill the jsonData into the fighterBasicData.
	 * 
	 * @param jsonData
	 */
	private static void fillInFighterBasicData(String jsonData) {
		JSONArray arrInfo;
		try {
			arrInfo = new JSONArray(jsonData);
			for (int index = 0; index < arrInfo.length(); ++index) {
				JSONArray row = arrInfo.getJSONArray(index);
				/**
				 * row: espnId, firstName, lastName.
				 */
				fighterBasicData.add(createFighterMap(row.getInt(0),
						row.getString(1), row.getString(2)));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Create a Fighter Map: Fullname -> espnId.
	 * 
	 * @param espnId
	 * @param firstName
	 * @param lastName
	 * @return Map<Fullname, espnId>
	 */
	private static Map<String, Integer> createFighterMap(int espnId,
			String firstName, String lastName) {
		// Uppercase firstName.
		firstName = firstName.substring(0, 1).toUpperCase()
				+ firstName.substring(1);
		lastName = lastName.substring(0, 1).toUpperCase()
				+ lastName.substring(1);
		String fullName = String.format("%s %s", firstName, lastName);
		Map<String, Integer> info = new HashMap<String, Integer>();
		info.put(fullName, espnId);
		return info;
	}

	/**
	 * Get the basic data in a ArrayList of mapping.
	 * 
	 * @return ArrayList<Map<String, Integer>> of Fighter basic info.
	 */
	public static ArrayList<Map<String, Integer>> getBasicData() {
		return fighterBasicData;
	}
}
