package com.example.bobek.navdrawertest.LogBookModule;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bobek.navdrawertest.DataModule.DatabaseContract;
import com.example.bobek.navdrawertest.DataModule.DatabaseReadWrite;
import com.example.bobek.navdrawertest.LogBookModule.gradepicker.FragmentParentGradeHolder;
import com.example.bobek.navdrawertest.LogBookModule.workoutpicker.FragmentParentWorkoutHolder;
import com.example.bobek.navdrawertest.MainActivity;
import com.example.bobek.navdrawertest.R;
import com.example.bobek.navdrawertest.UtilModule.TimeUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAddWorkout extends Fragment {

    Context mContext;

    final int PICK_TRAINING_REQUEST = 1;
    final int PICK_GRADE_REQUEST = 2;
    final int PICK_HOLDTYPE_REQUEST = 3;
    final int ADD_WORKOUT_NEW = 0;
    final int ADD_WORKOUT_EDIT = 1;
    final int INCREMENT_TIME_SHORT = 5000; // 5 seconds in milliseconds
    final int INCREMENT_TIME_LONG = 60000; // 1 minute in milliseconds
    final double INCREMENT_WEIGHT_SMALL = 0.5; // 0.5 KG
    final double INCREMENT_WEIGHT_LARGE = 5; // 5 KG
    final int INCREMENT_COUNT_SMALL = 1; // set count
    final int INCREMENT_COUNT_LARGE = 5; // set count
    final int ABSOLUTE_MINIMUM = 0;

    private ViewModelLogBook mViewModelLogBook;
    private ViewModelAddWorkout mViewModelAddWorkout;

    TextView titleWrapper; // Title wrapper

    // Chronometer
    LinearLayout chronometerWrapper;
    Chronometer chronometerTimer;
    Button chronometerStart;
    Button chronometerPause;
    Button chronometerReset;
    Button chronometerSave;

    LinearLayout workoutDateWrapper; // Date wrapper
    EditText workoutDateEditText; // Date EditText

    LinearLayout workoutTrainingWrapper; // Training type/name wrapper
    EditText workoutTrainingEditText; // Training type/name EditText

    LinearLayout workoutWeightWrapper; // Weight wrapper
    EditText workoutWeightEditText; // Weight EditText
    Button workoutWeightButtonMinus; // Weight MinusButton
    Button workoutWeightButtonPlus; // Weight PlusButton
    Button workoutWeightButtonMinusBig;  // Weight MinusButton
    Button workoutWeightButtonPlusBig;  // Weight PlusButton

    LinearLayout workoutSetCountWrapper; // Set Count  Wrapper
    EditText workoutSetCountEditText; // Set Count EditText
    Button workoutSetCountButtonMinus; // Set Count MinusButton
    Button workoutSetCountButtonPlus; // Set Count PlusButton
    Button workoutSetCountButtonMinusBig; // Set Count MinusButton
    Button workoutSetCountButtonPlusBig; // Set Count PlusButton

    LinearLayout workoutRepCountWrapper; // Rep Count Wrapper
    EditText workoutRepCountEditText; // Set Count EditText
    Button workoutRepCountButtonMinus; // Set Count MinusButton
    Button workoutRepCountButtonPlus; // Set Count PlusButton
    Button workoutRepCountButtonMinusBig; // Set Count MinusButton
    Button workoutRepCountButtonPlusBig; // Set Count PlusButton

    LinearLayout workoutRepDurationWrapper; // Rep Duration Wrapper
    EditText workoutRepDurationEditText; // Rep Duration EditText
    Button workoutRepDurationButtonMinus; // Rep Duration MinusButton
    Button workoutRepDurationButtonPlus; // Rep Duration PlusButton
    Button workoutRepDurationButtonMinusBig; // Rep Duration MinusButton
    Button workoutRepDurationButtonPlusBig; // Rep Duration PlusButton

    LinearLayout workoutRestWrapper; // Rest per set wrapper
    EditText workoutRestEditText; // Rest per set EditText
    Button workoutRestButtonMinus; // Rest per set MinusButton
    Button workoutRestButtonPlus; // Rest per set PlusButton
    Button workoutRestButtonMinusBig; // Rest per set MinusButton
    Button workoutRestButtonPlusBig; // Rest per set PlusButton

    LinearLayout workoutGradeWrapper; // Grade Wrapper
    EditText workoutGradeEditText; // Grade EditText

    LinearLayout workoutMoveCountWrapper; // Move Count wrapper
    EditText workoutMoveCountEditText; // Move Count EditText
    Button workoutMoveCountButtonMinus; // Move Count MinusButton
    Button workoutMoveCountButtonPlus; // Move Count PlusButton
    Button workoutMoveCountButtonMinusBig; // Move Count MinusButton
    Button workoutMoveCountButtonPlusBig; // Move Count PlusButton

    LinearLayout workoutWallAngleWrapper; // Wall Angle wrapper
    EditText workoutWallAngleEditText; // Wall Angle EditText
    Button workoutWallAngleButtonMinus; // Wall Angle MinusButton
    Button workoutWallAngleButtonPlus; // Wall Angle PlusButton
    Button workoutWallAngleButtonMinusBig; // Wall Angle MinusButton
    Button workoutWallAngleButtonPlusBig; // Wall Angle PlusButton

    LinearLayout workoutHoldTypeWrapper; // Hold Type Wrapper
    EditText workoutHoldTypeEditText; // Hold Type EditText

    LinearLayout workoutCompleteWrapper; // Hold Type Wrapper
    CheckBox workoutCompleteCheckBox; // Hold Type EditText

    Button saveButton;
    Button cancelButton;

    public FragmentAddWorkout() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModelLogBook = ViewModelProviders.of(getActivity()).get(ViewModelLogBook.class);
        mViewModelAddWorkout = ViewModelProviders.of(getActivity()).get(ViewModelAddWorkout.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_workout, container, false);
        mContext = this.getActivity();
        mapViews(view);

        mViewModelAddWorkout.setOutputIsNewWorkout(mViewModelLogBook.getIsNewWorkout());
        mViewModelAddWorkout.setInputRowID(mViewModelLogBook.getAddWorkoutRowId());
        mViewModelAddWorkout.setOutputDate(mViewModelLogBook.getAddClimbDate());

        if (mViewModelAddWorkout.getOutputIsNewWorkout()) {

            workoutDateEditText.setText(TimeUtils.convertDate(mViewModelAddWorkout.getOutputDate(), "yyyy-MM-dd"));

            showViews();

        } else {

            // Get the workout entry information
            mViewModelAddWorkout.getWorkoutInfo(mContext);

            // Get which fields should be shown
            mViewModelAddWorkout.getTrainingInputFields(mContext);

            //show the relevant views
            showViews();

            //fill the views with data
            fillViews();
        }

        onClickListenerInitiation();

        return view;
    }

    private void mapViews(View view) {

        // Chronometer
        chronometerWrapper = view.findViewById(R.id.chronometerWrapper);
        chronometerTimer = view.findViewById(R.id.chronometer);
        chronometerStart = view.findViewById(R.id.buttonStartChrono);
        chronometerPause = view.findViewById(R.id.buttonPauseChrono);
        chronometerReset = view.findViewById(R.id.buttonResetChrono);
        chronometerSave = view.findViewById(R.id.buttonSaveChrono);

        // Date Wrapper
        workoutDateWrapper = view.findViewById(R.id.workoutTextWrapper1);
        workoutDateEditText = view.findViewById(R.id.workoutEditText1);

        // Workout Type Wrapper
        workoutTrainingWrapper = view.findViewById(R.id.workoutTextWrapper2);
        workoutTrainingEditText = view.findViewById(R.id.workoutEditText2);

        // Weight Data Input Wrapper
        workoutWeightWrapper = view.findViewById(R.id.workoutTextWrapper3);
        workoutWeightEditText = view.findViewById(R.id.workoutEditText3);
        workoutWeightButtonMinus = view.findViewById(R.id.workoutButtonMinus3);
        workoutWeightButtonPlus = view.findViewById(R.id.workoutButtonPlus3);
        workoutWeightButtonMinusBig = view.findViewById(R.id.workoutButtonMinus3a);
        workoutWeightButtonPlusBig = view.findViewById(R.id.workoutButtonPlus3a);

        // Set Count Input Wrapper
        workoutSetCountWrapper = view.findViewById(R.id.workoutTextWrapper4);
        workoutSetCountEditText = view.findViewById(R.id.workoutEditText4);
        workoutSetCountButtonMinus = view.findViewById(R.id.workoutButtonMinus4);
        workoutSetCountButtonPlus = view.findViewById(R.id.workoutButtonPlus4);
        workoutSetCountButtonMinusBig = view.findViewById(R.id.workoutButtonMinus4a);
        workoutSetCountButtonPlusBig = view.findViewById(R.id.workoutButtonPlus4a);

        // Rep Count Input Wrapper
        workoutRepCountWrapper = view.findViewById(R.id.workoutTextWrapper5);
        workoutRepCountEditText = view.findViewById(R.id.workoutEditText5);
        workoutRepCountButtonMinus = view.findViewById(R.id.workoutButtonMinus5);
        workoutRepCountButtonPlus = view.findViewById(R.id.workoutButtonPlus5);
        workoutRepCountButtonMinusBig = view.findViewById(R.id.workoutButtonMinus5a);
        workoutRepCountButtonPlusBig = view.findViewById(R.id.workoutButtonPlus5a);

        // Rep Duration Input Wrapper
        workoutRepDurationWrapper = view.findViewById(R.id.workoutTextWrapper6);
        workoutRepDurationEditText = view.findViewById(R.id.workoutEditText6);
        workoutRepDurationButtonMinus = view.findViewById(R.id.workoutButtonMinus6);
        workoutRepDurationButtonPlus = view.findViewById(R.id.workoutButtonPlus6);
        workoutRepDurationButtonMinusBig = view.findViewById(R.id.workoutButtonMinus6a);
        workoutRepDurationButtonPlusBig = view.findViewById(R.id.workoutButtonPlus6a);

        // Rest Per Set Input Wrapper
        workoutRestWrapper = view.findViewById(R.id.workoutTextWrapper7);
        workoutRestEditText = view.findViewById(R.id.workoutEditText7);
        workoutRestButtonMinus = view.findViewById(R.id.workoutButtonMinus7);
        workoutRestButtonPlus = view.findViewById(R.id.workoutButtonPlus7);
        workoutRestButtonMinusBig = view.findViewById(R.id.workoutButtonMinus7a);
        workoutRestButtonPlusBig = view.findViewById(R.id.workoutButtonPlus7a);

        // Grade or Difficulty Input Wrapper
        workoutGradeWrapper = view.findViewById(R.id.workoutTextWrapper8);
        workoutGradeEditText = view.findViewById(R.id.workoutEditText8);

        // Move Count Input Wrapper
        workoutMoveCountWrapper = view.findViewById(R.id.workoutTextWrapper9);
        workoutMoveCountEditText = view.findViewById(R.id.workoutEditText9);
        workoutMoveCountButtonMinus = view.findViewById(R.id.workoutButtonMinus9);
        workoutMoveCountButtonPlus = view.findViewById(R.id.workoutButtonPlus9);
        workoutMoveCountButtonMinusBig = view.findViewById(R.id.workoutButtonMinus9a);
        workoutMoveCountButtonPlusBig = view.findViewById(R.id.workoutButtonPlus9a);

        // Wall Angle Input Wrapper
        workoutWallAngleWrapper = view.findViewById(R.id.workoutTextWrapper10);
        workoutWallAngleEditText = view.findViewById(R.id.workoutEditText10);
        workoutWallAngleButtonMinus = view.findViewById(R.id.workoutButtonMinus10);
        workoutWallAngleButtonPlus = view.findViewById(R.id.workoutButtonPlus10);
        workoutWallAngleButtonMinusBig = view.findViewById(R.id.workoutButtonMinus10a);
        workoutWallAngleButtonPlusBig = view.findViewById(R.id.workoutButtonPlus10a);

        // Hold Type Input Wrapper
        workoutHoldTypeWrapper = view.findViewById(R.id.workoutTextWrapper11);
        workoutHoldTypeEditText = view.findViewById(R.id.workoutEditText11);

        // Completeness Input Wrapper
        workoutCompleteWrapper = view.findViewById(R.id.workoutTextWrapper12);
        workoutCompleteCheckBox = view.findViewById(R.id.workoutCheckBox12);

        saveButton = view.findViewById(R.id.log_training_save);
        cancelButton = view.findViewById(R.id.log_training_cancel);
    }

    private void onClickListenerInitiation() {

        Log.i("AddWorkout", "onClickListenerInitiation");

        // Listener for the training selection
        workoutTrainingEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewModelAddWorkout.getOutputIsNewWorkout()) {
                    // Only allow workout type to be changed if saving a new workout
                    pickTraining();
                    mViewModelAddWorkout.resetOutputs();
                    fillViews();
                }
            }
        });

        // Listener for the save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        //TODO: update saving logic
                /*if (workoutCompleteCheckBox.isChecked()) {
                    mViewModelAddWorkout.setOutputCompleteCheckedState(1);
                } else {
                    mViewModelAddWorkout.setOutputCompleteCheckedState(0);
                }

                if (inputIntentCode == ADD_WORKOUT_NEW) {
                    if (outputWorkoutNumber != -1 && outputWorkoutName != -1) {
                        long outputRow = DatabaseReadWrite.writeWorkoutLogData(outputDate, outputWorkoutName, outputWorkoutNumber, counterWeight, counterSetCount,
                                counterRepCount, counterRepTime, counterRestTime, outputGradeName, outputGradeNumber, counterMoveCount, outputHoldType, counterWallAngle, outputCompleteCheckedState, AddWorkout.this);
                        DatabaseReadWrite.writeCalendarUpdate(DatabaseContract.IS_WORKOUT, outputDate, outputRow, AddWorkout.this);
                        //Toast.makeText(getApplicationContext(), "New Row ID: " + outputRow, Toast.LENGTH_SHORT).show();
                        exitFragment();
                    } else {
                        Toast.makeText(getApplicationContext(), "No workout type selected", Toast.LENGTH_SHORT).show();
                    }
                } else if (inputIntentCode == ADD_WORKOUT_EDIT) {
                    long outputRow = DatabaseReadWrite.updateWorkoutLogData(outputDate, outputWorkoutName, outputWorkoutNumber, counterWeight, counterSetCount,
                            counterRepCount, counterRepTime, counterRestTime, outputGradeName, outputGradeNumber, counterMoveCount, outputHoldType, counterWallAngle, inputRowID, outputCompleteCheckedState, AddWorkout.this);
                    //Toast.makeText(getApplicationContext(), "New Row ID: " + outputRow, Toast.LENGTH_SHORT).show();
                    exitFragment();
                }*/
                exitFragment();
            }
        });

        // Listener for the cancel button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitFragment();
            }
        });

        // Chronometer
        chronometerStart.setClickable(true);
        chronometerPause.setClickable(false);
        chronometerSave.setClickable(false);
        chronometerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometerTimer.setBase(SystemClock.elapsedRealtime() + mViewModelAddWorkout.getStopTime());
                chronometerTimer.start();
                chronometerStart.setClickable(false);
                chronometerPause.setClickable(true);
                chronometerSave.setClickable(false);
            }
        });
        chronometerPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.setStopTime(chronometerTimer.getBase() - SystemClock.elapsedRealtime());
                chronometerTimer.stop();
                chronometerStart.setClickable(true);
                chronometerPause.setClickable(false);
                chronometerSave.setClickable(true);
            }
        });
        chronometerReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometerTimer.setBase(SystemClock.elapsedRealtime());
                mViewModelAddWorkout.setStopTime(0);
                chronometerTimer.stop();
                chronometerStart.setClickable(true);
                chronometerPause.setClickable(false);
                chronometerSave.setClickable(false);
            }
        });
        chronometerSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.setSaveTime(Math.abs( mViewModelAddWorkout.getStopTime()));

                if (mViewModelAddWorkout.getOutputWorkoutNumber() == -1) {
                    // No workout selected
                    Log.i("CHRONO_LOG", "Option 1");
                    Toast.makeText(mContext, "No workout selected. Can't save data!", Toast.LENGTH_SHORT).show();
                } else {
                    // Workout is selected
                    // Give different options for saving data depending on data required for workout
                    if (mViewModelAddWorkout.getTriggerRepDuration() == 1 & mViewModelAddWorkout.getTriggerRestPerSet() == 0) {
                        // Need a
                        mViewModelAddWorkout.setCounterRepTime((int) mViewModelAddWorkout.getSaveTime());
                        workoutRepDurationEditText.setText(TimeUtils.convertDate(mViewModelAddWorkout.getCounterRepTime(), "mm:ss"));

                    } else if (mViewModelAddWorkout.getTriggerRepDuration() == 1 & mViewModelAddWorkout.getTriggerRestPerSet() == 1) {
                        CharSequence[] pickerValues = new CharSequence[]{"Rep Duration", "Rest Per Set"};
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("Pick a value to save to:");
                        builder.setItems(pickerValues, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    mViewModelAddWorkout.setCounterRepTime((int) mViewModelAddWorkout.getSaveTime());
                                    workoutRepDurationEditText.setText(TimeUtils.convertDate(mViewModelAddWorkout.getCounterRepTime(), "mm:ss"));
                                } else if (which == 1) {
                                    mViewModelAddWorkout.setCounterRestTime((int) mViewModelAddWorkout.getSaveTime());
                                    workoutRestEditText.setText(TimeUtils.convertDate(mViewModelAddWorkout.getCounterRestTime(), "mm:ss"));
                                }
                            }
                        });
                        builder.show();

                    } else if (mViewModelAddWorkout.getTriggerRepDuration() == 0 & mViewModelAddWorkout.getTriggerRestPerSet() == 1) {
                        CharSequence[] pickerValues = new CharSequence[]{"Rest Per Set"};
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("Pick a value to save to:");
                        builder.setItems(pickerValues, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mViewModelAddWorkout.setCounterRestTime((int) mViewModelAddWorkout.getSaveTime());
                                workoutRestEditText.setText(TimeUtils.convertDate(mViewModelAddWorkout.getCounterRestTime(), "mm:ss"));
                            }
                        });
                        builder.show();
                    }
                }


            }
        });

        // Weight Button Listeners
        workoutWeightButtonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementWeight(-INCREMENT_WEIGHT_SMALL);
                workoutWeightEditText.setText("" + mViewModelAddWorkout.getCounterWeight() + " Kg");
            }
        });
        workoutWeightButtonMinusBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementWeight(-INCREMENT_WEIGHT_LARGE);
                workoutWeightEditText.setText("" + mViewModelAddWorkout.getCounterWeight() + " Kg");
            }
        });
        workoutWeightButtonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementWeight(INCREMENT_WEIGHT_SMALL);
                workoutWeightEditText.setText("" + mViewModelAddWorkout.getCounterWeight() + " Kg");
            }
        });
        workoutWeightButtonPlusBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementWeight(INCREMENT_WEIGHT_LARGE);
                workoutWeightEditText.setText("" + mViewModelAddWorkout.getCounterWeight() + " Kg");
            }
        });

        // Set Count Button Listeners
        workoutSetCountButtonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementSetCount(-INCREMENT_COUNT_SMALL);
                workoutSetCountEditText.setText("" + mViewModelAddWorkout.getCounterSetCount());
            }
        });
        workoutSetCountButtonMinusBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementSetCount(-INCREMENT_COUNT_LARGE);
                workoutSetCountEditText.setText("" + mViewModelAddWorkout.getCounterSetCount());
            }
        });
        workoutSetCountButtonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementSetCount(INCREMENT_COUNT_SMALL);
                workoutSetCountEditText.setText("" + mViewModelAddWorkout.getCounterSetCount());
            }
        });
        workoutSetCountButtonPlusBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementSetCount(INCREMENT_COUNT_LARGE);
                workoutSetCountEditText.setText("" + mViewModelAddWorkout.getCounterSetCount());
            }
        });

        // Rep Count Button Listeners
        workoutRepCountButtonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementRepCount(-INCREMENT_COUNT_SMALL);
                workoutRepCountEditText.setText("" + mViewModelAddWorkout.getCounterRepCount());
            }
        });
        workoutRepCountButtonMinusBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementRepCount(-INCREMENT_COUNT_LARGE);
                workoutRepCountEditText.setText("" + mViewModelAddWorkout.getCounterRepCount());
            }
        });
        workoutRepCountButtonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementRepCount(INCREMENT_COUNT_SMALL);
                workoutRepCountEditText.setText("" + mViewModelAddWorkout.getCounterRepCount());
            }
        });
        workoutRepCountButtonPlusBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementRepCount(INCREMENT_COUNT_LARGE);
                workoutRepCountEditText.setText("" + mViewModelAddWorkout.getCounterRepCount());
            }
        });

        // Rep Duration Button Listeners
        workoutRepDurationButtonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementRepDuration(-INCREMENT_TIME_SHORT);
                workoutRepDurationEditText.setText(TimeUtils.convertDate(mViewModelAddWorkout.getCounterRepTime(), "mm:ss"));
            }
        });
        workoutRepDurationButtonMinusBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementRepDuration(-INCREMENT_TIME_LONG);
                workoutRepDurationEditText.setText(TimeUtils.convertDate(mViewModelAddWorkout.getCounterRepTime(), "mm:ss"));
            }
        });
        workoutRepDurationButtonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementRepDuration(INCREMENT_TIME_SHORT);
                workoutRepDurationEditText.setText(TimeUtils.convertDate(mViewModelAddWorkout.getCounterRepTime(), "mm:ss"));
            }
        });
        workoutRepDurationButtonPlusBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementRepDuration(INCREMENT_TIME_LONG);
                workoutRepDurationEditText.setText(TimeUtils.convertDate(mViewModelAddWorkout.getCounterRepTime(), "mm:ss"));
            }
        });

        // Rest Duration Button Listeners
        workoutRestButtonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementRestDuration(-INCREMENT_TIME_SHORT);
                workoutRestEditText.setText(TimeUtils.convertDate(mViewModelAddWorkout.getCounterRestTime(), "mm:ss"));
            }
        });
        workoutRestButtonMinusBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementRestDuration(-INCREMENT_TIME_LONG);
                workoutRestEditText.setText(TimeUtils.convertDate(mViewModelAddWorkout.getCounterRestTime(), "mm:ss"));
            }
        });
        workoutRestButtonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementRestDuration(INCREMENT_TIME_SHORT);
                workoutRestEditText.setText(TimeUtils.convertDate(mViewModelAddWorkout.getCounterRestTime(), "mm:ss"));
            }
        });
        workoutRestButtonPlusBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementRestDuration(INCREMENT_TIME_LONG);
                workoutRestEditText.setText(TimeUtils.convertDate(mViewModelAddWorkout.getCounterRestTime(), "mm:ss"));
            }
        });

        // Grade Type Listener
        workoutGradeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickGrade();
            }
        });

        // Move Count Button Listeners
        workoutMoveCountButtonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementMoveCount(-INCREMENT_COUNT_SMALL);
                workoutMoveCountEditText.setText("" + mViewModelAddWorkout.getCounterMoveCount());
            }
        });
        workoutMoveCountButtonMinusBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementMoveCount(-INCREMENT_COUNT_LARGE);
                workoutMoveCountEditText.setText("" + mViewModelAddWorkout.getCounterMoveCount());
            }
        });
        workoutMoveCountButtonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementMoveCount(INCREMENT_COUNT_SMALL);
                workoutMoveCountEditText.setText("" + mViewModelAddWorkout.getCounterMoveCount());
            }
        });
        workoutMoveCountButtonPlusBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementMoveCount(INCREMENT_COUNT_LARGE);
                workoutMoveCountEditText.setText("" + mViewModelAddWorkout.getCounterMoveCount());
            }
        });

        // Wall Angle Button Listeners
        workoutWallAngleButtonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementWallAngle(-INCREMENT_COUNT_SMALL);
                workoutWallAngleEditText.setText("" + mViewModelAddWorkout.getCounterWallAngle());
            }
        });
        workoutWallAngleButtonMinusBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementWallAngle(-INCREMENT_COUNT_LARGE);
                workoutWallAngleEditText.setText("" + mViewModelAddWorkout.getCounterWallAngle());
            }
        });
        workoutWallAngleButtonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementWallAngle(INCREMENT_COUNT_SMALL);
                workoutWallAngleEditText.setText("" + mViewModelAddWorkout.getCounterWallAngle());
            }
        });
        workoutWallAngleButtonPlusBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModelAddWorkout.incrementWallAngle(INCREMENT_COUNT_LARGE);
                workoutWallAngleEditText.setText("" + mViewModelAddWorkout.getCounterWallAngle());
            }
        });

        // Hold Type Listener
        workoutHoldTypeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickHoldType();
            }
        });

    }

    private void pickTraining() {
        FragmentParentWorkoutHolder fragmentParentWorkoutHolder = new FragmentParentWorkoutHolder();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContent, fragmentParentWorkoutHolder, MainActivity.fragmentNameParentGradeHolder)
                .addToBackStack(null)
                .commit();
    }

    private void pickGrade() {
        FragmentParentGradeHolder fragmentParentGradeHolder = new FragmentParentGradeHolder();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContent, fragmentParentGradeHolder, MainActivity.fragmentNameParentWorkoutHolder)
                .addToBackStack(null)
                .commit();
    }

    private void pickHoldType() {    }

