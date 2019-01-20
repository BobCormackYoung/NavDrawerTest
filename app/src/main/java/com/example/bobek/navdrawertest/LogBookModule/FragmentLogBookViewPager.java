package com.example.bobek.navdrawertest.LogBookModule;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bobek.navdrawertest.DataModule.DatabaseContract;
import com.example.bobek.navdrawertest.DataModule.DatabaseHelper;
import com.example.bobek.navdrawertest.DataModule.DatabaseReadWrite;
import com.example.bobek.navdrawertest.MainActivity;
import com.example.bobek.navdrawertest.R;
import com.example.bobek.navdrawertest.UtilModule.TimeUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.net.URL;
import java.util.ArrayList;

import static com.example.bobek.navdrawertest.UtilModule.TimeUtils.millisToStartOfDay;

/**
 * Created by Bobek on 26/02/2018.
 */

public class FragmentLogBookViewPager extends Fragment {

    private static final String KEY_DATE = "date";
    final long DAYPERIOD = 86400000;
    final int ITEM_NEW = 0;
    final int ITEM_EDIT = 1;
    long fragmentDate;
    ArrayList<LogBookArrayListItem> logBookArrayList;
    LogBookListArrayAdapter adapter;
    private TextView tvContent;
    private AVLoadingIndicatorView ivAvi;
    ListView listView;
    Context mContext;
    private ViewModelLogBook mViewModelLogBook;

