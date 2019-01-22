package com.example.bobek.navdrawertest.LogBookModule.gradepicker;


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
import com.example.bobek.navdrawertest.LogBookModule.ViewModelAddClimb;
import com.example.bobek.navdrawertest.LogBookModule.ascentpicker.AscentArrayAdapter;
import com.example.bobek.navdrawertest.LogBookModule.ascentpicker.AscentArrayListItem;
import com.example.bobek.navdrawertest.LogBookModule.ascentpicker.FragmentAscentHolder;
import com.example.bobek.navdrawertest.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentParentGradeHolder extends Fragment {

    private ViewModelAddClimb mViewModelAddClimb;
    private ArrayList<GradeArrayListItem> parentGradeArrayList;
    GradeArrayAdapter adapter;
    Context mContext;
    ListView listView;

    public FragmentParentGradeHolder() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModelAddClimb = ViewModelProviders.of(getActivity()).get(ViewModelAddClimb.class);
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
                mViewModelAddClimb.setOutputGradeName(adapter.getItem(position).getRowId());
                mViewModelAddClimb.setOutputStringGradeType(adapter.getItem(position).getName());
                launchChildFragment();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mViewModelAddClimb.getOutputGradeName()!=-1) {
            exitFragment();
        }
    }

    private void launchChildFragment() {
        FragmentChildGradeHolder fragmentChildGradeHolder = new FragmentChildGradeHolder();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContent, fragmentChildGradeHolder, "fragmentChildGradeHolder")
                .addToBackStack(null)
                .commit();
    }

    private void mapViews(View view) {
        listView=view.findViewById(R.id.parent_listview);
    }

    public void refreshData() {
        parentGradeArrayList = DatabaseReadWrite.getGradeTypeArrayList(mContext);
        adapter = new GradeArrayAdapter(mContext, parentGradeArrayList);
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
