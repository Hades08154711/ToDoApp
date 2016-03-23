package com.example.lisa.myapplication;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;
import android.util.Log;

/**
 * Created by Lisa on 15.03.2016.
 */
public class DataSource {
    private SQLiteDatabase database;
    private MySQLHelper dbHelper;
    public Context context;


    private static final String TABLE_USER = "user";
    private static final String TABLE_TODO = "aufgaben";
    private static final String LOG_TAG = DataSource.class.getSimpleName();
    /**
     * Constructor
     *
     * @param context
     */
    public DataSource(Context context) {
        this.context = context;
        dbHelper = new MySQLHelper(context);


    }

    /**
     * Open DB
     *
     * @throws SQLException
     */
    public void open() throws SQLException {
        if(database == null)
            database = dbHelper.getWritableDatabase();
        else if(!database.isOpen())
            database = dbHelper.getWritableDatabase();
    }

    /**
     * Close DB
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * Check whether DB is open or not
     * @return boolean
     */
    public boolean isOpen(){
        return database.isOpen();
    }

    /**
     *
     * @param user
     * @return CententValues
     */
    private ContentValues registerUserData(User user){
        ContentValues values = new ContentValues();
        values.put("Username", user.getUsername());
        values.put("Password", user.getPassword());
        return values;
    }

    /**
     *
     * @param user
     * @return boolean ob user in DB vorhanden ist oder nicht
     */
    public boolean registerUser(User user){
        try{ContentValues values = registerUserData(user);

            long insertId = database.insert(TABLE_USER, null, values);
            Log.e(LOG_TAG, "Register User erfolgreich");
            return true;
        }catch (Exception ex){
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
            return false;
        }

    }

    public boolean loginUser(User user){

        boolean result = false;

        try {
            Cursor cursor = database.query(TABLE_USER, null, " Username = ? AND Password = ?", new String[]{user.getUsername(), user.getPassword()}, null, null, null);
            Log.e(LOG_TAG, "User query - get Count: " + cursor.getCount());
            if (cursor.getCount() == 1) // UserName Not Exist

            {
                result = true;
            }

            cursor.close();

        }catch (Exception ex){
            Log.e(LOG_TAG, "Fehler beim Login:" + ex.getMessage());
        }
        return result;
    }

    public void setNewPassword(User user){

        ContentValues values = new ContentValues();
        values.put("Password", user.getPassword());

        try {
            database.update(TABLE_USER, values, "Username = ?", new String[]{user.getUsername()});
        }catch (Exception ex){
            Log.e(LOG_TAG, "Fehler beim passwort Update:" + ex.getMessage());
        }

    }

    public boolean firstLogin(Context c){
        boolean result = false;

        try {
            Cursor cursor = database.query(TABLE_USER, null, null, null, null, null, null);
            Log.e(LOG_TAG, "First Login? - get User Count: " + cursor.getCount());


            if (cursor.getCount() < 1) // UserName Not Exist
            {
                result = true;
            }

            cursor.close();

        }catch (Exception ex){
            Log.e(LOG_TAG, "Fehler beim pruefen ob erster Login:" + ex.getMessage());
        }
        return result;
    }

    private ContentValues addTodoData(String titel, String beschreibung, String datum, String uhrzeit, int wichtig){
        ContentValues values = new ContentValues();
        values.put("Title", titel);
        values.put("Info", beschreibung);
        values.put("Date", datum);
        values.put("Time", uhrzeit);
        values.put("Wichtig", wichtig);
        values.put("Erledigt", 0);
        return values;
    }

    public void removeItem(ToDo todo){
        try {
        database.delete(TABLE_TODO, "Id = '" + todo.getId() + "'", null);
        }catch (Exception ex){
            Log.e(LOG_TAG, "Fehler beim löschen eines Items:" + ex.getMessage());
        }
    }

    public void deleteAll(){
        database.delete(TABLE_TODO,null,null);
    }
    public ArrayList<ToDo> getAllTasks(){
        ArrayList<ToDo> result = new ArrayList<ToDo>();

        try {
            Cursor cursor = database.query(TABLE_TODO, null, null, null, null, null, null);
            cursor.moveToFirst();
            if (cursor.getCount() == 0){
                cursor.close();
                return result;
            }
            while (cursor.isAfterLast() == false) {
                ToDo todo = todoCursorToEntry(cursor);
                result.add(todo);
                cursor.moveToNext();
            }
            cursor.close();

            cursor.close();

        }catch (Exception ex){
            Log.e(LOG_TAG, "Fehler beim pruefen ob erster Login:" + ex.getMessage());
        }
        return result;
    }

    public void updateTaskCheck(ToDo todo, int id){
        ContentValues values = new ContentValues();
        values.put("Erledigt", todo.getErledigt());

        try {
            database.update(TABLE_TODO, values, "Id = " + id,null);
        }catch (Exception ex){
            Log.e(LOG_TAG, "Fehler beim passwort Update:" + ex.getMessage());
        }

    }

    private ToDo todoCursorToEntry(Cursor c) {
        ToDo todo;
        String t1,t2,t3,t4;
        int i1,i2;

        i1= c.getInt(c.getColumnIndex("Id"));
        t1 = c.getString(c.getColumnIndex("Title"));

//        t2 = todo.getInfo() ;
//        t3 = todo.getDate();
//        t4 = todo.getTime();
//        i2 = todo.getWichtig();
        todo = new ToDo(c.getInt(c.getColumnIndex("Id")), // ID
                c.getString(c.getColumnIndex("Title")), // TITEL
                c.getString(c.getColumnIndex("Info")), 	// INFO
                c.getString(c.getColumnIndex("Date")), 	// DATE
                c.getString(c.getColumnIndex("Time")), 		// TIME
                c.getInt(c.getColumnIndex("Wichtig")),
                c.getInt(c.getColumnIndex("Erledigt"))
        ); 		// WICHTIG
        return todo;
    }

    //if(ds.addTodo(etTitel.toString(), etBeschreibung.toString(), new java.sql.Date(dp.getDayOfMonth(),dp.getMonth()+1,dp.getYear()), new Time(tp.getHour(),tp.getMinute(),0),bWichtig)){
    public boolean addTodo(String titel, String beschreibung, String datum, String uhrzeit, int wichtig){

        try{ContentValues values = addTodoData(titel, beschreibung, datum, uhrzeit, wichtig);

            long insertId = database.insert(TABLE_TODO, null, values);
            Log.e(LOG_TAG, "Add ToDo erfolgreich" );
            return true;
        }catch (Exception ex){
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
            return false;
        }
    }
}
