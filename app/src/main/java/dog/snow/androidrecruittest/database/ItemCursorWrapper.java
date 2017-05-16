package dog.snow.androidrecruittest.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import dog.snow.androidrecruittest.model.Item;

public class ItemCursorWrapper extends CursorWrapper{

    public ItemCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Item getItem(){
        int id = getInt(getColumnIndex(ItemDb.ItemTable.Cols.ID));
        String name = getString(getColumnIndex(ItemDb.ItemTable.Cols.NAME));
        String description = getString(getColumnIndex(ItemDb.ItemTable.Cols.DESCRIPTION));
        String icon = getString(getColumnIndex(ItemDb.ItemTable.Cols.ICON));
        long timestamp = getLong(getColumnIndex(ItemDb.ItemTable.Cols.TIMESTAMP));
        String url = getString(getColumnIndex(ItemDb.ItemTable.Cols.URL));

        Item item = new Item();

        item.setId(id);
        item.setName(name);
        item.setDescription(description);
        item.setIcon(icon);
        item.setTimestamp(timestamp);
        item.setUrl(url);

        return item;
    }
}
