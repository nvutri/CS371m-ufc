package yftvn.ufc.activities;

import java.util.ArrayList;
import java.util.HashMap;

import yftvn.ufc.R;
import yftvn.ufc.R.id;
import yftvn.ufc.R.layout;
import yftvn.ufc.adapters.FightAdapter;
import yftvn.ufc.data.FighterBasicData;
import yftvn.ufc.models.FightEvent;

import com.parse.Parse;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class FightEventActivity extends Activity {
	private ListView listViewE;
	private ArrayList<FightEvent> fightEvents;

	private static HashMap<String, Integer> fighterEspnId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event);

		populateEvent();

		FightAdapter adapter = new FightAdapter(this, R.layout.event_row, fightEvents);
		listViewE = (ListView) findViewById(R.id.listViewE);

		View header = (View) getLayoutInflater().inflate(R.layout.event_header,
				null);
		listViewE.addHeaderView(header);
		listViewE.setAdapter(adapter);

		fighterEspnId = FighterBasicData.getEspnId();
		
		// TODO(rain): Change left, right to more descriptive names.
		listViewE.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentAdapter, View view,
					int position, long id) {
				TextView clickedViewL = (TextView) view.findViewById(R.id.left);
				TextView clickedViewR = (TextView) view
						.findViewById(R.id.right);
				String fighterNameL = clickedViewL.getText().toString();
				String fighterNameR = clickedViewR.getText().toString();
				int espnId1 = fighterEspnId.get(fighterNameL);
				int espnId2 = fighterEspnId.get(fighterNameR);
				Intent intent = new Intent(FightEventActivity.this,
						ComparisonProfileActivity.class);
				intent.putExtra("espnId1", espnId1);
				intent.putExtra("espnId2", espnId2);
				startActivity(intent);
			}
		});

	}

	/**
	 * Populate the fight array with some test data. Remove after connecting the
	 * app with the backend.
	 */

	private void populateEvent() {
		fightEvents = new ArrayList<FightEvent>();
		FightEvent one = new FightEvent("Anderson Silva", "Anthony Pettis");
		FightEvent two = new FightEvent("Anthony Pettis", "Ali Bagautinov");
		FightEvent three = new FightEvent("Chael Sonnen", "Anderson Silva");
		fightEvents.add(one);
		fightEvents.add(two);
		fightEvents.add(three);
	}

}
