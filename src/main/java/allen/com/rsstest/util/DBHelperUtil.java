package allen.com.rsstest.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import allen.com.rsstest.pojo.MagnetFilePojo;

/**
 * Created by Allen on 2016/3/18.
 */
public class DBHelperUtil {
    private static DBHelperUtil dbUtil;
    private SqlDbHelper mDbHelper;
    private SQLiteDatabase sqlitDB;

    private DBHelperUtil(Context context){
        mDbHelper = new SqlDbHelper(context,"magnetodb.db",null,1);
    }

    public static DBHelperUtil getInstance(Context context){
        if ( dbUtil== null) {
            dbUtil = new DBHelperUtil(context);
        }

        return dbUtil;
    }

    public void openSqlite(){
        sqlitDB = mDbHelper.getWritableDatabase();
    }

    public void closeSqlite(){
        if (sqlitDB.isOpen()){
            sqlitDB.close();
        }
    }

    public List showAllData(){
        List<MagnetFilePojo> list = new ArrayList<>();
        Cursor cursor = sqlitDB.rawQuery("SELECT * FROM magneto",null);
        if (cursor != null) {
            while (cursor.moveToNext()){
                MagnetFilePojo magneto = new MagnetFilePojo();
                magneto.setFileName(cursor.getString(cursor.getColumnIndex("fileName")));
                magneto.setFileSize(cursor.getString(cursor.getColumnIndex("fileSize")));
                magneto.setFileMagnet(cursor.getString(cursor.getColumnIndex("fileMagnet")));
                magneto.setFileUrl(cursor.getString(cursor.getColumnIndex("fileUrl")));
                list.add(magneto);
            }
            cursor.close();
        }
        return list;
    }

    public boolean deleteData(MagnetFilePojo magnet){
        sqlitDB.beginTransaction();
        sqlitDB.delete("magneto","fileMagnet = ?",new String[]{magnet.getFileMagnet()});
        sqlitDB.endTransaction();
        return true;
    }

    public boolean insertData(List<MagnetFilePojo> list){
        sqlitDB.beginTransaction();
        if (list.size() != 0) {
            for (MagnetFilePojo magneto:list) {
                ContentValues values = new ContentValues();
                values.put("fileName",magneto.getFileName());
                values.put("fileSize",magneto.getFileSize());
                values.put("fileMagnet",magneto.getFileMagnet());
                values.put("fileUrl",magneto.getFileUrl());
                sqlitDB.insertWithOnConflict("magneto",null,values,SQLiteDatabase.CONFLICT_REPLACE);
            }
        }
        sqlitDB.endTransaction();
        return true;
    }
}
