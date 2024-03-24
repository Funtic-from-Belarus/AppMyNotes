package com.example.mynotes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_TABLE = "goods";
    public static final String id = "id";
    public static final String name = "name";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DB_TABLE + "( "
                + id + " integer primary key autoincrement, "
                + name + " text"
                + ");");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(db);
    }

    public static class DB {
        private static final String DB_NAME = "mydb";
        private static final int DB_VERSION = 1;
        private static final String DB_TABLE = "goods";

        private final Context mCtx;
        private DBHelper mDBHelper;
        private SQLiteDatabase mDB;

        public DB(Context ctx) {
            mCtx = ctx;
        }

        // открыть подключение
        public void open() {
            mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
            mDB = mDBHelper.getWritableDatabase();
            mDB = mDBHelper.getReadableDatabase();

        }

        // закрыть подключение
        public void close() {
            if (mDBHelper != null) mDBHelper.close();
        }

        @SuppressLint("Range")
        public void populateNoteListArray() {

            String query = "SELECT " + id + ", "
                    + name + " FROM " + DB_TABLE;

            Cursor noteCursor = mDB.rawQuery(query, null);
            String noteId;
            String noteName;
            while (noteCursor.moveToNext()) {

                noteName = noteCursor.getString(noteCursor
                        .getColumnIndex(name));
                noteId = noteCursor.getString(noteCursor.getColumnIndex(id));
                MyNote note = new MyNote(Integer.parseInt(noteId), noteName);//MyNote.noteArrayList.size()
                MyNote.noteArrayList.add(note);

            }
        }

        // добавить запись в DB_TABLE
        public boolean addRec(String name) {

            ContentValues cv = new ContentValues();
            cv.put("name", name);
            long result = mDB.insert(DB_TABLE, null, cv);
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
        // обновить запись в DB_TABLE


        public boolean update(int id, String name) {
            ContentValues cv = new ContentValues();
            cv.put("name", name);
            long result = mDB.update(DB_TABLE, cv, "id = ?",
                    new String[]{String.valueOf(id)});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }

        // удалить запись из DB_TABLE
        public boolean delRec(long id) {
            long result = mDB.delete(DB_TABLE, "id = " + id, null);
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }

        // удалить все записи из DB_TABLE
        public void delAll() {
            mDB.delete(DB_TABLE, null, null);
        }

        public void delSequence() {
            String sql1 = "DELETE FROM " + DBHelper.DB_TABLE + ";";
            String sql2 = "DELETE FROM sqlite_sequence where name='" + DBHelper.DB_TABLE + "';";
            mDB.execSQL(sql1);
            mDB.execSQL(sql2);
        }

        @SuppressLint("Range")
        public ArrayList<MyNote> populateBeforeDestroy() {
            String query = "SELECT " + id + ", "
                    + name + " FROM " + DB_TABLE;

            Cursor noteCursor = mDB.rawQuery(query, null);
            String noteId;
            String noteName;
            while (noteCursor.moveToNext()) {

                noteName = noteCursor.getString(noteCursor
                        .getColumnIndex(name));
                noteId = noteCursor.getString(noteCursor.getColumnIndex(id));
                MyNote note = new MyNote(Integer.parseInt(noteId), noteName);//MyNote.noteArrayList.size()
                MyNote.noteArrayList.add(note);
            }
            return MyNote.noteArrayList;
        }

        @SuppressLint("Range")
        public boolean checkedExists(String cId) {
            String query = "SELECT COUNT(*) FROM " + DB_TABLE + " WHERE id = ?";
            Cursor noteCursor = mDB.rawQuery(query, new String[]{cId});
            noteCursor.moveToFirst();
            int count = noteCursor.getInt(0);
            noteCursor.close();
            return count > 0;
        }

        public String showDataById(String idValue) {
            String query = "select * from " + DB_TABLE + " where id = ?";
            Cursor cursor = mDB.rawQuery(query, new String[]{idValue});
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") String data = cursor.getString(cursor.getColumnIndex(name));
                return data;
            } else {
                return "No data found";
            }

        }
    }
}
