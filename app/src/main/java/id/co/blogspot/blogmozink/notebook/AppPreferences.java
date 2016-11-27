package id.co.blogspot.blogmozink.notebook;

import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AppPreferences extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_note_detail);
        android.app.FragmentManager fragmentManager  = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SettingsFragments settingsFragments = new SettingsFragments();
        fragmentTransaction.add(android.R.id.content,settingsFragments,"SETTINGS_FRAGMENT");
        fragmentTransaction.commit();
    }
    public static class SettingsFragments extends PreferenceFragment{
        @Override
        public void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.app_preferences);
        }
    }
}
