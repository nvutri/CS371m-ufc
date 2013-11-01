import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Walk through fighter profile pages to scrape wins and losses info.
 * 
 * @author nvutri
 */
public class Scrapers {
	private static final String FIGHTERS_TABLE = "./database/FIGHTERS_TABLE";
	private static final String FIGHTERS_RECORD = "./database/FIGHTERS_RECORD";
	private static final String ESPN_ADDR = "http://espn.go.com";
	private static final String ALL_FIGHTERS = ESPN_ADDR + "/mma/fighters";
	private static final String FIGHTER_INFO = ESPN_ADDR
			+ "mma/fighter/_/id/%d/%s-%s";

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(FIGHTERS_TABLE));
		String line;
		while ((line = br.readLine()) != null) {
			//System.out.println(line);
			String delims = "\t";
			String[] tokens = line.split(delims);
			int fighterId = Integer.parseInt(tokens[0]);
			String firstName = tokens[1];
			String lastName = tokens[2];
			System.out.println(fighterId + " " + firstName);
		}
		br.close();
	}

	/**
	 * Fetch the tag data from the URL webpage. Source: jsoup.org
	 * 
	 * @param url
	 * @param tags
	 * @throws IOException
	 */
	public static Elements fetch(String url, String tag) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Elements links = doc.select(tag);
		return links;
		//
		// System.out.printf("\nLinks: (%d)\n", links.size());
		// for (Element link : links) {
		// System.out.printf(" * a: <%s>  (%s)\n", link.attr("abs:href"),
		// link.text());
		// }
	}
}
