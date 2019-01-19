package com.example.bobek.navdrawertest.LogBookModule;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bobek.navdrawertest.DataModule.DatabaseContract;
import com.example.bobek.navdrawertest.DataModule.DatabaseReadWrite;
import com.example.bobek.navdrawertest.R;
import com.example.bobek.navdrawertest.UtilModule.TimeUtils;

import java.util.ArrayList;

public class LogBookListArrayAdapter extends ArrayAdapter<LogBookArrayListItem> {

    Context mContext;
    public static ArrayList<ExpandedArrayItem> itemExpanded = new ArrayList<>();

    public LogBookListArrayAdapter(Context context, ArrayList<LogBookArrayListItem> listItems) {
        super(context, 0, listItems);
        mContext = context;
        if (listItems!=null) {
            for (int i = 0; i < listItems.size(); i++) {
                itemExpanded.add(i, new ExpandedArrayItem(listItems.get(i).getRowId(), false)); // initializes all items value with false
            }
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.listview_item_log_book, parent, false);
        }

        LogBookArrayListItem currentItem = getItem(position);

        //map all views
        TextView titleTextView = listItemView.findViewById(R.id.log_book_list_item_title);
        TextView subtitleTextView = listItemView.findViewById(R.id.log_book_list_item_subtitle);
        ImageView titleIcon = listItemView.findViewById(R.id.trophy_icon);
        TextView titleIconText = listItemView.findViewById(R.id.trophy_text);
        final ImageView expandableArrow = listItemView.findViewById(R.id.iv_logbook_expand);
        final LinearLayout dataDisplayWrapper = listItemView.findViewById(R.id.ll_alldata_wrapper);

        final LinearLayout dataClimbingDisplayWrapper = listItemView.findViewById(R.id.ll_logbook_climbing_data_wrapper);
        LinearLayout dataClimbingWrapper1 = listItemView.findViewById(R.id.ll_logbook_climbing_data1_wrapper);
        LinearLayout dataClimbingWrapper2 = listItemView.findViewById(R.id.ll_logbook_climbing_data2_wrapper);
        LinearLayout dataClimbingWrapper3 = listItemView.findViewById(R.id.ll_logbook_climbing_data3_wrapper);
        LinearLayout dataClimbingWrapper4 = listItemView.findViewById(R.id.ll_logbook_climbing_data4_wrapper);
        LinearLayout dataClimbingWrapper5 = listItemView.findViewById(R.id.ll_logbook_climbing_data5_wrapper);
        TextView dataClimbingValue1 = listItemView.findViewById(R.id.tv_logbook_climbing_data1);
        TextView dataClimbingValue2 = listItemView.findViewById(R.id.tv_logbook_climbing_data2);
        TextView dataClimbingValue3 = listItemView.findViewById(R.id.tv_logbook_climbing_data3);
        ImageView dataClimbingValue4 = listItemView.findViewById(R.id.iv_logbook_climbing_data4);
        ImageView dataClimbingValue5 = listItemView.findViewById(R.id.iv_logbook_climbing_data5);

        final LinearLayout dataWorkoutDisplayWrapper = listItemView.findViewById(R.id.ll_logbook_workout_data_wrapper);
        LinearLayout dataWorkoutWrapper1 = listItemView.findViewById(R.id.ll_logbook_workout_data1_wrapper);
        LinearLayout dataWorkoutWrapper2 = listItemView.findViewById(R.id.ll_logbook_workout_data2_wrapper);
        LinearLayout dataWorkoutWrapper3 = listItemView.findViewById(R.id.ll_logbook_workout_data3_wrapper);
        LinearLayout dataWorkoutWrapper4 = listItemView.findViewById(R.id.ll_logbook_workout_data4_wrapper);
        LinearLayout dataWorkoutWrapper5 = listItemView.findViewById(R.id.ll_logbook_workout_data5_wrapper);
        LinearLayout dataWorkoutWrapper6 = listItemView.findViewById(R.id.ll_logbook_workout_data6_wrapper);
        LinearLayout dataWorkoutWrapper7 = listItemView.findViewById(R.id.ll_logbook_workout_data7_wrapper);
        LinearLayout dataWorkoutWrapper8 = listItemView.findViewById(R.id.ll_logbook_workout_data8_wrapper);
        LinearLayout dataWorkoutWrapper9 = listItemView.findViewById(R.id.ll_logbook_workout_data9_wrapper);
        TextView dataWorkoutValue1 = listItemView.findViewById(R.id.tv_logbook_workout_data1);
        TextView dataWorkoutValue2 = listItemView.findViewById(R.id.tv_logbook_workout_data2);
        TextView dataWorkoutValue3 = listItemView.findViewById(R.id.tv_logbook_workout_data3);
        TextView dataWorkoutValue4 = listItemView.findViewById(R.id.tv_logbook_workout_data4);
        TextView dataWorkoutValue5 = listItemView.findViewById(R.id.tv_logbook_workout_data5);
        TextView dataWorkoutValue6 = listItemView.findViewById(R.id.tv_logbook_workout_data6);
        TextView dataWorkoutValue7 = listItemView.findViewById(R.id.tv_logbook_workout_data7);
        TextView dataWorkoutValue8 = listItemView.findViewById(R.id.tv_logbook_workout_data8);
        ImageView dataWorkoutValue9 = listItemView.findViewById(R.id.iv_logbook_workout_data9);

