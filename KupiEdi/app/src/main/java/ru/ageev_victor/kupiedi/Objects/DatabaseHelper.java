package ru.ageev_victor.kupiedi.Objects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns {

    private static int DATABASE_VERSION = 1;
    public static final String PRODUCT_NAME = "product_name";
    public static final String PRODUCT_COUNT = "product_count";
    public static final String DATABASE_NAME = "kupiedi.db";
    public final SQLiteDatabase dataBase = getReadableDatabase();
    public String tempTableName;
    private static DatabaseHelper databaseHelper;

    public static DatabaseHelper getInstance(Context context) {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public SQLiteDatabase getDataBase() {
        return dataBase;
    }

    public void createTable(String tableName) {
        tempTableName = tableName;
        dataBase.execSQL("CREATE TABLE "
                + tableName + " (" + BaseColumns._ID
                + " integer primary key autoincrement, " + PRODUCT_NAME
                + " text not null, " + PRODUCT_COUNT
                + " integer)");
    }

    public ArrayList<DataFromDataBase> getData(String table) {
        ArrayList<DataFromDataBase> dataFromDataBases = new ArrayList<>();
        Cursor cursor = dataBase.query(table, new String[]{PRODUCT_NAME,
                        PRODUCT_COUNT},
                null, null,
                null, null, null);
        while (cursor.moveToNext()) {
            String productName = cursor.getString(cursor.getColumnIndex(PRODUCT_NAME));
            int productCount = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.PRODUCT_COUNT));
            dataFromDataBases.add(new DataFromDataBase(productName, productCount));
        }
        cursor.close();
        return dataFromDataBases;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    private void addFoodToDB(String table, ArrayList<String> foods) {
        for (String food : foods) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.PRODUCT_NAME, food);
            this.getDataBase().insert(table, null, values);
        }
    }

    public CharSequence[] loadList() {
        ArrayList<String> arrlist = new ArrayList<>();
        Cursor cursor = dataBase.rawQuery("SELECT name FROM sqlite_master " +
                "WHERE name NOT LIKE 'android_metadata' AND name NOT LIKE 'sqlite_sequence' AND name NOT LIKE 'foodTable'", null);
        while (cursor.moveToNext()) {
            String text = cursor.getString(cursor.getColumnIndex("name"));
            arrlist.add(text);
        }
        CharSequence[] lists = new CharSequence[arrlist.size()];
        for (int i = 0; i < arrlist.size(); i++) {
            lists[i] = arrlist.get(i);
        }
        cursor.close();
        return lists;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void deleteTable(String table) {
        dataBase.execSQL("DROP TABLE IF EXISTS " + table);
    }
}
