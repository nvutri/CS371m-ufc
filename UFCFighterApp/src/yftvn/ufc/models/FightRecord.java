package yftvn.ufc.models;

/**
 * Fighting Record.
 */
public class FightRecord {
	/* ESPN Event Id */
	private Integer eventId;

	/* Fighter ESPN Id */
	private Integer fighter;
	private Integer opponent;

	/* Fight Info */
	private String round;
	private String time;

	/* Fight Final Result */
	private String result;
	private String decision;

	/**
	 * Public Constructor of a Fight Record.
	 * 
	 * @param eventId
	 *            : the event in which the fight took place.
	 * @param fighter
	 *            : fighter ESPN ID.
	 * @param opponent
	 *            : opponent fighter ESPN ID.
	 * @param round
	 *            : number of rounds. Sometimes unknown.
	 * @param time
	 *            : duration. Sometimes unknown.
	 * @param result
	 *            : WIN | LOSS | DRAW.
	 * @param decision
	 *            : decision made by referee.
	 */
	public FightRecord(Integer eventId, Integer fighter, Integer opponent,
			String round, String time, String result, String decision) {
		this.eventId = eventId;
		this.fighter = fighter;
		this.opponent = opponent;
		this.round = round;
		this.time = time;
		this.result = result;
		this.decision = decision;
	}

	public Integer getEventId() {
		return eventId;
	}

	public Integer getFighter() {
		return fighter;
	}

	public Integer getOpponent() {
		return opponent;
	}

	public String getRound() {
		return round;
	}

	public String getTime() {
		return time;
	}

	public String getResult() {
		return result;
	}

	public String getDecision() {
		return decision;
	}

}
