import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
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
	private static final String FIGHT_INFO = "./database/FIGHT_INFO.json";

	public static void main(String[] args) throws IOException {
		// Establish Connection.
		String url = String.format(SCHEDULE_SITE);
		Document doc = Jsoup.connect(url).get();
		Elements eventTable = doc.select(SCHEDULE_TAG);
		ArrayList<Event> eList = parseTable(eventTable);
		printEvents(eList, EVENT_SCHEDULE);
		ArrayList<FightEvent> fEvents = new ArrayList<FightEvent>();
		for (Event ev : eList) {
			fEvents.addAll(FightEventScraper.parseFightEvent(ev.getEventId()));
		}
		printEvents(fEvents, FIGHT_INFO);
	}

	/**
	 * Print the list of events into a file in json format.
	 * 
	 * @param eventList
	 * @param dir
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	private static void printEvents(ArrayList<?> eventList, String dir)
			throws FileNotFoundException, UnsupportedEncodingException {
		Gson gson = new Gson();
		PrintWriter pw = new PrintWriter(dir, "UTF-8");
		pw.println(gson.toJson(eventList));
		pw.close();
	}

	/**
	 * 
	 * @param eventTable
	 * @return
	 * @throws IOException
	 */
	private static ArrayList<Event> parseTable(Elements eventTable)
			throws IOException {
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
		return eventList;
	}

	/**
	 * Parse one fight event into date, title and location.
	 * 
	 * @param fightEvent
	 * @return a fight Event
	 */
	private static Event parseFightEvent(Element fightEvent) {
		final String FIELD_TAG = "td";
		Elements fields = fightEvent.select(FIELD_TAG);
		Integer eventId = parseEventId(fields.get(1));
		String date = formatDate(fields.get(0).text());
		String eventTitle = fields.get(1).text();
		String location = fields.get(2).text();
		System.out.println(date);
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

	private static String formatDate(String rawDate) {
		Date date = new Date();
		try {
			Date curDate = new Date();
			date = new SimpleDateFormat("MMM dd", Locale.ENGLISH)
					.parse(rawDate);
			date.setYear(curDate.getYear());
			if (date.before(curDate)) {
				date.setYear(curDate.getYear() + 1);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		df.setTimeZone(tz);
		return df.format(date);
	}
}
