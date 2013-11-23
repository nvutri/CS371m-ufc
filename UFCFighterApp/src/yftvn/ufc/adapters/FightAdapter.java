package yftvn.ufc.adapters;

import java.util.ArrayList;

import yftvn.ufc.R;
import yftvn.ufc.models.FightEvent;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FightAdapter extends ArrayAdapter<FightEvent>
{
	Context context; 
    int layoutResourceId;    
    ArrayList<FightEvent> data = null;
    
    public FightAdapter(Context context, int layoutResourceId, ArrayList<FightEvent> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        FightHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new FightHolder();
            holder.left = (TextView)row.findViewById(R.id.left);
            holder.right = (TextView)row.findViewById(R.id.right);
            
            row.setTag(holder);
        }
        else
        {
            holder = (FightHolder)row.getTag();
        }
        
        FightEvent fightEvent = data.get(position);
        holder.left.setText(fightEvent.getLeft());
        holder.right.setText(fightEvent.getRight());
        
        return row;
    }
    
    static class FightHolder
    {
        TextView left;
        TextView right;
    }

}
