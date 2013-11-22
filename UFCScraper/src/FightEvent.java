/**
 * Class for a fight event.
 */
public class FightEvent {
	/* ESPN Event Id */
	private Integer eventId;

	/* Fighter ESPN Id */
	private Integer firstFighterId;
	private Integer secondFighterId;

	/* Fighter Name */
	private String firstFighter;
	private String secondFighter;

	/* Fight Info */
	private String weightClass;
	private Boolean champBelt;
	private String round;

	public FightEvent(Integer eventId, Integer firstFighterId,
			Integer secondFighterId, String firstFighter, String secondFighter,
			String weightClass, Boolean champBelt, String round) {
		this.eventId = eventId;
		this.firstFighterId = firstFighterId;
		this.secondFighterId = secondFighterId;
		this.firstFighter = firstFighter;
		this.secondFighter = secondFighter;
		this.weightClass = weightClass;
		this.champBelt = champBelt;
		this.round = round;
	}

}
