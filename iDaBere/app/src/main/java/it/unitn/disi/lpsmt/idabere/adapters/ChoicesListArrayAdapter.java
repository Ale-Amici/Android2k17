package it.unitn.disi.lpsmt.idabere.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import it.unitn.disi.lpsmt.idabere.models.Order;
import it.unitn.disi.lpsmt.idabere.models.OrderItem;

/**
 * Created by ale on 31/05/17.
 */

public class ChoicesListArrayAdapter  extends ArrayAdapter<OrderItem> {

    private Context mContext;
    ArrayList<OrderItem> mOrderItems;
    int layoutId;

    public ChoicesListArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<OrderItem> mOrderItems) {
        super(context, resource, mOrderItems);
        this.mOrderItems = mOrderItems;
        layoutId = resource;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View choiceView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        choiceView = layoutInflater.inflate(layoutId,null);
        //TODO SET THE NAME, PRICE, COUNTER to the layout
        //TODO SET THE LISTENERS TO THE + AND - BUTTONS

        OrderItem orderItem = mOrderItems.get(position);
    }
}
