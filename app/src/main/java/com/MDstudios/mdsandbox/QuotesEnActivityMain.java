package com.MDstudios.mdsandbox;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jawad Nasser on 8/10/2014.
 *
 * TODO: Use alternative to deprecated methods
 */
public class QuotesEnActivityMain extends ListActivity {
    private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;

    private static final int INSERT_ID = Menu.FIRST;
    private static final int DELETE_ID = Menu.FIRST + 1;

    private QuotesEnDBAdapter mDbHelper;
    private Cursor mNotesCursor;

    public Button button;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quotesen_activity_main);
        mDbHelper = new QuotesEnDBAdapter(this);
        mDbHelper.open();
        fillData();
        registerForContextMenu(getListView());
        button = (Button)findViewById(R.id.genRan);
        button.setOnClickListener(mAddListener);
    }

    private View.OnClickListener mAddListener = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            //long id1 = 0;
            // do something when the button is clicked
            try
            {
                String quote = "";
                quote = mDbHelper.getRandomEntry();
                Context context = getApplicationContext();
                CharSequence text = quote;
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            catch (Exception ex)
            {
                Context context = getApplicationContext();
                CharSequence text = ex.toString();
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    };

    private void fillData() {
        /*
        // Initial, deprecated method
        // Get all of the rows from the database and create the item list
        mNotesCursor = mDbHelper.fetchAllQuotes();
        startManagingCursor(mNotesCursor); // Activity closes cursor

        // Create an array to specify the fields we want to display in the list (only TITLE)
        String[] from = new String[]{QuotesEnDBAdapter.KEY_QUOTES};

        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[]{R.id.text1};

        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter notes =
                new SimpleCursorAdapter(this, R.layout.row_simple, mNotesCursor, from, to);
        setListAdapter(notes);
        */


        // Get all the data in a cursor
        Cursor cursor = mDbHelper.fetchAllQuotes();

        // Loop through all of the data and add to a list
        List<String> quotesList = new ArrayList<String>();
        if(cursor.moveToFirst()){
            // Get which column the quotes belong in
            int quoteIndex = cursor.getColumnIndex(mDbHelper.KEY_QUOTES);
            do{
                // First get the current quote from the cursor
                String quote = cursor.getString(quoteIndex);

                // Then add the data to the list
                quotesList.add(quote);
            } while(cursor.moveToNext());
        }


        // Convert list to string array
    //    String[] quotesData = quotesList.toArray(new String[quotesList.size()]);

        // Create an array adapter to use with listView
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.row_simple,quotesList);

        setListAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, INSERT_ID,0, R.string.menu_insert);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case INSERT_ID:
                createNote();
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                mDbHelper.deleteQuote(info.id);
                fillData();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void createNote() {
        Intent i = new Intent(this, QuotesEnActivityEdit.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Cursor c = mNotesCursor;
        c.moveToPosition(position);
        Intent i = new Intent(this, QuotesEnActivityEdit.class);
        i.putExtra(QuotesEnDBAdapter.KEY_ROWID, id);
        i.putExtra(QuotesEnDBAdapter.KEY_QUOTES, c.getString(
                c.getColumnIndexOrThrow(QuotesEnDBAdapter.KEY_QUOTES)));
        startActivityForResult(i, ACTIVITY_EDIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Bundle extras = intent.getExtras();
        switch(requestCode) {
            case ACTIVITY_CREATE:
                String title = extras.getString(QuotesEnDBAdapter.KEY_QUOTES);
                mDbHelper.createQuote(title);
                fillData();
                break;
            case ACTIVITY_EDIT:
                Long rowId = extras.getLong(QuotesEnDBAdapter.KEY_ROWID);
                if (rowId != null) {
                    String editTitle = extras.getString(QuotesEnDBAdapter.KEY_QUOTES);
                    mDbHelper.updateQuote(rowId, editTitle);
                }
                fillData();
                break;
        }
    }
}
