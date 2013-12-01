import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

/**
 * Walk through fighter profile pages to scrape wins and losses info.
 * 
 * @author nvutri
 */
public class FightersScraper {
	private static final String FIGHTERS_TABLE = "./database/FIGHTERS_TABLE";
	private static final String FIGHTERS_RECORD = "./database/FIGHTERS_RECORD.json";

	private static final String ESPN_ADDR = "http://espn.go.com/";
	private static final String FIGHTER_INFO = ESPN_ADDR
			+ "mma/fighter/_/id/%d/";

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(FIGHTERS_TABLE));
		ArrayList<Fighter> fighterList = new ArrayList<Fighter>();
		String line;
		int lineNumber = 0;
		try {
			while ((line = br.readLine()) != null) {
				++lineNumber;
				String delims = "\t";
				String[] tokens = line.split(delims);
				int espnId = Integer.parseInt(tokens[0]);
				String firstName = tokens[1];
				String lastName = tokens[2];
				Fighter fighter = scrapeFighter(espnId, firstName, lastName);
				if (fighter != null) {
					fighterList.add(fighter);
				} else {
					System.out.printf("%d: Fighter %d %s %s \n", lineNumber,
							espnId, firstName, lastName);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			br.close();
		}
		printFighter(fighterList, FIGHTERS_RECORD);
	}

	/**
	 * Print a fighter info in json format.
	 * 
	 * @throws IOException
	 */
	private static void printFighter(ArrayList<Fighter> fighters, String dir)
			throws IOException {
		Gson gson = new Gson();
		PrintWriter pw = new PrintWriter(dir);
		pw.println(gson.toJson(fighters));
		pw.close();
	}

	/**
	 * Check if a fighter has a photo.
	 * 
	 * @param doc
	 * @return True if the fighter has a photo on ESPN. False otherwise.
	 */
	private static boolean hasPhoto(Document doc) {
		return (doc.select("div.main-headshot").size() > 0);
	}

	/**
	 * Fetch a fighter info by scraping ESPN.com.
	 * 
	 * @param espnId
	 * @param firstName
	 * @param lastName
	 * @return a Fighter object if it has a photo, null otherwise.
	 * @throws IOException
	 */
	private static Fighter scrapeFighter(int espnId, String firstName,
			String lastName) {

		// Establish Connection.
		String url = String.format(FIGHTER_INFO, espnId);
		Document doc = new Document(url);
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
		if (hasPhoto(doc)) {
			// Get the fighting career stats.
			Record rec = scrapeRecord(doc);
			return new Fighter(espnId, firstName, lastName, rec);
		} else {
			return null;
		}

	}

	/**
	 * Scrape the fighter MMA Records.
	 * 
	 * @param doc
	 * @return the general fighting record.
	 */
	private static Record scrapeRecord(Document doc) {
		final String FIGHTER_TAG = "table.header-stats";
		// Scrape tables for winning info.
		Elements tables = doc.select(FIGHTER_TAG);
		// Assert 2 tables: WINS-LOSSES-DRAWS and KO-SUBMISSION.
		assert 2 == tables.size();
		Element career = tables.get(0);
		Element result = tables.get(1);
		// Parse Career Stats.
		HashMap<String, String> careerStats = parseTable(career);
		HashMap<String, String> resultStats = parseResult(result);
		int wins = Integer.parseInt(careerStats.get("WINS"));
		int losses = Integer.parseInt(careerStats.get("LOSSES"));
		int submission = Integer.parseInt(resultStats.get("SUBMISSION"));
		int ko = Integer.parseInt(resultStats.get("(T)KO"));
		// Parse Result Details.
		return new Record(wins, submission, ko, losses);
	}

	/**
	 * Parse the career stats table.
	 * 
	 * @param career
	 * @return an array of wins & losses.
	 */
	private static HashMap<String, String> parseTable(Element table) {
		final String TABLE_COLUMN = "td";
		final String TABLE_HEAD = "th";
		Elements cols = table.select(TABLE_COLUMN);
		Elements heads = table.select(TABLE_HEAD);
		HashMap<String, String> stats = new HashMap<String, String>();
		Iterator<Element> colIter = cols.iterator();
		for (Element head : heads) {
			stats.put(head.text(), colIter.next().text());
		}
		return stats;
	}

	/**
	 * Parse the detail into numbers.
	 * 
	 * @param result
	 * @return
	 */
	private static HashMap<String, String> parseResult(Element result) {
		final String RESULT_DETAIL_DELIMS = " - ";
		HashMap<String, String> stats = parseTable(result);
		for (String key : stats.keySet()) {
			String val = stats.get(key);
			String[] tokens = val.split(RESULT_DETAIL_DELIMS);
			String wins = tokens[0];
			stats.put(key, wins);
		}
		return stats;
	}
}
