package id.co.blogspot.blogmozink.notebook;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteEditFragment extends Fragment {
    private ImageButton noteCatButton;
    private EditText title ,message;
    private Note.Category savedButtonCategory;
    private AlertDialog confirmDialogObject;
    private AlertDialog categoryDialogObject;
    private static final String MODIFIED_CATEGORY = "Modified Category";

    private long noteId = 0;

    private boolean newNote = false;
    public NoteEditFragment() {
        // Required empty public constructor
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(MODIFIED_CATEGORY, savedButtonCategory);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle =  this.getArguments();
        if (bundle != null){
            newNote = bundle.getBoolean(NoteDetailActivity.NEW_NOTE_EXTRA, false);
        }
        if (savedInstanceState !=null){
            savedButtonCategory = (Note.Category) savedInstanceState.get(MODIFIED_CATEGORY);
        }

        View fragmentLayout = inflater.inflate(R.layout.fragment_note_edit, container, false);

        title = (EditText) fragmentLayout.findViewById(R.id.editNoteTitle);
        message = (EditText) fragmentLayout.findViewById(R.id.editNoteMessage);
        noteCatButton = (ImageButton) fragmentLayout.findViewById(R.id.editNoteButton);

        Intent intent = getActivity().getIntent();
        //Menerima String yang dikirim dari activity yang lain
        title.setText(intent.getExtras().getString(MainActivity.NOTE_TITLE_EXTRA,""));
        message.setText(intent.getExtras().getString(MainActivity.NOTE_MESSAGE_EXTRA,""));
        noteId = intent.getExtras().getLong(MainActivity.NOTE_ID_EXTRA, 0);
        //Menerima Variabel Category
        if (savedButtonCategory !=null){
            noteCatButton.setImageResource(Note.categoryToDrawabale(savedButtonCategory));
        }
        else if(!newNote){
            Note.Category noteCat = (Note.Category) intent.getSerializableExtra(MainActivity.NOTE_CATEGORY_EXTRA);
            savedButtonCategory = noteCat;
            noteCatButton.setImageResource(Note.categoryToDrawabale(noteCat));
        }
        Button savedButton = (Button) fragmentLayout.findViewById(R.id.saveNote);

        buildCategoryDialog();
        buildConfirmDialog();
        noteCatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryDialogObject.show();
            }
        });

        savedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialogObject.show();
            }
        });
        return fragmentLayout;
    }
    private void buildCategoryDialog (){
        final String[] categories = new String[]{"Personal", "Technical", "Quote","Finance"} ;
        AlertDialog.Builder categoryBuilder = new AlertDialog.Builder(getActivity());
        categoryBuilder.setTitle("Choose The Note Type");
        categoryBuilder.setSingleChoiceItems(categories, 0 , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                categoryDialogObject.cancel();
                switch (item){
                    case 0:
                        savedButtonCategory = Note.Category.PERSONAL;
                        noteCatButton.setImageResource(R.drawable.p);
                        break;
                    case 1:
                        savedButtonCategory = Note.Category.TECHNICAL;
                        noteCatButton.setImageResource(R.drawable.t);
                        break;
                    case 2:

                        savedButtonCategory = Note.Category.QUOTE;
                        noteCatButton.setImageResource(R.drawable.q);
                        break;
                    case 3:
                        savedButtonCategory = Note.Category.FINANCE;
                        noteCatButton.setImageResource(R.drawable.f);
                        break;
                }
            }
        });
        categoryDialogObject = categoryBuilder.create();
    }


    private void buildConfirmDialog(){
        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(getActivity());
        confirmBuilder.setTitle("Apa anda yakin?");
        confirmBuilder.setMessage("Apakah anda yakin untuk menyimpan note ?");

        confirmBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("Save Note", "Note Title: " + title.getText() + "Note Message: " +message.getText()
                        + "Note Category: " +savedButtonCategory);

                NoteBookDbAdapter dbAdapter = new NoteBookDbAdapter(getActivity().getBaseContext());
                dbAdapter.open();

                if(newNote){
                    dbAdapter.createNote(title.getText() + "", message.getText() + "",
                            (savedButtonCategory == null) ? Note.Category.PERSONAL: savedButtonCategory);
                }else {
                    dbAdapter.updateNote(noteId, title.getText()  + " ", message.getText() + "", savedButtonCategory);
                }
                Intent intent = new Intent(getActivity(), MainActivity.class);
                dbAdapter.close();
                startActivity(intent);
            }
        });
        confirmBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        });
        confirmDialogObject = confirmBuilder.create();
    }
}
