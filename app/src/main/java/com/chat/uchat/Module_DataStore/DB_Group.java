package com.chat.uchat.Module_DataStore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.chat.uchat.Module_DataModel.MDL_Group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DB_Group {
    private static GroupDBHelper mDbHelper = null;

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DB_Group() {
    }

    private static DB_Group instance = null;

    public static DB_Group getInstance(Context context) {
        if (instance == null) {
            instance = new DB_Group();
            mDbHelper = new GroupDBHelper(context);
        }
        return instance;
    }

    public void addGroup(MDL_Group MDLGroup) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_GROUP_ID, MDLGroup.id);
        values.put(FeedEntry.COLUMN_GROUP_NAME, MDLGroup.groupInfo.get("name"));
        values.put(FeedEntry.COLUMN_GROUP_ADMIN, MDLGroup.groupInfo.get("admin"));

        for (String idMenber : MDLGroup.member) {
            values.put(FeedEntry.COLUMN_GROUP_MEMBER, idMenber);
            // Insert the new row, returning the primary key value of the new row
            db.insert(FeedEntry.TABLE_NAME, null, values);
        }
    }

    public void deleteGroup(String idGroup){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(FeedEntry.TABLE_NAME, FeedEntry.COLUMN_GROUP_ID + " = " + idGroup , null);
    }


    public void addListGroup(ArrayList<MDL_Group> listMDLGroup) {
        for (MDL_Group MDLGroup : listMDLGroup) {
            addGroup(MDLGroup);
        }
    }

    public MDL_Group getGroup(String id){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + FeedEntry.TABLE_NAME + " where " + FeedEntry.COLUMN_GROUP_ID +" = " + id, null);
        MDL_Group newMDLGroup = new MDL_Group();
        while (cursor.moveToNext()) {
            String idGroup = cursor.getString(0);
            String nameGroup = cursor.getString(1);
            String admin = cursor.getString(2);
            String member = cursor.getString(3);
            newMDLGroup.id = idGroup;
            newMDLGroup.groupInfo.put("name", nameGroup);
            newMDLGroup.groupInfo.put("admin", admin);
            newMDLGroup.member.add(member);
        }
        return newMDLGroup;
    }

    public ArrayList<MDL_Group> getListGroups() {
        Map<String, MDL_Group> mapGroup = new HashMap<>();
        ArrayList<String> listKey = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        try {
            Cursor cursor = db.rawQuery("select * from " + FeedEntry.TABLE_NAME, null);
            while (cursor.moveToNext()) {
                String idGroup = cursor.getString(0);
                String nameGroup = cursor.getString(1);
                String admin = cursor.getString(2);
                String member = cursor.getString(3);
                if (!listKey.contains(idGroup)) {
                    MDL_Group newMDLGroup = new MDL_Group();
                    newMDLGroup.id = idGroup;
                    newMDLGroup.groupInfo.put("name", nameGroup);
                    newMDLGroup.groupInfo.put("admin", admin);
                    newMDLGroup.member.add(member);
                    listKey.add(idGroup);
                    mapGroup.put(idGroup, newMDLGroup);
                } else {
                    mapGroup.get(idGroup).member.add(member);
                }
            }
            cursor.close();
        } catch (Exception e) {
            return new ArrayList<MDL_Group>();
        }

        ArrayList<MDL_Group> listMDLGroup = new ArrayList<>();
        for (String key : listKey) {
            listMDLGroup.add(mapGroup.get(key));
        }

        return listMDLGroup;
    }

    public void dropDB() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }


    public static class FeedEntry implements BaseColumns {
        static final String TABLE_NAME = "groups";
        static final String COLUMN_GROUP_ID = "groupID";
        static final String COLUMN_GROUP_NAME = "name";
        static final String COLUMN_GROUP_ADMIN = "admin";
        static final String COLUMN_GROUP_MEMBER = "memberID";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry.COLUMN_GROUP_ID + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_GROUP_NAME + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_GROUP_ADMIN + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_GROUP_MEMBER + TEXT_TYPE + COMMA_SEP +
                    "PRIMARY KEY (" + FeedEntry.COLUMN_GROUP_ID + COMMA_SEP +
                    FeedEntry.COLUMN_GROUP_MEMBER + "))";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;


    private static class GroupDBHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        static final int DATABASE_VERSION = 1;
        static final String DATABASE_NAME = "GroupChat.db";

        GroupDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }
}
