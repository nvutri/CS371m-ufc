package yftvn.ufc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class FighterBasicData {
	private static ArrayList<Integer> fighterEspnId;
	private static ArrayList<String> fighterNames;

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
		fighterEspnId = new ArrayList<Integer>();
		fighterNames = new ArrayList<String>();
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
	 * Get the basic data in a ArrayList of mapping.
	 * 
	 * @return ArrayList<Integer> of Fighter espnId.
	 */
	public static ArrayList<Integer> getEspnId() {
		return fighterEspnId;
	}

	/**
	 * Get the list of names to be fed into the listview.
	 * 
	 * @return String[]
	 */
	public static String[] getFighterNames() {
		String[] names = new String[fighterNames.size()];
		for (int i = 0; i < fighterNames.size(); ++i) {
			names[i] = fighterNames.get(i);
		}
		return names;
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
				fighterEspnId.add(row.getInt(0));
				fighterNames
						.add(getFullName(row.getString(1), row.getString(2)));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the uppercase full name.
	 * 
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	private static String getFullName(String firstName, String lastName) {
		firstName = firstName.substring(0, 1).toUpperCase()
				+ firstName.substring(1);
		lastName = lastName.substring(0, 1).toUpperCase()
				+ lastName.substring(1);
		return String.format("%s %s", firstName, lastName);
	}
}
