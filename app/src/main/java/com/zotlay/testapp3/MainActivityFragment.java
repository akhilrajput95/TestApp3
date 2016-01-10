package com.zotlay.testapp3;

import android.content.ContentValues;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment  {


    TextView barStatus;
    Location mLastLocation;
    String mLatitudeText;
    String mLongitudeText;
    Uri mNewUri;
    ContentValues mNewValues = new ContentValues();

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        AlarmReceiver alarm= new AlarmReceiver();
        alarm.setAlarm(getActivity());

        final Button button = (Button) view.findViewById(R.id.button);
         barStatus=(TextView) view.findViewById(R.id.textView);
        button.setTag(1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final int status =(Integer) arg0.getTag();

                //code for gathering information from GPSService class directly.
                mLastLocation=GPSService.mCurrentLocation;
                mNewValues.put(MyDataBaseStructure.FeedEntry.STATUS, button.getText().toString());
                mNewValues.put(MyDataBaseStructure.FeedEntry.TIMESTAMP, getDateTime());
                mNewValues.put(MyDataBaseStructure.FeedEntry.LATITUDE, mLatitudeText);
                mNewValues.put(MyDataBaseStructure.FeedEntry.LONGITUDE, mLongitudeText);
                mNewUri = getActivity().getContentResolver().insert(MyDataBaseStructure.FeedEntry.CONTENT_URI_Table,mNewValues);
                Log.d("data", mNewUri.toString());


                //code for change in button text on clicking
                if(status == 1) {
                    button.setText("DEPARTED");
                    barStatus.setText("JOURNEY ENDED");
                    arg0.setTag(0);
                } else {
                    button.setText("ARRIVED");
                    barStatus.setText("JOURNEY STARTED");
                    arg0.setTag(1);
                }



            }

            });
        return view;

    }
    //this method gets the time stamp.
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }






}
