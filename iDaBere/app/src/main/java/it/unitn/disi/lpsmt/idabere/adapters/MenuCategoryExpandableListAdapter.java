package it.unitn.disi.lpsmt.idabere.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.models.Bar;

/**
 * Created by giovanni on 06/05/2017.
 */

public class MenuCategoryExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;

    public MenuCategoryExpandableListAdapter(Context context, Bar bar) {
        this.context = context;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
//        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
//                .get(childPosititon);
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.menu_list_item,null);
        }



        /*
        * Expand/Collapse card item when clicked
        */

        final View cardInfos =  convertView.findViewById(R.id.item_infos_layout);
        final View toppingsSectionLayout = convertView.findViewById(R.id.toppings_section_layout);


        cardInfos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toppingsSectionLayout.isShown()){
                    toppingsSectionLayout.setVisibility(View.GONE);
                } else {
                    toppingsSectionLayout.setVisibility(View.VISIBLE);
                }

            }
        });


        return convertView;
}

    @Override
    public int getChildrenCount(int groupPosition) {
//        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
//                .size();
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
//        return this._listDataHeader.get(groupPosition);
        return null;
    }

    @Override
    public int getGroupCount() {

//        return this._listDataHeader.size();
        return 0;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.menu_list_category, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.category_text_name);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    
}
