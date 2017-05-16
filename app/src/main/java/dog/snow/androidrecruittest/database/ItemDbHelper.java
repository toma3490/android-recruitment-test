package dog.snow.androidrecruittest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ItemDbHelper extends SQLiteOpenHelper{

    public static final int VERSION = 1;
    public static final String DB_NAME = "ItemDb.db";
    private static final String CREATE_DB =
            "Create table " + ItemDb.ItemTable.NAME +
            "(" + " _id integer primary key autoincrement, " + ItemDb.ItemTable.Cols.ID +
            ", " + ItemDb.ItemTable.Cols.NAME +
            ", " + ItemDb.ItemTable.Cols.DESCRIPTION +
            ", " + ItemDb.ItemTable.Cols.ICON +
            ", " + ItemDb.ItemTable.Cols.TIMESTAMP +
            ", " + ItemDb.ItemTable.Cols.URL +
            ")";

    public ItemDbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
