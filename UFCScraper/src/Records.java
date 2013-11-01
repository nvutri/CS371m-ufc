/**
 * Figher Records.
 * 
 * @author nvutri
 */
public class Records {
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
	public Records(int wins, int submission, int knockout, int losses) {
		super();
		this.wins = wins;
		this.submission = submission;
		this.knockout = knockout;
		this.losses = losses;
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

	@Override
	public String toString() {
		return String.format("Wins %d, Submission %d, KO %d, Losses %d",
				this.wins, this.submission, this.knockout, this.losses);
	}
}
