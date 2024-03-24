package com.example.mynotes;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyInterface {
    private MyFragmentPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private View fragment1, fragment2, fragment3, fragment4;
    private ListView listView;
    private DBHelper.DB db;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        viewPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.add(new Fragment1(), "Show");
        viewPagerAdapter.add(new Fragment2(), "Add");
        viewPagerAdapter.add(new Fragment3(), "Del");
        viewPagerAdapter.add(new Fragment4(), "Update");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        initView();
        initDB();
    }


    private void initDB() {
        db = new DBHelper.DB(this);
        db.open();
        //db.delSequence();
        db.populateNoteListArray();
    }

    @SuppressLint("ResourceType")
    private void initView() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        fragment1 = layoutInflater.inflate(R.layout.fragment1, null);
        fragment2 = layoutInflater.inflate(R.layout.fragment2, null);
        fragment3 = layoutInflater.inflate(R.layout.fragment3, null);
        fragment4 = layoutInflater.inflate(R.layout.fragment4, null);
        listView = fragment1.findViewById(R.layout.mynote_list);
    }


    private void AddData(String newEntry) {

        boolean insertData = db.addRec(newEntry);

        if (insertData == true) {
            Toast.makeText(this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onDataReceived(String newEntry) {
        if (newEntry.length() != 0) {
            AddData(newEntry);
        } else {
            Toast.makeText(MainActivity.this, "Incorrect input " + newEntry, Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onDataDelete(int id) {
        if (!(db.checkedExists(Integer.toString(id)))) {
            Toast.makeText(this, "Data not found!", Toast.LENGTH_SHORT).show();
        } else {
            boolean delData = db.delRec(id);
            if (delData == true) {
                Toast.makeText(this, "Data Successfully Deleted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
            }
        }


    }


    @Override
    public void onDataUpgrate(int id, String data) {
        if (!(db.checkedExists(Integer.toString(id)))) {
            Toast.makeText(this, "Data not found!", Toast.LENGTH_SHORT).show();
        } else {
            boolean upData = db.update(id, data);
            if (upData == true) {
                Toast.makeText(this, "Data Successfully Updated!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void methodPopulate() {
        db.populateNoteListArray();
    }

    @Override
    public String getTextFromDB(int id) {
        return null;
    }


    ArrayList<MyNote> alMyNote;

    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
        db.close();
    }

}