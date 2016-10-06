package ru.ageev_victor.kupiedi.Activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import ru.ageev_victor.kupiedi.R;

public class SettingsActivity extends PreferenceActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }




}
