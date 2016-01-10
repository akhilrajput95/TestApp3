package com.zotlay.testapp3;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Akhil Rajput on 1/8/2016.
 */
public class MyDataBaseStructure {

    public static final String AUTHORITY =
            "com.zotlay.testapp3.provider";

    public static final Uri CONTENT_URI =
            Uri.parse("content://"+AUTHORITY);


    public MyDataBaseStructure(){};


    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String STATUS = "STATUS";
        public static final String TIMESTAMP = "TIMESTAMP";
        public static final String LATITUDE = "LATITUDE";
        public static final String LONGITUDE = "LONGITUDE";
        public static final String[] PROJECTION_ALL =        // use this for retrieving data. not used yet
                                { STATUS, TIMESTAMP,LATITUDE,LONGITUDE};
        public static final Uri CONTENT_URI_Table =
                Uri.withAppendedPath(
                        CONTENT_URI,
                        TABLE_NAME);


    }




}
