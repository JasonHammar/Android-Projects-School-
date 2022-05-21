package edu.sjsu.android.mapstest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LocationsDB extends SQLiteOpenHelper {
    private static String DBNAME = "locationmarkersqlite";

    private static int DATABASE_VERSION = 1;

    public static final String _ID = "_id";
    public static final String LAT = "lat";
    public static final String LNG = "lng";
    public static final String ZOOM = "zom";
    private static final String DATABASE_TABLE_NAME = "locations";
    private SQLiteDatabase mDB;

    static final String CREATE_DB_TABLE =    "create table " + DATABASE_TABLE_NAME + " ( " +
            _ID + " integer primary key autoincrement , " +
            LNG + " double , " +
            LAT + " double , " +
            ZOOM + " text " +
            " ) ";

    public LocationsDB(Context context) {
        super(context, DBNAME, null, DATABASE_VERSION);
        this.mDB = getWritableDatabase();
    }



    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(CREATE_DB_TABLE);
    }


    public long insert(ContentValues contentValues){
        return mDB.insert(DATABASE_TABLE_NAME, null, contentValues);


    }



    public int delete(){
        return mDB.delete(DATABASE_TABLE_NAME, null , null);

    }


    public Cursor query(){
        return mDB.query(DATABASE_TABLE_NAME, new String[] { _ID,  LAT , LNG, ZOOM } , null, null, null, null, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}