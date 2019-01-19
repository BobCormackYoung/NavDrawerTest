package com.example.bobek.navdrawertest.LogBookModule;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.bobek.navdrawertest.DataModule.DatabaseContract;
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
    int outputFirstAscent = -1;
    long outputDate = -1;
    int inputIntentCode = -1;
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
    }

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
    public void setInputIntentCode(int input) {inputIntentCode=input;}
    public int getInputIntentCode() {return inputIntentCode;}
    public void setInputRowID(int input) {inputRowID=input;}
    public int getInputRowID() {return inputRowID;}

    public void setGpsAccessPermission(boolean input) {gpsAccessPermission=input;}
    public boolean getGpsAccessPermission() {return gpsAccessPermission;}
    public void setRequestingLocationUpdates(boolean input) {mRequestingLocationUpdates=input;}
    public boolean getRequestingLocationUpdates() {return mRequestingLocationUpdates;}

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
}