        TextView itemDivider = listItemView.findViewById(R.id.list_item_divider);
        ImageView iconView = listItemView.findViewById(R.id.log_book_list_item_icon_image);

        // check if climb or training
        int logTag = currentItem.getClimbCode();
        // get the foreign key row ID
        final int rowID = currentItem.getRowId();
        // cursor position

        //need to hide the data display wrapper initially
        if (itemExpanded.get(position).getIsExpanded()) {
            dataDisplayWrapper.setVisibility(View.VISIBLE);
            expandableArrow.setImageResource(R.drawable.ic_baseline_expand_less_24px);
        } else {
            dataDisplayWrapper.setVisibility(View.GONE);
            expandableArrow.setImageResource(R.drawable.ic_baseline_expand_more_24px);
        }

        if (logTag == DatabaseContract.IS_CLIMB) {

            dataClimbingDisplayWrapper.setVisibility(View.VISIBLE);
            dataWorkoutDisplayWrapper.setVisibility(View.GONE);

            Bundle climbingDataBundle = DatabaseReadWrite.EditClimbLoadEntry(rowID, mContext); // Updated
            int locationId = climbingDataBundle.getInt("outputLocationId"); // Updated
            Bundle locationDataBundle = DatabaseReadWrite.LocationLoadEntry(locationId, mContext); // Updated

            titleTextView.setText(climbingDataBundle.getString("outputRouteName"));
            subtitleTextView.setText(DatabaseReadWrite.getGradeTypeClimb(climbingDataBundle.getInt("outputGradeName"), mContext) + " | " + DatabaseReadWrite.getGradeTextClimb(climbingDataBundle.getInt("outputGradeNumber"), mContext));
            dataClimbingValue1.setText(DatabaseReadWrite.getGradeTypeClimb(climbingDataBundle.getInt("outputGradeName"), mContext) + " | " + DatabaseReadWrite.getGradeTextClimb(climbingDataBundle.getInt("outputGradeNumber"), mContext));
            dataClimbingValue2.setText(locationDataBundle.getString("outputLocationName")); // Updated
            dataClimbingValue3.setText(DatabaseReadWrite.getAscentNameTextClimb(climbingDataBundle.getInt("outputAscent"), mContext));
            int firstAscentCode = climbingDataBundle.getInt("outputFirstAscent");
            if (firstAscentCode == DatabaseContract.FIRSTASCENT_TRUE) {
                listItemView.findViewById(R.id.trophy_icon).setVisibility(View.VISIBLE);
                listItemView.findViewById(R.id.trophy_text).setVisibility(View.VISIBLE);
                dataClimbingValue4.setImageResource(R.drawable.ic_baseline_check_box_checked_24px);
            } else {
                listItemView.findViewById(R.id.trophy_icon).setVisibility(View.GONE);
                listItemView.findViewById(R.id.trophy_text).setVisibility(View.GONE);
                dataClimbingValue4.setImageResource(R.drawable.ic_baseline_check_box_unchecked_24px);
            }
            int gpsCode = locationDataBundle.getInt("outputIsGps"); // Updated
            if (gpsCode == DatabaseContract.IS_GPS_TRUE) {
                dataClimbingValue5.setImageResource(R.drawable.ic_baseline_check_box_checked_24px);
            } else {
                dataClimbingValue5.setImageResource(R.drawable.ic_baseline_check_box_unchecked_24px);
            }

            itemDivider.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorClimbingItemsV2));
            iconView.setImageResource(R.drawable.icons_drawstringbag96);

        } else if (logTag == DatabaseContract.IS_WORKOUT) {

            dataClimbingDisplayWrapper.setVisibility(View.GONE);
            dataWorkoutDisplayWrapper.setVisibility(View.VISIBLE);

            Bundle workoutDataBundle = DatabaseReadWrite.EditWorkoutLoadEntry(rowID, mContext);
            int workoutTypeCode = workoutDataBundle.getInt("outputWorkoutTypeCode");
            int workoutCode = workoutDataBundle.getInt("outputWorkoutCode");
            String outputStringWorkoutName = DatabaseReadWrite.getWorkoutTextClimb(workoutCode, mContext);
            String outputStringWorkoutType = DatabaseReadWrite.getWorkoutTypeClimb(workoutTypeCode, mContext);
            titleTextView.setText(outputStringWorkoutType + " | " + outputStringWorkoutName);
            subtitleTextView.setVisibility(View.GONE);

            Bundle workoutFieldsBundle = DatabaseReadWrite.workoutLoadFields(workoutCode, mContext);

            //Item is a training session, not a climb
            int outputIsSetCount = workoutFieldsBundle.getInt("outputIsSetCount");
            int outputIsRepCountPerSet = workoutFieldsBundle.getInt("outputIsRepCountPerSet");
            if (outputIsSetCount == DatabaseContract.IS_TRUE && outputIsRepCountPerSet == DatabaseContract.IS_TRUE) {
                dataWorkoutWrapper1.setVisibility(View.VISIBLE);
                dataWorkoutValue1.setText(Integer.toString(workoutDataBundle.getInt("outputRepCount")) + " x " + Integer.toString(workoutDataBundle.getInt("outputSetCount")));
            } else if (outputIsSetCount == DatabaseContract.IS_TRUE && outputIsRepCountPerSet == DatabaseContract.IS_FALSE) {
                dataWorkoutWrapper1.setVisibility(View.VISIBLE);
                dataWorkoutValue1.setText("1 x " + Integer.toString(workoutDataBundle.getInt("outputSetCount")));
            } else {
                dataWorkoutWrapper1.setVisibility(View.GONE);
            }

            int outputRepDurationPerSet = workoutFieldsBundle.getInt("outputRepDurationPerSet");
            if (outputRepDurationPerSet == DatabaseContract.IS_TRUE) {
                dataWorkoutWrapper2.setVisibility(View.VISIBLE);
                dataWorkoutValue2.setText(TimeUtils.convertDate(workoutDataBundle.getInt("outputRepDuration"), "mm:ss"));
            } else {
                dataWorkoutWrapper2.setVisibility(View.GONE);
            }

            int outputIsRestDuratonPerSet = workoutFieldsBundle.getInt("outputIsRestDuratonPerSet");
            if (outputIsRestDuratonPerSet == DatabaseContract.IS_TRUE) {
                dataWorkoutWrapper3.setVisibility(View.VISIBLE);
                dataWorkoutValue3.setText(TimeUtils.convertDate(workoutDataBundle.getInt("outputRestDuration"), "mm:ss"));
            } else {
                dataWorkoutWrapper3.setVisibility(View.GONE);
            }

            int outputIsWeight = workoutFieldsBundle.getInt("outputIsWeight");
            if (outputIsWeight == DatabaseContract.IS_TRUE) {
                dataWorkoutWrapper4.setVisibility(View.VISIBLE);
                dataWorkoutValue4.setText(Double.toString(workoutDataBundle.getDouble("outputWeight")) + " Kg");
            } else {
                dataWorkoutWrapper4.setVisibility(View.GONE);
            }

            int outputIsGradeCode = workoutFieldsBundle.getInt("outputIsGradeCode");
            if (outputIsGradeCode == DatabaseContract.IS_TRUE) {
                dataWorkoutWrapper5.setVisibility(View.VISIBLE);
                if (workoutDataBundle.getInt("outputGradeCode") == -1 || workoutDataBundle.getInt("outputGradeCode") == 0) {
                    dataWorkoutValue5.setText("No Data");
                } else {
                    String outputStringGradeName = DatabaseReadWrite.getGradeTextClimb(workoutDataBundle.getInt("outputGradeCode"), mContext);
                    String outputStringGradeType = DatabaseReadWrite.getGradeTypeClimb(workoutDataBundle.getInt("outputGradeTypeCode"), mContext);
                    dataWorkoutValue5.setText(outputStringGradeType + " | " + outputStringGradeName);
                    subtitleTextView.setVisibility(View.VISIBLE);
                    subtitleTextView.setText(outputStringGradeType + " | " + outputStringGradeName);
                }
            } else {
                dataWorkoutWrapper5.setVisibility(View.GONE);
            }

            int outputIsMoveCount = workoutFieldsBundle.getInt("outputIsMoveCount");
            if (outputIsMoveCount == DatabaseContract.IS_TRUE) {
                dataWorkoutWrapper6.setVisibility(View.VISIBLE);
                dataWorkoutValue6.setText(Integer.toString(workoutDataBundle.getInt("outputMoveCount")));
            } else {
                dataWorkoutWrapper6.setVisibility(View.GONE);
            }

            int outputIsWallAngle = workoutFieldsBundle.getInt("outputIsWallAngle");
            if (outputIsWallAngle == DatabaseContract.IS_TRUE) {
                dataWorkoutWrapper7.setVisibility(View.VISIBLE);
                dataWorkoutValue7.setText(Integer.toString(workoutDataBundle.getInt("outputWallAngle")));
            } else {
                dataWorkoutWrapper7.setVisibility(View.GONE);
            }

            int outputIsHoldType = workoutFieldsBundle.getInt("outputIsHoldType");
            if (outputIsHoldType == DatabaseContract.IS_TRUE) {
                dataWorkoutWrapper8.setVisibility(View.VISIBLE);
                if (workoutDataBundle.getInt("outputHoldType") == -1 || workoutDataBundle.getInt("outputHoldType") == 0) {
                    dataWorkoutValue8.setText("No Data");
                } else {
                    String outputStringHoldType = DatabaseReadWrite.getHoldTypeText(workoutDataBundle.getInt("outputHoldType"), mContext);
                    dataWorkoutValue8.setText(outputStringHoldType);
                }
            } else {
                dataWorkoutWrapper8.setVisibility(View.GONE);
            }

            dataWorkoutWrapper9.setVisibility(View.VISIBLE);
            if (workoutDataBundle.getInt("outputIsComplete") == DatabaseContract.IS_COMPLETE) {
                dataWorkoutValue9.setImageResource(R.drawable.ic_baseline_check_box_checked_24px);
            } else {
                dataWorkoutValue9.setImageResource(R.drawable.ic_baseline_check_box_unchecked_24px);
            }

            itemDivider.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorTrainingClimbItemsV2));
            iconView.setImageResource(R.drawable.icons_weightlifting96);

            listItemView.findViewById(R.id.trophy_icon).setVisibility(View.GONE);
            listItemView.findViewById(R.id.trophy_text).setVisibility(View.GONE);
        }

        //onlick for expanding the data display wrapper
        expandableArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDataDisplayWrapper(dataDisplayWrapper, expandableArrow, position, rowID);
            }
        });


        return listItemView;
    }

    private void toggleDataDisplayWrapper(View view, ImageView arrow, int pos, int rowID) {

        if (itemExpanded.get(pos).getIsExpanded()) {
            //item is expanded, un-expand it
            //view.findViewById(R.id.ll_copyworkout_workoutdata).setVisibility(View.GONE);
            view.setVisibility(View.GONE);
            arrow.setImageResource(R.drawable.ic_baseline_expand_more_24px);
            itemExpanded.set(pos, new ExpandedArrayItem(rowID, false));
        } else if (!itemExpanded.get(pos).getIsExpanded()) {
            //item is unexpanded, expand it
            //view.findViewById(R.id.ll_copyworkout_workoutdata).setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
            arrow.setImageResource(R.drawable.ic_baseline_expand_less_24px);
            itemExpanded.set(pos, new ExpandedArrayItem(rowID, true));
        }

    }

}
