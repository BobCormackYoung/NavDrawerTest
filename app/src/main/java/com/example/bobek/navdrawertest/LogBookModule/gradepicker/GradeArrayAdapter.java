package com.example.bobek.navdrawertest.LogBookModule.gradepicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bobek.navdrawertest.R;

import java.util.ArrayList;

public class GradeArrayAdapter extends ArrayAdapter<GradeArrayListItem> {

    Context mContext;

    public GradeArrayAdapter(Context context, ArrayList<GradeArrayListItem> listItems) {
        super(context, 0 ,listItems);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item_nodescription, parent, false);
        }

        GradeArrayListItem currentItem = getItem(position);

        final TextView listItemText = listItemView.findViewById(R.id.list_item_text);
        listItemText.setText(currentItem.getName());

        return listItemView;
    }

}
