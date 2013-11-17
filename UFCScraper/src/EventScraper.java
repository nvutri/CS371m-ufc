import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

/**
 * Scrape espn.go.com/mma/schedule to get all the coming fights.
 */
public class EventScraper {
	private static final String SCHEDULE_SITE = "http://espn.go.com/mma/schedule";
	private static final String SCHEDULE_TAG = "table.tablehead";
	private static final String EVENT_SCHEDULE = "./database/EVENT_SCHEDULE.json";

	public static void main(String[] args) throws IOException {
		// Establish Connection.
		String url = String.format(SCHEDULE_SITE);
		Document doc = Jsoup.connect(url).get();
		Elements eventTable = doc.select(SCHEDULE_TAG);
		parseTable(eventTable);
	}

	private static void parseTable(Elements eventTable) throws IOException {
		// Tables tag.
		final String STATHEAD_TAG = "stathead";
		final String ROW_TAG = "tr";

		final String UFC_TAG = "UFC";
		// 2 tables: this week event and scheduled events.
		final Integer NUM_TABLES = 2;
		ArrayList<Event> eventList = new ArrayList<Event>();

		int tableIndex = 0;
		Elements rows = eventTable.select(ROW_TAG);
		for (Element row : rows) {
			if (row.className().equals(STATHEAD_TAG)) {
				// Check to make sure only parse the first 2 tables.
				++tableIndex;
				if (tableIndex > NUM_TABLES)
					break;
			} else if (row.text().contains(UFC_TAG)) {
				// Check if this is UFC event.
				Event event = parseFightEvent(row);
				eventList.add(event);
			}
		}
		Gson gson = new Gson();
		PrintWriter pw = new PrintWriter(EVENT_SCHEDULE, "UTF-8");
		pw.println(gson.toJson(eventList));
		pw.close();
	}

	/**
	 * Parse one fight event into date, title and location.
	 * 
	 * @param fightEvent
	 * @return a fight Event
	 */
	private static Event parseFightEvent(Element fightEvent) {
		final String FIELD_TAG = "td";
		final String ESPN_DATE_FORMAT = "MMM dd";
		Elements fields = fightEvent.select(FIELD_TAG);
		Integer eventId = parseEventId(fields.get(1));
		String date = fields.get(0).text();
		String eventTitle = fields.get(1).text();
		String location = fields.get(2).text();
		return new Event(date, eventId, eventTitle, location);
	}

	/**
	 * Parse the event Id out of the field.
	 * 
	 * @param eventField
	 * @return the integer event field id.
	 */
	private static Integer parseEventId(Element eventField) {
		Element eventId = eventField.select("a[href]").first();
		// String to be scanned to find the pattern.
		String line = eventId.attr("href");
		String pattern = "(eventId=)(\\d+)";
		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);
		// Now create matcher object.
		Matcher m = r.matcher(line);
		if (m.find()) {
			return Integer.valueOf(m.group(2));
		}
		return 0;
	}
}
