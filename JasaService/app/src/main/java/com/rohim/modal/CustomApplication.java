package com.rohim.modal;

import android.database.sqlite.SQLiteOpenHelper;

import com.clough.android.androiddbviewer.ADBVApplication;
import com.rohim.common.DatabaseHelper;

/**
 * Created by Asus on 25/10/2016.
 */

public class CustomApplication extends ADBVApplication {

    @Override
    public SQLiteOpenHelper getDataBase() {
        return new DatabaseHelper(getApplicationContext());
    }
}
