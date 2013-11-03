package yftvn.ufc;

import java.util.List;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Class to query Fighters Data. Assuming that Parse.initialize before calling
 * any methods here.
 * 
 * @author nvutri
 */
public class FighterData {

	// Fighter info table.
	private static final String FIGHTER_TABLE = "Fighters";
	private static final String FIGHTER_TABLE_ESPNID = "espnId";

	/**
	 * Query a Fighter from the database given his espnId.
	 * 
	 * @param espnId
	 * @throws ParseException
	 * @return
	 */
	public static Fighter getFighter(int espnId) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(FIGHTER_TABLE);
		query.whereEqualTo(FIGHTER_TABLE_ESPNID, espnId);
		ParseObject fighterParse = new ParseObject(FIGHTER_TABLE);
		List<ParseObject> fighterList;
		try {
			fighterList = query.find();
			fighterParse = fighterList.get(0);
			assert 1 == fighterList.size();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Fighter(espnId, fighterParse);
	}

}