    public static FragmentLogBookViewPager newInstance(long date) {
        FragmentLogBookViewPager fragmentFirst = new FragmentLogBookViewPager();
        Bundle args = new Bundle();
        args.putLong(KEY_DATE, date);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModelLogBook = ViewModelProviders.of(getActivity()).get(ViewModelLogBook.class);
        Log.i("LogBookFragmentContent","onCreate "+ TimeUtils.convertDate(fragmentDate, "yyyy-MM-dd"));
        final long millis = getArguments().getLong(KEY_DATE);
        if (millis > 0) {
            final Context context = getActivity();
            if (context != null) {
                //tvContentValue = "This is the content for the date " + TimeUtils.getFormattedDate(context, millis);
                fragmentDate = millis;
                return;
            }
        }
        //fragmentDate = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_book_view_pager, container, false);
        Log.i("LogBookFragmentContent","onCreateView "+ TimeUtils.convertDate(fragmentDate, "yyyy-MM-dd"));

        //closeDrawer();

        //ivAvi = view.findViewById(R.id.iv_av_loading_indicator);
        //ivAvi.hide();
        mContext = getActivity();
        long dayStart = fragmentDate - millisToStartOfDay();
        long dayEnd = dayStart + DAYPERIOD;
        listView = view.findViewById(R.id.log_book_list);

        //adapter = new LogBookListArrayAdapter(context, logBookArrayList);
        //ListView listView = view.findViewById(R.id.log_book_list);
        //listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Log.i("EditClimb", "OnItemClick " + (int) id + " " + position + " " + fragmentDate);

                int childRowID = adapter.getItem(position).getRowId();
                int isClimb = adapter.getItem(position).getClimbCode();

                // Find the child row ID & whether it is a climb or not
                //int childRowID = DatabaseReadWrite.getCalendarTrackerChildRowID(id, mContext);
                //int isClimb = DatabaseReadWrite.getCalendarTrackerIsClimb(id, mContext);

                // if it is a climb, then start a new intent for modifying the climb, if not, start for modifying training
                if (isClimb == DatabaseContract.IS_CLIMB) {
                    mViewModelLogBook.setIsNewClimbFalse();
                    mViewModelLogBook.setAddClimbRowId(childRowID);
                    mViewModelLogBook.setAddClimbDate(fragmentDate);
                    /*Intent editClimbIntent = new Intent(context, AddClimb.class);
                    editClimbIntent.putExtra("EditOrNewFlag", ITEM_EDIT);
                    editClimbIntent.putExtra("RowID", childRowID);
                    editClimbIntent.putExtra("Date", fragmentDate);
                    // Start the new activity
                    startActivity(editClimbIntent);*/

                    Log.i("EditClimb", "OnItemClick " + (int) id + " " + ITEM_EDIT + " " + fragmentDate);

                    FragmentAddClimb fragmentAddClimb = new FragmentAddClimb();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.flContent, fragmentAddClimb, "fragmentAddClimb")
                            .addToBackStack(null)
                            .commit();

                } /*else {
                    Intent editWorkoutIntent = new Intent(context, AddWorkout.class);
                    editWorkoutIntent.putExtra("EditOrNewFlag", ITEM_EDIT);
                    editWorkoutIntent.putExtra("RowID", childRowID);
                    editWorkoutIntent.putExtra("Date", fragmentDate);
                    // Start the new activity
                    Log.i("TAG ME UP", "OnItemClick " + (int) id + " " + ITEM_EDIT + " " + fragmentDate);
                    startActivity(editWorkoutIntent);
                }*/
            }
        });

        /*listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {

                AlertDialog.Builder deleteAlert = deleteDialog(id);

                deleteAlert.show();

                return true;
            }
        });*/

        return view;

    }

    private void closeDrawer() {
        MainActivity.getDrawer().closeDrawer(GravityCompat.START);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("LogBookFragmentContent","onResume "+ TimeUtils.convertDate(fragmentDate, "yyyy-MM-dd"));
        refreshData();
        //refreshCursor();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void refreshData() {
        long dayStart = fragmentDate - millisToStartOfDay();
        long dayEnd = dayStart + DAYPERIOD;
        logBookArrayList = DatabaseReadWrite.getCalendarEntriesBetweenDates(dayStart, dayEnd, (Context) getActivity());
        adapter = new LogBookListArrayAdapter(mContext, logBookArrayList);
        listView.setAdapter(adapter);
    }

    //public Cursor getCursorBetweenDates(long dateStart, long dateEnd, SQLiteDatabase db) {
    //    return db.rawQuery("select * from " + DatabaseContract.CalendarTrackerEntry.TABLE_NAME + " where " + DatabaseContract.CalendarTrackerEntry.COLUMN_DATE + " BETWEEN '" + dateStart + "' AND '" + dateEnd + "' ORDER BY Date ASC", null);
    //}

    /*public void refreshCursor() {
        long dayStart = fragmentDate - millisToStartOfDay();
        long dayEnd = dayStart + DAYPERIOD;
        if (cursor!=null) {
            cursor.close();
        }

        LoadCursorDataInput cursorDataInput = new LoadCursorDataInput();
        cursorDataInput.setDayEnd(dayEnd);
        cursorDataInput.setDayStart(dayStart);
        LoadCursorTask runner = new LoadCursorTask();
        runner.execute(cursorDataInput);

    }*/

    /*public void updateExpandedItemsList() {
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int rowID = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.CalendarTrackerEntry.COLUMN_ROWID));
            LogBookListAdapter.itemExpanded.add(i, new ExpandedArrayItem(rowID, false)); // initializes all items value with false
        }
    }*/

    /*private class LoadCursorTask extends AsyncTask<LoadCursorDataInput, Integer, Cursor> {
        protected Cursor doInBackground(LoadCursorDataInput... loadCursorDataInputs) {
            Log.i("AsyncTask","doInBackground "+ TimeUtils.convertDate(fragmentDate, "yyyy-MM-dd"));
            long startDate = loadCursorDataInputs[0].getDayStart();
            long endDate = loadCursorDataInputs[0].getDayEnd();
            return getCursorBetweenDates(startDate, endDate, database);
        }

        protected void onPreExecute() {
            Log.i("AsyncTask","onPreExecute "+ TimeUtils.convertDate(fragmentDate, "yyyy-MM-dd"));
            Log.i("AsyncTask","onPreExecute "+ database.isOpen() + " " + TimeUtils.convertDate(fragmentDate, "yyyy-MM-dd"));
            final Context context = getActivity();
            handler = new DatabaseHelper(context);
            database = handler.getWritableDatabase();
            //ivAvi.show();
        }

        protected void onPostExecute(Cursor result) {
            Log.i("AsyncTask","onPostExecute "+ TimeUtils.convertDate(fragmentDate, "yyyy-MM-dd"));
            if (cursor!=null) {
                cursor.close();
            }
            cursor=result;
            if (adapter!=null) {
                adapter.changeCursor(cursor);
            }
            updateExpandedItemsList();
            //ivAvi.hide();
        }
    }*/

    /*private class LoadCursorDataInput {

        long dayStart;
        long dayEnd;
        SQLiteDatabase cursorDatabase;

        public void setCursorDatabase(SQLiteDatabase cursorDatabase) {
            this.cursorDatabase = cursorDatabase;
        }

        public void setDayEnd(long dayEnd) {
            this.dayEnd = dayEnd;
        }

        public void setDayStart(long dayStart) {
            this.dayStart = dayStart;
        }

        public long getDayEnd() {
            return dayEnd;
        }

        public long getDayStart() {
            return dayStart;
        }

        public SQLiteDatabase getCursorDatabase() {
            return cursorDatabase;
        }

    }*/

}