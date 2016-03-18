package com.example.lisa.myapplication;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Lisa on 15.03.2016.
 */
public class DataSource {
    private SQLiteDatabase database;
    private MySQLHelper dbHelper;
    public Context context;

    private static final String TABLE_USER = "user";
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

    private ContentValues registerUserData(User user){
        ContentValues values = new ContentValues();
        values.put("Name", user.getName());
        values.put("Username", user.getUsername());
        values.put("Password", user.getPassword());
        return values;
    }

    public void registerUser(User user){
        try{ContentValues values = registerUserData(user);

            long insertId = database.insert(TABLE_USER, null, values);
            Log.e(LOG_TAG, "Register User erfolgreich" );

        }catch (Exception ex){
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
        }

    }

    public boolean loginUser(User user){

        boolean result = false;

        try {
            Cursor cursor = database.query("user", null, " Username=? and Password=?",
                    new String[] { user.getUsername(),user.getPassword() }, null, null, null);

            if (cursor.getCount() == 1) // UserName Not Exist
            {
                result = true;
            }

            cursor.close();

        }catch (Exception ex){
            Log.e(LOG_TAG, "Fehler beim Login:" + ex.getMessage());
        }
        database.close();
        return result;
    }
//    /**
//     * Return ContentValues to insert new dataset
//     * @param City
//     */
//    private ContentValues cityData(City entry) {
//        ContentValues values = new ContentValues();
//        values.put("NAME", entry.getName());
//        values.put("COUNTRY", entry.getCountry());
//        values.put("POPULATION", entry.getPopulation());
//        return values;
//    }
//
//
//    /**
//     * Convert Cursor to City
//     * @param cursor
//     * @return City
//     *
//     */
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
//     * insert a new dataset of city
//     * @param City
//     */
//    public void insertCity(City entry) {
//        ContentValues values = cityData(entry);
//        long insertId = database.insert(TABLE_MYCITIES, null, values);
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
}
