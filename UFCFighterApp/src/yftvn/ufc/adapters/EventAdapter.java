package yftvn.ufc.adapters;

import java.util.ArrayList;

import yftvn.ufc.R;
import yftvn.ufc.models.UFCEvent;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EventAdapter extends ArrayAdapter<UFCEvent> 
{
	private Context context;
	private int layoutResourceId;
	private ArrayList<UFCEvent> ufcEvents;

	public EventAdapter(Context context, int layoutResourceId, ArrayList<UFCEvent> ufcEvents) 
	{
		super(context, layoutResourceId, ufcEvents);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.ufcEvents = ufcEvents;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		EventHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new EventHolder();
			holder.eventName = (TextView) row.findViewById(R.id.event_name);

			row.setTag(holder);
		} else {
			holder = (EventHolder) row.getTag();
		}
		// Populate ListView with fighter names.
		UFCEvent ufcEvent = ufcEvents.get(position);
		holder.eventName.setText(ufcEvent.getTitle());
		return row;
	}

	static class EventHolder {
		TextView eventName;
	}

}
