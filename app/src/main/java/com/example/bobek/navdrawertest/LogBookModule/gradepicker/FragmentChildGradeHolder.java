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
import com.example.bobek.navdrawertest.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentChildGradeHolder extends Fragment {

    private ViewModelAddClimb mViewModelAddClimb;
    private ArrayList<GradeArrayListItem> childGradeArrayList;
    GradeArrayAdapter adapter;
    Context mContext;
    ListView listView;

    public FragmentChildGradeHolder() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModelAddClimb = ViewModelProviders.of(getActivity()).get(ViewModelAddClimb.class);
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
                mViewModelAddClimb.setOutputGradeNumber(adapter.getItem(position).getRowId());
                mViewModelAddClimb.setOutputStringGradeName(adapter.getItem(position).getName());
                exitFragment();
            }
        });

        return view;
    }

    private void mapViews(View view) {
        listView=view.findViewById(R.id.child_list);
    }

    public void refreshData() {
        childGradeArrayList = DatabaseReadWrite.getGradeArrayList(mContext, mViewModelAddClimb.getOutputGradeName());
        adapter = new GradeArrayAdapter(mContext, childGradeArrayList);
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
