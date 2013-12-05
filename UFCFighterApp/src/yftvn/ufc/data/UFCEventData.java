package yftvn.ufc.data;

import java.util.ArrayList;
import java.util.List;

import yftvn.ufc.models.UFCEvent;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Query the Event data from Parse. Adapt this data into a list of Event.
 * 
 */
public class UFCEventData {
	/**
	 * UFC Event Table Meta Info from Parse.
	 */
	private static final String EVENT_TABLE = "Events";
	private static final String DATE_FIELD = "date";
	private static final String EVENT_ID_FIELD = "eventId";
	private static final String TITLE_FIELD = "title";
	private static final String LOCATION_FIELD = "location";

	/**
	 * Get all upcoming events.
	 * 
	 * @return ArrayList of all UFCEvents
	 */
	public static ArrayList<UFCEvent> getEvents() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(EVENT_TABLE);
		List<ParseObject> eventList = new ArrayList<ParseObject>();
		try {
			eventList = query.find();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// Populate Parse Object into custom UFCEvent Class.
		ArrayList<UFCEvent> ufcEvents = new ArrayList<UFCEvent>();
		for (ParseObject pE : eventList) {
			UFCEvent fE = new UFCEvent(pE.getString(DATE_FIELD),
					pE.getInt(EVENT_ID_FIELD), pE.getString(TITLE_FIELD),
					pE.getString(LOCATION_FIELD));
			ufcEvents.add(fE);
		}
		return ufcEvents;
	}
}
