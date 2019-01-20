package com.example.bobek.navdrawertest.LogBookModule;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bobek.navdrawertest.DataModule.DatabaseContract;
import com.example.bobek.navdrawertest.DataModule.DatabaseHelper;
import com.example.bobek.navdrawertest.DataModule.DatabaseReadWrite;
import com.example.bobek.navdrawertest.MainActivity;
import com.example.bobek.navdrawertest.R;
import com.example.bobek.navdrawertest.UtilModule.TimeUtils;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.wang.avi.AVLoadingIndicatorView;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentAddClimb.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentAddClimb#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddClimb extends Fragment {

    private static final String[] LOCATION_PERMS = {Manifest.permission.ACCESS_FINE_LOCATION};
    private static final int LOCATION_REQUEST = 3;
    private static final int REQUEST_CHECK_SETTINGS = 4;

    private ViewModelAddClimb mViewModelAddClimb;
    private ViewModelLogBook mViewModelLogBook;

    Context mContext;

    private AVLoadingIndicatorView ivAvi;
    private ImageView ivGreenTick;
    private ImageView ivGreyCross;
    TextView textViewLatitude;
    TextView textViewLongitude;
    EditText dateView;
    EditText routeNameView;
    EditText locationNewNameView;
    CheckBox firstAscentCheckBox;
    EditText locationNameView;
    EditText ascentTypeView;
    EditText gradeView;
    Button gpsButton;
    Button cancelButton;
    Button saveButton;
    SpinnerDialog spinnerDialog;

    private FusedLocationProviderClient mFusedLocationClient;
    LocationRequest mLocationRequest;
    LocationCallback mLocationCallback;

    private OnFragmentInteractionListener mListener;

    public FragmentAddClimb() {
        // Required empty public constructor
    }

    public static FragmentAddClimb newInstance() {
        FragmentAddClimb fragment = new FragmentAddClimb();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModelAddClimb = ViewModelProviders.of(getActivity()).get(ViewModelAddClimb.class);
        mViewModelLogBook = ViewModelProviders.of(getActivity()).get(ViewModelLogBook.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_climb, container, false);

        mContext = this.getActivity();

        mapViews(view);
        ivGreenTick.setVisibility(View.GONE);
        ivGreyCross.setVisibility(View.VISIBLE);
        ivAvi.hide();

        checkGpsAccessPermission();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    Log.i("AddClimb GPS", "mLocationCallback > onLocationResult = null");
                    ivGreenTick.setVisibility(View.GONE);
                    ivGreyCross.setVisibility(View.VISIBLE);
                    //ivAvi.setVisibility(View.GONE);
                    ivAvi.hide();
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...
                    mViewModelAddClimb.setOutputLatitude(location.getLatitude());
                    textViewLatitude.setText("" + mViewModelAddClimb.getOutputLatitude());
                    mViewModelAddClimb.setOutputLongitude(location.getLongitude());
                    textViewLongitude.setText("" + mViewModelAddClimb.getOutputLongitude());
                    mViewModelAddClimb.setOutputHasGps(DatabaseContract.IS_GPS_TRUE);

                    ivGreenTick.setVisibility(View.VISIBLE);
                    ivGreyCross.setVisibility(View.GONE);
                    ivAvi.hide();

                    // new GPS value... turn off GPS updates
                    Log.i("AddClimb GPS", "mLocationCallback > onLocationResult = turning off location updates");
                    stopLocationUpdates();
                    mViewModelAddClimb.setRequestingLocationUpdates(false);
                }
            }

            ;
        };

        //Intent inputIntent = getIntent();
        //inputIntentCode = inputIntent.getIntExtra("EditOrNewFlag", 0);
        //inputRowID = inputIntent.getIntExtra("RowID", 0);
        //outputDate = inputIntent.getLongExtra("Date", 0);

        mViewModelAddClimb.setInputIsNewClimb(mViewModelLogBook.getIsNewClimb());
        mViewModelAddClimb.setInputRowID(mViewModelLogBook.getAddClimbRowId());
        mViewModelAddClimb.setOutputDate(mViewModelLogBook.getAddClimbDate());

        if (mViewModelAddClimb.getInputIsNewClimb()) {
            // Add a new climb, don't import any data to the form
            //outputDate = Calendar.getInstance().getTimeInMillis();
            dateView.setText(TimeUtils.convertDate(mViewModelAddClimb.getOutputDate(), "yyyy-MM-dd"));
            locationNewNameView.setVisibility(View.GONE); // Hide the location name input view

        } else {
            // Edit existing record, import data into the form
            // Load climb log data for a specific row ID
            mViewModelAddClimb.loadClimbDetails(mContext);
            routeNameView.setText(mViewModelAddClimb.getOutputRouteName());
            dateView.setText(mViewModelAddClimb.getOutputDateString());
            if (mViewModelAddClimb.getOutputFirstAscent() == DatabaseContract.FIRSTASCENT_TRUE) {
                firstAscentCheckBox.setChecked(true);
            } else if (mViewModelAddClimb.getOutputFirstAscent() == DatabaseContract.FIRSTASCENT_FALSE) {
                firstAscentCheckBox.setChecked(false);
            }
            gradeView.setText(mViewModelAddClimb.getOutputStringGradeType() + " | " + mViewModelAddClimb.getOutputStringGradeName());
            ascentTypeView.setText(mViewModelAddClimb.getOutputStringAscentType());
            locationNameView.setText(mViewModelAddClimb.getOutputLocationName());
            locationNewNameView.setVisibility(View.GONE);
            if (mViewModelAddClimb.getOutputHasGps() == DatabaseContract.IS_GPS_TRUE) {
                textViewLatitude.setText("" + mViewModelAddClimb.getOutputLatitude());
                textViewLongitude.setText("" + mViewModelAddClimb.getOutputLongitude());
                ivGreenTick.setVisibility(View.VISIBLE);
                ivGreyCross.setVisibility(View.GONE);
                ivAvi.hide();
            } else {
                textViewLatitude.setText("No data");
                textViewLongitude.setText("No data");
                ivGreenTick.setVisibility(View.GONE);
                ivGreyCross.setVisibility(View.VISIBLE);
                ivAvi.hide();
            }
        }


        // Listener for the grade selection
        gradeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pickGrade();
            }
        });

        // Listener for the ascent-type selection
        ascentTypeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pickAscentType();
            }
        });

        // Listener for the location picker selection
        mViewModelAddClimb.initialiseLocationArrays(mContext);
        spinnerDialog = new SpinnerDialog(this.getActivity(), mViewModelAddClimb.getLocationNames(), "Select Location");
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                if (position == 0) {
                    locationNameView.setText(item);
                    mViewModelAddClimb.setOutputLocationId(mViewModelAddClimb.getLocationIds().get(position));
                    locationNewNameView.setVisibility(View.VISIBLE);
                    mViewModelAddClimb.setOutputIsNewLocation(true);
                } else {
                    mViewModelAddClimb.setOutputIsNewLocation(false);
                    locationNameView.setText(item);
                    mViewModelAddClimb.setOutputLocationId(mViewModelAddClimb.getLocationIds().get(position));
                    locationNewNameView.setVisibility(View.GONE);
                    mViewModelAddClimb.setOutputHasGps(mViewModelAddClimb.getLocationIsGps().get(position));
                    mViewModelAddClimb.setOutputLatitude(mViewModelAddClimb.getLocationLatitudes().get(position));
                    mViewModelAddClimb.setOutputLongitude(mViewModelAddClimb.getLocationLongitudes().get(position));
                    mViewModelAddClimb.setOutputLocationName(item);
                    if (mViewModelAddClimb.getOutputHasGps() == DatabaseContract.IS_GPS_TRUE) {
                        textViewLatitude.setText("" + mViewModelAddClimb.getOutputLatitude());
                        textViewLongitude.setText("" + mViewModelAddClimb.getOutputLongitude());
                        ivGreenTick.setVisibility(View.VISIBLE);
                        ivGreyCross.setVisibility(View.GONE);
                        ivAvi.hide();
                    } else {
                        textViewLatitude.setText("No data");
                        textViewLongitude.setText("No data");
                        ivGreenTick.setVisibility(View.GONE);
                        ivGreyCross.setVisibility(View.VISIBLE);
                        ivAvi.hide();
                    }
                }
            }
        });

        locationNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerDialog.showSpinerDialog();
            }
        });

        // Listener for GPS button
        gpsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mViewModelAddClimb.getGpsAccessPermission()) {
                    Log.i("AddClimb GPS", "onCreate > GPS Button Pressed Access > gpsAccessPermission=true");
                    //gpsGetLastLocation();
                    createLocationRequest();
                    startLocationUpdates();
                } else {
                    Log.i("AddClimb GPS", "onCreate > GPS Button Pressed Access > gpsAccessPermission=false");
                }
            }
        });

        // Listener for checking the first-ascent check-box
        firstAscentCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked) {
                    mViewModelAddClimb.setOutputFirstAscent(DatabaseContract.FIRSTASCENT_TRUE);
                } else {
                    mViewModelAddClimb.setOutputFirstAscent(DatabaseContract.FIRSTASCENT_FALSE);
                }
            }
        });

        // Listener for the save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText locationNameViewOutput;
                if (mViewModelAddClimb.getOutputIsNewLocation()) {
                    locationNameViewOutput = locationNewNameView;
                } else {
                    locationNameViewOutput = locationNameView;
                }

                mViewModelAddClimb.setOutputRouteName(routeNameView.getText().toString());
                mViewModelAddClimb.setOutputLocationName(locationNameViewOutput.getText().toString());

                if (checkDataFields()) {
                    if (mViewModelAddClimb.getInputIsNewClimb()) {
                        mViewModelAddClimb.saveNewClimb(mContext);
                        exitFragment();
                    } else {
                        mViewModelAddClimb.updateExistingClimb(mContext);
                        exitFragment();
                    }
                }

            }
        });


        // Listener for the cancel button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitFragment();
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == RESULT_OK) {
                Log.i("AddClimb GPS", "onActivityResult = Success!");
                mViewModelAddClimb.setRequestingLocationUpdates(true);
            } else {
                Log.i("AddClimb GPS", "onActivityResult = No Success!");
            }
        }
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

    protected void exitFragment() {
        mViewModelAddClimb.resetData();
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fragmentManager.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            //super.onBackPressed();
        }
    }

    protected void mapViews(View view) {
        ivGreenTick = view.findViewById(R.id.iv_green_tick);
        ivGreyCross = view.findViewById(R.id.iv_grey_cross);
        ivAvi = view.findViewById(R.id.iv_av_loading_indicator);
        textViewLatitude = view.findViewById(R.id.tv_latitude);
        textViewLongitude = view.findViewById(R.id.tv_longitude);
        routeNameView = view.findViewById(R.id.et_add_climb_1);
        locationNewNameView = view.findViewById(R.id.et_add_climb_2);
        locationNameView = view.findViewById(R.id.et_add_climb_2a);
        ascentTypeView = view.findViewById(R.id.et_add_climb_3);
        gradeView = view.findViewById(R.id.et_add_climb_4);
        dateView = view.findViewById(R.id.et_add_climb_5);
        firstAscentCheckBox = view.findViewById(R.id.cb_add_climb_firstascent);
        gpsButton = view.findViewById(R.id.bt_getGps);
        cancelButton = view.findViewById(R.id.bt_add_climb_cancel);
        saveButton = view.findViewById(R.id.bt_add_climb_save);
    }

    // check permission for accessing location
    private void checkGpsAccessPermission() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.i("AddClimb GPS", "checkGpsAccessPermission = No access, ask get permission");
                requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);
            }
        } else {
            mViewModelAddClimb.setGpsAccessPermission(true);
        }
    }

    // handle gps permission request result
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mViewModelAddClimb.setGpsAccessPermission(true);
            Log.i("AddClimb GPS", "onRequestPermissionsResult = Access granted");
        }

    }

    // get the last GPS location
    @SuppressLint("MissingPermission")
    private void gpsGetLastLocation() {
        mFusedLocationClient.getLastLocation().addOnSuccessListener((Activity) mContext, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    mViewModelAddClimb.setOutputLatitude(location.getLatitude());
                    textViewLatitude.setText("" + mViewModelAddClimb.getOutputLatitude());
                    mViewModelAddClimb.setOutputLongitude(location.getLongitude());
                    textViewLongitude.setText("" + mViewModelAddClimb.getOutputLongitude());
                    mViewModelAddClimb.setOutputHasGps(DatabaseContract.IS_GPS_TRUE);
                } else {
                }
            }
        });
    }

    // create the location request
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000); // 10 seonds
        mLocationRequest.setFastestInterval(5000); // 5 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        SettingsClient client = LocationServices.getSettingsClient(mContext);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener((Activity) mContext, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
                mViewModelAddClimb.setRequestingLocationUpdates(true);
            }
        });

        task.addOnFailureListener((Activity) mContext, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    Log.i("AddClimb GPS", "createLocationRequest > task.addOnFailureListener = No Success!");
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult((Activity) mContext,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        Log.i("AddClimb GPS", "startLocationUpdates = starting updates");

        if (mViewModelAddClimb.getGpsAccessPermission()) {
            ivGreenTick.setVisibility(View.GONE);
            ivGreyCross.setVisibility(View.GONE);
            //ivAvi.setVisibility(View.VISIBLE);
            ivAvi.show();
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null /* Looper */);
        }
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        ivAvi.hide();
        Log.i("AddClimb GPS", "stopLocationUpdates = stopping location updates");
    }

    private boolean checkDataFields() {

        boolean trigger = true;

        if (mViewModelAddClimb.getOutputDate() == -1) {
            trigger = false;
            toggleEditTextColor(dateView, false);
            Log.i("checkDataFields", "outputDate");
        } else {
            toggleEditTextColor(dateView, true);
        }

        if (mViewModelAddClimb.getOutputRouteName().trim().equals("")) {
            trigger = false;
            toggleEditTextColor(routeNameView, false);
            Log.i("checkDataFields", "outputRouteName");
        } else {
            toggleEditTextColor(routeNameView, true);
        }

        if (mViewModelAddClimb.getOutputAscent() == -1) {
            trigger = false;
            toggleEditTextColor(ascentTypeView, false);
            Log.i("checkDataFields", "outputAscent");
        } else {
            toggleEditTextColor(ascentTypeView, true);
        }

        if (mViewModelAddClimb.getOutputGradeName() == -1) {
            trigger = false;
            toggleEditTextColor(gradeView, false);
            Log.i("checkDataFields", "outputGradeName");
        } else {
            toggleEditTextColor(gradeView, true);
        }

        if (mViewModelAddClimb.getOutputGradeNumber() == -1) {
            trigger = false;
            toggleEditTextColor(gradeView, false);
            Log.i("checkDataFields", "outputGradeNumber");
        } else {
            toggleEditTextColor(gradeView, true);
        }

        if (mViewModelAddClimb.getOutputFirstAscent() == -1) {
            trigger = false;
            Log.i("checkDataFields", "outputFirstAscent");
        }

        // create new location, but no name entered
        if (mViewModelAddClimb.getOutputLocationId() == -2 && mViewModelAddClimb.getOutputLocationName().trim().equals("")) {
            trigger = false;
            toggleEditTextColor(locationNewNameView, false);
            Log.i("checkDataFields", "outputLocationId & outputLocationName");
        } else {
            toggleEditTextColor(locationNewNameView, true);
        }

        // location not entered at all
        if (mViewModelAddClimb.getOutputLocationId() == -1) {
            trigger = false;
            toggleEditTextColor(locationNameView, false);
            Log.i("checkDataFields", "outputLocationId");
        } else {
            toggleEditTextColor(locationNameView, true);
        }


        if (!trigger) {
            Toast.makeText(mContext, "Insufficient information - please ensure all fields are filled", Toast.LENGTH_SHORT).show();
        }

        return trigger;

    }

    private void toggleEditTextColor(EditText view, boolean state) {
        if (state) {
            view.setHintTextColor((getResources().getColor(R.color.availableInput)));
        } else {
            view.setHintTextColor((getResources().getColor(R.color.missingInput)));
        }
    }
}