/*    private void putWorkout(Intent inputData) {

        Log.i("AddWorkout", "putWorkout");

        // The user picked a workout, get the workout name
        outputWorkoutNumber = inputData.getIntExtra("OutputWorkoutNumber", -1);
        String outputStringWorkoutName = DatabaseReadWrite.getWorkoutTextClimb(outputWorkoutNumber, this);

        // The user picked a workout, get the workout type name
        // Put workout text in the view
        outputWorkoutName = inputData.getIntExtra("OutputWorkoutName", -1);
        String outputStringWorkoutType = DatabaseReadWrite.getWorkoutTypeClimb(outputWorkoutName, this);
        workoutTrainingEditText.setText(outputStringWorkoutType + " | " + outputStringWorkoutName);

        // Display the missing fields
        Bundle bundle = DatabaseReadWrite.workoutLoadFields(outputWorkoutNumber, this);
        getTrainingInputFields(bundle);
        showViews();
    }

    private void putGrade(Intent data) {

        Log.i("AddWorkout", "putGrade");

        // The user picked a grade, get the grade number
        outputGradeNumber = data.getIntExtra("OutputGradeNumber", -1);
        String outputStringGradeName = DatabaseReadWrite.getGradeTextClimb(outputGradeNumber, this);

        Log.i("ADDWORKOUT LOG", "put grade: " + outputGradeNumber + " " + outputGradeName);
        outputGradeName = data.getIntExtra("OutputGradeName", -1);
        String outputStringGradeType = DatabaseReadWrite.getGradeTypeClimb(outputGradeName, this);
        workoutGradeEditText.setText(outputStringGradeType + " | " + outputStringGradeName);
    }

    private void putHoldType(Intent data) {

        Log.i("AddWorkout", "putHoldType");

        outputHoldType = data.getIntExtra("OutputData", -1);
        String outputStringHoldType = DatabaseReadWrite.getHoldTypeText(outputHoldType, this);
        workoutHoldTypeEditText.setText(outputStringHoldType);
    }*/



    private void showViews() {

        if (mViewModelAddWorkout.getTriggerWeight() == DatabaseContract.IS_TRUE) {
            workoutWeightWrapper.setVisibility(View.VISIBLE);
        } else {
            workoutWeightWrapper.setVisibility(View.GONE);
        }

        if (mViewModelAddWorkout.getTriggerSetCount() == DatabaseContract.IS_TRUE) {
            workoutSetCountWrapper.setVisibility(View.VISIBLE);
        } else {
            workoutSetCountWrapper.setVisibility(View.GONE);
        }

        if (mViewModelAddWorkout.getTriggerRepCount() == DatabaseContract.IS_TRUE) {
            workoutRepCountWrapper.setVisibility(View.VISIBLE);
        } else {
            workoutRepCountWrapper.setVisibility(View.GONE);
        }

        if (mViewModelAddWorkout.getTriggerRepDuration() == DatabaseContract.IS_TRUE) {
            workoutRepDurationWrapper.setVisibility(View.VISIBLE);
        } else {
            workoutRepDurationWrapper.setVisibility(View.GONE);
        }

        if (mViewModelAddWorkout.getTriggerRestPerSet() == DatabaseContract.IS_TRUE) {
            workoutRestWrapper.setVisibility(View.VISIBLE);
        } else {
            workoutRestWrapper.setVisibility(View.GONE);
        }

        if (mViewModelAddWorkout.getTriggerGradeCode() == DatabaseContract.IS_TRUE) {
            workoutGradeWrapper.setVisibility(View.VISIBLE);
        } else {
            workoutGradeWrapper.setVisibility(View.GONE);
        }

        if (mViewModelAddWorkout.getTriggerMoveCount() == DatabaseContract.IS_TRUE) {
            workoutMoveCountWrapper.setVisibility(View.VISIBLE);
        } else {
            workoutMoveCountWrapper.setVisibility(View.GONE);
        }

        if (mViewModelAddWorkout.getTriggerWallAngle() == DatabaseContract.IS_TRUE) {
            workoutWallAngleWrapper.setVisibility(View.VISIBLE);
        } else {
            workoutWallAngleWrapper.setVisibility(View.GONE);
        }

        if (mViewModelAddWorkout.getTriggerHoldType() == DatabaseContract.IS_TRUE) {
            workoutHoldTypeWrapper.setVisibility(View.VISIBLE);
        } else {
            workoutHoldTypeWrapper.setVisibility(View.GONE);
        }

        workoutCompleteWrapper.setVisibility(View.VISIBLE);

    }

    private void fillViews() {

        workoutDateEditText.setText(TimeUtils.convertDate(mViewModelAddWorkout.getOutputDate(), "yyyy-MM-dd"));
        workoutWeightEditText.setText("" + mViewModelAddWorkout.getCounterWeight() + " Kg");
        workoutRestEditText.setText(TimeUtils.convertDate(mViewModelAddWorkout.getCounterRestTime(), "mm:ss"));
        workoutRepCountEditText.setText(Integer.toString(mViewModelAddWorkout.getCounterRepCount()));
        workoutRepDurationEditText.setText(TimeUtils.convertDate(mViewModelAddWorkout.getCounterRepTime(), "mm:ss"));
        workoutSetCountEditText.setText(Integer.toString(mViewModelAddWorkout.getCounterSetCount()));
        workoutMoveCountEditText.setText(Integer.toString(mViewModelAddWorkout.getCounterMoveCount()));
        workoutWallAngleEditText.setText(Integer.toString(mViewModelAddWorkout.getCounterWallAngle()));

        if (mViewModelAddWorkout.getOutputGradeNumber() == -1) {
            workoutGradeEditText.setText("");
        } else {
            String outputStringGradeName = DatabaseReadWrite.getGradeTextClimb(mViewModelAddWorkout.getOutputGradeNumber(), mContext);
            String outputStringGradeType = DatabaseReadWrite.getGradeTypeClimb(mViewModelAddWorkout.getOutputGradeName(), mContext);
            workoutGradeEditText.setText(outputStringGradeType + " | " + outputStringGradeName);
        }

        if (mViewModelAddWorkout.getOutputHoldType() == -1) {
            workoutHoldTypeEditText.setText("");
        } else {
            String outputStringHoldType = DatabaseReadWrite.getHoldTypeText(mViewModelAddWorkout.getOutputHoldType(), mContext);
            workoutHoldTypeEditText.setText(outputStringHoldType);
        }

        if (mViewModelAddWorkout.getOutputWorkoutNumber() == -1) {
            workoutTrainingEditText.setText("");
        } else {
            String outputStringWorkoutName = DatabaseReadWrite.getWorkoutTextClimb(mViewModelAddWorkout.getOutputWorkoutNumber(), mContext);
            String outputStringWorkoutType = DatabaseReadWrite.getWorkoutTypeClimb(mViewModelAddWorkout.getOutputWorkoutName(), mContext);
            workoutTrainingEditText.setText(outputStringWorkoutType + " | " + outputStringWorkoutName);
        }

        if (mViewModelAddWorkout.getOutputCompleteCheckedState() == 0) {
            workoutCompleteCheckBox.setChecked(false);
        } else if (mViewModelAddWorkout.getOutputCompleteCheckedState() == 1) {
            workoutCompleteCheckBox.setChecked(true);
        }

    }

