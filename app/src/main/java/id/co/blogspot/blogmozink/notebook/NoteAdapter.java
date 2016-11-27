package id.co.blogspot.blogmozink.notebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Axoriea-LPMX on 12/11/2016.
 */

public class NoteAdapter extends ArrayAdapter<Note> {

    public static class ViewHolder{
        TextView title;
        TextView note;
        ImageView noteIcon;

    }
    public NoteAdapter(Context context, ArrayList<Note> notes){
        super(context,0,notes);
    }
    ViewHolder viewHolder;
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //mengambil data dari note yang ada suatu posiis
        Note note = getItem(position);

        //Cek apakah view yang akan diambil sedang hilang, selain itu maka  buat yang listRow yang baru
        if(convertView == null){
        //Membuat ViewHolder ketika tidak ada view yang dipilih
            viewHolder=new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row,parent,false);
        //Mereferensikan View yang telah di dklarasikan di class ViewHOlder
            viewHolder.title= (TextView) convertView.findViewById(R.id.listItemNoteTitle);
            viewHolder.note= (TextView) convertView.findViewById(R.id.listItemNoteBody);
            viewHolder.noteIcon= (ImageView) convertView.findViewById(R.id.listItemNoteImg);

        //Set tag untuk menyimpan viewholder yang mnyimpan referensi widget
            convertView.setTag(viewHolder);
        }
        else{//Jika ada convertView maka ambillah convertView yang memiliki tag ke widgetnya
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //Isi view yang telah direferensikan

        viewHolder.title.setText(note.getTitle());
        viewHolder.note.setText(note.getMessage());
        viewHolder.noteIcon.setImageResource(note.getAssociatedDrawable());

        //Setelah VIew di modifikasi selanjutnya dikembalikan sehingga bisa di perlihatkan
        return convertView;
    }

}
