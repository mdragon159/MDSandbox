package com.MDstudios.mdsandbox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jawad Nasser on 8/11/2014.
 *
 * TODO: Replace deprecated method Date.getMonth()
 * TODO: Replace keys with hardcoded strings wherever applicable
 *
 *
 */
public class ITrackerDBAdapter {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_YEAROFREWARDS = "YearOfRewards";
    public static final String KEY_MONTHOFREWARDS = "MonthOfRewards";
    public static final String KEY_DAYOFREWARDS = "DayOfRewards";
    public static final String KEY_NUMOFREWARDS = "NumOfRewards";
    private static final String TAG = "DBAdapter";

    private static final String DATABASE_NAME = "BlockbusterRewards";
    private static final String DATABASE_TABLE = "tblRewards";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            "create table tblRewards (_id integer primary key autoincrement, "
                    + "YearOfRewards text not null, MonthOfRewards text not null," +
                    "DayOfRewards text not null," +
                    " NumOfRewards int not null );";

    private static final Date date = new Date();
    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public ITrackerDBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS titles");
            onCreate(db);
        }
    }

    //---opens the database---
    public ITrackerDBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    //---insert a title into the database---
    public long insertRewards(String yearRewards, String monthRewards, String dayRewards, String numRewards)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_YEAROFREWARDS, yearRewards);
        initialValues.put(KEY_MONTHOFREWARDS, monthRewards);
        initialValues.put(KEY_DAYOFREWARDS, dayRewards);
        initialValues.put(KEY_NUMOFREWARDS, Integer.parseInt(numRewards));
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public int getAllRewards()
    {
        Cursor cursor = db.rawQuery(
                "SELECT SUM(NumOfRewards) FROM tblRewards", null);
        if(cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return cursor.getInt(0);

    }

    public int getMonthlyRewards()
    {

        Cursor cursor = getMonthRewards();/*db.rawQuery(
                    "SELECT SUM(NumOfRewards) FROM tblRewards WHERE MonthOfRewards = " +
                    Calendar.MONTH + " AND YearOfRewards = " + Calendar.YEAR, null);*/
        if(cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return cursor.getInt(0);

    }

    //---retrieves all the titles---
    public Cursor getMonthRewards()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        String WHERE = "MonthOfRewards = " + date.getMonth() + " AND YearOfRewards = " + year;
        return db.query(DATABASE_TABLE, new String[] {
                        "SUM(" + KEY_NUMOFREWARDS + ")"},
                WHERE,
                null,
                null,
                null,
                null);
    }
}
