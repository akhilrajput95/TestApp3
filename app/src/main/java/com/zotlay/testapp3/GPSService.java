package com.zotlay.testapp3;

import android.Manifest;
import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Akhil Rajput on 1/8/2016.
 */
public class GPSService extends IntentService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public GoogleApiClient mGoogleApiClient;
    public LocationRequest mLocationRequest;
    public static Location mCurrentLocation;//stores new location coordinates.
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 9500;     //time interval for next location request
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    String mLatitudeText;  //just used for toast
    String mLongitudeText;  //just used for toast
    Uri mNewUri;  //stores the uri for new row added without status.
    ContentValues mNewValues = new ContentValues();  //stores the value for new row to b added.


    public GPSService() {
        super("GPSService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        buildGoogleApiClient();
        if(!mGoogleApiClient.isConnected())mGoogleApiClient.connect();




    }

    protected synchronized void buildGoogleApiClient() {
        Log.i("build", "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();


        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void startLocationUpdates() {
        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        Log.d("akhil", "rajput");


    }



    @Override
    public void onConnected(Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startLocationUpdates();

        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mCurrentLocation != null) {

            mLatitudeText = String.valueOf(mCurrentLocation.getLatitude());
            mLongitudeText = String.valueOf(mCurrentLocation.getLongitude());
            Toast.makeText(getApplicationContext(), "latitude="+mLatitudeText, Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "longitude="+mLongitudeText, Toast.LENGTH_SHORT).show();
            mNewValues.put(MyDataBaseStructure.FeedEntry.STATUS, "");
            mNewValues.put(MyDataBaseStructure.FeedEntry.TIMESTAMP,  getDateTime());
            mNewValues.put(MyDataBaseStructure.FeedEntry.LATITUDE, mLatitudeText);
            mNewValues.put(MyDataBaseStructure.FeedEntry.LONGITUDE, mLongitudeText);
            mNewUri = getContentResolver().insert(MyDataBaseStructure.FeedEntry.CONTENT_URI_Table,mNewValues);
                    }

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        Log.d("location","changed");
    }
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
