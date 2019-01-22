package com.example.bobek.navdrawertest.LogBookModule.gradepicker;

public class GradeArrayListItem {

    private int mRowId;
    private int mParentId;
    private String mName;
    private double mRelativeDifficulty;

    public GradeArrayListItem (int rowId, int parentId, String name, double relativeDifficulty) {
        mRowId = rowId;
        mParentId = parentId;
        mName = name;
        mRelativeDifficulty = relativeDifficulty;
    }

    public GradeArrayListItem (int rowId, String name) {
        mRowId = rowId;
        mName = name;
    }

    public int getRowId() {return mRowId;}
    public int getParentId() {return mParentId;}
    public String getName() {return mName;}
    public double getRelativeDifficulty() {return mRelativeDifficulty;}

}