/*    private void getTrainingInputFields(Bundle bundle) {
        //int outputIsClimb = bundle.getInt("outputIsClimb");
        triggerWeight = bundle.getInt("outputIsWeight");
        triggerSetCount = bundle.getInt("outputIsSetCount");
        triggerRepCount = bundle.getInt("outputIsRepCountPerSet");
        triggerRepDuration = bundle.getInt("outputRepDurationPerSet");
        triggerRestPerSet = bundle.getInt("outputIsRestDuratonPerSet");
        triggerGradeCode = bundle.getInt("outputIsGradeCode");
        triggerMoveCount = bundle.getInt("outputIsMoveCount");
        triggerWallAngle = bundle.getInt("outputIsWallAngle");
        triggerHoldType = bundle.getInt("outputIsHoldType");

    }*/

/*    private void getWorkoutInfoFromBundle(Bundle inputBundle) {

        Log.i("AddWorkout", "getWorkoutInfoFromBundle");

        outputWorkoutNumber = inputBundle.getInt("outputWorkoutCode");
        outputWorkoutName = inputBundle.getInt("outputWorkoutTypeCode");
        counterWeight = inputBundle.getDouble("outputWeight");
        counterRestTime = inputBundle.getInt("outputRestDuration");
        counterRepCount = inputBundle.getInt("outputRepCount");
        counterRepTime = inputBundle.getInt("outputRepDuration");
        counterSetCount = inputBundle.getInt("outputSetCount");
        outputGradeName = inputBundle.getInt("outputGradeTypeCode");
        outputGradeNumber = inputBundle.getInt("outputGradeCode");
        counterMoveCount = inputBundle.getInt("outputMoveCount");
        counterWallAngle = inputBundle.getInt("outputWallAngle");
        outputHoldType = inputBundle.getInt("outputHoldType");
        outputCompleteCheckedState = inputBundle.getInt("outputIsComplete");
    }*/

