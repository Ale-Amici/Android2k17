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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

import it.unitn.disi.lpsmt.idabere.R;
import it.unitn.disi.lpsmt.idabere.activities.AddChoiceActivity;
import it.unitn.disi.lpsmt.idabere.models.Addition;

/**
 * Created by giovanni on 08/05/2017.
 */

public class AdditionListArrayListAdapter extends ArrayAdapter<Addition> {

    private Context mContext;
    private int layoutId;
    private ArrayList<Addition> additions;
    private HashSet<Integer> selectedAdditionsIds;

    public AdditionListArrayListAdapter(Context activityContext, int layout_id, ArrayList<Addition> additions) {
        super(activityContext, layout_id, additions);
        mContext = activityContext;
        layoutId = layout_id;
        this.additions = additions;
        selectedAdditionsIds = new HashSet<>();
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(layoutId,null);

        final CheckBox additionCheckBox = (CheckBox) convertView.findViewById(R.id.addition_check_box);
        additionCheckBox.setText(additions.get(position).getName());

        TextView price = (TextView) convertView.findViewById(R.id.addition_price_label);
        //default price
        price.setText(additions.get(position).getPrice() + "");
        additionCheckBox.setTag(position);
        //View additionChoiceRow = convertView.findViewById(R.id.addition_choice_row);

        additionCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = ((CheckBox)v);
                Toast.makeText( v.getContext(), "Hai cliccato su" + cb.getText().toString(), Toast.LENGTH_LONG  );
                int itemId =additions.get((Integer)v.getTag()).getId();
                if(cb.isChecked()){
                    selectedAdditionsIds.add(itemId);
                }else{
                    selectedAdditionsIds.remove(itemId);
                }
                ((AddChoiceActivity)mContext).updatePreview();

            }
        });

        return convertView;

    }

    public HashSet<Integer> getSelectedAdditionsIds(){
        return selectedAdditionsIds;
    }
}