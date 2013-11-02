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

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getSubmission() {
		return submission;
	}

	public void setSubmission(int submission) {
		this.submission = submission;
	}

	public int getKnockout() {
		return knockout;
	}

	public void setKnockout(int knockout) {
		this.knockout = knockout;
	}

	public int getLosses() {
		return losses;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}
}
