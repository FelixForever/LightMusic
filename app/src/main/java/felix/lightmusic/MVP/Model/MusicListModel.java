package felix.lightmusic.MVP.Model;

import java.util.List;

import felix.lib.Base.Rx.NextSubscriber;
import felix.lib.Base.Rx.ThreadTransform;
import felix.lightmusic.DB.Music;
import felix.lightmusic.DB.MusicList;
import felix.lightmusic.DB.MusicListDao;
import felix.lightmusic.DB.MusicListItem;
import felix.lightmusic.Util.DBUtil;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by felix on 11/12/2016.
 */


public class MusicListModel {
    /**
     * 获取音乐列表
     *
     * @return
     */
    public static Observable<List<MusicList>> getMusicList() {
        return Observable.create(new Observable.OnSubscribe<List<MusicList>>() {
            @Override
            public void call(Subscriber<? super List<MusicList>> subscriber) {
                List<MusicList> musicLists = DBUtil.getMusicListDao().queryBuilder().list();
                subscriber.onNext(musicLists);
            }
        })
                .compose(new ThreadTransform<>());
    }

    /**
     * 根据列表获取音乐列表
     *
     * @param listId
     * @return
     */
    public static Observable<List<Music>> getListMusic(long listId) {
        return Observable
                .create(new Observable.OnSubscribe<List<MusicListItem>>() {
                    @Override
                    public void call(Subscriber<? super List<MusicListItem>> subscriber) {
                        List<MusicListItem> musicListItems = DBUtil.getMusicListDao()
                                .load(listId).getMusicsItems();
                        subscriber.onNext(musicListItems);
                    }
                })
                .flatMap(musicListItems -> Observable.from(musicListItems))
                .map(musicListItem -> musicListItem.getMusic())
                .toList()
                .compose(new ThreadTransform<>());
    }

    /**
     * 根据列表获取音乐列表
     *
     * @param listId
     * @return
     */
    public static Observable<MusicList> getMusicList(long listId) {
        if (listId == -1) {
            return getMusicList()
                    .map(musicLists -> musicLists == null || musicLists.isEmpty() ? null : musicLists.get(0));
        }
        return Observable
                .create(new Observable.OnSubscribe<MusicList>() {
                    @Override
                    public void call(Subscriber<? super MusicList> subscriber) {
                        MusicList musicListItems = DBUtil.getMusicListDao()
                                .load(listId);
                        subscriber.onNext(musicListItems);
                    }
                })
                .compose(new ThreadTransform<>());
    }


    /**
     * 给定列表，添加新列表
     *
     * @param musicList
     */
    public static MusicList addNewList(MusicList musicList) {
        DBUtil.getMusicListDao().insert(musicList);
        return musicList;
    }

    /**
     * 给定名称添加新列表
     *
     * @param name
     */
    public static MusicList addNewList(String name) {
        MusicList musicList = new MusicList(System.currentTimeMillis(), name);
        return addNewList(musicList);
    }

    /**
     * 添加音乐到指定列表
     *
     * @param listId
     * @param musics
     */
    public static void addMusicToList(long listId, List<Music> musics) {
        Observable.from(musics)
                .map(music -> music.getId())
                .map(aLong -> new MusicListItem(System.currentTimeMillis(), listId, aLong))
                .toList()
                .compose(new ThreadTransform<>())
                .subscribe(new NextSubscriber<List<MusicListItem>>() {
                    @Override
                    public void onNext(List<MusicListItem> musicListItems) {
                        DBUtil.getMusicListItemDao().insertInTx(musicListItems);
                    }
                });
    }

    /**
     * 创建列表并添加音乐
     *
     * @param name
     * @param musics
     */
    public static void addMusicToList(String name, List<Music> musics) {
        final MusicList musicList = addNewList(name);
        addMusicToList(musicList.getId(), musics);
    }


    /**
     * 更新名称
     *
     * @param listId
     * @param newName
     */
    public static void updateListName(long listId, String newName) {
        MusicList musicList = DBUtil.getMusicListDao().load(listId);
        musicList.setName(newName);
        DBUtil.getMusicListDao().update(musicList);
    }


    /**
     * 删除指定列表
     *
     * @param listId
     */
    public static void deleteList(long listId) {
        DBUtil.getMusicListDao().deleteByKey(listId);
    }

    /**
     * 根据名字删除列表
     *
     * @param name
     */
    public static void deleteList(String name) {
        final MusicListDao musicListDao = DBUtil.getMusicListDao();
        MusicList musicList = musicListDao.queryBuilder().where(MusicListDao.Properties.Name.eq(name)).build().uniqueOrThrow();
        musicListDao.delete(musicList);
    }


}
