package com.example.mynotes;

public interface MyInterface {
    void onDataReceived(String data);
    void onDataDelete(int id);
    void onDataUpgrate(int id, String data);
    void methodPopulate();
    String getTextFromDB(int id);
}
