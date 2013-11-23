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
	private String weightClass;
	private Boolean champBelt;
	private String round;
	private String duration;

	/* Fight Final Result */
	private FightResult result;
	private String decision;
}
