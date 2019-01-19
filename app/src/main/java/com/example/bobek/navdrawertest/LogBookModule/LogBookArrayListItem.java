package com.example.bobek.navdrawertest.LogBookModule;

import android.provider.BaseColumns;

public class LogBookArrayListItem {

    //public final static String _ID = BaseColumns._ID;
    //public final static String COLUMN_DATE = "Date";
    //public final static String COLUMN_ISCLIMB = "IsClimbCode";
    //public final static String COLUMN_ROWID = "RowID";


    private int mId;
    private long mDate;
    private int mClimbCode;
    private int mRowId;

    public LogBookArrayListItem (int id, long date, int climbCode, int rowId) {
        mId = id;
        mDate = date;
        mClimbCode = climbCode;
        mRowId= rowId;
    }

    public int getId() {return mId;}
    public long getDate() {return mDate;}
    public int getClimbCode() {return mClimbCode;}
    public int getRowId() {return mRowId;}

}
