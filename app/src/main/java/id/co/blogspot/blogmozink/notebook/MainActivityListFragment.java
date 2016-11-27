package id.co.blogspot.blogmozink.notebook;


import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivityListFragment extends ListFragment {

    private ArrayList<Note> notes;
    private NoteAdapter noteAdapter;
    @Override
    public void onActivityCreated (Bundle SavedInstanceState){
        super.onActivityCreated(SavedInstanceState);

        NoteBookDbAdapter dbAdapter = new NoteBookDbAdapter(getActivity().getBaseContext());
        dbAdapter.open();
        notes = dbAdapter.getAllNotes();
        noteAdapter = new NoteAdapter(getActivity(), notes);
        setListAdapter(noteAdapter);
        dbAdapter.close();

        getListView().setDivider(ContextCompat.getDrawable(getActivity(),android.R.color.black));
        getListView().setDividerHeight(1);
        //Register konteks menu sehingga jika di klik lama maka menu keluar
        registerForContextMenu(getListView());
    }

    @Override//Jalan ketika List item di tekan
    public void onListItemClick (ListView list, View vi, int position, long id){
        super.onListItemClick(list,vi,position,id);
        launchNoteDetailActivity(MainActivity.FragmentToLaunch.VIEW,position);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.long_press_menu,menu);

    }

    @Override
    public boolean onContextItemSelected (MenuItem item){
        //mengambil posisi item yang di long press
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int rowPosition=info.position;
        Note note = (Note) getListAdapter().getItem(rowPosition);
        switch (item.getItemId()){
            case R.id.edit:
                launchNoteDetailActivity(MainActivity.FragmentToLaunch.EDIT,rowPosition);
                Log.d("Menu Clicks", "We Pressed Edit!");
                return true;

            case R.id.delete:
                NoteBookDbAdapter dbAdapter = new NoteBookDbAdapter(getActivity().getBaseContext());
                dbAdapter.open();
                dbAdapter.deleteNote(note.getID());
                notes.clear();
                notes.addAll(dbAdapter.getAllNotes());
                noteAdapter.notifyDataSetChanged();
                dbAdapter.close();
                return true;
        }
        return super.onContextItemSelected(item);

    }

    private void launchNoteDetailActivity(MainActivity.FragmentToLaunch ftl, int position){
        //Mengambil informasi note yang sedang di klik sesuai poisition1
        Note note=(Note) getListAdapter().getItem(position);
        //membuat intent yang akan memulai activity noteDetail
        Intent intent=new Intent(getActivity(),NoteDetailActivity.class);
        //Mengirim informasi note yang kita klik ke activity note detial
        intent.putExtra(MainActivity.NOTE_TITLE_EXTRA,note.getTitle());
        intent.putExtra(MainActivity.NOTE_MESSAGE_EXTRA,note.getMessage());
        intent.putExtra(MainActivity.NOTE_CATEGORY_EXTRA,note.getCategory());
        intent.putExtra(MainActivity.NOTE_ID_EXTRA,note.getID());

        switch (ftl){
            case VIEW:
                intent.putExtra(MainActivity.NOTE_TO_LOAD_EXTRA, MainActivity.FragmentToLaunch.VIEW);
                break;
            case EDIT:
                intent.putExtra(MainActivity.NOTE_TO_LOAD_EXTRA, MainActivity.FragmentToLaunch.EDIT);
                break;
        }
        startActivity(intent);

    }
}
