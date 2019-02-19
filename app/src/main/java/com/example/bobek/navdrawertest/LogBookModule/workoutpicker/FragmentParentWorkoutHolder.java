package com.example.bobek.navdrawertest.LogBookModule.workoutpicker;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bobek.navdrawertest.DataModule.DatabaseReadWrite;
import com.example.bobek.navdrawertest.LogBookModule.ViewModelAddWorkout;
import com.example.bobek.navdrawertest.MainActivity;
import com.example.bobek.navdrawertest.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentParentWorkoutHolder extends Fragment {


    private ViewModelAddWorkout mViewModelAddWorkout;
    private ArrayList<WorkoutArrayListItem> parentWorkoutArrayList;
    WorkoutArrayAdapter adapter;
    Context mContext;
    ListView listView;

    public FragmentParentWorkoutHolder() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModelAddWorkout = ViewModelProviders.of(getActivity()).get(ViewModelAddWorkout.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_parent_list, container, false);
        mContext = getActivity();
        mapViews(view);
        refreshData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModelAddWorkout.setOutputWorkoutName(adapter.getItem(position).getId());
                mViewModelAddWorkout.setOutputStringWorkoutType(adapter.getItem(position).getName());
                launchChildFragment();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mViewModelAddWorkout.getOutputWorkoutName()!=-1) {
            exitFragment();
        } else {
            refreshData();
        }
    }

    private void launchChildFragment() {
        FragmentChildWorkoutHolder fragmentChildWorkoutHolder = new FragmentChildWorkoutHolder();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContent, fragmentChildWorkoutHolder, MainActivity.fragmentNameChildWorkoutHolder)
                .addToBackStack(null)
                .commit();
    }

    private void mapViews(View view) {
        listView=view.findViewById(R.id.parent_listview);
    }

    public void refreshData() {
        parentWorkoutArrayList = DatabaseReadWrite.getWorkoutTypeArrayList(mContext);
        adapter = new WorkoutArrayAdapter(mContext, parentWorkoutArrayList);
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
