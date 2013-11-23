package yftvn.ufc.activities;

import java.util.HashMap;

import yftvn.ufc.R;
import yftvn.ufc.data.FighterBasicData;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
				int espnId = fighterEspnId.get(fighterName);
				Intent intent = new Intent(FighterSearchActivity.this,
						FighterProfileActivity.class);
				intent.putExtra("espnId", espnId);
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
}