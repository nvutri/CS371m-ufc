import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Walk through all fighter profile pages to scrape all their fight history.
 */
public class FightRecordScraper {
	private static final String FIGHTERS_ESPNID = "./database/FIGHTERS_ESPNID";
	private static final String FIGHT_HISTORY = "./database/FIGHT_HISTORY.json";

	private static final String ESPN_MMA = "http://espn.go.com/mma/fighter";
	private static final String FIGHTER_HISTORY = ESPN_MMA
			+ "/history/_/id/%d/";
	private static final String HISTORY_TABLE = "table.mod-player-stats";

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(FIGHTERS_ESPNID));
		String line;
		ArrayList<FightRecord> allFights = new ArrayList<FightRecord>();
		while ((line = br.readLine()) != null) {
			Integer espnId = Integer.valueOf(line);
			allFights.addAll(scrapeFighterHistory(espnId));
		}
		EventScraper.dumpArrayList(allFights, FIGHT_HISTORY);
		br.close();
	}

	/**
	 * Scrape history fighting of a fighter
	 */
	private static ArrayList<FightRecord> scrapeFighterHistory(int espnId) {
		// Establish Connection.
		String url = String.format(FIGHTER_HISTORY, espnId);
		Document doc = new Document(url);
		// Get the page.
		Boolean connected = false;
		while (!connected) {
			try {
				doc = Jsoup.connect(url).get();
				connected = true;
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Re Connecting.");
			}
		}
		Element table = doc.select(HISTORY_TABLE).first();
		ArrayList<FightRecord> fightHistory = parseTable(espnId, table);
		return fightHistory;
	}

	/**
	 * Parse the fight history table.
	 * 
	 * @param fighterId
	 * @param table
	 * @return
	 */
	private static ArrayList<FightRecord> parseTable(Integer fighterId,
			Element table) {
		final String TABLE_ROW = "tr.oddrow, tr.evenrow";
		ArrayList<FightRecord> fights = new ArrayList<FightRecord>();
		Elements rows = table.select(TABLE_ROW);
		for (Element row : rows) {
			fights.add(parseFight(fighterId, row));
		}
		return fights;
	}

	/**
	 * Parse the fighting detail into a FightRecord.
	 * 
	 * @param fighterId
	 * @param row
	 * @return
	 */
	private static FightRecord parseFight(Integer fighterId, Element row) {
		final String TABLE_COL = "td";
		final Integer EVENT_FIELD_ID = 1;
		final Integer OPPONENT_FIELD_ID = 2;
		final Integer RESULT_FIELD_ID = 3;
		final Integer DECISION_FIELD_ID = 4;
		final Integer ROUND_FIELD_ID = 5;
		final Integer TIME_FIELD_ID = 6;

		Elements cols = row.select(TABLE_COL);
		Integer eventId = EventScraper.parseEventId(cols.get(EVENT_FIELD_ID));
		Integer opponentId = FightEventScraper.parseFighterId(cols
				.get(OPPONENT_FIELD_ID));
		FightResult fightResult = FightResult.valueOf(cols.get(RESULT_FIELD_ID)
				.text().toUpperCase());
		String fightDecision = cols.get(DECISION_FIELD_ID).text();
		Integer fightRound = Integer.valueOf(cols.get(ROUND_FIELD_ID).text());
		String fightTime = cols.get(TIME_FIELD_ID).text();
		FightRecord rec = new FightRecord(eventId, fighterId, opponentId,
				fightRound, fightTime, fightResult, fightDecision);
		return rec;
	}
}
