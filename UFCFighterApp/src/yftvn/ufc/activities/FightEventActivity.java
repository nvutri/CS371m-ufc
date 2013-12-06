package yftvn.ufc.activities;

import java.util.ArrayList;
import yftvn.ufc.R;
import yftvn.ufc.adapters.FightAdapter;
import yftvn.ufc.data.FightEventData;
import yftvn.ufc.models.FightEvent;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FightEventActivity extends Activity {
	
	// A tag for the cat log
	private static final String TAG = "Event";
	
	/**
	 * The event ID and name for the UFC event this activity will display.
	 */
	private int eventId;
	private String eventName;
	
	private ArrayList<FightEvent> fightEvents;

	/**
	 * TextView fields.
	 */
	private static TextView mEventTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event);
		
		Bundle bundle = getIntent().getExtras();
		eventId = bundle.getInt("eventId");
		eventName = bundle.getString("eventName");
		
		Log.d(TAG, "Event ID is: "+eventId);
		Log.d(TAG, "Event Name is: "+eventName);
		
		// Get all fight Events from Parse.
		fightEvents = FightEventData.getAllFightEvents(eventId);
		
		initHeaderView();
		
		// Displaying all the fight events.
		FightAdapter adapter = new FightAdapter(this, R.layout.event_row,
				fightEvents);
		ListView listViewE = (ListView) findViewById(R.id.listViewE);
		listViewE.setAdapter(adapter);

		listViewE.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentAdapter, View view,
					int position, long id) {
				FightEvent fEvent = fightEvents.get(position);
				Intent intent = new Intent(FightEventActivity.this,
						ComparisonProfileActivity.class);
				intent.putExtra("espnId1", fEvent.getFirstFighterId());
				intent.putExtra("espnId2", fEvent.getSecondFighterId());
				startActivity(intent);
			}
		});
	}
	
	/**
	 * Associate the header TextView field with an object.
	 */
	private void initHeaderView() 
	{
		mEventTextView = (TextView) findViewById(R.id.eventHeader);
		mEventTextView.setText(eventName);
	}
	
	@Override 
	public boolean onCreateOptionsMenu(Menu menu) 
	{ 
		super.onCreateOptionsMenu(menu); 

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
			case R.id.event_menu:
				eventMenu();
				return true;
				
			case R.id.fighter_search_menu: 
				fighterSearchMenu();   	
				return true;
		}
		return false;
	}
	
	public void eventMenu()
	{
		Intent intent = new Intent(FightEventActivity.this,
				FightEventListActivity.class);
		startActivity(intent);
	}
	
	public void fighterSearchMenu()
	{
		Intent intent = new Intent(FightEventActivity.this,
				FighterSearchActivity.class);
		startActivity(intent);
	}
}
