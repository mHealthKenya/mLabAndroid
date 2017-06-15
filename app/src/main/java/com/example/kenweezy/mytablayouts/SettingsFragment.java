package com.example.kenweezy.mytablayouts;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by KENWEEZY on 2017-01-15.
 */

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.mysettings);
    }
}
