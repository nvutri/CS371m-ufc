package yftvn.ufc.models;

/**
 * UFC Event Info.
 */
public class UFCEvent {
	private Integer eventId;
	private String title;
	private String location;
	private String date;

	public UFCEvent(String date, Integer eventId, String title, String location) {
		this.date = date;
		this.eventId = eventId;
		this.title = title;
		this.location = location;
	}

	public Integer getEventId() {
		return eventId;
	}

	public String getTitle() {
		return title;
	}

	public String getLocation() {
		return location;
	}

	public String getDate() {
		return date;
	}

}
