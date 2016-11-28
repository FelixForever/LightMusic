package felix.lightmusic.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import felix.lightmusic.DB.DaoMaster;
import felix.lightmusic.DB.DaoSession;
import felix.lightmusic.DB.MusicDao;
import felix.lightmusic.DB.MusicListDao;
import felix.lightmusic.DB.MusicListItemDao;

/**
 * Created by felix on 11/12/2016.
 */


public class DBUtil {

    private static MusicDao sMusicDao;
    private static MusicListDao sMusicListDao;
    private static MusicListItemDao sMusicListItemDao;

    public static void init(Context context) {
        final DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "music-db", null);
        final SQLiteDatabase db = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        final DaoMaster daoMaster = new DaoMaster(db);

        final DaoSession daoSession = daoMaster.newSession();
        sMusicDao = daoSession.getMusicDao();
        sMusicListDao = daoSession.getMusicListDao();
        sMusicListItemDao = daoSession.getMusicListItemDao();
    }

    public static MusicDao getMusicDao() {
        return sMusicDao;
    }

    public static MusicListDao getMusicListDao() {
        return sMusicListDao;
    }

    public static MusicListItemDao getMusicListItemDao() {
        return sMusicListItemDao;
    }
}
