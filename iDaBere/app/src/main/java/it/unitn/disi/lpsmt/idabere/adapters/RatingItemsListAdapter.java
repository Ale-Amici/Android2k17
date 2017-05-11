package it.unitn.disi.lpsmt.idabere.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import it.unitn.disi.lpsmt.idabere.R;

/**
 * Created by giovanni on 10/05/2017.
 */

public class RatingItemsListAdapter extends ArrayAdapter {

    private List<String> mDataset;
    private Context mContext;
    private int layoutId;


    // Provide a suitable constructor (depends on the kind of dataset)
    public RatingItemsListAdapter(Context activityContext, int layout_id, ArrayList<String> itemList) {
        super(activityContext, layout_id, itemList);
        mContext = activityContext;
        layoutId = layout_id;
        mDataset = itemList;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return mDataset.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(layoutId,null);

        TextView itemName = (TextView) convertView.findViewById(R.id.item_name);
        itemName.setText(mDataset.get(position));
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

}

