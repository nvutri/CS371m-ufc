package yftvn.ufc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.Parse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;


public class FighterSearchActivity extends Activity 
{
	SimpleAdapter simpleAdpt;
	List<Map<String, Integer>> fighterList;
	
	public static boolean isConnected;
	private static final String PARSE_APPLICATION_ID = "AJ0JAEbsMNs3pRi9poiROGLxopvwD9Y44aXs8rkz";
	private static final String PARSE_CLIENT_KEY = "ia1k06D9lHgWncELjHm49xsrbREVWUCn7flMc0ic";

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.fighter_search);
	    
	    ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		isConnected = activeNetwork != null
				&& activeNetwork.isConnectedOrConnecting();
		if (isConnected) {
			// 1. Init Parse Connection.
			Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);
			
			FighterBasicData.initialize();
		    while(!FighterBasicData.isReady())
		    {
		    	
		    }
		    fighterList = FighterBasicData.getBasicData();
		} else {
			// TODO(nvutri): displayNetworkAlert(getApplicationContext());
		}

	    // We get the ListView component from the layout
	    ListView lv = (ListView) findViewById(R.id.listView);

	    // This is a simple adapter that accepts as parameter
	    // Context
	    // Data list
	    // The row layout that is used during the row creation
	    // The keys used to retrieve the data
	    // The View id used to show the data. The key number and the view id must match
	    simpleAdpt = new SimpleAdapter(this, fighterList, android.R.layout.simple_list_item_1, new String[] {"planet"}, new int[] {android.R.id.text1});

	    lv.setAdapter(simpleAdpt);
	    
	    // React to user clicks on item
	    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	         public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
	                                 long id) {

	             // We know the View is a TextView so we can cast it
	             TextView clickedView = (TextView) view;

	             Toast.makeText(FighterSearchActivity.this, "Item with id ["+id+"] - Position ["+position+"] - Planet ["+clickedView.getText()+"]", Toast.LENGTH_SHORT).show();

	         }
	    });
	    
	    // we register for the contextmneu        
	    registerForContextMenu(lv);
	    
	    
	}
	
	// This method is called when user selects an Item in the Context menu
	@Override
	public boolean onContextItemSelected(MenuItem item) 
	{
		Intent goToNextActivity = new Intent(getApplicationContext(), FighterProfileActivity.class);
		startActivity(goToNextActivity);
	    return true;
	}
	
	// We want to create a context Menu when the user long click on an item
	  @Override
	  public void onCreateContextMenu(ContextMenu menu, View v,
	          ContextMenuInfo menuInfo) {

	      super.onCreateContextMenu(menu, v, menuInfo);
	      AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) menuInfo;

	      // We know that each row in the adapter is a Map
	      HashMap map =  (HashMap) simpleAdpt.getItem(aInfo.position);

	      menu.setHeaderTitle("Options for " + map.get("planet"));
	      menu.add(1, 1, 1, "Details");
	      menu.add(1, 2, 2, "Delete");

	  }
}