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

}
