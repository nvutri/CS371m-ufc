/**
 * Figher Records.
 * 
 * @author nvutri
 */
public class Records {
	private int wins;
	private int submission;
	private int knockout;
	private int decision;
	private int losses;

	/**
	 * Public constructor.
	 * 
	 * @param wins
	 * @param submission
	 * @param knockout
	 * @param decision
	 * @param losses
	 */
	public Records(int wins, int submission, int knockout, int decision,
			int losses) {
		super();
		this.wins = wins;
		this.submission = submission;
		this.knockout = knockout;
		this.decision = decision;
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

	public int getDecision() {
		return decision;
	}

	public void setDecision(int decision) {
		this.decision = decision;
	}

	public int getLosses() {
		return losses;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

}
