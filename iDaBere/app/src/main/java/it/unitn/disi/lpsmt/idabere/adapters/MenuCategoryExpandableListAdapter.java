package it.unitn.disi.lpsmt.idabere.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.activities.AddChoiceActivity;
import it.unitn.disi.lpsmt.idabere.activities.ItemInfoActivity;
import it.unitn.disi.lpsmt.idabere.activities.MenuActivity;
import it.unitn.disi.lpsmt.idabere.models.BarMenu;
import it.unitn.disi.lpsmt.idabere.models.BarMenuItem;

/**
 * Created by giovanni on 06/05/2017.
 */

public class MenuCategoryExpandableListAdapter extends BaseExpandableListAdapter implements Filterable {

    private Context context;
    private BarMenu originalBarMenu;
    private BarMenu filteredBarMenu;
    private ArrayList<String> categories;
    private Button addPreferredButton;
    private HashMap<String, ArrayList<BarMenuItem>> menuForAdapter;
    private MenuFilter menuFilter;

    public MenuCategoryExpandableListAdapter(Context context, BarMenu originalBarMenu) {
        this.context = context;
        this.originalBarMenu = originalBarMenu;
        this.filteredBarMenu = originalBarMenu;
        setMenuForAdapter(filteredBarMenu);


    }

    private void setMenuForAdapter(BarMenu barMenu) {
        menuForAdapter = new HashMap<>();
        this.categories = new ArrayList<>();

        //INSERISCO LE CATEGORIE ASSEGNATE A CIASCUN MENUITEM
        for(BarMenuItem item: barMenu.getBarMenuItemList()){
            if(item.getCategory() == null || item.getCategory().isEmpty()){
                System.err.println("ERRORE");
                //throw new Exception("ITEM SENZA CATEGORIA"); //TODO decidere come gestire gli errori nell'applicazione
            }
            else{
                if(menuForAdapter.get(item.getCategory()) == null){
                    menuForAdapter.put(item.getCategory(), new ArrayList<BarMenuItem>());
                    categories.add(item.getCategory());
                }
                menuForAdapter.get(item.getCategory()).add(item);
            }

        }


        //TODO INSERISCO LA CATEGORIA DELLE OFFERTE

        //TODO INSERISCO LA CATEGORIA DEI PREFERITI
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        ArrayList<BarMenuItem> barMenuItems = menuForAdapter.get(categories.get(groupPosition));
        BarMenuItem child = barMenuItems.get(childPosititon);
        return child;
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
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final BarMenuItem child = (BarMenuItem) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.menu_list_item,null);
        }

        /**INSERISCI DATI NELLA CARD **/
        TextView infoText = (TextView) convertView.findViewById(R.id.info_text);

        infoText.setText(child.getName());

        /*
        * Open correct Item info when button clicked
        * */
        final ImageButton itemInfoImageButton = (ImageButton) convertView.findViewById(R.id.item_info_button);
        itemInfoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ITEM_CLICKED_ID_KEY = "ITEM_ID";
                Intent itemInfoIntend = new Intent(context, ItemInfoActivity.class);
                itemInfoIntend.putExtra(ITEM_CLICKED_ID_KEY,child.getId());
                context.startActivity(itemInfoIntend);
            }
        });

        /*
        * Expand/Collapse card item when clicked
        */


        final View cardInfos =  convertView.findViewById(R.id.item_infos_layout);
        final View ChoicesSectionLayout = convertView.findViewById(R.id.choices_section_layout);


        cardInfos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ChoicesSectionLayout.isShown()){
                    ChoicesSectionLayout.setVisibility(View.GONE);
                } else {
                    ChoicesSectionLayout.setVisibility(View.VISIBLE);
                }

            }
        });

        //il bottone "Nuova Scelta"
        final View newChoiceButton = convertView.findViewById(R.id.new_chioce_button);
        newChoiceButton.setTag(child.getId()); //imposto come tag il barMenuItem.getId()

        return convertView;
}

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.menuForAdapter.get(categories.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return categories.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return categories.size();
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
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public Filter getFilter() {
        if (menuFilter == null) {
            menuFilter = new MenuFilter();
        }

        return menuFilter;
    }

    private class MenuFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                BarMenu tempBarMenu = new BarMenu();
                // search content in friend list
                for (BarMenuItem item: originalBarMenu.getBarMenuItemList()) {
                    if(item.getName().toLowerCase().contains(constraint.toString().toLowerCase())){
                        tempBarMenu.getBarMenuItemList().add(item);
                    }
                }

                filterResults.count = tempBarMenu.getBarMenuItemList().size();
                filterResults.values = tempBarMenu;
            } else {
                filterResults.count = originalBarMenu.getBarMenuItemList().size();
                filterResults.values = originalBarMenu;
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
            filteredBarMenu = (BarMenu) results.values;
            setMenuForAdapter(filteredBarMenu);
            notifyDataSetChanged();
        }
    }
}
