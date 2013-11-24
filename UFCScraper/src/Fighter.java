/**
 * Fighter Info.
 * 
 * @author nvutri
 * 
 */
public class Fighter {
	private int espnId;
	private String firstName;
	private String lastName;
	private int wins;
	private int submission;
	private int ko;
	private int losses;

	/**
	 * Public constructor.
	 * 
	 * @param espnId
	 * @param firstName
	 * @param lastName
	 * @param record
	 */
	public Fighter(int espnId, String firstName, String lastName, Record record) {
		super();
		this.espnId = espnId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.wins = record.getWins();
		this.submission = record.getSubmission();
		this.ko = record.getKnockout();
		this.losses = record.getLosses();
	}

}
