package it.unitn.disi.lpsmt.idabere.adapters;

import android.app.Activity;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.google.android.gms.vision.text.Text;

import java.util.ArrayList;
import java.util.HashMap;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.activities.AddChoiceActivity;
import it.unitn.disi.lpsmt.idabere.activities.ItemInfoActivity;
import it.unitn.disi.lpsmt.idabere.activities.MenuActivity;
import it.unitn.disi.lpsmt.idabere.models.Addition;
import it.unitn.disi.lpsmt.idabere.models.BarMenu;
import it.unitn.disi.lpsmt.idabere.models.BarMenuItem;
import it.unitn.disi.lpsmt.idabere.models.Order;
import it.unitn.disi.lpsmt.idabere.models.OrderItem;
import it.unitn.disi.lpsmt.idabere.session.AppSession;

/**
 * Created by giovanni on 06/05/2017.
 */

public class MenuCategoryExpandableListAdapter extends BaseExpandableListAdapter implements Filterable {

    private Context context;
    private MyAddNewChoiceListener myAddNewChoiceListener;
    private BarMenu originalBarMenu;
    private BarMenu filteredBarMenu;
    private ArrayList<String> categories;
    private HashMap<String, ArrayList<BarMenuItem>> menuForAdapter;//il menu diviso in categorie
    //private Button addPreferredButton;
    private MenuFilter menuFilter;
    private TextView totalPriceInfo;


    static final private int SELECT_NEW_CHOICE_REQUEST = 1;

    public MenuCategoryExpandableListAdapter(Context context, BarMenu originalBarMenu, TextView totalPriceInfo) {
        this.context = context;
        this.originalBarMenu = originalBarMenu;
        this.filteredBarMenu = originalBarMenu;
        this.totalPriceInfo = totalPriceInfo;
        myAddNewChoiceListener = new MyAddNewChoiceListener();
        setMenuForAdapter(filteredBarMenu);
        updateTotalPrice();
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
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.menu_list_item, null);
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


        View cardInfos =  convertView.findViewById(R.id.item_infos_layout);
        View ChoicesSectionLayout = convertView.findViewById(R.id.choices_section_layout);


        cardInfos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View choicesSection = ((View)(view.getParent())).findViewById(R.id.choices_section_layout);

