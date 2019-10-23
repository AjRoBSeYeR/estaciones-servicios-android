package android.training.demoapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;

/**
 * A simple {@link Fragment} subclass.
 */
public class AjustesFragment extends PreferenceFragmentCompat {


    public AjustesFragment( ) {

    }

    @Override
    public void
    onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.ajustes, rootKey);
        //addPreferencesFromResource(R.xml.ajustes);
    }





}