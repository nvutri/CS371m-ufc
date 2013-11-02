package yftvn.ufc;

import com.parse.ParseObject;

/**
 * Fighter Info.
 * 
 * @author nvutri
 */
public class Fighter {
	private int espnId;
	private String firstName;
	private String lastName;
	private Record record;

	/**
	 * Public constructor.
	 * 
	 * @param espnId
	 * @param firstName
	 * @param lastName
	 * @param record
	 */
	public Fighter(int espnId, String firstName, String lastName, Record record) {
		this.espnId = espnId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.record = record;
	}

	/**
	 * Initialize from the ParseObject.
	 * 
	 * @param espnId
	 * @param fighterParse
	 */
	public Fighter(int espnId, ParseObject fighterParse) {
		this(espnId, fighterParse.getString("firstName"), fighterParse
				.getString("lastName"), new Record(fighterParse));
		assert espnId == fighterParse.getInt("espnId");
	}

	/**
	 * Construct basic fighter with no record.
	 * 
	 * @param espnId
	 * @param firstName
	 * @param lastName
	 */
	public Fighter(int espnId, String firstName, String lastName) {
		this(espnId, firstName, lastName, null);
	}

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

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

}
