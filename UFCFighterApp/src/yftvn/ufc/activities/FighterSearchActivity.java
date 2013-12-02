package yftvn.ufc.activities;

import java.util.HashMap;

import yftvn.ufc.R;
import yftvn.ufc.data.FighterBasicData;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Class to display a list of all fighters. It will initialize connection to
 * server, and download core information. Assuming users to have Internet
 * connection.
 */
public class FighterSearchActivity extends Activity {

	// A tag for the cat log
	private static final String TAG = "UFC Fighter App";
	
	/**
	 * The ESPN ID for the fighter this profile will display. This value will
	 * also be sent to comparison search so the comparison search can display a
	 * mini profile for the same fighter.
	 */
	private int espnId1;
	
	private static HashMap<String, Integer> fighterEspnId;
	private static String[] fighterNames;
	private ArrayAdapter<String> listAdapter;

	// User input to be searched with
	EditText inputSearch;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fighter_search);

		fighterEspnId = FighterBasicData.getEspnId();
		fighterNames = FighterBasicData.getFighterNames();
		// Display the list of fighters.
		displayListView();

	}

	/**
	 * Display the list view of all fighters.
	 */
	private void displayListView() {
		ListView lv = (ListView) findViewById(R.id.listView);
		inputSearch = (EditText) findViewById(R.id.inputSearch);
		listAdapter = new ArrayAdapter<String>(this, R.layout.list_item,
				R.id.fighter_name, fighterNames);

		lv.setAdapter(listAdapter);
		// React to user clicks on item
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentAdapter, View view,
					int position, long id) {
				TextView clickedView = (TextView) view
						.findViewById(R.id.fighter_name);
				String fighterName = clickedView.getText().toString();
				espnId1 = fighterEspnId.get(fighterName);
				Intent intent = new Intent(FighterSearchActivity.this,
						FighterProfileActivity.class);
				intent.putExtra("espnId1", espnId1);
				startActivity(intent);
			}
		});

		/**
		 * Add Search Filter to List View.
		 */
		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				// When user changed the Text
				FighterSearchActivity.this.listAdapter.getFilter().filter(cs);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
			}
		});

		// we register for the contextmneu
		registerForContextMenu(lv);
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
		Intent intent = new Intent(FighterSearchActivity.this,
				FightEventActivity.class);
		startActivity(intent);
	}
	
	public void fighterSearchMenu()
	{
		
	}
}