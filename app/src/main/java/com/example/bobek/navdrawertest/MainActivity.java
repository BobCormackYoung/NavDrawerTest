package com.example.bobek.navdrawertest;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.bobek.navdrawertest.LogBookModule.FragmentAddClimb;
import com.example.bobek.navdrawertest.LogBookModule.FragmentAddWorkout;
import com.example.bobek.navdrawertest.LogBookModule.FragmentLogBook;
import com.example.bobek.navdrawertest.LogBookModule.ViewModelAddClimb;
import com.example.bobek.navdrawertest.LogBookModule.ViewModelAddWorkout;
import com.example.bobek.navdrawertest.LogBookModule.ViewModelLogBook;
import com.example.bobek.navdrawertest.LogBookModule.gradepicker.FragmentChildGradeHolder;
import com.example.bobek.navdrawertest.LogBookModule.gradepicker.FragmentParentGradeHolder;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment = null;
    Class fragmentClass;
    private ViewModelMainActivity mViewModelMainActivity;
    private ViewModelLogBook mViewModelLogBook;
    private ViewModelAddClimb mViewModelAddClimb;
    private ViewModelAddWorkout mViewModelAddWorkout;
    static DrawerLayout drawer;

    String fragmentName;
    public static String fragmentNameDashboard = "FragmentDashboard";
    public static String fragmentNameLogBook = "FragmentLogBook";
    public static String fragmentNameCalendar = "FragmentCalendar";
    public static String fragmentNameLocationManager = "FragmentLocationManager";
    public static String fragmentNameAddClimb = "fragmentAddClimb";
    public static String fragmentNameAddWorkout = "fragmentAddWorkout";
    public static String fragmentNameChildGradeHolder = "fragmentChildGradeHolder";
    public static String fragmentNameParentGradeHolder = "fragmentParentGradeHolder";
    public static String fragmentNameChildWorkoutHolder = "fragmentChildWorkoutHolder";
    public static String fragmentNameParentWorkoutHolder = "fragmentParentWorkoutHolder";
    public static String fragmentNameAscentHolder = "fragmentAscentHolder";
    public static FragmentParentGradeHolder fragmentParentGradeHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);

        // Get a new or existing ViewModel from the ViewModelProvider.
        // ViewModel for main activity
        mViewModelMainActivity = ViewModelProviders.of(this).get(ViewModelMainActivity.class);
        // ViewModel for LogBookData
        mViewModelLogBook = ViewModelProviders.of(this).get(ViewModelLogBook.class);
        mViewModelAddClimb = ViewModelProviders.of(this).get(ViewModelAddClimb.class);
        mViewModelAddWorkout = ViewModelProviders.of(this).get(ViewModelAddWorkout.class);

        fragmentParentGradeHolder = new FragmentParentGradeHolder();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (mViewModelMainActivity.getFragmentClass() == null){
            fragmentClass = FragmentDashboard.class;
        } else {
            fragmentClass = mViewModelMainActivity.getFragmentClass();
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

    }

    @Override
    public void onBackPressed() {
        //List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        //int fragCount = fragmentList.size();
        //Fragment lastFragment = fragmentList.get(fragCount-1);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.flContent);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fragment instanceof FragmentAddClimb) {
            mViewModelAddClimb.resetData();
            getSupportFragmentManager().popBackStack();
        } else if (fragment instanceof FragmentAddWorkout) {
            mViewModelAddWorkout.resetData();
            getSupportFragmentManager().popBackStack();
        //} else if (fragment instanceof FragmentChildGradeHolder) {

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            fragmentClass = FragmentDashboard.class;
            fragmentName = fragmentNameDashboard;
            mViewModelLogBook.resetData();
        } else if (id == R.id.nav_log_book) {
            fragmentClass = FragmentLogBook.class;
            fragmentName = fragmentNameLogBook;
        } else if (id == R.id.nav_calendar) {
            fragmentClass = FragmentCalendar.class;
            fragmentName = fragmentNameCalendar;
            mViewModelLogBook.resetData();
        } else if (id == R.id.nav_location_manager) {
            fragmentClass = FragmentLocationManager.class;
            fragmentName = fragmentNameLocationManager;
            mViewModelLogBook.resetData();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        try {
            mViewModelMainActivity.setFragmentClass(fragmentClass);
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment, fragmentName).commit();


        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static DrawerLayout getDrawer() {
        return drawer;
    }

    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }



}
