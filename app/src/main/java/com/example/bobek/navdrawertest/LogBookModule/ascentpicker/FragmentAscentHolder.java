package com.example.bobek.navdrawertest.LogBookModule.ascentpicker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bobek.navdrawertest.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAscentHolder#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAscentHolder extends Fragment {



    public FragmentAscentHolder() {
        // Required empty public constructor
    }


    public static FragmentAscentHolder newInstance() {
        FragmentAscentHolder fragment = new FragmentAscentHolder();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }

}
