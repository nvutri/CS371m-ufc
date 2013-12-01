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
import android.widget.ListView;

public class FightEventActivity extends Activity {
	
	// A tag for the cat log
	private static final String TAG = "UFC Fighter App";
	
	private static final int DIALOG_ONE_ID = 1;
	private static final int DIALOG_TWO_ID = 2;
	
	/*
	 * for determining at what point this activity was called
	 * 0 = First time this activity has been called. A fighter/fighters has yet to be selected
	 * 1 = The user has already selected one fighter previously before navigating back to here
	 * 2 = The user has selected 2 fighters previously, before navigating back to here
	 */
	private int mode = 0;
	
	/**
	 * The ESPN ID for the fighter this profile will display. This value will
	 * also be sent to comparison search so the comparison search can display a
	 * mini profile for the same fighter.
	 */
	private int espnId1, espnId2;
	
	private ArrayList<FightEvent> fightEvents;

	// TODO (nvutri): Change from hard-coding to a variable.
	private static final Integer EVENT_ID = 400477454;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event);
		
		Bundle bundle = getIntent().getExtras();
		if (bundle.getInt("espnId2") != 0)
		{
			mode = 2;
			espnId1 = bundle.getInt("espnId1");
			espnId2 = bundle.getInt("espnId2");
		}
		else if (bundle.getInt("espnId1") != 0)
		{
			mode = 1;
			espnId1 = bundle.getInt("espnId1");
			espnId2 = 0;
		}
		else
		{
			espnId1 = 0;
			espnId2 = 0;
		}
		
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
				
			case R.id.fighter_profile_menu:
				fighterProfileMenu();
				return true;
				
			case R.id.comparison_search_menu:
				comparisonSearchMenu();
				return true;
				
			case R.id.comparison_profile_menu:
				comparisonProfileMenu();
				return true;
		}
		return false;
	}
	
	public void eventMenu()
	{
		
	}
	
	public void fighterSearchMenu()
	{
		Intent intent = new Intent(FightEventActivity.this,
				FighterSearchActivity.class);
		intent.putExtra("espnId1", espnId1);
		intent.putExtra("espnId2", espnId2);
		startActivity(intent);
	}
	
	public void fighterProfileMenu()
	{
		if (mode == 0)
		{
			showDialog(DIALOG_ONE_ID);
		}
		else
		{
			Intent intent = new Intent(FightEventActivity.this,
					FighterProfileActivity.class);
			intent.putExtra("espnId1", espnId1);
			intent.putExtra("espnId2", espnId2);
			startActivity(intent);
		}
	}
	
	public void comparisonSearchMenu()
	{
		if (mode == 0)
		{
			showDialog(DIALOG_ONE_ID);
		}
		else
		{
			Intent intent = new Intent(FightEventActivity.this,
					ComparisonSearchActivity.class);
			intent.putExtra("espnId1", espnId1);
			intent.putExtra("espnId2", espnId2);
			startActivity(intent);
		}
	}
	
	public void comparisonProfileMenu()
	{
		if (mode != 2)
		{
			showDialog(DIALOG_TWO_ID);
		}
		else
		{
			Intent intent = new Intent(FightEventActivity.this,
					ComparisonProfileActivity.class);
			intent.putExtra("espnId1", espnId1);
			intent.putExtra("espnId2", espnId2);
			startActivity(intent);
		}
	}
	
	protected Dialog onCreateDialog(int id) 
	{
		Dialog dialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		switch(id) {
			case DIALOG_ONE_ID:
				dialog = createMissingDialog(builder, 1);
				break;
			case DIALOG_TWO_ID:
				dialog = createMissingDialog(builder, 2);
				break;
		}
 
		if(dialog == null)
			Log.d(TAG, "Dialog has a null value");
		else
			Log.d(TAG, "Dialog created: " + id + ", dialog: " + dialog);
		return dialog;   
	}
	
	// helper method for creating dialog
	private Dialog createMissingDialog(Builder builder, int count) 
	{
		if (count == 1)
		{
			builder.setMessage(R.string.one); 
		}
		else
		{
			builder.setMessage(R.string.two); 
		}
		
		builder.setPositiveButton("OK", null);	
		return builder.create();
	}
}
