package it.unitn.disi.lpsmt.idabere.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import it.unitn.disi.lpsmt.idabere.R;

/**
 * Created by giovanni on 08/05/2017.
 */

public class AdditionListArrayListAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private int layoutId;
    private ArrayList<String> data;

    public AdditionListArrayListAdapter(Context activityContext, int layout_id, ArrayList<String> sizesList) {
        super(activityContext, layout_id, sizesList);
        mContext = activityContext;
        layoutId = layout_id;
        data = sizesList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(layoutId,null);

        CheckBox sizeItem = (CheckBox) convertView.findViewById(R.id.addition_check_box);
        sizeItem.setText(data.get(position));

        TextView price = (TextView) convertView.findViewById(R.id.addition_price_label);
        //default price
        price.setText(R.string.menu_list_item_price);

        return convertView;

    }
}