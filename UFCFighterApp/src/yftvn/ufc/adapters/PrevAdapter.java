package yftvn.ufc.adapters;

import java.util.ArrayList;

import yftvn.ufc.data.FighterData;
import yftvn.ufc.models.FightRecord;
import yftvn.ufc.models.Fighter;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * FightAdapter to display FightEvent List with fighter names.
 */
public class PrevAdapter extends ArrayAdapter<FightRecord> {
	
	// A tag for the cat log
	private static final String TAG = "PrevAdapter";
	
	private Context context;
	private int layoutResourceId;
	private ArrayList<FightRecord> prevFights;

	public PrevAdapter(Context context, int layoutResourceId,
			ArrayList<FightRecord> prevFights) {
		super(context, layoutResourceId, prevFights);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.prevFights = prevFights;
		
		Log.d(TAG, "prevFights size is: "+ prevFights.size());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		Log.d(TAG, "position view is : "+ position);
		
		View row = convertView;
		PrevHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new PrevHolder();
//			holder.winner = (TextView) row.findViewById(R.id.pWinner);
//			holder.details = (TextView) row.findViewById(R.id.pDetails);

			row.setTag(holder);
		} 
		else 
		{
			holder = (PrevHolder) row.getTag();
		}
		// Populate ListView with fighter names.
		FightRecord fightRecord = prevFights.get(position);
		String whoWon;
		Fighter profile;
		
		if ((fightRecord.getResult()).equals("WIN"))
		{
			profile = FighterData.getFighter(fightRecord.getFighter());
			whoWon = "W: "+profile.getFullName();
		}
		else if ((fightRecord.getResult()).equals("LOSS"))
		{
			profile = FighterData.getFighter(fightRecord.getOpponent());
			whoWon = "W: "+profile.getFullName();
		}
		else
		{
			whoWon = fightRecord.getResult();
		}
		
		holder.winner.setText(whoWon);
		holder.details.setText(fightRecord.getDecision() + " at Round "+fightRecord.getRound()+" "+fightRecord.getTime());
		return row;
	}

	static class PrevHolder {
		TextView winner;
		TextView details;
	}

}
