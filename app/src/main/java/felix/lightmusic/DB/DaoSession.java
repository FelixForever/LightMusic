package felix.lightmusic.DB;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig musicDaoConfig;
    private final DaoConfig musicListDaoConfig;
    private final DaoConfig musicListItemDaoConfig;

    private final MusicDao musicDao;
    private final MusicListDao musicListDao;
    private final MusicListItemDao musicListItemDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        musicDaoConfig = daoConfigMap.get(MusicDao.class).clone();
        musicDaoConfig.initIdentityScope(type);

        musicListDaoConfig = daoConfigMap.get(MusicListDao.class).clone();
        musicListDaoConfig.initIdentityScope(type);

        musicListItemDaoConfig = daoConfigMap.get(MusicListItemDao.class).clone();
        musicListItemDaoConfig.initIdentityScope(type);

        musicDao = new MusicDao(musicDaoConfig, this);
        musicListDao = new MusicListDao(musicListDaoConfig, this);
        musicListItemDao = new MusicListItemDao(musicListItemDaoConfig, this);

        registerDao(Music.class, musicDao);
        registerDao(MusicList.class, musicListDao);
        registerDao(MusicListItem.class, musicListItemDao);
    }
    
    public void clear() {
        musicDaoConfig.getIdentityScope().clear();
        musicListDaoConfig.getIdentityScope().clear();
        musicListItemDaoConfig.getIdentityScope().clear();
    }

    public MusicDao getMusicDao() {
        return musicDao;
    }

    public MusicListDao getMusicListDao() {
        return musicListDao;
    }

    public MusicListItemDao getMusicListItemDao() {
        return musicListItemDao;
    }

}
