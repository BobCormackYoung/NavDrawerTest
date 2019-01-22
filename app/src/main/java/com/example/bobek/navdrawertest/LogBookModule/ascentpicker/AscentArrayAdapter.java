package com.example.bobek.navdrawertest.LogBookModule.ascentpicker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.bobek.navdrawertest.LogBookModule.ExpandedArrayItem;
import com.example.bobek.navdrawertest.LogBookModule.LogBookArrayListItem;
import com.example.bobek.navdrawertest.R;
import com.example.bobek.navdrawertest.UtilModule.TimeUtils;

import java.util.ArrayList;

public class AscentArrayAdapter extends ArrayAdapter<AscentArrayListItem> {

    Context mContext;

    public AscentArrayAdapter(Context context, ArrayList<AscentArrayListItem> listItems) {
        super(context, 0, listItems);
        mContext = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item_description, parent, false);
        }

        AscentArrayListItem currentItem = getItem(position);

        // Find fields to populate in inflated template
        final TextView listItemText = listItemView.findViewById(R.id.list_item_desc_text);
        // Populate fields with extracted properties
        listItemText.setText(currentItem.getAscentType());

        final String description = currentItem.getAscentDescription();

        ImageView infoButton = listItemView.findViewById(R.id.list_item_desc_info_button);

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
