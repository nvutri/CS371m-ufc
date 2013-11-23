package yftvn.ufc.activities;

import java.util.ArrayList;

import yftvn.ufc.R;
import yftvn.ufc.adapters.FightAdapter;
import yftvn.ufc.data.FightEventData;
import yftvn.ufc.models.FightEvent;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class FightEventActivity extends Activity {
	private ArrayList<FightEvent> fightEvents;

	// TODO (nvutri): Change from hard-coding to a variable.
	private static final Integer EVENT_ID = 400477454;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event);
		// Get all fight Events from Parse.
		fightEvents = FightEventData.getAllFightEvents(EVENT_ID);

		// Displaying all the fight events.
		FightAdapter adapter = new FightAdapter(this, R.layout.event_row,
				fightEvents);
		ListView listViewE = (ListView) findViewById(R.id.listViewE);
		View header = (View) getLayoutInflater().inflate(R.layout.event_header,
				null);
		listViewE.addHeaderView(header);
		listViewE.setAdapter(adapter);

		listViewE.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentAdapter, View view,
					int position, long id) {
				FightEvent fEvent = fightEvents.get(position - 1);
				Intent intent = new Intent(FightEventActivity.this,
						ComparisonProfileActivity.class);
				intent.putExtra("espnId1", fEvent.getFirstFighterId());
				intent.putExtra("espnId2", fEvent.getSecondFighterId());
				startActivity(intent);
			}
		});
	}
}
