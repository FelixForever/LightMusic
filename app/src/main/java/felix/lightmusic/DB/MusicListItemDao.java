package felix.lightmusic.DB;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table MUSIC_LIST_ITEM.
*/
public class MusicListItemDao extends AbstractDao<MusicListItem, Long> {

    public static final String TABLENAME = "MUSIC_LIST_ITEM";

    /**
     * Properties of entity MusicListItem.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "ID");
        public final static Property MusicId = new Property(1, Long.class, "musicId", false, "MUSIC_ID");
        public final static Property MusicListId = new Property(2, Long.class, "musicListId", false, "MUSIC_LIST_ID");
    };

    private DaoSession daoSession;

    private Query<MusicListItem> music_MusicsItemsQuery;
    private Query<MusicListItem> musicList_MusicsItemsQuery;

    public MusicListItemDao(DaoConfig config) {
        super(config);
    }
    
    public MusicListItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'MUSIC_LIST_ITEM' (" + //
                "'ID' INTEGER PRIMARY KEY ," + // 0: id
                "'MUSIC_ID' INTEGER," + // 1: musicId
                "'MUSIC_LIST_ID' INTEGER);"); // 2: musicListId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'MUSIC_LIST_ITEM'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, MusicListItem entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long musicId = entity.getMusicId();
        if (musicId != null) {
            stmt.bindLong(2, musicId);
        }
 
        Long musicListId = entity.getMusicListId();
        if (musicListId != null) {
            stmt.bindLong(3, musicListId);
        }
    }

    @Override
    protected void attachEntity(MusicListItem entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public MusicListItem readEntity(Cursor cursor, int offset) {
        MusicListItem entity = new MusicListItem( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // musicId
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2) // musicListId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, MusicListItem entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMusicId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setMusicListId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(MusicListItem entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(MusicListItem entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "musicsItems" to-many relationship of Music. */
    public List<MusicListItem> _queryMusic_MusicsItems(Long musicId) {
        synchronized (this) {
            if (music_MusicsItemsQuery == null) {
                QueryBuilder<MusicListItem> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.MusicId.eq(null));
                music_MusicsItemsQuery = queryBuilder.build();
            }
        }
        Query<MusicListItem> query = music_MusicsItemsQuery.forCurrentThread();
        query.setParameter(0, musicId);
        return query.list();
    }

    /** Internal query to resolve the "musicsItems" to-many relationship of MusicList. */
    public List<MusicListItem> _queryMusicList_MusicsItems(Long musicListId) {
        synchronized (this) {
            if (musicList_MusicsItemsQuery == null) {
                QueryBuilder<MusicListItem> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.MusicListId.eq(null));
                musicList_MusicsItemsQuery = queryBuilder.build();
            }
        }
        Query<MusicListItem> query = musicList_MusicsItemsQuery.forCurrentThread();
        query.setParameter(0, musicListId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getMusicDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getMusicListDao().getAllColumns());
            builder.append(" FROM MUSIC_LIST_ITEM T");
            builder.append(" LEFT JOIN MUSIC T0 ON T.'MUSIC_ID'=T0.'ID'");
            builder.append(" LEFT JOIN MUSIC_LIST T1 ON T.'MUSIC_LIST_ID'=T1.'ID'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected MusicListItem loadCurrentDeep(Cursor cursor, boolean lock) {
        MusicListItem entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Music music = loadCurrentOther(daoSession.getMusicDao(), cursor, offset);
        entity.setMusic(music);
        offset += daoSession.getMusicDao().getAllColumns().length;

        MusicList musicList = loadCurrentOther(daoSession.getMusicListDao(), cursor, offset);
        entity.setMusicList(musicList);

        return entity;    
    }

    public MusicListItem loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<MusicListItem> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<MusicListItem> list = new ArrayList<MusicListItem>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<MusicListItem> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<MusicListItem> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}