import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Scrape a fight event to get the fighting info.
 */
public class FightEventScraper {
	private static final String FIGHT_SITE = "http://espn.go.com/mma/fightcenter?eventId=%d";
	private static final String FIGHT_TAG = "div.fightcard";

	/**
	 * Query the fight event given its id, and parse into an ArrayList of
	 * FightEvents.
	 * 
	 * @param eventId
	 * @return a ArrayList of fight event.
	 * @throws IOException
	 */
	public static ArrayList<FightEvent> parseFightEvent(int eventId)
			throws IOException {
		// Establish Connection.
		String url = String.format(FIGHT_SITE, eventId);
		Document doc = Jsoup.connect(url).get();
		Elements fightCards = doc.select(FIGHT_TAG);
		ArrayList<FightEvent> fightEvents = new ArrayList<FightEvent>();
		for (Element fCard : fightCards) {
			fightEvents.add(parseFightCard(fCard, eventId));
		}
		return fightEvents;
	}

	/**
	 * Parse a div of Fight Card into a fight event.
	 * 
	 * @param fightCard
	 * @param eventId
	 * @return a Fight Event
	 */
	private static FightEvent parseFightCard(Element fightCard, Integer eventId) {
		// Fight Card table tags.
		final String ROUND_CLASS = "div.round";
		final String WEIGHT_CLASS = "div.weightclass";
		final String CHAMP_BELT_TAG = "img[src*=mma_champ_belt.gif]";
		final String PLAYER_ONE_CLASS = "div.player1";
		final String PLAYER_TWO_CLASS = "div.player2";

		String round = fightCard.select(ROUND_CLASS).first().text();
		Element weightClass = fightCard.select(WEIGHT_CLASS).first();
		Boolean champBelt = weightClass.select(CHAMP_BELT_TAG).isEmpty();
		Element firstFighter = fightCard.select(PLAYER_ONE_CLASS).first();
		Element secondFighter = fightCard.select(PLAYER_TWO_CLASS).first();
		Integer firstFighterId = parseFighterId(firstFighter);
		Integer secondFighterId = parseFighterId(secondFighter);

		return new FightEvent(eventId, firstFighterId, secondFighterId,
				firstFighter.text(), secondFighter.text(), weightClass.text(),
				champBelt, round);
	}

	/**
	 * Parse a fighter Id given the div card.
	 * 
	 * @param fightEvent
	 * @return
	 */
	private static Integer parseFighterId(Element fighter) {
		final String FIGHTER_ID_PATTERN = "(fighter/_/id/)(\\d+)";
		Element fighterId = fighter.select("a[href]").first();
		// String to be scanned to find the pattern.
		String line = fighterId.attr("href");
		// Create a Pattern object
		Pattern r = Pattern.compile(FIGHTER_ID_PATTERN);
		// Now create matcher object.
		Matcher m = r.matcher(line);
		if (m.find()) {
			return Integer.valueOf(m.group(2));
		}
		return 0;
	}
}
