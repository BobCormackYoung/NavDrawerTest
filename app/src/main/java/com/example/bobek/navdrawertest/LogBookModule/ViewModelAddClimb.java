package com.example.bobek.navdrawertest.LogBookModule;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.bobek.navdrawertest.DataModule.DatabaseContract;
import com.example.bobek.navdrawertest.DataModule.DatabaseHelper;
import com.example.bobek.navdrawertest.DataModule.DatabaseReadWrite;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import java.util.ArrayList;

import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class ViewModelAddClimb extends AndroidViewModel {

    int outputGradeNumber = -1;
    int outputGradeName = -1;
    int outputAscent = -1;
    int outputLocationId = -1;
    int outputHasGps = 0;
    double outputLatitude = 0;
    double outputLongitude = 0;
    String outputLocationName = null;
    String outputRouteName = null;
    String outputDateString = null;
    String outputStringGradeName = null;
    String outputStringGradeType = null;
    String outputStringAscentType = null;
    int outputFirstAscent = DatabaseContract.FIRSTASCENT_FALSE; //intialise as false, i.e. not first ascent
    long outputDate = -1;
    boolean inputIsNewClimb = true;
    int inputRowID = -1;
    boolean gpsAccessPermission = false;
    boolean mRequestingLocationUpdates = false;

    ArrayList<String> locationNames = new ArrayList<>();
    ArrayList<Integer> locationIds = new ArrayList<>();
    ArrayList<Integer> locationIsGps = new ArrayList<>();
    ArrayList<Double> locationLatitudes = new ArrayList<>();
    ArrayList<Double> locationLongitudes = new ArrayList<>();
    boolean outputIsNewLocation = true;

    public ViewModelAddClimb(@NonNull Application application) {super(application);}

    public void resetData() {
        outputGradeNumber = -1;
        outputGradeName = -1;
        outputAscent = -1;
        outputLocationId = -1;
        outputHasGps = 0;
        outputLatitude = 0;
        outputLongitude = 0;
        outputLocationName = null;
        outputRouteName = null;
        outputDateString = null;
        outputStringGradeName = null;
        outputStringGradeType = null;
        outputStringAscentType = null;
        outputFirstAscent = DatabaseContract.FIRSTASCENT_FALSE; //intialise as false, i.e. not first ascent
        outputDate = -1;
        inputIsNewClimb = true;
        inputRowID = -1;
        gpsAccessPermission = false;
        mRequestingLocationUpdates = false;

        locationNames.clear();
        locationIds.clear();
        locationIsGps.clear();
        locationLatitudes.clear();
        locationLongitudes.clear();
        outputIsNewLocation = true;
    }

    public ArrayList<String> getLocationNames() {return locationNames;}
    public ArrayList<Integer> getLocationIds() {return locationIds;}
    public ArrayList<Integer> getLocationIsGps() {return locationIsGps;}
    public ArrayList<Double> getLocationLatitudes() {return locationLatitudes;}
    public ArrayList<Double> getLocationLongitudes() {return locationLongitudes;}

    public void setOutputGradeNumber(int input) {outputGradeNumber=input;}
    public int getOutputGradeNumber() {return outputGradeNumber;}
    public void setOutputGradeName(int input) {outputGradeName=input;}
    public int getOutputGradeName() {return outputGradeName;}
    public void setOutputAscent(int input) {outputAscent=input;}
    public int getOutputAscent() {return outputAscent;}
    public void setOutputLocationId(int input) {outputLocationId=input;}
    public int getOutputLocationId() {return outputLocationId;}
    public void setOutputHasGps(int input) {outputHasGps=input;}
    public int getOutputHasGps() {return outputHasGps;}
    public void setOutputFirstAscent(int input) {outputFirstAscent=input;}
    public int getOutputFirstAscent() {return outputFirstAscent;}
    public void setInputRowID(int input) {inputRowID=input;}
    public int getInputRowID() {return inputRowID;}

    public void setGpsAccessPermission(boolean input) {gpsAccessPermission=input;}
    public boolean getGpsAccessPermission() {return gpsAccessPermission;}
    public void setRequestingLocationUpdates(boolean input) {mRequestingLocationUpdates=input;}
    public boolean getRequestingLocationUpdates() {return mRequestingLocationUpdates;}
    public void setOutputIsNewLocation(boolean input) {outputIsNewLocation=input;}
    public boolean getOutputIsNewLocation () {return outputIsNewLocation;}
    public void setInputIsNewClimb(boolean input) {inputIsNewClimb=input;}
    public boolean getInputIsNewClimb() {return inputIsNewClimb;}

    public void setOutputDate(long input) {outputDate=input;}
    public long getOutputDate() {return outputDate;}

    public void setOutputLatitude(double input) {outputLatitude=input;}
    public double getOutputLatitude() {return outputLatitude;}
    public void setOutputLongitude(double input) {outputLongitude=input;}
    public double getOutputLongitude() {return outputLongitude;}

    public void setOutputLocationName(String input) {outputLocationName=input;}
    public String getOutputLocationName() {return outputLocationName;}
    public void setOutputRouteName(String input) {outputRouteName=input;}
    public String getOutputRouteName() {return outputRouteName;}
    public void setOutputDateString(String input) {outputDateString=input;}
    public String getOutputDateString() {return outputDateString;}
    public void setOutputStringGradeName(String input) {outputStringGradeName=input;}
    public String getOutputStringGradeName() {return outputStringGradeName;}
    public void setOutputStringGradeType(String input) {outputStringGradeType=input;}
    public String getOutputStringGradeType() {return outputStringGradeType;}
    public void setOutputStringAscentType(String input) {outputStringAscentType=input;}
    public String getOutputStringAscentType() {return outputStringAscentType;}

    public void loadClimbDetails(Context context) {
        Bundle bundle = DatabaseReadWrite.EditClimbLoadEntry(inputRowID, context);
        outputLocationId = bundle.getInt("outputLocationId");
        Bundle locationDataBundle = DatabaseReadWrite.LocationLoadEntry(outputLocationId, context);

        outputRouteName = bundle.getString("outputRouteName");
        outputDate = bundle.getLong("outputDate");
        outputDateString = bundle.getString("outputDateString");
        outputFirstAscent = bundle.getInt("outputFirstAscent");
        outputGradeNumber = bundle.getInt("outputGradeNumber");
        outputGradeName = bundle.getInt("outputGradeName");
        outputStringGradeName = DatabaseReadWrite.getGradeTextClimb(outputGradeNumber, context);
        outputStringGradeType = DatabaseReadWrite.getGradeTypeClimb(outputGradeName, context);
        outputAscent = bundle.getInt("outputAscent");
        outputStringAscentType = DatabaseReadWrite.getAscentNameTextClimb(outputAscent, context);
        outputLocationName = locationDataBundle.getString("outputLocationName");
        outputHasGps = locationDataBundle.getInt("outputIsGps");
        if (outputHasGps == DatabaseContract.IS_GPS_TRUE) {
            outputLatitude = locationDataBundle.getDouble("outputGpsLatitude");
            outputLongitude = locationDataBundle.getDouble("outputGpsLongitude");
        }
    }

    public void initialiseLocationArrays(Context context) {

        locationNames.add("Create New");
        locationIds.add(-2);
        locationLatitudes.add(-999.0);
        locationLongitudes.add(-999.0);
        locationIsGps.add(-1);

        DatabaseHelper handler = new DatabaseHelper(context);
        SQLiteDatabase database = handler.getWritableDatabase();

        Cursor locationCursor = DatabaseReadWrite.GetAllLocations(database);

        try {
            //check if the cursor has any items
            if (locationCursor.getCount() > 0) {
                int i = 0;
                //cycle through all items and add to the list
                while (i < locationCursor.getCount()) {
                    locationCursor.moveToPosition(i);

                    int idColumnOutput = locationCursor.getColumnIndex(DatabaseContract.LocationListEntry.COLUMN_LOCATIONNAME);
                    locationNames.add(locationCursor.getString(idColumnOutput));

                    idColumnOutput = locationCursor.getColumnIndex(DatabaseContract.LocationListEntry._ID);
                    locationIds.add(locationCursor.getInt(idColumnOutput));

                    idColumnOutput = locationCursor.getColumnIndex(DatabaseContract.LocationListEntry.COLUMN_ISGPS);
                    locationIsGps.add(locationCursor.getInt(idColumnOutput));

                    idColumnOutput = locationCursor.getColumnIndex(DatabaseContract.LocationListEntry.COLUMN_GPSLATITUDE);
                    locationLatitudes.add(locationCursor.getDouble(idColumnOutput));

                    idColumnOutput = locationCursor.getColumnIndex(DatabaseContract.LocationListEntry.COLUMN_GPSLONGITUDE);
                    locationLongitudes.add(locationCursor.getDouble(idColumnOutput));

                    i++;
                }

            }
        } finally {
            locationCursor.close();
            database.close();
            handler.close();
        }
    }

    public void saveNewClimb(Context context) {
        long writeResult = DatabaseReadWrite.writeClimbLogData(outputRouteName,
                outputIsNewLocation,
                outputLocationName,
                outputLocationId,
                outputAscent,
                outputGradeName,
                outputGradeNumber,
                outputDate,
                outputFirstAscent,
                outputHasGps,
                outputLatitude,
                outputLongitude,
                context);
        DatabaseReadWrite.writeCalendarUpdate(DatabaseContract.IS_CLIMB, outputDate, writeResult, context);
    }

    public void updateExistingClimb(Context context) {
        long updateResult = DatabaseReadWrite.updateClimbLogData(outputRouteName,
                outputIsNewLocation,
                outputLocationName,
                outputLocationId,
                outputAscent,
                outputGradeName,
                outputGradeNumber,
                outputDate,
                outputFirstAscent,
                outputHasGps,
                outputLatitude,
                outputLongitude,
                inputRowID,
                context);
    }


}
