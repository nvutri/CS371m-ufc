package yftvn.ufc;

import java.util.List;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Class to query Fighters Data. Assuming that Parse.initialize before calling
 * any methods here.
 * 
 * @author nvutri
 */
public class FightData {

	/**
	 * Query a Fighter from the database given his espnId.
	 * 
	 * @param espnId
	 * @return
	 */
	public static Fighter getFighter(int espnId) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Fighters");
		query.whereEqualTo("espnId", espnId);
		ParseObject fighterParse = new ParseObject("Fighters");
		try {
			List<ParseObject> fighterList = query.find();
			fighterParse = fighterList.get(0);
			assert 1 == fighterList.size();
		} catch (ParseException e) {
			Log.d("Fighter", "Error: " + e.getMessage());
		}
		return new Fighter(espnId, fighterParse);
	}

}
