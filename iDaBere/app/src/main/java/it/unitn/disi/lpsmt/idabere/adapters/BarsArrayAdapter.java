package it.unitn.disi.lpsmt.idabere.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import it.unitn.disi.lpsmt.idabere.Models.Bar;
import it.unitn.disi.lpsmt.idabere.R;

import java.util.ArrayList;

/**
 * Created by ale on 20/05/17.
 */

public class BarsArrayAdapter extends ArrayAdapter<Bar> implements Filterable {
    private ArrayList<Bar> barsList;
    private ArrayList<Bar> filteredBarsList;
    private Context context;
    private int rowLayout;
    private BarFilter barFilter;

    public BarsArrayAdapter(@NonNull Context context, @LayoutRes int layoutId, @NonNull ArrayList<Bar> bars) {
        super(context, layoutId, bars);
        this.context = context;
        barsList = bars;
        filteredBarsList = bars;
        rowLayout = layoutId;

        getFilter();
    }


    /**
     * Get size of user list
     * @return userList size
     */
    @Override
    public int getCount() {
        return filteredBarsList.size();
    }


    @Nullable
    @Override
    public Bar getItem(int position) {
        return filteredBarsList.get(position);
    }

    /**
     * Get user list item id
     * @param i item index
     * @return current item id
     */
    @Override
    public long getItemId(int i) {
        return i;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = vi.inflate(rowLayout, null);
        Bar bar = filteredBarsList.get(position);
        TextView t = (TextView) convertView.findViewById(R.id.bar_text_view);
        t.setText(bar.getName());
        return convertView;
    }



    @Override
    public Filter getFilter() {
        if (barFilter == null) {
            barFilter = new BarFilter();
        }

        return barFilter;
    }

    private class BarFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<Bar> tempList = new ArrayList<>();
                // search content in friend list
                for (Bar bar: barsList) {
                    if (bar.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(bar);
                    }
                }
                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = barsList.size();
                filterResults.values = barsList;
            }

            return filterResults;
        }

        /**
         * Notify about filtered list to ui
         * @param constraint text
         * @param results filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredBarsList = (ArrayList<Bar>) results.values;
            notifyDataSetChanged();
        }
    }
}
