package yftvn.ufc;

import java.util.ArrayList;
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
	private ListView listViewE;
	private ArrayList<Fight> event;

	private static HashMap<String, Integer> fighterEspnId;
	private static String[] fighterNames;
	private ArrayAdapter<String> listAdapter;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event);
		
		populateEvent();
		
		FightAdapter adapter = new FightAdapter(this, R.layout.event_row, event);
		listViewE = (ListView) findViewById(R.id.listViewE);
		
		View header = (View)getLayoutInflater().inflate(R.layout.event_header, null);
        listViewE.addHeaderView(header);
        listViewE.setAdapter(adapter);
        
        
        //NOT CORRECT - the clicked textview has 2 text it needs to track, the left and right fighter
        listViewE.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentAdapter, View view,
					int position, long id) {
				TextView clickedViewL = (TextView) view.findViewById(R.id.left);
				TextView clickedViewR = (TextView) view.findViewById(R.id.right);
				String fighterNameL = clickedViewL.getText().toString();
				String fighterNameR = clickedViewR.getText().toString();
				int espnId1 = fighterEspnId.get(fighterNameL);
				int espnId2 = fighterEspnId.get(fighterNameR);
				Intent intent = new Intent(EventActivity.this,
						ComparisonProfileActivity.class);
				intent.putExtra("espnId1", espnId1);
				intent.putExtra("espnId2", espnId2);
				startActivity(intent);
			}
		});

		fighterEspnId = FighterBasicData.getEspnId();
		fighterNames = FighterBasicData.getFighterNames();

	}
	
	/**
	 * Populate the fight array with some test data. Remove after connecting the app with the backend.
	 */
	
	private void populateEvent()
	{
		event = new ArrayList<Fight>();
		Fight one = new Fight("Anderson Silva", "Anthony Pettis");
		Fight two = new Fight("Anthony Pettis", "Ali Bagautinov");
		Fight three = new Fight("Chael Sonnen", "Anderson Silva");
		event.add(one);
		event.add(two);
		event.add(three);
	}
	
}
