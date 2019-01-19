package com.example.bobek.navdrawertest.LogBookModule;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bobek.navdrawertest.R;
import com.example.bobek.navdrawertest.UtilModule.CachingFragmentStatePagerAdapter;
import com.example.bobek.navdrawertest.UtilModule.TimeUtils;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentLogBook.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentLogBook#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLogBook extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ViewModelLogBook mViewModelLogBook;

    private static Context mContext;
    //final int ADD_CLIMB_NEW = 0;
    //final int ADD_CLIMB_EDIT = 1;
    //final int ADD_WORKOUT_NEW = 0;
    //final int ADD_WORKOUT_EDIT = 1;
    //private CachingFragmentStatePagerAdapter adapterViewPager;
    final long DAYPERIOD = 86400000;
    private static Calendar currentDate;
    private CachingFragmentStatePagerAdapter adapterViewPager;
    //private long outputDate;

    private static final String ARG_PARAM1 = "param1";

    View view;
    TextView logBookHeader;
    View button_previous_day;
    View button_next_day;
    ViewPager viewPager;
    View button_add_workout;
    View button_add_climb;
    View button_copy_climb;

    public FragmentLogBook() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public FragmentLogBook newInstance() {
        FragmentLogBook fragment = new FragmentLogBook();

        //currentDate = Calendar.getInstance();
        //long outputDate = currentDate.getTimeInMillis();

        //mViewModelLogBook = ViewModelProviders.of(this).get(ViewModelLogBook.class);
        //mViewModelLogBook.setCurrentDate(Calendar.getInstance().getTimeInMillis());

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the LogBook ViewModel from the parent class
        mViewModelLogBook = ViewModelProviders.of(getActivity()).get(ViewModelLogBook.class);
        if (!mViewModelLogBook.getIsDate()) {mViewModelLogBook.setCurrentDate(Calendar.getInstance());}
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_log_book, container, false);
        mapViews();
        adapterViewPager = new MyPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapterViewPager);

        // Set pager to current date
        viewPager.setCurrentItem(TimeUtils.getPositionForDay(mViewModelLogBook.getCurrentDate()));
        refreshViews();
        // Set PageChangeListener
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            // When new page is selected, udpate the header to match the new date
            @Override
            public void onPageSelected(int position) {
                Calendar newDate = TimeUtils.getDayForPosition(position);
                mViewModelLogBook.setCurrentDate(newDate);
                refreshViews();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Previous Day Button
        button_previous_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewPager.getCurrentItem();
                int newPosition = position - 1;
                viewPager.setCurrentItem(newPosition, true);
                //Calendar newDate = TimeUtils.getDayForPosition(newPosition);
                //mViewModelLogBook.setCurrentDate(newDate);
                refreshViews();
            }
        });

        // Next Day Button
        button_next_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewPager.getCurrentItem();
                int newPosition = position + 1;
                viewPager.setCurrentItem(newPosition, true);
                //Calendar newDate = TimeUtils.getDayForPosition(newPosition);
                //mViewModelLogBook.setCurrentDate(newDate);
                refreshViews();
            }
        });

        // Add Climb Button
        button_add_climb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the currently displayed page position
                // Get it's calendar instance (date)
                // Convert date to milliseconds
                int position = viewPager.getCurrentItem();
                Calendar cal = TimeUtils.getDayForPosition(position);
                long date = cal.getTimeInMillis();

                mViewModelLogBook.setIsNewClimbTrue();
                mViewModelLogBook.setAddClimbDate(date);
                mViewModelLogBook.setAddClimbRowId(-1);

                FragmentAddClimb fragmentAddClimb = new FragmentAddClimb();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent, fragmentAddClimb, "fragmentAddClimb")
                        .addToBackStack(null)
                        .commit();
            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("FragmentLogBook","onAttach");
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Log.i("FragmentLogBook","onDetach");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void mapViews() {
        logBookHeader = view.findViewById(R.id.textview_date);
        button_previous_day = view.findViewById(R.id.button_previous_day);
        button_next_day = view.findViewById(R.id.button_next_day);
        viewPager = view.findViewById(R.id.log_book_viewpager);
        button_add_workout = view.findViewById(R.id.button_add_workout);
        button_add_climb = view.findViewById(R.id.button_log_climb);
        button_copy_climb = view.findViewById(R.id.button_copy_workout);
    }

    private void refreshViews() {
        logBookHeader.setText(TimeUtils.convertDate(mViewModelLogBook.getCurrentDate().getTimeInMillis(), "yyyy-MM-dd"));
    }

    // Pager Adapter
    public static class MyPagerAdapter extends CachingFragmentStatePagerAdapter {

        private Calendar cal;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return TimeUtils.DAYS_OF_TIME;
        }

        @Override
        public Fragment getItem(int position) {
            long timeForPosition = TimeUtils.getDayForPosition(position).getTimeInMillis();
            return FragmentLogBookViewPager.newInstance(timeForPosition);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Calendar cal = TimeUtils.getDayForPosition(position);
            return TimeUtils.getFormattedDate(mContext, cal.getTimeInMillis());
        }


    }

}
