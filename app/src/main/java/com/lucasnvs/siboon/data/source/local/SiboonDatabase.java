package com.lucasnvs.siboon.data.source.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.lucasnvs.siboon.utils.Constants;

@Database(entities = {LocalProduct.class}, version = Constants.DB_VERSION, exportSchema = false)
public abstract class SiboonDatabase extends RoomDatabase {

    private static volatile SiboonDatabase instance;

    public abstract ProductDAO productDAO();

    public static SiboonDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (SiboonDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    SiboonDatabase.class, Constants.DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
