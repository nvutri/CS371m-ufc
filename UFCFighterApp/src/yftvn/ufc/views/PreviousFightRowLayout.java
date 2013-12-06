package yftvn.ufc.views;

import yftvn.ufc.R;
import yftvn.ufc.data.FighterBasicData;
import yftvn.ufc.models.FightRecord;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PreviousFightRowLayout extends LinearLayout {
	private TextView winner, decision, round, time;

	/**
	 * Set private field value based on Fight Record.
	 * 
	 * @param context
	 */
	private void inflateLayout(Context context) {
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.previous_fight_row, this);
		this.winner = (TextView) view.findViewById(R.id.previous_fight_winner);
		this.decision = (TextView) view
				.findViewById(R.id.previous_fight_decision);
		this.round = (TextView) view.findViewById(R.id.previous_fight_round);
		this.time = (TextView) view.findViewById(R.id.previous_fight_time);
	}

	/**
	 * Inflate the layout and populate the fightRec to the text fields.
	 * 
	 * @param context
	 * @param fightRec
	 */
	public PreviousFightRowLayout(Context context, FightRecord fightRec) {
		super(context);
		inflateLayout(context);
		Integer winnerId = fightRec.getWinner();
		winner.setText(FighterBasicData.getFighterName(winnerId));
		decision.setText(fightRec.getDecision());
		round.setText(fightRec.getRound());
		time.setText(fightRec.getTime());
	}

	public PreviousFightRowLayout(Context context) {
		super(context);
		inflateLayout(context);
	}
}
