package com.MDstudios.mdsandbox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Random;

/**
 * Created by Jawad Nasser on 8/10/2014.
 */
public class QuotesEnDBAdapter {
    static Random random = new Random();
    public static final String KEY_QUOTES = "quotes";
    public static final String KEY_ROWID = "_id";

    private static final String TAG = "QuotesDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    /**
     * Database creation sql statement
     */
      private static final String DATABASE_NAME = "Random";
    private static final String DATABASE_TABLE = "tblRandomQuotesEn";
    private static final String DATABASE_CREATE =
            "create table "+DATABASE_TABLE+" (_id integer primary key autoincrement, "
                    + "quotes text not null);";
    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(db);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     *
     * @param ctx the Context within which to work
     */
    public QuotesEnDBAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public QuotesEnDBAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public long createQuote(String quotes) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_QUOTES, quotes);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteQuote(long rowId) {

        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor fetchAllQuotes() {

        /* Arguments...
        * 1) Database table
        * 2) Columns the statement should return
        * 3) Rows the columns should return if any
        * 4) Selection arguments
        * 5) Group by SQL function
        * 6) A having SQL statement
        * 7) Order by SQL function
        */
        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_QUOTES}, null, null, null, null, null);
    }

    public Cursor fetchQuote(long rowId) throws SQLException {

        Cursor mCursor =

                mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                KEY_QUOTES}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public boolean updateQuote(long rowId, String title) {
        ContentValues args = new ContentValues();
        args.put(KEY_QUOTES, title);

        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public int getAllEntries()
    {
        Cursor cursor = mDb.rawQuery(
                "SELECT COUNT(quotes) FROM "+DATABASE_TABLE, null);
        if(cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return cursor.getInt(0);

    }

    public String getRandomEntry()
    {
        int id = 1;
        id = getAllEntries();

        int rand = random.nextInt(id) + 1;
        Cursor cursor = mDb.rawQuery(
                "SELECT quotes FROM "+DATABASE_TABLE+" WHERE _id = " + rand, null);
        if(cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        return cursor.getString(0);

    }
}
