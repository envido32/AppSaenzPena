package com.electiva.envido32.appsaenzpena;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class ConfigurationFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

    }
}
