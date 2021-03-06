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
import com.example.bobek.navdrawertest.MainActivity;
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
    Bundle inputBundle;

    public FragmentChildGradeHolder() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModelAddClimb = ViewModelProviders.of(getActivity()).get(ViewModelAddClimb.class);
        inputBundle=getArguments();
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

                mViewModelAddClimb.setOutputGradeName(inputBundle.getInt("gradeTypeKey"));
                mViewModelAddClimb.setOutputStringGradeType(inputBundle.getString("gradeTypeName"));

                Log.i("ChildGradeHolder","GradeName: " + mViewModelAddClimb.getOutputStringGradeName());
                Log.i("ChildGradeHolder","GradeType: " + mViewModelAddClimb.getOutputStringGradeType());

                exitFragment();
            }
        });

        return view;
    }

    private void mapViews(View view) {
        listView=view.findViewById(R.id.child_list);
    }

    public void refreshData() {
        childGradeArrayList = DatabaseReadWrite.getGradeArrayList(mContext, inputBundle.getInt("gradeTypeKey"));
        adapter = new GradeArrayAdapter(mContext, childGradeArrayList);
        listView.setAdapter(adapter);
    }

    protected void exitFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack(fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount()-2).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            //fragmentManager.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            //super.onBackPressed();
        }
    }

}
