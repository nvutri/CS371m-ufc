import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Walk through fighter profile pages to scrape wins and losses info.
 * 
 * @author nvutri
 */
public class Scrapers {
	private static final String FIGHTERS_TABLE = "./database/FIGHTERS_TABLE";
	private static final String FIGHTERS_RECORD = "./database/FIGHTERS_RECORD";

	private static final String ESPN_ADDR = "http://espn.go.com/";
	private static final String FIGHTER_INFO = ESPN_ADDR
			+ "mma/fighter/_/id/%d/";

	public static void main(String[] args) throws IOException {
		final String FIGHTERS_RECORD_COL = "ESPNID\tFIRST NAME\tLAST NAME\tWINS\tSUBMISSION\tKO\tLOSSES";
		final String FIGHTERS_RECORD_FORMAT = "%d\t%s\t%s\t%d\t%d\t%d\t%d";

		BufferedReader br = new BufferedReader(new FileReader(FIGHTERS_TABLE));
		PrintWriter pw = new PrintWriter(FIGHTERS_RECORD, "UTF-8");
		pw.println(FIGHTERS_RECORD_COL);

		String line;
		int lineNumber = 0;
		try {
			while ((line = br.readLine()) != null) {
				++lineNumber;
				line = br.readLine();
				String delims = "\t";
				String[] tokens = line.split(delims);
				int espnId = Integer.parseInt(tokens[0]);
				String firstName = tokens[1];
				String lastName = tokens[2];

				Fighters fighter = scrapeFighter(espnId, firstName, lastName);
				if (fighter != null) {
					Records rec = fighter.getRecord();
					String fighterInfo = String.format(FIGHTERS_RECORD_FORMAT,
							fighter.getEspnId(), fighter.getFirstName(),
							fighter.getLastName(), rec.getWins(),
							rec.getSubmission(), rec.getKnockout(),
							rec.getLosses());
					pw.println(fighterInfo);
					pw.flush();
					System.out.printf("%d: Fighter %d %s %s Known \n",
							lineNumber, espnId, firstName, lastName);
				} else {
					System.out.printf("%d: Fighter %d %s %s - No photo\n",
							lineNumber, espnId, firstName, lastName);
				}
				
			}
		} finally {
			pw.close();
			br.close();
		}
	}

	/**
	 * Check if a fighter has a photo.
	 * 
	 * @param doc
	 * @return True if the fighter has a photo on ESPN. False otherwise.
	 */
	private static boolean hasPhoto(Document doc) {
		return doc.select("div.main-headshot").size() > 0;
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
	private static Fighters scrapeFighter(int espnId, String firstName,
			String lastName) throws IOException {

		// Establish Connection.
		String url = String.format(FIGHTER_INFO, espnId);
		Document doc = Jsoup.connect(url).get();

		if (hasPhoto(doc)) {
			// Get the fighting career stats.
			Records rec = scrapeRecord(doc);
			return new Fighters(espnId, firstName, lastName, rec);
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
	private static Records scrapeRecord(Document doc) {
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
		return new Records(wins, submission, ko, losses);
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
