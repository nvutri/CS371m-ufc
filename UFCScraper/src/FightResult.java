/**
 * Result of a Fight.
 */
public enum FightResult {
	WIN("Win"), LOSS("Loss"), DRAW("Draw");
	private String value;

	private FightResult(String value) {
		this.value = value;
	}
}
