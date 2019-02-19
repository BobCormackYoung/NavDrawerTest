package com.example.bobek.navdrawertest.LogBookModule;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.bobek.navdrawertest.DataModule.DatabaseReadWrite;

public class ViewModelAddWorkout extends AndroidViewModel {

    boolean outputIsNewWorkout = false;

    int outputWorkoutNumber = -1; // Workout number
    String outputStringWorkoutName = null; //Workout name
    int outputWorkoutName = -1; // Workout name
    String outputStringWorkoutType = null; //Workout type
    int inputIntentCode = -1;
    int inputRowID = -1;
    long stopTime = 0;
    long saveTime = 0;

    int triggerWeight = 0;
    int triggerSetCount = 0;
    int triggerRepCount = 0;
    int triggerRestPerSet = 0;
    int triggerRepDuration = 0;
    int triggerGradeCode = 0;
    int triggerMoveCount = 0;
    int triggerWallAngle = 0;
    int triggerHoldType = 0;

    // Initialise output values
    long outputDate = -1;
    int outputGradeNumber = -1;
    int outputGradeName = -1;
    int outputHoldType = -1;
    int outputCompleteCheckedState = 0; // 1 = checked/complete, 0 = unchecked/incomplete

    // Initialise counters
    double counterWeight = 0;
    int counterRestTime = 0;
    int counterRepCount = 1;
    int counterRepTime = 0;
    int counterSetCount = 1;
    int counterMoveCount = 0;
    int counterWallAngle = 0;

    public ViewModelAddWorkout(@NonNull Application application) {super(application);
    }

    public void setOutputIsNewWorkout(boolean input) {outputIsNewWorkout = input;}
    public boolean getOutputIsNewWorkout() {return outputIsNewWorkout;}

    public void setOutputWorkoutNumber(int input) {outputWorkoutNumber = input;}
    public int getOutputWorkoutNumber() {return outputWorkoutNumber;}
    public void setOutputStringWorkoutName(String input) {outputStringWorkoutName = input;}
    public String getOutputStringWorkoutName() {return outputStringWorkoutName;}
    public void setOutputWorkoutName(int input) {outputWorkoutName = input;}
    public int getOutputWorkoutName() {return outputWorkoutName;}
    public void setOutputStringWorkoutType(String input) {outputStringWorkoutType = input;}
    public String getOutputStringWorkoutType() {return outputStringWorkoutType;}
    public void setInputIntentCode(int input) {inputIntentCode = input;}
    public int getInputIntentCode() {return inputIntentCode;}
    public void setInputRowID(int input) {inputRowID = input;}
    public int getInputRowID() {return inputRowID;}
    public void setStopTime(long input) {stopTime = input;}
    public long getStopTime() {return stopTime;}
    public void setSaveTime(long input) {saveTime = input;}
    public long getSaveTime() {return saveTime;}

    public void setTriggerWeight(int input) {triggerWeight = input;}
    public int getTriggerWeight() {return triggerWeight;}
    public void setTriggerSetCount(int input) {triggerSetCount = input;}
    public int getTriggerSetCount() {return triggerSetCount;}
    public void setTriggerRepCount(int input) {triggerRepCount = input;}
    public int getTriggerRepCount() {return triggerRepCount;}
    public void setTriggerRestPerSet(int input) {triggerRestPerSet = input;}
    public int getTriggerRestPerSet() {return triggerRestPerSet;}
    public void setTriggerRepDuration(int input) {triggerRepDuration = input;}
    public int getTriggerRepDuration() {return triggerRepDuration;}
    public void setTriggerGradeCode(int input) {triggerGradeCode = input;}
    public int getTriggerGradeCode() {return triggerGradeCode;}
    public void setTriggerMoveCount(int input) {triggerMoveCount = input;}
    public int getTriggerMoveCount() {return triggerMoveCount;}
    public void setTriggerWallAngle(int input) {triggerWallAngle = input;}
    public int getTriggerWallAngle() {return triggerWallAngle;}
    public void setTriggerHoldType(int input) {triggerHoldType = input;}
    public int getTriggerHoldType() {return triggerHoldType;}

    public void setOutputDate(long input) {outputDate = input;}
    public long getOutputDate() {return outputDate;}
    public void setOutputGradeNumber(int input) {outputGradeNumber = input;}
    public int getOutputGradeNumber() {return outputGradeNumber;}
    public void setOutputGradeName(int input) {outputGradeName = input;}
    public int getOutputGradeName() {return outputGradeName;}
    public void setOutputHoldType(int input) {outputHoldType = input;}
    public int getOutputHoldType() {return outputHoldType;}
    public void setOutputCompleteCheckedState(int input) {outputCompleteCheckedState = input;}
    public int getOutputCompleteCheckedState() {return outputCompleteCheckedState;}

