package yftvn.ufc;

import com.parse.ParseObject;

/**
 * Figher Records.
 * 
 * @author nvutri
 */
public class Record {
	private int wins;
	private int submission;
	private int knockout;
	private int losses;

	/**
	 * Public constructor.
	 * 
	 * @param wins
	 * @param submission
	 * @param knockout
	 * @param losses
	 */
	public Record(int wins, int submission, int knockout, int losses) {
		this.wins = wins;
		this.submission = submission;
		this.knockout = knockout;
		this.losses = losses;
	}

	/**
	 * Constructor from a Parse Object.
	 * 
	 * @param fighterParse
	 */
	public Record(ParseObject fighterParse) {
		this.wins = fighterParse.getInt("wins");
		this.submission = fighterParse.getInt("submission");
		this.knockout = fighterParse.getInt("ko");
		this.losses = fighterParse.getInt("losses");
	}

	public int getWins() {
		return wins;
	}

	public int getSubmission() {
		return submission;
	}

	public int getKnockout() {
		return knockout;
	}

	public int getDecisionWins() {
		return wins - knockout - submission;
	}

	public int getLosses() {
		return losses;
	}
}
