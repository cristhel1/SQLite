package com.example.isi19.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.isi19.myapplication.db.FeedReaderContract;
import com.example.isi19.myapplication.db.FeedReaderDbHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Gets the data repository in write mode
        FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, "Algo");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE, "otroAlgo");

// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        Log.i("ID", String.valueOf(newRowId));

        //Read the new row

        SQLiteDatabase db2 = mDbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE
        };

// Filter results WHERE "title" = 'My Title'
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { "Algo" };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

        Cursor c = db2.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

       /* while (c.moveToNext()){
            String title = c.getString(c.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE));
            String subTitle = c.getString(c.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE));
            Log.i("MA", "title: " + title + ", subtitle: " + subTitle);
        }*/

       c.moveToFirst();
       for (int i= 0; i < c.getCount(); i++){
           String title = c.getString(c.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE ));
           Log.i("MA", "title: " + title);
           c.moveToNext();
       }

    }


}
