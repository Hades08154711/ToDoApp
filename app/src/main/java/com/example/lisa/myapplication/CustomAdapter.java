package com.example.lisa.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 23.03.2016.
 */
public class CustomAdapter extends ArrayAdapter {

    private Context context;
    private int resource;
    private LayoutInflater inflater;
    private List<ToDo> allTasks;
    private DataSource ds;

    public CustomAdapter (Context context, List<ToDo> values,DataSource ds) { // or String[][] or whatever

        super(context, R.layout.customlistview, values);
        this.ds = ds;
        this.context = context;
        this.resource = R.layout.customlistview;
        this.inflater = LayoutInflater.from(context);
        this.allTasks = values;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = (RelativeLayout) inflater.inflate(resource, null);

        TextView tv = (TextView) convertView.findViewById(R.id.title);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox);

        tv.setText(allTasks.get(position).getTitel());
        tv.setId(allTasks.get(position).getId());

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, Task.class);
                context.startActivity(myIntent);
            }
        });
        tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                removeItemFromList(v.getId());
                return true;
            }
        });
        cb.setText("");

        //cb.setId(allTasks.get(position).getId());
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editeChecked(v, v.getId());
            }
        });
        return convertView;
    }

    private void editeChecked(View v, int id){
        CheckBox checkBox = ((CheckBox)v);
        int myInt = (checkBox.isChecked()) ? 1 : 0;

        for (int i = 0; i < allTasks.size(); i++) {
            if (allTasks.get(i).getId() == id) {
                allTasks.get(i).setErledigt(myInt);
                ds.updateTaskCheck(allTasks.get(i), myInt);
            }
        }
    }

    // method to remove list item
    protected void removeItemFromList(final int position) {
        final int deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(
                context);

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this item?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TOD O Auto-generated method stub

                // main code on after clicking yes
                for (int i = 0; i < allTasks.size(); i++) {
                    if (allTasks.get(i).getId() == position) {
                        ds.removeItem(allTasks.get(i));
                        allTasks.remove(i);
                    }
                }

                notifyDataSetChanged();
                notifyDataSetInvalidated();

            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        alert.show();

    }
}