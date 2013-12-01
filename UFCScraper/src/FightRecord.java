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
	private Integer round;
	private String time;

	/* Fight Final Result */
	private FightResult result;
	private String decision;

	public FightRecord(Integer eventId, Integer fighter, Integer opponent,
			Integer round, String time, FightResult result, String decision) {
		this.eventId = eventId;
		this.fighter = fighter;
		this.opponent = opponent;
		this.round = round;
		this.time = time;
		this.result = result;
		this.decision = decision;
	}

}
