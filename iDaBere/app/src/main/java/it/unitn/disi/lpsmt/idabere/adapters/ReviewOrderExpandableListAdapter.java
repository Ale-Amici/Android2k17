package it.unitn.disi.lpsmt.idabere.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.models.BarMenu;

/**
 * Created by giovanni on 06/05/2017.
 */

public class ReviewOrderExpandableListAdapter extends MenuCategoryExpandableListAdapter {


    public ReviewOrderExpandableListAdapter(Context context, BarMenu originalBarMenu, TextView totalPriceInfo, ExpandableListView mExpandableListView) {
        super(context, originalBarMenu, totalPriceInfo, mExpandableListView);
    }


}
