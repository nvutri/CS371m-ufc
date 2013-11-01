/**
 * Fighter Info.
 * 
 * @author nvutri
 * 
 */
public class Fighters {
	private int espnId;
	private String firstName;
	private String lastName;
	private Records record;

	public int getEspnId() {
		return espnId;
	}

	public void setEspnId(int espnId) {
		this.espnId = espnId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Records getRecord() {
		return record;
	}

	public void setRecord(Records record) {
		this.record = record;
	}

	/**
	 * Public constructor.
	 * 
	 * @param espnId
	 * @param firstName
	 * @param lastName
	 * @param record
	 */
	public Fighters(int espnId, String firstName, String lastName,
			Records record) {
		super();
		this.espnId = espnId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.record = record;
	}

}
