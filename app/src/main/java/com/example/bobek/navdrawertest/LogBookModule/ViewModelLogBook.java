package com.example.bobek.navdrawertest.LogBookModule;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.bobek.navdrawertest.UtilModule.TimeUtils;

import java.util.Calendar;

public class ViewModelLogBook extends AndroidViewModel {

    Calendar currentDate = null;
    boolean isDate = false;
    boolean isNewClimb = true;
    int addClimbRowId;
    long addClimbDate;

    public ViewModelLogBook(@NonNull Application application) {
        super(application);
    }

    public void setCurrentDate(Calendar date) {
        Log.i("ViewModelLogBook","setCurrentDate: "+TimeUtils.convertDate(date.getTimeInMillis(), "yyyy-MM-dd"));
        currentDate = date;
        setIsDateTrue();
    }
    public Calendar getCurrentDate() {return currentDate;}

    public void setIsDateFalse() {isDate = false;}
    public void setIsDateTrue() {isDate = true;}
    public boolean getIsDate() {return isDate;}

    public void setIsNewClimbTrue() {isNewClimb = true;}
    public void setIsNewClimbFalse() {isNewClimb = false;}
    public boolean getIsNewClimb() {return isNewClimb;}

    public void setAddClimbRowId(int inAddClimbRowId) {addClimbRowId = inAddClimbRowId;}
    public int getAddClimbRowId() {return addClimbRowId;}

    public void setAddClimbDate(long inAddClimbDate) {addClimbDate=inAddClimbDate;}
    public long getAddClimbDate() {return addClimbDate;}



    public void resetData() {
        currentDate=null;
        setIsDateFalse();
        setIsNewClimbTrue();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i("ViewModelLogBook","onCleared");
    }
}
