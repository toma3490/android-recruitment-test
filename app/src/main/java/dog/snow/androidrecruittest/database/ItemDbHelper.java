package dog.snow.androidrecruittest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ItemDbHelper extends SQLiteOpenHelper{

    public static final int VERSION = 1;
    public static final String DB_NAME = "ItemDbSchema.db";
    private static final String CREATE_DB =
            "Create table " + ItemDbSchema.ItemTable.NAME +
            "(" + " _id integer primary key autoincrement, " + ItemDbSchema.ItemTable.Cols.ID +
            ", " + ItemDbSchema.ItemTable.Cols.NAME +
            ", " + ItemDbSchema.ItemTable.Cols.DESCRIPTION +
            ", " + ItemDbSchema.ItemTable.Cols.ICON +
            ", " + ItemDbSchema.ItemTable.Cols.TIMESTAMP +
            ", " + ItemDbSchema.ItemTable.Cols.URL +
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
