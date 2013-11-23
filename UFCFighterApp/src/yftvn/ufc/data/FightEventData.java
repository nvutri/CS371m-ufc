package yftvn.ufc.data;

import java.util.ArrayList;
import java.util.List;

import yftvn.ufc.models.FightEvent;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class FightEventData {
	/**
	 * Fight Event Table Meta Info from Parse.
	 */
	private static final String FIGHT_EVENT_TABLE = "FightEvents";
	private static final String EVENT_ID = "eventId";
	private static final String CHAMP_BELT = "champBelt";
	private static final String FIRST_FIGHTER = "firstFighter";
	private static final String SECOND_FIGHTER = "secondFighter";
	private static final String FIRST_FIGHTER_ID = "firstFighterId";
	private static final String SECOND_FIGHTER_ID = "secondFighterId";
	private static final String WEIGHT_CLASS = "weightClass";

	/**
	 * Get all fight events.
	 * 
	 * @param eventId
	 * @return ArrayList of all FightEvents
	 */
	public static ArrayList<FightEvent> getAllFightEvents(Integer eventId) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(FIGHT_EVENT_TABLE);
		query.whereEqualTo(EVENT_ID, eventId);
		// Query the find event;
		List<ParseObject> fightEventList = new ArrayList<ParseObject>();
		try {
			fightEventList = query.find();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// Populate Parse Object into custom FightEvent Class.
		ArrayList<FightEvent> fightEvents = new ArrayList<FightEvent>();
		for (ParseObject pfEvent : fightEventList) {
			FightEvent fE = new FightEvent(pfEvent.getInt(EVENT_ID),
					pfEvent.getInt(FIRST_FIGHTER_ID),
					pfEvent.getInt(SECOND_FIGHTER_ID),
					pfEvent.getString(FIRST_FIGHTER),
					pfEvent.getString(SECOND_FIGHTER),
					pfEvent.getString(WEIGHT_CLASS),
					pfEvent.getBoolean(CHAMP_BELT));
			fightEvents.add(fE);
		}
		return fightEvents;
	}

}
