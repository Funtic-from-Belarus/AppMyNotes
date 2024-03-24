package com.example.mynotes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment3 extends Fragment {
    private MyInterface mListener;

    EditText etId;
    Button btnDel;

    public Fragment3() {
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

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment3, container, false);
        etId = v.findViewById(R.id.etId);
        btnDel = v.findViewById(R.id.btnDelete);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((etId.getText().toString() + "") == "") {
                    Toast.makeText(getActivity(), "Incorrect input", Toast.LENGTH_SHORT).show();
                } else {
                    int id = Integer.valueOf(String.valueOf(etId.getText()));
                    sendDataToActivity(etId.getText().toString() + "");
                    MyNote.noteArrayList.clear();
                    populateData();
                    etId.setText("");
                }


            }
        });
        return v;
    }

    private void populateData() {
        mListener.methodPopulate();
    }

    private void sendDataToActivity(String s) {
        mListener.onDataDelete(Integer.parseInt(s));
    }
}

