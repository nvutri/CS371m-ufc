package yftvn.ufc.activities;

import java.util.ArrayList;

import yftvn.ufc.R;
import yftvn.ufc.adapters.EventAdapter;
import yftvn.ufc.data.UFCEventData;
import yftvn.ufc.models.UFCEvent;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class FightEventListActivity extends Activity {
	// A tag for the cat log.
	private static final String TAG = "Event List";

	private ArrayList<UFCEvent> ufcEvents;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_list);
		if (ParseInitActivity.initialize(this)) {
			// Get all fight Events from Parse.
			ufcEvents = UFCEventData.getEvents();
			// Displaying all the fight events.
			EventAdapter adapter = new EventAdapter(this,
					R.layout.event_list_row, ufcEvents);
			ListView eventListView = (ListView) findViewById(R.id.eventListView);
			eventListView.setAdapter(adapter);
			eventListView
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						public void onItemClick(AdapterView<?> parentAdapter,
								View view, int position, long id) {
							UFCEvent uEvent = ufcEvents.get(position);
							Intent intent = new Intent(
									FightEventListActivity.this,
									FightEventActivity.class);
							intent.putExtra("eventId", uEvent.getEventId());
							Log.d(TAG,
									"Event ID being passed: "
											+ uEvent.getEventId());
							intent.putExtra("eventName", uEvent.getTitle());
							Log.d(TAG, "Event Name/Title being passed: "
									+ uEvent.getTitle());
							startActivity(intent);
						}
					});
		} else {
			displayNetworkAlert();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.event_menu:
			return true;

		case R.id.fighter_search_menu:
			fighterSearchMenu();
			return true;
		}
		return false;
	}

	private void fighterSearchMenu() {
		Intent intent = new Intent(FightEventListActivity.this,
				FighterSearchActivity.class);
		startActivity(intent);
	}

	/**
	 * Display No network activity alert.
	 */
	private void displayNetworkAlert() {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("No Network Activity");
		alertDialog
				.setMessage("This app requires network activity to work properly");
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.show();
	}
}
