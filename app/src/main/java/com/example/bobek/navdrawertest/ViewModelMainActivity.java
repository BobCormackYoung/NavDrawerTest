package com.example.bobek.navdrawertest;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

public class ViewModelMainActivity extends AndroidViewModel {

    Class fragmentClass = null;

    public ViewModelMainActivity(@NonNull Application application) {
        super(application);
    }

    Class getFragmentClass() {return fragmentClass;};

    public void setFragmentClass(Class fragment) {
        this.fragmentClass = fragment;
    }
}
