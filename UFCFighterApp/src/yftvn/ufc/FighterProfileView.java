package yftvn.ufc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class FighterProfileView extends View
{
		
	private Bitmap fighterPic;
		
	public FighterProfileView(Context context) 
	{
		super(context);
	}
		
	public FighterProfileView(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
	}
		
	public FighterProfileView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
	}
		
	public void initialize(String name) 
	{
		fighterPic = BitmapFactory.decodeResource(getResources(), R.drawable.anderson_silva);
		//I want to call R.drawable.name, but it won't let me pass in the name as a variable
	}
		
	@Override
	public void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas);
		
		canvas.drawBitmap(fighterPic, 
						null, // src
						new Rect(0, 0, 150, 150), // dest
						null);
	}


}


