package com.example.bobek.navdrawertest.LogBookModule.workoutpicker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bobek.navdrawertest.LogBookModule.gradepicker.GradeArrayListItem;
import com.example.bobek.navdrawertest.R;

import java.util.ArrayList;

public class WorkoutArrayAdapter extends ArrayAdapter<WorkoutArrayListItem> {

    Context mContext;

    public WorkoutArrayAdapter(Context context, ArrayList<WorkoutArrayListItem> listItems) {
        super(context, 0 ,listItems);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item_description, parent, false);
        }

        WorkoutArrayListItem currentItem = getItem(position);

        // Find fields to populate in inflated template
        final TextView listItemText = listItemView.findViewById(R.id.list_item_desc_text);
        // Populate fields with extracted properties
        listItemText.setText(currentItem.getName());

        final String description = currentItem.getDescription();

        ImageView infoButton = listItemView.findViewById(R.id.list_item_desc_info_button);

        if (currentItem.getDescription()==null) {
            infoButton.setVisibility(View.GONE);
        }

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context mContext = listItemText.getContext();

                AlertDialog.Builder deleteAlert = descriptionAlert(description, mContext);

                deleteAlert.show();
            }
        });

        return listItemView;
    }

    public AlertDialog.Builder descriptionAlert(final String description, final Context context) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        //alert.setTitle("Alert!!");
        alert.setMessage(description);
        alert.setPositiveButton("DISMISS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return (alert);

    }

}
