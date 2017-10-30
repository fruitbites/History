package com.soundbread.history.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.soundbread.history.data.db.model.Bookmark;
import com.soundbread.history.data.db.model.Recent;
import com.soundbread.history.di.scope.ApplicationContext;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;


/**
 * Created by fruitbites on 2016-05-09.
 */

@Singleton
public class HistoryDatabaseHelper extends SQLiteOpenHelper {

    private static File DATABASE_FILE;

    public static String DATABASE_NAME = "local.db";
    public static int DATABASE_VERSION = 1;


    private static final String TABLE_BOOKMARK = "BOOKMARK";
    private static final String TABLE_RECENT = "RECENT";

    private Context mContext = null;

    /*
    private static HistoryDatabaseHelper mInstance;








    public static synchronized HistoryDatabaseHelper getInstance(Context context) {
        if (mInstance == null) {

            mInstance = new HistoryDatabaseHelper(context);
        }
        return mInstance;
    }
    */

    @Inject
    public HistoryDatabaseHelper(@ApplicationContext Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.mContext = context;
    }



    /*
        Called when the database is created for the FIRST time.
        If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.

        즉, DISK에 DATABASE_NAME = "bible.db"이라는 DB가 있다면 절대로 두번 불리지 않는다
    */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Timber.d("onCreate(SQLiteDatabase db) called");

        String CREATE_BOOKMARK_TABLE = "CREATE TABLE " + TABLE_BOOKMARK +
                "(" +
                 "id" + " VARCHAR(8)," + // Define a primary key
                 "cdtm DATETIME , " +
                 "mdtm DATETIME " +
                ")";

        String CREATE_RECENT_TABLE = "CREATE TABLE " + TABLE_RECENT +
                "(" +
                "id" + " VARCHAR(8)," + // Define a primary key
                "cdtm DATETIME , " +
                "mdtm DATETIME " +
                ")";



