package com.example.bobek.navdrawertest.LogBookModule.workoutpicker;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.bobek.navdrawertest.DataModule.DatabaseReadWrite;
import com.example.bobek.navdrawertest.LogBookModule.ViewModelAddClimb;
import com.example.bobek.navdrawertest.LogBookModule.ViewModelAddWorkout;
import com.example.bobek.navdrawertest.LogBookModule.gradepicker.GradeArrayAdapter;
import com.example.bobek.navdrawertest.LogBookModule.gradepicker.GradeArrayListItem;
import com.example.bobek.navdrawertest.R;

import java.util.ArrayList;

public class FragmentChildWorkoutHolder extends Fragment {

    private ViewModelAddWorkout mViewModelAddWorkout;
    private ArrayList<WorkoutArrayListItem> childWorkoutArrayList;
    WorkoutArrayAdapter adapter;
    Context mContext;
    ListView listView;

    public FragmentChildWorkoutHolder() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModelAddWorkout = ViewModelProviders.of(getActivity()).get(ViewModelAddWorkout.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_child_list, container, false);
        mContext = getActivity();
        mapViews(view);
        refreshData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModelAddWorkout.setOutputWorkoutNumber(adapter.getItem(position).getId());
                mViewModelAddWorkout.setOutputStringWorkoutName(adapter.getItem(position).getName());
                exitFragment();
            }
        });

        return view;
    }

    private void mapViews(View view) {
        listView=view.findViewById(R.id.child_list);
    }

    public void refreshData() {
        childWorkoutArrayList = DatabaseReadWrite.getWorkoutArrayList(mContext, mViewModelAddWorkout.getOutputWorkoutName());
        adapter = new WorkoutArrayAdapter(mContext, childWorkoutArrayList);
        listView.setAdapter(adapter);
    }

    protected void exitFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            //super.onBackPressed();
        }
    }




}
