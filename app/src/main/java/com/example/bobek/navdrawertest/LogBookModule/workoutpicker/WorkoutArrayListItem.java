package com.example.bobek.navdrawertest.LogBookModule.workoutpicker;

public class WorkoutArrayListItem {

    private int mId;
    private int mParentId;
    private String mName;
    private String mDescription;

    public WorkoutArrayListItem (int id, int parentId, String name) {
        mId = id;
        mParentId = parentId;
        mName = name;
    }

    public WorkoutArrayListItem (int id, String name, String description) {
        mId = id;
        mName = name;
        mDescription = description;
    }

    public int getId() {return mId;}
    public int getParentId() {return mParentId;}
    public String getName() {return mName;}
    public String getDescription() {return mDescription;}

}
