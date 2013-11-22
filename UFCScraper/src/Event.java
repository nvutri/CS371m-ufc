import org.apache.commons.lang3.text.WordUtils;

/**
 * UFC Event Info.
 */
public class Event {
	private Integer eventId;
	private String title;
	private String location;
	private String date;

	public Event(String date, Integer eventId, String title, String location) {
		this.date = date;
		this.eventId = eventId;
		this.title = title;
		this.location = WordUtils.capitalize(location.toLowerCase());
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
