package yftvn.ufc.adapters;

import java.util.ArrayList;

import yftvn.ufc.R;
import yftvn.ufc.data.FighterData;
import yftvn.ufc.models.FightRecord;
import yftvn.ufc.models.Fighter;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FightHistoryAdapter extends ArrayAdapter<FightRecord> {
	private Context context;
	private int layoutResourceId;
	private ArrayList<FightRecord> fightHistory;

	public FightHistoryAdapter(Context context, int layoutResourceId,
			ArrayList<FightRecord> fightHistory) {
		super(context, layoutResourceId, fightHistory);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.fightHistory = fightHistory;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		FightRecordHolder holder = null;

		if (view == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			view = inflater.inflate(layoutResourceId, parent, false);
			holder = new FightRecordHolder(view);
			view.setTag(holder);
		} else {
			holder = (FightRecordHolder) view.getTag();
		}
		// Populate ListView with fighter names.
		holder.setRecordHolder(fightHistory.get(position));
		return view;
	}

	static class FightRecordHolder {
		TextView opponent, result, decision, round, time;

		public FightRecordHolder(View row) {
			this.opponent = (TextView) row
					.findViewById(R.id.fight_history_opponent);
			this.result = (TextView) row
					.findViewById(R.id.fight_history_result);
			this.decision = (TextView) row
					.findViewById(R.id.fight_history_decision);
			this.round = (TextView) row.findViewById(R.id.fight_history_round);
			this.time = (TextView) row.findViewById(R.id.fight_history_time);
		}

		/**
		 * Populate the holder row with the record of information.
		 * 
		 * Note: require one query to get the opponent name.
		 * 
		 * @param rec
		 */
		public void setRecordHolder(FightRecord rec) {
			Fighter opFighter = FighterData.getFighter(rec.getOpponent());
			opponent.setText(opFighter.getFullName());
			result.setText(rec.getResult());
			decision.setText(rec.getDecision());
			round.setText(rec.getResult());
			time.setText(rec.getTime());
		}
	}

}