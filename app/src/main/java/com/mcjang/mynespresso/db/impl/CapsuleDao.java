package com.mcjang.mynespresso.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mcjang.mynespresso.app.login.UserModel;
import com.mcjang.mynespresso.db.DBHelper;

/**
 * Created by mcjan on 2016-11-28.
 */

public class CapsuleDao extends DBHelper {

    public CapsuleDao(Context paramContext) {
        super(paramContext);
    }

    public void saveUserAccount(UserModel user) {
        ContentValues cv = new ContentValues();
        cv.put("USER_EMAIL", user.getUserEmail());
        cv.put("USER_PASSWORD", user.getUserPassword());
        cv.put("USER_NAME", user.getUserName());
        SQLiteDatabase db = getWritableDatabase();
        db.insert("USERS", null, cv);
        db.close();
    }

    public UserModel getUserModel() {

        String query = "SELECT USER_EMAIL, USER_PASSWORD FROM USERS";
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        UserModel user = null;

        if(cursor.moveToNext()) {
            user = new UserModel();
            user.setUserEmail(cursor.getString(0));
            user.setUserPassword(cursor.getString(1));
        }

        return user;

    }

}
