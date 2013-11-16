package yftvn.ufc;

import java.util.HashMap;

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

public class EventActivity extends Activity 
{

	private static HashMap<String, Integer> fighterEspnId;
	private static String[] fighterNames;
	private ArrayAdapter<String> listAdapter;


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
				int espnId = fighterEspnId.get(fighterName);
				Intent intent = new Intent(FighterSearchActivity.this,
						FighterProfileActivity.class);
				intent.putExtra("espnId", espnId);
				startActivity(intent);
			}
		});
	}

}
