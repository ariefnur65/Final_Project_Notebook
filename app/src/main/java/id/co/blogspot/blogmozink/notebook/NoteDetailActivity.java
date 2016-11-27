package id.co.blogspot.blogmozink.notebook;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NoteDetailActivity extends AppCompatActivity {

    public static final String NEW_NOTE_EXTRA = "New Note ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        createAndAddFragment();
    }

    private  void createAndAddFragment() {
        Intent intent = getIntent();
        MainActivity.FragmentToLaunch fragmentToLaunch = (MainActivity.FragmentToLaunch)
                intent.getSerializableExtra(MainActivity.NOTE_TO_LOAD_EXTRA);

        FragmentManager fragmentManager = getSupportFragmentManager();
        //Fragment transaction pakenya support.v4.app.fragmenttransaction
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        switch (fragmentToLaunch){
            case EDIT:
                setTitle(R.string.edit_fragment_title );
                NoteEditFragment noteEditFragment = new NoteEditFragment();
                fragmentTransaction.add(R.id.note_container, noteEditFragment,"NOTE_EDIT_FRAGMENT");
                break;
            case VIEW:
                setTitle(R.string.view_fragment_title);
                NoteViewFragment noteViewFragment = new NoteViewFragment();
                fragmentTransaction.add(R.id.note_container, noteViewFragment,"NOTE_VIEW_FRAGMENT");
                break;
            case CREATE:
                NoteEditFragment noteCreateFragment = new NoteEditFragment();
                setTitle(R.string.create_fragment_title);
                Bundle bundle = new Bundle();
                bundle.putBoolean(NEW_NOTE_EXTRA,true);
                noteCreateFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.note_container, noteCreateFragment,"NOTE_CREATE_FRAGMENT");
                break;
        }


        fragmentTransaction.commit();
    }
}
