package com.example.bobek.navdrawertest.LogBookModule.workoutpicker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.bobek.navdrawertest.R;
import com.example.bobek.navdrawertest.DataModule.DatabaseHelper;
import com.example.bobek.navdrawertest.DataModule.DatabaseReadWrite;

/**
 * Created by Bobek on 11/02/2018.
 */

public class ChildWorkoutHolder extends AppCompatActivity {

    private static final String TAG = "ChildWorkoutHolder";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_child_list);

        Intent selectorIntent = getIntent();
        int selectorID = selectorIntent.getIntExtra("selector", 0);

        //Create handler to connect to SQLite DB
        DatabaseHelper handler = new DatabaseHelper(this);
        final SQLiteDatabase database = handler.getWritableDatabase();

        Log.i(TAG, "" + selectorID);
        //Log.i(TAG,"SELECT * FROM " + DatabaseContract.WorkoutListEntry.TABLE_NAME);
        //Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseContract.WorkoutListEntry.TABLE_NAME + " WHERE " + DatabaseContract.WorkoutListEntry.COLUMN_WORKOUTTYPECODE + "=" + selectorID, null);
        //Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseContract.WorkoutListEntry.TABLE_NAME, null);
        final Cursor cursor = DatabaseReadWrite.getWorkoutList(selectorID, database);
        Log.i(TAG, "Count: " + cursor.getCount());

        ChildWorkoutAdapter childAdapter = new ChildWorkoutAdapter(this, cursor);

        ListView childListView = findViewById(R.id.child_list);

        childListView.setAdapter(childAdapter);

        childListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                int output = (int) id;

                Log.i(TAG, "" + output);

                Intent outputIntent = new Intent();
                outputIntent.putExtra("OutputData", output);
                setResult(RESULT_OK, outputIntent);

                try {
                    finish();
                } finally {
                    cursor.close();
                    database.close();
                    Log.i(TAG, "finally...");
                }

            }
        });

    }
}

