package dog.snow.androidrecruittest.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dog.snow.androidrecruittest.database.ItemCursorWrapper;
import dog.snow.androidrecruittest.database.ItemDbHelper;
import dog.snow.androidrecruittest.database.ItemDbSchema;
import dog.snow.androidrecruittest.model.Item;

public class DatabaseController {
    private static String TAG = DatabaseController.class.getSimpleName();

    private static DatabaseController controller;
    private Context context;
    private SQLiteDatabase database;

    public static DatabaseController getInstance(Context context){
        if (controller == null){
            controller = new DatabaseController(context);
        }
        return controller;
    }

    private DatabaseController(Context context){
        this.context = context.getApplicationContext();
        database = new ItemDbHelper(this.context).getWritableDatabase();
        Log.d(TAG, "database created");
    }

    public List<Item> getItems(){
        List<Item> items = new ArrayList<>();

        Cursor cursor = database.query(ItemDbSchema.ItemTable.NAME, null, null, null, null, null, null);
        ItemCursorWrapper cursorWrapper = new ItemCursorWrapper(cursor);

        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()){
                items.add(cursorWrapper.getItem());
                Log.d(TAG, "cursor added item = " + cursorWrapper.getItem().getId().toString());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }

        return items;
    }

    public Item getItem(Integer id){
        ItemCursorWrapper cursorWrapper = queryItems(ItemDbSchema.ItemTable.Cols.ID + "=?", new String[]{id.toString()});

        try {
            if (cursorWrapper.getCount() == 0){
                return null;
            }
            cursorWrapper.moveToFirst();
            return cursorWrapper.getItem();
        } finally {
            cursorWrapper.close();
        }
    }

    private ItemCursorWrapper queryItems(String s, String[] args) {
        Cursor cursor = database.query(ItemDbSchema.ItemTable.NAME, null, s, args, null, null, null);
        return new ItemCursorWrapper(cursor);
    }

    public void clearDatabase(){
        database.execSQL("DELETE FROM " + ItemDbSchema.ItemTable.NAME);
    }

    public void addItem(Item item){
        ContentValues values = getContentValues(item);
        database.insert(ItemDbSchema.ItemTable.NAME, null, values);
    }

    private static ContentValues getContentValues(Item item) {
        ContentValues values = new ContentValues();
        values.put(ItemDbSchema.ItemTable.Cols.ID, item.getId().toString());
        values.put(ItemDbSchema.ItemTable.Cols.NAME, item.getName());
        values.put(ItemDbSchema.ItemTable.Cols.DESCRIPTION, item.getDescription());
        values.put(ItemDbSchema.ItemTable.Cols.ICON, item.getIcon());
        values.put(ItemDbSchema.ItemTable.Cols.TIMESTAMP, item.getTimestamp());
        values.put(ItemDbSchema.ItemTable.Cols.URL, item.getUrl());
        return values;
    }
}
