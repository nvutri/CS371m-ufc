package yftvn.ufc.views;

import yftvn.ufc.R;
import yftvn.ufc.data.FighterBasicData;
import yftvn.ufc.models.FightRecord;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FightHistoryRowLayout extends LinearLayout {
	private TextView opponent, result, decision, round, time;

	/**
	 * Set private field value based on Fight Record.
	 * 
	 * @param context
	 */
	private void inflateLayout(Context context) {
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.fight_history_row, this);
		this.opponent = (TextView) view
				.findViewById(R.id.fight_history_opponent);
		this.result = (TextView) view.findViewById(R.id.fight_history_result);
		this.decision = (TextView) view
				.findViewById(R.id.fight_history_decision);
		this.round = (TextView) view.findViewById(R.id.fight_history_round);
		this.time = (TextView) view.findViewById(R.id.fight_history_time);
	}

	public FightHistoryRowLayout(Context context, FightRecord fightRec) {
		super(context);
		inflateLayout(context);
		Integer opponentId = fightRec.getOpponent();
		opponent.setText(FighterBasicData.getFighterName(opponentId));
		result.setText(fightRec.getResult());
		decision.setText(fightRec.getDecision());
		round.setText(fightRec.getRound());
		time.setText(fightRec.getTime());
	}

	public FightHistoryRowLayout(Context context) {
		super(context);
		inflateLayout(context);
	}

}