    public void setCounterWeight(double input) {counterWeight = input;}
    public double getCounterWeight() {return counterWeight;}
    public void setCounterRestTime(int input) {counterRestTime = input;}
    public int getCounterRestTime() {return counterRestTime;}
    public void setCounterRepCount(int input) {counterRepCount = input;}
    public int getCounterRepCount() {return counterRepCount;}
    public void setCounterRepTime(int input) {counterRepTime = input;}
    public int getCounterRepTime() {return counterRepTime;}
    public void setCounterSetCount(int input) {counterSetCount = input;}
    public int getCounterSetCount() {return counterSetCount;}
    public void setCounterMoveCount(int input) {counterMoveCount = input;}
    public int getCounterMoveCount() {return counterMoveCount;}
    public void setCounterWallAngle(int input) {counterWallAngle = input;}
    public int getCounterWallAngle() {return counterWallAngle;}

    public void resetData() {
        outputWorkoutNumber = -1;
        outputStringWorkoutName = null;
        outputWorkoutName = -1;
        outputStringWorkoutType = null;
        inputIntentCode = -1;
        inputRowID = -1;
        stopTime = 0;
        saveTime = 0;
        triggerWeight = 0;
        triggerSetCount = 0;
        triggerRepCount = 0;
        triggerRestPerSet = 0;
        triggerRepDuration = 0;
        triggerGradeCode = 0;
        triggerMoveCount = 0;
        triggerWallAngle = 0;
        triggerHoldType = 0;
        outputDate = -1;
        outputGradeNumber = -1;
        outputGradeName = -1;
        outputHoldType = -1;
        outputCompleteCheckedState = 0;
        counterWeight = 0;
        counterRestTime = 0;
        counterRepCount = 1;
        counterRepTime = 0;
        counterSetCount = 1;
        counterMoveCount = 0;
        counterWallAngle = 0;
    }

    public void resetOutputs() {
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
    }

    public void incrementWeight(double increment) {
        if (counterWeight + increment >= 0) {
            counterWeight = counterWeight + increment;
        } else {
            counterWeight = 0;
        }
    }

    public void incrementSetCount(int increment) {
        if (counterSetCount + increment >= 1) {
            counterSetCount = counterSetCount + increment;
        } else {
            counterSetCount = 1;
        }
    }

    public void incrementRepCount(int increment) {
        if (counterRepCount + increment >= 1) {
            counterRepCount = counterRepCount + increment;
        } else {
            counterRepCount = 1;
        }
    }

    public void incrementRepDuration(int increment) {
        if (counterRepTime + increment >= 0) {
            counterRepTime = counterRepTime + increment;
        } else {
            counterRepTime = 0;
        }
    }

    public void incrementRestDuration(int increment) {
        if (counterRestTime + increment >= 0) {
            counterRestTime = counterRestTime + increment;
        } else {
            counterRestTime = 0;
        }
    }

    public void incrementMoveCount(int increment) {
        if (counterMoveCount + increment >= 0) {
            counterMoveCount = counterMoveCount + increment;
        } else {
            counterMoveCount = 0;
        }
    }

    public void incrementWallAngle(int increment) {
        if (counterWallAngle + increment >= 0) {
            counterWallAngle = counterWallAngle + increment;
        } else {
            counterWallAngle = 0;
        }
    }

    public void getWorkoutInfo(Context context){
        Bundle workoutEntryBundle = DatabaseReadWrite.EditWorkoutLoadEntry(inputRowID, context);
        outputWorkoutNumber = workoutEntryBundle.getInt("outputWorkoutCode");
        outputWorkoutName = workoutEntryBundle.getInt("outputWorkoutTypeCode");
        counterWeight = workoutEntryBundle.getDouble("outputWeight");
        counterRestTime = workoutEntryBundle.getInt("outputRestDuration");
        counterRepCount = workoutEntryBundle.getInt("outputRepCount");
        counterRepTime = workoutEntryBundle.getInt("outputRepDuration");
        counterSetCount = workoutEntryBundle.getInt("outputSetCount");
        outputGradeName = workoutEntryBundle.getInt("outputGradeTypeCode");
        outputGradeNumber = workoutEntryBundle.getInt("outputGradeCode");
        counterMoveCount = workoutEntryBundle.getInt("outputMoveCount");
        counterWallAngle = workoutEntryBundle.getInt("outputWallAngle");
        outputHoldType = workoutEntryBundle.getInt("outputHoldType");
        outputCompleteCheckedState = workoutEntryBundle.getInt("outputIsComplete");

    }

    public void getTrainingInputFields(Context context) {
        Bundle workoutFieldsBundle = DatabaseReadWrite.workoutLoadFields(outputWorkoutNumber, context);
        //int outputIsClimb = bundle.getInt("outputIsClimb");
        triggerWeight = workoutFieldsBundle.getInt("outputIsWeight");
        triggerSetCount = workoutFieldsBundle.getInt("outputIsSetCount");
        triggerRepCount = workoutFieldsBundle.getInt("outputIsRepCountPerSet");
        triggerRepDuration = workoutFieldsBundle.getInt("outputRepDurationPerSet");
        triggerRestPerSet = workoutFieldsBundle.getInt("outputIsRestDuratonPerSet");
        triggerGradeCode = workoutFieldsBundle.getInt("outputIsGradeCode");
        triggerMoveCount = workoutFieldsBundle.getInt("outputIsMoveCount");
        triggerWallAngle = workoutFieldsBundle.getInt("outputIsWallAngle");
        triggerHoldType = workoutFieldsBundle.getInt("outputIsHoldType");
    }
}