        db.execSQL(CREATE_BOOKMARK_TABLE);
        db.execSQL(CREATE_RECENT_TABLE);
        Timber.i("SQLiteDatabase onCreate Called - BOOKMARK TABLES CREATED");

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }


    /*
        Called when the database needs to be upgraded.
        This method will only be called if a database already exists on disk with the same DATABASE_NAME,
        but the DATABASE_VERSION is different than the version of the database that exists on disk.
    */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKMARK);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECENT);
            onCreate(db);
        }
    }


   
    public long addOrUpdateBookmark(Bookmark bookmark) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        long termId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("id",bookmark.getId());

            values.put("cdtm", System.currentTimeMillis());
            values.put("mdtm", System.currentTimeMillis());


            // First try to update the user in case the user already exists in the database
            // This assumes userNames are unique
            DecimalFormat df = new DecimalFormat("0");
            int rows = db.update(TABLE_BOOKMARK, values, " id = ?  ", new String[]{ bookmark.getId() });

            // Check if update succeeded
            if (rows == 1) {
                // Get the primary key of the user we just updated
                String termsSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ? ", "id", TABLE_BOOKMARK, "id");


                Cursor cursor = db.rawQuery(termsSelectQuery, new String[]{ bookmark.getId() });

                try {
                    if (cursor.moveToFirst()) {
                        termId = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            }
            else {
                // term with this id did not already exist, so insert new user
                termId = db.insertOrThrow(TABLE_BOOKMARK, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Timber.e(e,"Error while trying to add or update bookmark (" + bookmark.getId() + ")");
        } finally {
            db.endTransaction();
        }
        return termId;
    }





    public List<Bookmark> getBookmarks() {
        List<Bookmark> list = new ArrayList<Bookmark>();


        DecimalFormat df = new DecimalFormat("0");

        String[] columns = {"id","cdtm","mdtm"};

        String orderBy = " mdtm desc ";

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_BOOKMARK, columns, null,null, null,null,orderBy);
        try {
            if (cursor.moveToFirst()) {
                do {

                    String id = cursor.getString(cursor.getColumnIndex("id"));

                    Date cdtm = new Date(cursor.getLong(cursor.getColumnIndex("cdtm")));
                    Date mdtm = new Date(cursor.getLong(cursor.getColumnIndex("mdtm")));


                    Bookmark mark = new Bookmark(id,cdtm,mdtm);
                    list.add(mark);
                } while(cursor.moveToNext());
            }

        } catch (Exception e) {
            Timber.e(e, "Error while trying to get all the bookmarks from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return list;
    }




    public void deleteBookmark(String id) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            DecimalFormat df = new DecimalFormat("0");
            int rows = db.delete(TABLE_BOOKMARK,"id = ? ", new String[]{ id });
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Timber.e(e,"Error while trying to delete bookmark using id (" + id + ")");
        } finally {
            db.endTransaction();
        }
    }



    public void deleteBookmark(Bookmark bookmark) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            DecimalFormat df = new DecimalFormat("0");
            int rows = db.delete(TABLE_BOOKMARK,"id = ? ", new String[]{ bookmark.getId()});
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Timber.e(e,"Error while trying to delete bookmark using bookmark (" + bookmark.getId() + ")");
        } finally {
            db.endTransaction();
        }
    }





    public long addOrUpdateRecent(String id) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        long termId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("id",id);
            values.put("cdtm", System.currentTimeMillis());
            values.put("mdtm", System.currentTimeMillis());


            // First try to update the user in case the user already exists in the database
            // This assumes userNames are unique
            DecimalFormat df = new DecimalFormat("0");
            int rows = db.update(TABLE_RECENT, values, " id = ?  ", new String[]{ id });

            // Check if update succeeded
            if (rows == 1) {
                // Get the primary key of the user we just updated
                String termsSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ? ", "id", TABLE_RECENT, "id");


                Cursor cursor = db.rawQuery(termsSelectQuery, new String[]{ id });

                try {
                    if (cursor.moveToFirst()) {
                        termId = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            }
            else {
                // term with this id did not already exist, so insert new user
                termId = db.insertOrThrow(TABLE_RECENT, null, values);
                Timber.d("termId " + termId);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Timber.e(e,"Error while trying to add or update recent using id (" + id + ")");
        } finally {
            db.endTransaction();
        }
        return termId;
    }






    public List<Recent> getRecents() {
        List<Recent> list = new ArrayList<Recent>();


        DecimalFormat df = new DecimalFormat("0");

        String[] columns = {"id","cdtm","mdtm"};

        String orderBy = " mdtm desc ";

        SQLiteDatabase db = getReadableDatabase();


        Cursor cursor = db.query(TABLE_RECENT, columns, null,null, null,null,orderBy);
        try {
            if (cursor.moveToFirst()) {
                do {

                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    Date cdtm = new Date(cursor.getLong(cursor.getColumnIndex("cdtm")));
                    Date mdtm = new Date(cursor.getLong(cursor.getColumnIndex("mdtm")));

                    Recent recent = new Recent(id,cdtm,mdtm);
                    list.add(recent);
                } while(cursor.moveToNext());
            }

        } catch (Exception e) {
            Timber.e(e, "Error while trying to get all the recents from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return list;
    }




    public void deleteRecent(String id) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            DecimalFormat df = new DecimalFormat("0");
            int rows = db.delete(TABLE_RECENT,"id = ? ", new String[]{ id });
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Timber.e(e,"Error while trying to delete recent using id(" + id + ")");
        } finally {
            db.endTransaction();
        }
    }



    public void deleteRecent(Recent recent) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            DecimalFormat df = new DecimalFormat("0");
            int rows = db.delete(TABLE_RECENT,"id = ? ", new String[]{ recent.getId()});
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Timber.e(e,"Error while trying to delete recent using recent (" + recent.getId() + ")");
        } finally {
            db.endTransaction();
        }
    }

    public void deleteRecents() {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            DecimalFormat df = new DecimalFormat("0");
            int rows = db.delete(TABLE_RECENT,"1 = 1", null);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Timber.e(e,"Error while trying to delete all recents from database");
        } finally {
            db.endTransaction();
        }
    }





}
