package com.example.mynotes;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment4 extends Fragment {
    private MyInterface mListener;

    EditText etName3, etId2;
    Button btnUp;

    public Fragment4() {
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
        View v = inflater.inflate(R.layout.fragment4, container, false);
        etName3 = v.findViewById(R.id.etName3);
        etId2 = v.findViewById(R.id.etId2);
        btnUp = v.findViewById(R.id.btnUpdate);
        etId2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int i = 0;
                for (int t = 0; t < MyNote.noteArrayList.size(); t++) {
                    try {
                        if (Integer.parseInt(String.valueOf(etId2.getText())) == MyNote.noteArrayList.get(t).getId()) {
                            etName3.setText(MyNote.noteArrayList.get(t).getName());
                            break;
                        } else {
                            etName3.setText("");
                            continue;
                        }
                    } catch (NumberFormatException nfe) {
                        etName3.setText("");
                        continue;
                    }

                }

            }
        });
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //populateDataById(Integer.valueOf(String.valueOf(etId2.getText())));

                if ((etId2.getText().toString() + "") == "" && (etName3.getText().toString() + "") == "") {
                    Toast.makeText(getActivity(), "Incorrect input", Toast.LENGTH_SHORT).show();
                } else {
                    int id = Integer.valueOf(String.valueOf(etId2.getText()));
                    String desc = String.valueOf(etName3.getText());
                    MyNote note = new MyNote(id, desc);
                    sendDataToActivity(etId2.getText().toString(), etName3.getText().toString() + "");
                    MyNote.noteArrayList.clear();
                    populateData();
                    etId2.setText("");
                    etName3.setText("");
                }


            }
        });
        return v;
    }

    private String populateDataById(int id) {
        return mListener.getTextFromDB(id);
    }

    private void populateData() {
        mListener.methodPopulate();
    }

    private void sendDataToActivity(String toString, String s) {
        mListener.onDataUpgrate((Integer.parseInt(toString)), s);
    }
}
