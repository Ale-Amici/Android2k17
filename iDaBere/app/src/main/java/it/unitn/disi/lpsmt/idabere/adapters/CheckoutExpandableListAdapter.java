package it.unitn.disi.lpsmt.idabere.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.models.BarMenu;
import it.unitn.disi.lpsmt.idabere.models.BarMenuItem;
import it.unitn.disi.lpsmt.idabere.models.Order;

/**
 * Created by giovanni on 11/06/2017.
 */

public class CheckoutExpandableListAdapter extends MenuCategoryExpandableListAdapter {

    private Order currentOrder;

    public CheckoutExpandableListAdapter(Context context, BarMenu originalBarMenu, TextView totalPriceInfo, Order order) {
        super(context, originalBarMenu, totalPriceInfo);
        currentOrder = order;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return super.getChild(groupPosition, childPosititon);
    }



}
