package com.soundbread.history.data.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.soundbread.history.data.db.model.Content;
import com.soundbread.history.data.db.model.Incident;
import com.soundbread.history.di.scope.ApplicationContext;
import com.soundbread.history.di.scope.DatabaseInfo;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

/**
 * Created by fruitbites on 2016-05-09.
 */

@Singleton
public class InformationDatabaseHelper extends SQLiteAssetHelper {
    private static File DATABASE_FILE;

    public static String DATABASE_NAME = "history.db";

    public static int DATABASE_VERSION = 1;


    // Table Names
    private static final String TABLE_INCIDENT = "INCIDENT";
    private static final String TABLE_CONTENT = "CONTENT";



    /*
    private static InformationDatabaseHelper mInstance;



    private Context mContext = null;




    public static synchronized InformationDatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new InformationDatabaseHelper(context);
        }
        return mInstance;
    }


    private InformationDatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
*/

    @Inject
    public InformationDatabaseHelper(@ApplicationContext Context context,
                    @DatabaseInfo String dbName,
                    @DatabaseInfo Integer version) {
        super(context, dbName, null, version);
    }

    public List<Content> getContents(String id) {
        List<Content> contents = new ArrayList<Content>();


        DecimalFormat df = new DecimalFormat("0");

        String[] columns = {"id","ord","title","content","cdtm","mdtm"};
        String where = " id = ? ";
        String[] params = { id };
        String orderBy = " ord ";

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTENT, columns, where, params, null,null,orderBy);
        try {
            if (cursor.moveToFirst()) {
                do {

                    int ord = cursor.getInt(cursor.getColumnIndex("ord"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String content = cursor.getString(cursor.getColumnIndex("content"));

                    Content c = new Content(id,ord,title,content);
                    contents.add(c);
                } while(cursor.moveToNext());
            }

        } catch (Exception e) {
            Timber.e(e, "Error while trying to get all the content from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return contents;
    }



    public Incident getIncident(String id) throws IOException {
        Incident incident = null;


        DecimalFormat df = new DecimalFormat("0");

        String[] columns = {"id","time","tp","dynasty","name","chinese","photo","thumb_on","thumb_off","desc","birth","alias","period","event","materials","remains","person","organization","antecedents"};
        String where = "id = ? ";
        String[] params = {id};

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_INCIDENT, columns, where, params, null,null,null);

        try {
            if (cursor.moveToFirst()) {


                int time = cursor.getInt(cursor.getColumnIndex("time"));
                String tp = cursor.getString(cursor.getColumnIndex("tp"));
                String dynasty = cursor.getString(cursor.getColumnIndex("dynasty"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String chinese = cursor.getString(cursor.getColumnIndex("chinese"));
                String photo = cursor.getString(cursor.getColumnIndex("photo"));
                String thumbOn = cursor.getString(cursor.getColumnIndex("thumb_on"));
                String thumbOff = cursor.getString(cursor.getColumnIndex("thumb_off"));
                String desc = cursor.getString(cursor.getColumnIndex("desc"));
                String birth = cursor.getString(cursor.getColumnIndex("birth"));
                String alias = cursor.getString(cursor.getColumnIndex("alias"));
                String period = cursor.getString(cursor.getColumnIndex("period"));
                String event = cursor.getString(cursor.getColumnIndex("event"));
                String materials = cursor.getString(cursor.getColumnIndex("materials"));
                String remains = cursor.getString(cursor.getColumnIndex("remains"));
                String person = cursor.getString(cursor.getColumnIndex("person"));
                String organization = cursor.getString(cursor.getColumnIndex("organization"));
                String antecedents = cursor.getString(cursor.getColumnIndex("antecedents"));

                List<Content> contents = getContents(id);

                incident = new Incident(id, time, tp, dynasty, name, chinese, photo, thumbOn, thumbOff, desc, birth, alias, period, event, materials, remains, person, organization, antecedents, contents);
            }
            else {
                throw new IOException("No Data");
            }
        } catch (Exception e) {
            Timber.e(e, "Error while trying to get term using id + (" + id + ") from database");
            throw new IOException(e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return incident;
    }

    public List<Incident> getIncidents() throws IOException {
        List<Incident> incidents = new ArrayList<Incident>();


        DecimalFormat df = new DecimalFormat("0");

        String[] columns = {"id","time","tp","dynasty","name","chinese","photo","thumb_on","thumb_off","desc","birth","alias","period","event","materials","remains","person","organization","antecedents"};
        String orderBy = " time ";

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_INCIDENT, columns, null, null, null,null,orderBy);

        try {
            if(cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    int time = cursor.getInt(cursor.getColumnIndex("time"));
                    String tp = cursor.getString(cursor.getColumnIndex("tp"));
                    String dynasty = cursor.getString(cursor.getColumnIndex("dynasty"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String chinese = cursor.getString(cursor.getColumnIndex("chinese"));
                    String photo = cursor.getString(cursor.getColumnIndex("photo"));
                    String thumbOn = cursor.getString(cursor.getColumnIndex("thumb_on"));
                    String thumbOff = cursor.getString(cursor.getColumnIndex("thumb_off"));
                    String desc = cursor.getString(cursor.getColumnIndex("desc"));
                    String birth = cursor.getString(cursor.getColumnIndex("birth"));
                    String alias = cursor.getString(cursor.getColumnIndex("alias"));
                    String period = cursor.getString(cursor.getColumnIndex("period"));
                    String event = cursor.getString(cursor.getColumnIndex("event"));
                    String materials = cursor.getString(cursor.getColumnIndex("materials"));
                    String remains = cursor.getString(cursor.getColumnIndex("remains"));
                    String person = cursor.getString(cursor.getColumnIndex("person"));
                    String organization = cursor.getString(cursor.getColumnIndex("organization"));
                    String antecedents = cursor.getString(cursor.getColumnIndex("antecedents"));

                    List<Content> contents = new ArrayList<Content>();

                    incidents.add(new Incident(id, time, tp, dynasty, name, chinese, photo, thumbOn, thumbOff, desc, birth, alias, period, event, materials, remains, person, organization, antecedents, contents));
                }while(cursor.moveToNext());
            }

        } catch (Exception e) {
            Timber.e(e, "Error while trying to get incidents from database");
            throw new IOException(e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return incidents;
    }











}
