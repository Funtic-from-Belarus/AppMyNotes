package com.example.mynotes;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment2 extends Fragment {
    private MyInterface mListener;

    EditText etName2;
    Button btnAdd;

    public Fragment2() {
        // required empty public constructor.
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MyInterface) {
            mListener = (MyInterface) context;
        } else {
            throw new RuntimeException(context.toString() + "must implements MyInterface");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment2, container, false);
        etName2 = v.findViewById(R.id.etName2);
        btnAdd = v.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDataToActivity(etName2.getText().toString() + "");
                MyNote.noteArrayList.clear();
                populateData();
                etName2.setText("");
            }
        });

        return v;
    }

    private void populateData() {
        mListener.methodPopulate();
    }

    private void sendDataToActivity(String toString) {
        mListener.onDataReceived(toString);
    }


}

