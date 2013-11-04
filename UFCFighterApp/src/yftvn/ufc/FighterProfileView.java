package yftvn.ufc;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Class to display Fighter Information.
 */
public class FighterProfileView extends View {
	/**
	 * The URL Format to get the photo of the fighter based on his espnId. The
	 * format request 3 integers: espnId, width, height.
	 */
	private static final String PHOTO_URL_FORMAT = "http://a.espncdn.com/combiner/i?img=/i/headshots/mma/players/full/%d.png&w=%d&h=%d";
	private static final int PHOTO_DEFAULT_HEIGHT = 150;
	private static final int PHOTO_DEFAULT_WIDTH = 150;

	public FighterProfileView(Context context) {
		super(context);
	}

	public FighterProfileView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FighterProfileView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void initialize(int espnId) {
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	/**
	 * @param espnId
	 * @return String of the correct Photo URL to be displayed.
	 */
	private String getPhotoURL(int espnId) {
		return String.format(PHOTO_URL_FORMAT, espnId, PHOTO_DEFAULT_HEIGHT,
				PHOTO_DEFAULT_WIDTH);
	}
}
