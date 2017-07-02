package it.unitn.disi.lpsmt.idabere.adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.activities.AddChoiceActivity;
import it.unitn.disi.lpsmt.idabere.activities.ItemInfoActivity;
import it.unitn.disi.lpsmt.idabere.models.Addition;
import it.unitn.disi.lpsmt.idabere.models.BarMenu;
import it.unitn.disi.lpsmt.idabere.models.BarMenuItem;
import it.unitn.disi.lpsmt.idabere.models.Customer;
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
    protected ArrayList<String> categories;
    protected HashMap<String, ArrayList<BarMenuItem>> menuForAdapter;//il menu diviso in categorie
    private ExpandableListView mExpandableListView;
    //private Button addPreferredButton;
    private MenuFilter menuFilter;
    private TextView totalPriceInfo;
    private int lastItemExpandedGroupPosition;
    private int lastItemExpandedChildPosition;
    private View lastItemExpandedView;
    private boolean randomDrinkPressed;

    static final private int SELECT_NEW_CHOICE_REQUEST = 1;
    //l'ultima categoria espansa
    private int lastCategoryExpandedPosition = -1;

    public MenuCategoryExpandableListAdapter(Context context, BarMenu originalBarMenu, TextView totalPriceInfo, ExpandableListView mExpandableListView) {
        this.context = context;
        this.originalBarMenu = originalBarMenu;
        this.filteredBarMenu = originalBarMenu;
        this.totalPriceInfo = totalPriceInfo;
        this.mExpandableListView = mExpandableListView;
        myAddNewChoiceListener = new MyAddNewChoiceListener();

        setMenuForAdapter(filteredBarMenu);

        randomDrinkPressed = false;

        updateTotalPrice();

        lastItemExpandedGroupPosition = -1;
        lastItemExpandedChildPosition = -1;
        lastItemExpandedView = null;
        //TODO togliere l'animazione di apertura della categoria, cambiare colore alle categorie
    }


    protected void setMenuItemCategories(BarMenu barMenu){
        //INSERISCO LE CATEGORIE ASSEGNATE A CIASCUN MENUITEM
        for(BarMenuItem item: barMenu.getBarMenuItemList()){
            if(item.getCategory() == null || item.getCategory().isEmpty()){
                System.err.println("ERRORE");
                //throw new Exception("ITEM SENZA CATEG ORIA"); //TODO decidere come gestire gli errori nell'applicazione
            }
            else{
                if(menuForAdapter.get(item.getCategory()) == null){
                    menuForAdapter.put(item.getCategory(), new ArrayList<BarMenuItem>());
                    categories.add(item.getCategory());
                }
                menuForAdapter.get(item.getCategory()).add(item);

            }
        }
        Collections.sort(categories);
        for(ArrayList<BarMenuItem> item: menuForAdapter.values()){
            Collections.sort(item);
        }
    }

    protected void setMenuDealsCategory(BarMenu barMenu){
        //TODO INSERISCO LA CATEGORIA DELLE OFFERTE
    }

    protected void setMenuFavouritesCategory(BarMenu barMenu){
        String preferredsCategory = context.getResources().getString(R.string.preferreds_category_name);
        Customer currentCustomer = AppSession.getInstance().getmCustomer();
        menuForAdapter.put(preferredsCategory, new ArrayList<BarMenuItem>());
        if (currentCustomer.getId() != -1 && currentCustomer.getPreferredItems() != null && ! currentCustomer.getPreferredItems().isEmpty() ){
            for(BarMenuItem item: currentCustomer.getPreferredItems()){
                BarMenuItem tmp = barMenu.getBarMenuItemById(item.getId());
                if ( tmp != null){
                    menuForAdapter.get(preferredsCategory).add(tmp);
                }

            }
        }

        if (!menuForAdapter.get(preferredsCategory).isEmpty()){
            categories.add(0,preferredsCategory);
        } else {
            categories.remove(preferredsCategory);
        }

    }

    protected void setMenuForAdapter(BarMenu barMenu) {

        menuForAdapter = new HashMap<>();
        this.categories = new ArrayList<>();
        setMenuItemCategories(barMenu);
        setMenuFavouritesCategory(originalBarMenu);
        //setMenuDealsCategory(barMenu);


    }

    public void refreshMenuAdapter(){
        this.setMenuForAdapter(originalBarMenu);
        notifyDataSetChanged();
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
    public View getChildView(final int groupPosition,final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.menu_list_item, null);
        }
        final View itemView = convertView;
        final BarMenuItem child = (BarMenuItem) getChild(groupPosition, childPosition);
        final RelativeLayout ChoicesSectionLayout = (RelativeLayout)itemView.findViewById(R.id.choices_section_layout);
        final TextView infoText = (TextView) itemView.findViewById(R.id.info_text);
        final TextView itemPriceTv = (TextView) itemView.findViewById(R.id.item_price);
        final View cardInfos =  itemView.findViewById(R.id.item_infos_layout);
        final LinearLayout choicesLinearLayout = (LinearLayout) itemView.findViewById(R.id.choices_linear_layout);
        final Button newChoiceButton = (Button)itemView.findViewById(R.id.new_chioce_button);

        //IMPOSTO LA VISIBILITÀ DELLA SEZIONE DELLE SCELTE IN BASE ALL'ULTIMO ITEM CHE HA SELEZIONATO L'UTENTE
        if(groupPosition == lastItemExpandedGroupPosition && childPosition == lastItemExpandedChildPosition){
            ChoicesSectionLayout.setVisibility(View.VISIBLE);
            lastItemExpandedView = itemView;
        }
        else{
            ChoicesSectionLayout.setVisibility(View.GONE);
        }

        /**INSERISCI DATI NELLA CARD **/
        infoText.setText(child.getName());
        itemPriceTv.setText(new DecimalFormat("##0.00").format(child.getLowestPrice()) );


        /*
        * Open correct Item info when button clicked
        * */
        final ImageButton itemInfoImageButton = (ImageButton) itemView.findViewById(R.id.item_info_button);
        itemInfoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ITEM_CLICKED_ID_KEY = "ITEM_ID";
                Intent itemInfoIntent = new Intent(context, ItemInfoActivity.class);
                itemInfoIntent.putExtra(ITEM_CLICKED_ID_KEY,child.getId());
                context.startActivity(itemInfoIntent);
            }
        });
        //GESTIONE CLICK SU ITEM VIEW, espansione o riduzione della choiceSection. Solo 1 item alla volta è aperto
        cardInfos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //l'ultimo oggetto espanso
                if (ChoicesSectionLayout.isShown()){
                    ChoicesSectionLayout.setVisibility(View.GONE);
                    lastItemExpandedView = null;
                    lastItemExpandedChildPosition = -1;
                    lastItemExpandedGroupPosition = -1;
                } else {
                    if(lastItemExpandedView != null){
                        View lastItemChoiceSection = (lastItemExpandedView).findViewById(R.id.choices_section_layout);
                        lastItemChoiceSection.setVisibility(View.GONE);
                    }
                    lastItemExpandedChildPosition = childPosition;
                    lastItemExpandedGroupPosition = groupPosition;
                    lastItemExpandedView = itemView;
                    ChoicesSectionLayout.setVisibility(View.VISIBLE);
                }
                notifyDataSetChanged();
            }
        });


        //INSERISCO LE SCELTE NEL LINEAR LAYOUT APPOSITO
        insertChoices(choicesLinearLayout, child.getId());

        //IMPOSTO IL BOTTONE NUOVA SCELTA
        newChoiceButton.setTag(child.getId()); //imposto come tag il barMenuItem.getId()
        newChoiceButton.setOnClickListener(myAddNewChoiceListener);

        return itemView;
    }

    public void showItemWithPosition(int groupPosition, int childPosition){
        lastItemExpandedChildPosition = childPosition;
        lastItemExpandedGroupPosition = groupPosition;
        randomDrinkPressed = true;
        notifyDataSetChanged();
    }

    private void insertChoices(final LinearLayout choicesLinearLayout,int barMenuItemId) {
        // Prendo la lista delle scelte, imposto l'adapter e lo aggiungo alla mappa barMenuItemId-ChoicesAdapter
        Order sessionOrder = AppSession.getInstance().getmCustomer().getOrder();
        ArrayList<OrderItem> choices = sessionOrder.getOrderListFromBarMenuItemId(barMenuItemId);//le scelte
        //Log.d("LE SCELTE:", choices.toString());
        LayoutInflater inflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);//prendo l'inflater

        //choicesLinearLayout.removeAllViews();
        while(choicesLinearLayout.getChildCount() > 0){
            choicesLinearLayout.removeViewAt(0);
        }

        if (choices.size() == 0){
            choicesLinearLayout.requestLayout();

        }

        //AGGIUNGO TUTTI GLI ELEMENTI DELL'ORDINE
        for(final OrderItem orderItem: choices){
            final View newChoiceView = inflater.inflate(R.layout.menu_choice_item, null); //faccio l'inflate del layout della nuova scelta
            final Button removeChoiceBT = (Button) newChoiceView.findViewById(R.id.remove_choice_button);
            final TextView choiceDescriptionTV = (TextView) newChoiceView.findViewById(R.id.choice_description);
            final TextView choiceDimensionDescriptionTV = (TextView) newChoiceView.findViewById(R.id.choices_size_description);
            final TextView choiceSinglePriceTV = (TextView) newChoiceView.findViewById(R.id.choice_single_price);
            final TextView choiceQuantityTV = (TextView) newChoiceView.findViewById(R.id.choice_quantity);
            final ImageButton plusIB = (ImageButton) newChoiceView.findViewById(R.id.plus_button);
            final ImageButton minusIB = (ImageButton) newChoiceView.findViewById(R.id.minus_button);

            // LA STRINGA DELLE ADDITION
            String additionString = "";
            int i = 0;
            for(Addition a: orderItem.getAdditions()){
                if(i ++ == 0){
                    additionString += a.getName() ;
                }
                else{
                    additionString += ", " + a.getName() ;
                }

            }

            // TODO inserire nel DB il valore --Nessuna Scelta--
            if (additionString.equals("")){
                additionString = context.getResources().getString(R.string.no_choice_description);
            }

            //INSERIMENTO DATI NELLA CHIOCE VIEW
            choiceDescriptionTV.setText(additionString);
            choiceDimensionDescriptionTV.setText(orderItem.getSize().getName());
            choiceSinglePriceTV.setText(new DecimalFormat("##0.00").format(orderItem.getSingleItemPrice()));
            choiceQuantityTV.setText(orderItem.getQuantity() + "");

            //AGGIUNTA DEI LISTENERS PER I BOTTONI + E -

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

            //AGGIUNGO LA CHOICE VIEW NEL LINEAR LAYOUT
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
        totalPriceInfo.setText(new DecimalFormat("##0.00").format(price));
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

    @TargetApi(Build.VERSION_CODES.M)
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
        // TODO LOGICA DI ESPANSIONE DELLA CATEGORIA DELL'ITEM SELEZIONATO
        if(lastItemExpandedChildPosition != -1 && lastItemExpandedGroupPosition != -1){
            BarMenuItem child = (BarMenuItem) getChild(lastItemExpandedGroupPosition, lastItemExpandedChildPosition);
            if(child.getCategory().equals(getGroup(groupPosition))){
                if(!isExpanded && randomDrinkPressed ) {
                    mExpandableListView.expandGroup(groupPosition);
                }
                randomDrinkPressed = false;
            }
        }

        int groupBgColor = context.getResources().getColor(R.color.colorSecondaryLight,null);
        if (headerTitle.equals(context.getResources().getString(R.string.preferreds_category_name))){
            convertView.findViewById(R.id.bookmark_preferreds_icon).setVisibility(View.VISIBLE);
            groupBgColor = context.getResources().getColor(R.color.preferred_category_color,null);
        } else {
            convertView.findViewById(R.id.bookmark_preferreds_icon).setVisibility(View.GONE);
        }

        convertView.setBackgroundColor(groupBgColor);


        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        if (lastCategoryExpandedPosition != -1
                && groupPosition != lastCategoryExpandedPosition) {
            mExpandableListView.collapseGroup(lastCategoryExpandedPosition);
        }
        lastCategoryExpandedPosition = groupPosition;
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
            newChoiceIntent.putExtra("barMenuItemId", (Integer) v.getTag());
            //prendo l'id del bar menu item dal tag sul bottone
            newChoiceIntent.setClass(context, AddChoiceActivity.class);
            ((Activity)context).startActivityForResult(newChoiceIntent, SELECT_NEW_CHOICE_REQUEST);

        }
    }
}
