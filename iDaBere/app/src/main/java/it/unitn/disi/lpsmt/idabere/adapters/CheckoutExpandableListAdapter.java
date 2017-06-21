package it.unitn.disi.lpsmt.idabere.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.models.Addition;
import it.unitn.disi.lpsmt.idabere.models.Order;
import it.unitn.disi.lpsmt.idabere.models.OrderItem;

/**
 * Created by giovanni on 11/06/2017.
 */

public class CheckoutExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private Order mCurrentOrder;
    private HashMap<String, ArrayList<OrderItem>> mMenuForAdapter;//il menu diviso in categorie
    private ArrayList<String> mItemsNames;


    public CheckoutExpandableListAdapter(Context context, Order currentOrder) {
        mContext = context;
        mCurrentOrder = currentOrder;
        setMenuForAdapter(mCurrentOrder);

    }

    private void setMenuForAdapter(Order order) {
        mMenuForAdapter = new HashMap<>();
        this.mItemsNames = new ArrayList<>();

        //INSERISCO LE CATEGORIE ASSEGNATE A CIASCUN ORDER ITEM
        for(OrderItem item: mCurrentOrder.getOrderItems()){
            if(item.getBarMenuItem().getName() == null || item.getBarMenuItem().getName().isEmpty()){
                System.err.println("ERRORE");
                //throw new Exception("ITEM SENZA CATEGORIA"); //TODO decidere come gestire gli errori nell'applicazione
            }
            else{
                if(mMenuForAdapter.get(item.getBarMenuItem().getName()) == null){
                    mMenuForAdapter.put(item.getBarMenuItem().getName(), new ArrayList<OrderItem>());
                    mItemsNames.add(item.getBarMenuItem().getName());
                }
                mMenuForAdapter.get(item.getBarMenuItem().getName()).add(item);
            }

        }

    }

    @Override
    public int getGroupCount() {
        return mItemsNames.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.mMenuForAdapter.get(mItemsNames.get(groupPosition)).size();
    }

    @Override
    public String getGroup(int groupPosition) {
        return mItemsNames.get(groupPosition);
    }

    @Override
    public OrderItem getChild(int groupPosition, int childPosition) {

        ArrayList<OrderItem> barMenuItems = mMenuForAdapter.get(mItemsNames.get(groupPosition));
        OrderItem child = barMenuItems.get(childPosition);
        return child;

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        // List item title
        LayoutInflater inflater;

        String headerTitle = getGroup(groupPosition);

        if (convertView == null) {
           inflater= (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.checkout_list_item_title, null);
        }


        TextView itemNameTV = (TextView) convertView.findViewById(R.id.item_name);
        TextView itemTotalQntyTV = (TextView) convertView.findViewById(R.id.item_total_qnty);
        TextView itemTotalPriceTV = (TextView) convertView.findViewById(R.id.item_total_price);

        itemNameTV.setText(headerTitle);
        itemTotalQntyTV.setText(Integer.toString(mMenuForAdapter.get(headerTitle).size()));

        // Calculate total items for the specified group of items
        ArrayList<OrderItem> orderItems = mMenuForAdapter.get(mItemsNames.get(groupPosition));
        int total = 0;
        for (OrderItem item :orderItems){
            total += item.getQuantity();
        }
        itemTotalQntyTV.setText(Integer.toString(total));
        itemTotalPriceTV.setText(new DecimalFormat("##0.00").format(mCurrentOrder.getTotalPriceByItemName(headerTitle)));

        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);


        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final OrderItem child = (OrderItem) getChild(groupPosition, childPosition);
        LayoutInflater inflater = inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);;

        // Single list item

        if (convertView == null) {
            inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.checkout_list_single_item, null);
        }


        TextView singleItemAdditionsTV = (TextView) convertView.findViewById(R.id.item_additions);
        TextView singleItemSizeTV = (TextView) convertView.findViewById(R.id.item_size);
        TextView singleItemQntyTV = (TextView) convertView.findViewById(R.id.item_quantity);
        TextView singleItemPriceTV = (TextView) convertView.findViewById(R.id.item_price);


        singleItemAdditionsTV.setText(child.getAdditionsAsString());

        singleItemSizeTV.setText(child.getSize().getName());
        singleItemQntyTV.setText(Integer.toString(child.getQuantity()));
        singleItemPriceTV.setText(new DecimalFormat("##0.00").format(child.getSingleItemPrice()*child.getQuantity()));


        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
