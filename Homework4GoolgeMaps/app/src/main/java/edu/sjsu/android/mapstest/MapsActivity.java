package edu.sjsu.android.mapstest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.sjsu.android.mapstest.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LoaderManager.LoaderCallbacks<Cursor> {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private final LatLng LOCATION_UNIV = new LatLng(37.335371, -121.881050);
    private final LatLng LOCATION_CS = new LatLng(37.333714, -121.881860);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Button CS = (Button) findViewById(R.id.btnCS);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //getSupportLoaderManager().initLoader(0, null, this);
        LoaderManager.getInstance(this);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng point) {
                MarkerOptions markOpt = new MarkerOptions();
                markOpt.position(point);
                googleMap.addMarker(markOpt);
                LocationInsertTask insertTask = new LocationInsertTask();
                ContentValues values = new ContentValues();
                insertTask.execute(values);
                values.put(LocationsDB.LAT, point.latitude);
                values.put(LocationsDB.LNG, point.longitude);
                values.put(LocationsDB.ZOOM, googleMap.getCameraPosition().zoom);
                Toast.makeText(getBaseContext(), "Marker has been added to the map", Toast.LENGTH_SHORT).show();
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener(){

            @Override
            public void onMapLongClick(@NonNull LatLng latLng) {
                googleMap.clear();
                LocationDeleteTask deleteTask = new LocationDeleteTask();
                deleteTask.execute();
                Toast.makeText(getBaseContext(), "All markers have been removed", Toast.LENGTH_SHORT).show();
            }
        });
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(LOCATION_CS).title("find me Here!"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LOCATION_CS));
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_CS, 18);
        mMap.animateCamera(update);
    }

    public void onClick_CS(View v){
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_CS, 18);
        mMap.animateCamera(update);
    }

    public void onClick_Univ(View v){
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNIV, 14);
        mMap.animateCamera(update);
    }

    public void onClick_City(View v){
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNIV, 10);
        mMap.animateCamera(update);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        Uri uri = LocationsContentProvider.CONTENT_URI;
        Loader<Cursor> c = new CursorLoader(this, uri, null, null, null, null);
        return c;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> arg0, Cursor arg1) {
        int locationCount = 0;
        double lat = 0;
        double lng = 0;
        float zoom = 0;

        if(arg1 != null){
            locationCount = arg1.getCount();
            arg1.moveToFirst();
        }else{
            locationCount = 0;
        }



        for(int i = 0; i < locationCount; i++){
            lat = arg1.getDouble(arg1.getColumnIndex(LocationsDB.LAT));
            lng = arg1.getDouble(arg1.getColumnIndex(LocationsDB.LNG));
            zoom = arg1.getFloat(arg1.getColumnIndex(LocationsDB.ZOOM));
            LatLng location = new LatLng(lat, lng);
            drawMarker(location);
            arg1.moveToNext();
        }

        if(locationCount > 0){
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat,lng)));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(zoom));
        }
    }

    private void drawMarker(LatLng point){

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(point);
        mMap.addMarker(markerOptions);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    private class LocationInsertTask extends AsyncTask<ContentValues, Void, Void> {
        @Override
        protected Void doInBackground(ContentValues... contentValues) {

            /** Setting up values to insert the clicked location into SQLite database */
            getContentResolver().insert(LocationsContentProvider.CONTENT_URI, contentValues[0]);
            return null;
        }
    }

    //public class LocationInsertTaskImpl extends LocationInsertTask { }

    private class LocationDeleteTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {

            /** Deleting all the locations stored in SQLite database */
            getContentResolver().delete(LocationsContentProvider.CONTENT_URI, null, null);
            return null;
        }
    }
}