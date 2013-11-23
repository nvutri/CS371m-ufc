package yftvn.ufc.models;

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

	public FightEvent(Integer eventId, Integer firstFighterId,
			Integer secondFighterId, String firstFighter, String secondFighter,
			String weightClass, Boolean champBelt) {
		this.eventId = eventId;
		this.firstFighterId = firstFighterId;
		this.secondFighterId = secondFighterId;
		this.firstFighter = firstFighter;
		this.secondFighter = secondFighter;
		this.weightClass = weightClass;
		this.champBelt = champBelt;
	}

	public Integer getEventId() {
		return eventId;
	}

	public Integer getFirstFighterId() {
		return firstFighterId;
	}

	public Integer getSecondFighterId() {
		return secondFighterId;
	}

	public String getFirstFighter() {
		return firstFighter;
	}

	public String getSecondFighter() {
		return secondFighter;
	}

	public String getWeightClass() {
		return weightClass;
	}

	public Boolean getChampBelt() {
		return champBelt;
	}

}
