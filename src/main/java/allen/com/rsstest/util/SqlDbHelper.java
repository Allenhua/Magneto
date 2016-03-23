package allen.com.rsstest.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Allen on 2016/3/18.
 */
public class SqlDbHelper extends SQLiteOpenHelper {

    private final String sql = "create table if not exists magneto ("
            + "id integer primary key autoincrement, "
            + "fileName text, "
            + "fileSize text, "
            + "fileMagnet text, "
            + "fileUrl text unique)";

    private Context mContext;

    public SqlDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
        Toast.makeText(mContext,"数据库创建成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
