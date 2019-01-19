package com.example.bobek.navdrawertest.LogBookModule.ascentpicker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.bobek.navdrawertest.DataModule.DatabaseHelper;
import com.example.bobek.navdrawertest.R;
import com.example.bobek.navdrawertest.DataModule.DatabaseReadWrite;

/**
 * Created by Bobek on 01/02/2018.
 */

public class AscentHolder extends AppCompatActivity {

    private static final String TAG = "AscentHolder";
    Cursor cursor;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_parent_list);

        //Create handler to connect to SQLite DB
        DatabaseHelper handler = new DatabaseHelper(this);
        database = handler.getWritableDatabase();
        // Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseContract.AscentEntry.TABLE_NAME, null);
        cursor = DatabaseReadWrite.getAscentList(database);

        AscentAdapter parentAdapter = new AscentAdapter(this, cursor);

        ListView parentListView = findViewById(R.id.parent_listview);

        parentListView.setAdapter(parentAdapter);

        parentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Log.i(TAG, Long.toString(id));

                int output = (int) id;

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
