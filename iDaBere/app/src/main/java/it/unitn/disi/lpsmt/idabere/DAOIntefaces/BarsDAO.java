package it.unitn.disi.lpsmt.idabere.DAOIntefaces;

import android.location.Address;

import java.util.ArrayList;

import it.unitn.disi.lpsmt.idabere.Models.Bar;

/**
 * Created by giovanni on 14/05/2017.
 */

public interface BarsDAO {

    public ArrayList<Bar> getBarsByCoordinates (Address address);
    public ArrayList<Bar> getAllBars ();
    public Bar getBarByName (String name);

}
