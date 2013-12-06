package yftvn.ufc.views;

import yftvn.ufc.R;
import yftvn.ufc.data.FighterBasicData;
import yftvn.ufc.models.FightRecord;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ComparisonFightRowLayout extends LinearLayout {

	private TextView opponent, result1, result2;
	private static final String resultFormat = "%s - %s";

	private void inflateLayout(Context context) {
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.comparison_fight_row, this);
		this.opponent = (TextView) view
				.findViewById(R.id.comparison_fight_opponent);
		this.result1 = (TextView) view
				.findViewById(R.id.comparison_fight_result_1);
		this.result2 = (TextView) view
				.findViewById(R.id.comparison_fight_result_2);
	}

	/**
	 * Inflate the layout. Display 2 fight records into the layout.
	 * 
	 * @param context
	 * @param fightRec1
	 * @param fightRec2
	 */
	public ComparisonFightRowLayout(Context context, FightRecord fightRec1,
			FightRecord fightRec2) {
		super(context);
		inflateLayout(context);
		Integer opponentId = fightRec1.getOpponent();
		opponent.setText(FighterBasicData.getFighterName(opponentId));
		String formRes1 = formatResult(fightRec1.getResult(),
				fightRec1.getDecision());
		String formRes2 = formatResult(fightRec2.getResult(),
				fightRec2.getDecision());
		result1.setText(formRes1);
		result2.setText(formRes2);
	}

	public ComparisonFightRowLayout(Context context) {
		super(context);
		inflateLayout(context);
	}

	private String formatResult(String result, String decision) {
		return String.format(resultFormat, result, decision);
	}
}
