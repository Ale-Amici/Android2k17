package it.unitn.disi.lpsmt.idabere.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import it.unitn.disi.lpsmt.idabere.R;

public class QrCodeScannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_scanner);

        // Show arrow back action button

        // Handle intent received from Search bar activity

        // TODO Inserire i metodi per restituire ad searchBarActivity il risultato della scan tramite intent
        // TODO Aggiungere la libreria per leggere i QR codes https://code.tutsplus.com/tutorials/android-sdk-create-a-barcode-reader--mobile-17162

    }
}
