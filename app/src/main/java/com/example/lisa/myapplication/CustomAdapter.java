package com.example.lisa.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 23.03.2016.
 */
public class CustomAdapter extends ArrayAdapter {
    private static final String LOG_TAG = DataSource.class.getSimpleName();
    private Context context;
    private int resource;
    private LayoutInflater inflater;
    private List<ToDo> allTasks;
    private DataSource ds;
    private Appdaten ad;

    ViewHolder vh;
    public CustomAdapter (Context context, List<ToDo> values,DataSource ds, Appdaten ad) { // or String[][] or whatever

        super(context, R.layout.customlistview, values);
        this.ds = ds;
        this.ad = ad;
        this.context = context;
        this.resource = R.layout.customlistview;
        this.inflater = LayoutInflater.from(context);
        this.allTasks = values;
        ad = new Appdaten();

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

//        if(convertView == null){

            convertView = (RelativeLayout) inflater.inflate(resource, null);
            ImageView iv = (ImageView) convertView.findViewById(R.id.imageView);
            TextView tv = (TextView) convertView.findViewById(R.id.title);
            CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox);
            if (allTasks.get(position).getErledigt() == 1){
                cb.setChecked(true);
            }
            if (allTasks.get(position).getWichtig() == 1){

                iv.setVisibility(View.VISIBLE);
            }else{
                iv.setVisibility(View.INVISIBLE);
            }
            cb.setText("");

            tv.setText(allTasks.get(position).getTitel());
            tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.deleteTodo(context);
                ad.addTodo(context,allTasks.get(position));
                Intent myIntent = new Intent(context, Task.class);
                context.startActivity(myIntent);
            }
        });

            tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                removeItemFromList(allTasks.get(position).getId());
                return true;
            }
        });
            cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   editeChecked(v, allTasks.get(position).getId());
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
                ds.updateTaskCheck(allTasks.get(i));
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
    static class ViewHolder {
        CheckBox cb;
        TextView title;
        int position;
    }
}

