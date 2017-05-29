package it.unitn.disi.lpsmt.idabere.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

import it.unitn.disi.lpsmt.idabere.models.BarCounter;
import it.unitn.disi.lpsmt.idabere.models.Table;

/**
 * Created by giovanni on 29/05/2017.
 */

public class CountersArrayAdapter extends ArrayAdapter<BarCounter> {

    public CountersArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<BarCounter> objects) {
        super(context, resource, objects);
    }


}
