package dog.snow.androidrecruittest.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dog.snow.androidrecruittest.database.ItemCursorWrapper;
import dog.snow.androidrecruittest.database.ItemDb;
import dog.snow.androidrecruittest.database.ItemDbHelper;
import dog.snow.androidrecruittest.model.Item;

public class DatabaseController {

    private static DatabaseController controller;
    private Context context;
    private SQLiteDatabase database;

    public static DatabaseController getDatabaseController(Context context){
        if (controller == null){
            controller = new DatabaseController(context);
        }
        return controller;
    }

    private DatabaseController(Context context){
        this.context = context;
        database = new ItemDbHelper(context).getWritableDatabase();
    }

    public List<Item> getItems(){
        List<Item> items = new ArrayList<>();

        Cursor cursor = database.query(ItemDb.ItemTable.NAME, null, null, null, null, null, null);
        ItemCursorWrapper cursorWrapper = new ItemCursorWrapper(cursor);

        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()){
                items.add(cursorWrapper.getItem());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }

        return items;
    }

    public Item getItem(Integer id){
        ItemCursorWrapper cursorWrapper = queryItems(ItemDb.ItemTable.Cols.ID + "=?", new String[]{id.toString()});

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
        Cursor cursor = database.query(ItemDb.ItemTable.NAME, null, s, args, null, null, null);
        return new ItemCursorWrapper(cursor);
    }

    public void clearDatabase(){
        database.execSQL("Delete from table " + ItemDb.ItemTable.NAME);
    }

    public void addItem(Item item){
        ContentValues values = getContentValues(item);
        database.insert(ItemDb.ItemTable.NAME, null, values);
    }

    private static ContentValues getContentValues(Item item) {
        ContentValues values = new ContentValues();
        values.put(ItemDb.ItemTable.Cols.ID, item.getId().toString());
        values.put(ItemDb.ItemTable.Cols.NAME, item.getName());
        values.put(ItemDb.ItemTable.Cols.DESCRIPTION, item.getDescription());
        values.put(ItemDb.ItemTable.Cols.ICON, item.getIcon());
        values.put(ItemDb.ItemTable.Cols.TIMESTAMP, item.getTimestamp());
        values.put(ItemDb.ItemTable.Cols.URL, item.getUrl());
        return values;
    }
}