/*    private void hideViews() {

        Log.i("AddWorkout", "hideViews");

        // Weight Data Input Wrapper
        workoutWeightWrapper.setVisibility(View.GONE);
        // Set Count Input Wrapper
        workoutSetCountWrapper.setVisibility(View.GONE);
        // Rep Count Input Wrapper
        workoutRepCountWrapper.setVisibility(View.GONE);
        // Rep Duration Input Wrapper
        workoutRepDurationWrapper.setVisibility(View.GONE);
        // Rest Per Set Input Wrapper
        workoutRestWrapper.setVisibility(View.GONE);
        // Grade or Difficulty Input Wrapper
        workoutGradeWrapper.setVisibility(View.GONE);
        // Move Count Input Wrapper
        workoutMoveCountWrapper.setVisibility(View.GONE);
        // Wall Angle Input Wrapper
        workoutWallAngleWrapper.setVisibility(View.GONE);
        // Hold Type Input Wrapper
        workoutHoldTypeWrapper.setVisibility(View.GONE);
        // Complete Input Wrapper
        workoutCompleteWrapper.setVisibility(View.GONE);
    }*/

/*    private void resetOutputs() {

        Log.i("AddWorkout", "resetOutputs");

        // reset output values
        outputGradeNumber = -1;
        outputGradeName = -1;
        outputHoldType = -1;
        outputCompleteCheckedState = 0;

        // reset counters
        counterWeight = 0;
        counterRestTime = 0;
        counterRepCount = 1;
        counterRepTime = 0;
        counterSetCount = 1;
        counterMoveCount = 0;
        counterWallAngle = 0;
    }*/

    protected void exitFragment() {
        mViewModelAddWorkout.resetData();
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            //super.onBackPressed();
        }
    }
}
