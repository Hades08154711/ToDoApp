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
    private static final String TABLE_TODO = "todo";
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
            Log.e(LOG_TAG, "Register User erfolgreich" );
            return true;
        }catch (Exception ex){
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
            return false;
        }

    }

    public boolean loginUser(User user){

        boolean result = false;

        try {
            Cursor cursor = database.query("user", null, " Username = ? AND Password = ?", new String[]{user.getUsername(), user.getPassword()}, null, null, null);
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
            database.update("user", values, "Username = ?", new String[]{user.getUsername()});
        }catch (Exception ex){
            Log.e(LOG_TAG, "Fehler beim passwort Update:" + ex.getMessage());
        }

    }

    public boolean firstLogin(){
        boolean result = false;

        try {
            Cursor cursor = database.query("user", null, null, null, null, null, null);
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

    private ContentValues addTodoData(String titel, String beschreibung, Date datum, Time uhrzeit, int wichtig){
        ContentValues values = new ContentValues();
        values.put("Title", titel);
        values.put("Info", beschreibung);
        values.put("Date", datum.toString());
        values.put("Time", uhrzeit.toString());
        values.put("Wichtig", wichtig);

        return values;
    }

    public boolean addTodo(String titel, String beschreibung, Date datum, Time uhrzeit, int wichtig){
        try{ContentValues values = addTodoData(titel, beschreibung, datum, uhrzeit, wichtig);

            long insertId = database.insert(TABLE_TODO, null, values);
            Log.e(LOG_TAG, "Register User erfolgreich" );
            return true;
        }catch (Exception ex){
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
            return false;
        }
    }
}


//    private City cityCursorToEntry(Cursor cursor) {
//        City entry;
//        entry = new City(cursor.getLong(0), // ID
//                cursor.getString(1), 	// NAME
//                cursor.getString(2), 	// COUNTRY
//                cursor.getInt(3)); 		// POPULATION
//        return entry;
//    }
//

//    /**
//     * Delete all data of table
//     */
//    public void deleteCityTable() {
//        database.execSQL("delete from " + TABLE_MYCITIES);
//    }
//
//    /**
//     * Get all data of table
//     * @return list<City>
//     */
//    public List<City> getCities() {
//        List<City> myCityList = new ArrayList<City>();
//        Cursor cursor = database.query(TABLE_MYCITIES, null,
//                null, null, null, null, null);
//        cursor.moveToFirst();
//        if (cursor.getCount() == 0){
//            cursor.close();
//            return myCityList;
//        }
//        while (cursor.isAfterLast() == false) {
//            City entry = cityCursorToEntry(cursor);
//            myCityList.add(entry);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return myCityList;
//    }