package com.example.a71p.database;
import  android.util.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.google.android.libraries.places.api.Places;
import java.util.ArrayList;

import com.example.a71p.model.LostFoundItem;

public class LostFoundDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "lost_found.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "items";

    public LostFoundDatabaseHelper (Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "type TEXT," +
                "name TEXT," +
                "phone TEXT," +
                "description TEXT," +
                "date TEXT," +
                "location TEXT)";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertItem(LostFoundItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("type", item.getType());
        values.put("name", item.getName());
        values.put("phone", item.getPhone());
        values.put("description", item.getDescription());
        values.put("date", item.getDate());
        values.put("location", item.getLocation());
        long result = db.insert(TABLE_NAME, null, values); // 성공 시 -1이 아닌 rowId 반환

        if (result != -1) {
            Log.d("DB_INSERT", "Item inserted: " + item.toString());
        } else {
            Log.e("DB_INSERT", "Insert failed for item: " + item.toString());
        }
        db.close();
    }

    public ArrayList<LostFoundItem> getAllItems() {
        ArrayList<LostFoundItem> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                LostFoundItem item = new LostFoundItem(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                );
                list.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }
}