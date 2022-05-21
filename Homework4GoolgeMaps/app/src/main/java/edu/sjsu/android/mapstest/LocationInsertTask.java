package edu.sjsu.android.mapstest;

import android.content.ContentValues;

public interface LocationInsertTask {
    void doInBackground(ContentValues... contentValues);
}
