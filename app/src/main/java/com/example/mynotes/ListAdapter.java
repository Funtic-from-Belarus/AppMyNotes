package com.example.mynotes;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class ListAdapter extends ArrayAdapter<MyNote> {

    public ListAdapter(Context context, ArrayList<MyNote> notes) {
        super(context, 0, notes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyNote note = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.mynote_list, parent, false);
        }
        TextView id = convertView.findViewById(R.id.tv_id);
        TextView desc = convertView.findViewById(R.id.tv_name);
        id.setText(String.valueOf(note.getId()));
        desc.setText(note.getName());
        return convertView;
    }


}
