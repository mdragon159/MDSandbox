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
 *
 * Create and manage a SQLite database through this adapter
 */
public class QuotesDBAdapter {
    int id = 0;
    public static final String KEY_ROWID = "_id";
    public static final String KEY_QUOTE = "Quote";
    private static final String TAG = "DBAdapter"; // Used for logging

    private static final String DATABASE_NAME = "Random";
    private static final String DATABASE_TABLE = "tblRandomQuotes";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            "create table tblRandomQuotes (_id integer primary key autoincrement, "
                    + "Quote text not null );";

    private final Context context; // Store a passed in context for future use

    private DatabaseHelper DBHelper; //
    private SQLiteDatabase db; // The actual db that this adapter manages

    // Constructor to create a new database adapter
    public QuotesDBAdapter(Context ctx)
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
            db.execSQL("DROP TABLE IF EXISTS tblRandomQuotes");
            onCreate(db);
        }
    }

    //---opens the database---
    public QuotesDBAdapter open() throws SQLException
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
    public long insertQuote(String Quote)
    {
        // ContentValues allow the ability to store set of values for insert statements

        /* Steps to insert information into a database
        *   1) Create new ContentValues
        *   2) Insert data into ContentValues in key:value format
        *   3) Insert ContentValues into database
        */
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_QUOTE, Quote);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---returns how many entries in database---
    public int getAllEntries()
    {
        // There's another way to do this instead of a classic raw query
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(Quote) FROM tblRandomQuotes", null);
        // Replace name of table with constant above

        // Moves the cursor to the first result
        if(cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return cursor.getInt(0);

    }

    //---returns a random string from the database---
    public String getRandomEntry()
    {
/*
        id = getAllEntries();
        Random random = new Random();
        int rand = random.nextInt(getAllEntries()); // TODO: Shhorten this?

        Log.d("MD","getRandomEntry: id = "+id+" | rand = "+rand);

        // ??? Purpose? Can databases not handle 0?
        if(rand == 0)
            ++rand;

        // Get the quote itself as a cursor
        Cursor cursor = db.rawQuery(
                "SELECT Quote FROM tblRandomQuotes WHERE _id = " + rand, null);

        // Just in case multiple data, move cursor to starting position and return data
        if(cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        return cursor.getString(0);
*/

        // Can also do db.rawQuery("SELECT * FROM mainTable ORDER BY RANDOM() LIMIT 1", null);
        String[] columns = {KEY_QUOTE};
        Cursor cursor = db.query(DATABASE_TABLE,columns,
                null, null, null, null, "RANDOM()","1");
        if(cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        return cursor.getString(0);
    }

    public void deleteAll(){
        db.delete(DATABASE_TABLE,null,null);
    }
}