                if (choicesSection.isShown()){
                    choicesSection.setVisibility(View.GONE);
                } else {
                    choicesSection.setVisibility(View.VISIBLE);
                }

            }
        });


        // Prendo la lista delle scelte, imposto l'adapter e lo aggiungo alla mappa barMenuItemId-ChoicesAdapter
        Order sessionOrder = AppSession.getInstance().getmCustomer().getOrder();
        ArrayList<OrderItem> choices = sessionOrder.getOrderListFromBarMenuItemId(child.getId());//le scelte
        Log.d("LE SCELTE:", choices.toString());
        LinearLayout choicesLinearLayout = (LinearLayout) convertView.findViewById(R.id.choices_linear_layout);//il linear layout delle scelte
        //choicesLinearLayout.setAdapter(new ChoicesListArrayAdapter(convertView.getContext(), R.layout.menu_choice_item, choices));//impost l'adapter per la list view delle scelte
        //menuOrderMap.put(child.getId(),(ChoicesListArrayAdapter) choicesLinearLayout.getAdapter());//aggiungo l'adapter alla mappa per poi aggiornarla se cambia la lista degli ordini

        //il bottone "Nuova Scelta"
        insertChoices(choicesLinearLayout, choices);
        Button newChoiceButton = (Button)convertView.findViewById(R.id.new_chioce_button);
        newChoiceButton.setTag(child.getId()); //imposto come tag il barMenuItem.getId()
        newChoiceButton.setOnClickListener(myAddNewChoiceListener);
        return convertView;
    }

    private void insertChoices(final LinearLayout choicesLinearLayout, final ArrayList<OrderItem> choices) {
        LayoutInflater inflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);//prendo l'inflater

        choicesLinearLayout.removeAllViews();// TODO ora distruggo tutto, invece conviene aggiungere solo l'item necessario
        choicesLinearLayout.invalidate();
        for(final OrderItem orderItem: choices){
            final View newChoiceView = inflater.inflate(R.layout.menu_choice_item, null); //faccio l'inflate del layout della nuova scelta

            final Button removeChoiceBT = (Button) newChoiceView.findViewById(R.id.remove_choice_button);

            // INSERISCO I DATI NELLA NUOVA VIEW
            TextView choiceDescriptionTV = (TextView) newChoiceView.findViewById(R.id.choice_description);
            TextView choiceDimensionDescriptionTV = (TextView) newChoiceView.findViewById(R.id.choices_size_description);
            TextView choiceSinglePriceTV = (TextView) newChoiceView.findViewById(R.id.choice_single_price);
            TextView choiceQuantityTV = (TextView) newChoiceView.findViewById(R.id.choice_quantity);

            String description = "";
            for(Addition a: orderItem.getAdditions()){
                description += a.getName() + ", ";
            }

            // TODO inserire nel DB il valore --Nessuna Scelta--
            if (description.equals("")){
                description = context.getResources().getString(R.string.no_choice_description);
            }

            choiceDescriptionTV.setText(description);
            choiceDimensionDescriptionTV.setText(orderItem.getSize().getName());
            choiceSinglePriceTV.setText(orderItem.getSingleItemPrice() + context.getResources().getString(R.string.menu_list_item_currency));
            choiceQuantityTV.setText(orderItem.getQuantity() + "");

            //AGGIUNTA DEI LISTENERS PER I BOTTONI + E -
            ImageButton plusIB = (ImageButton) newChoiceView.findViewById(R.id.plus_button);
            ImageButton minusIB = (ImageButton) newChoiceView.findViewById(R.id.minus_button);

            plusIB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderItem.setQuantity(orderItem.getQuantity() + 1);
                    notifyDataSetChanged();
                }
            });

            minusIB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // CONTROLLO SE SIA PRESENTE UNA SOLA QUANTITA', QUINDI RIMUOVO L'ITEM
                    if (orderItem.getQuantity() > 1) {
                        orderItem.setQuantity(orderItem.getQuantity() - 1);
                    } else {
                        removeChoice(orderItem, choicesLinearLayout, newChoiceView);
                    }
                    notifyDataSetChanged();
                }
            });

            // AGGIUNGO LA SWIPE GESTURE PER ELIMINARE LA SCELTA PER INTERO
            ((SwipeLayout) newChoiceView).setShowMode(SwipeLayout.ShowMode.PullOut);

            removeChoiceBT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeChoice(orderItem, choicesLinearLayout, newChoiceView);
                    notifyDataSetChanged();
                }
            });

            //AGGIUNGO LA VIEW NEL LINEAR LAYOUT
            choicesLinearLayout.addView(newChoiceView);

        }
    }

    private void removeChoice(OrderItem orderItem, LinearLayout choicesLinearLayout, View newChoiceView ) {

            choicesLinearLayout.removeView(newChoiceView);
            int removedItemIndex = AppSession.getInstance().getmCustomer().getOrder().removeExistentOrderItem(orderItem);
            if (removedItemIndex != -1) {
                Toast.makeText(context,"Scelta rimossa",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context,"Scelta NON rimossa",Toast.LENGTH_SHORT).show();
            }

    }

    public void updateTotalPrice () {
        AppSession.getInstance().getmCustomer().getOrder().calculateTotalPrice();
        double price = AppSession.getInstance().getmCustomer().getOrder().getTotalPrice();
        Log.d("PRICE", "updateTotalPrice: "+Double.toString(price));
        totalPriceInfo.setText(Double.toString(price));
    }

    @Override
    public void notifyDataSetChanged() {
        updateTotalPrice();
        super.notifyDataSetChanged();
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

    private class MyAddNewChoiceListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent newChoiceIntent = new Intent();
            newChoiceIntent.putExtra("barMenuItemId", (Integer) v.getTag());//prendo l'id del bar menu item dal tag sul bottone
            newChoiceIntent.setClass(context, AddChoiceActivity.class);
            ((Activity)context).startActivityForResult(newChoiceIntent, SELECT_NEW_CHOICE_REQUEST);

        }
    }
}
