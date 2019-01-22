package com.example.bobek.navdrawertest.LogBookModule.ascentpicker;

public class AscentArrayListItem {

    private int mId;
    private String mAscentType;
    private String mAscentDescription;

    public AscentArrayListItem (int id, String ascentType, String ascentDescription) {
        mId = id;
        mAscentType = ascentType;
        mAscentDescription = ascentDescription;
    }

    public int getId() {return mId;}
    public String getAscentType() {return mAscentType;}
    public String getAscentDescription() {return mAscentDescription;}

}
