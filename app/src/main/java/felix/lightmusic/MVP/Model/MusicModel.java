package felix.lightmusic.MVP.Model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import felix.lib.Base.Rx.NextSubscriber;
import felix.lib.Base.Rx.ThreadTransform;
import felix.lightmusic.DB.Music;
import felix.lightmusic.DB.MusicList;
import felix.lightmusic.DB.MusicListItem;
import felix.lightmusic.DB.MusicListItemDao;
import felix.lightmusic.Util.DBUtil;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by felix on 11/10/2016.
 */


public class MusicModel {
    /**
     * 从本地数据库获取音乐列表
     *
     * @param context
     * @return
     */
    public static Observable<List<Music>> getMusicListFromDB(Context context) {
        return Observable
                .create(new Observable.OnSubscribe<List<Music>>() {
                    @Override
                    public void call(Subscriber<? super List<Music>> subscriber) {
                        final List<Music> musics = DBUtil.getMusicDao().queryBuilder().list();
                        subscriber.onNext(musics);
                        subscriber.onCompleted();
                    }
                })
                .flatMap(musics -> musics == null || musics.isEmpty() ? getMusicListFromProvider(context) : Observable.just(musics))
                .compose(new ThreadTransform<>());
    }

    /**
     * 从系统获取音乐列表
     *
     * @param context
     * @return
     */
    private static Observable<List<Music>> getMusicListFromProvider(Context context) {
        return Observable.create(subscriber -> {
            List<Music> musicList = getMusicList(context);
            subscriber.onNext(musicList);
            subscriber.onCompleted();
        });
    }

    /**
     * 从系统获取音乐列表
     *
     * @param context
     * @return
     */
    private static List<Music> getMusicList(Context context) {
        List<Music> musicList = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();

        Cursor cursor = resolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (null == cursor) {
            return musicList;
        }

        final int idIndex = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
        final int titleIndex = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        final int artistIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        final int albumIdIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
        final int albumIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
        final int sizeIndex = cursor.getColumnIndex(MediaStore.Audio.Media.SIZE);
        final int durationIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
        final int nameIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
        final int urlIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        while (cursor.moveToNext()) {
            Music musicRepo = new Music();
            musicRepo.setIsFavorite(false);
//  自己建的类，用于存放查询到的音乐信息
//  播放音乐是用的是创建MediaPlayer实例，为其传递音乐文件的路径
            long id = cursor.getLong(idIndex);        //音乐id
            String title = cursor.getString(titleIndex); // 标题
            String arrtist = cursor.getString(artistIndex); // 歌手
            if ("<unknown>".equals(arrtist)) {
                arrtist = "未知艺术家";
            }
            int albumId = cursor.getInt(albumIdIndex);//专辑Id
            String album = cursor.getString(albumIndex); // 专辑图片
            long size = cursor.getLong(sizeIndex); // 大小
            long duration = cursor.getLong(durationIndex); // 时长
            String url = cursor.getString(urlIndex); // 音乐文件的路径
            String name = cursor.getString(nameIndex);// 音乐文件名
            if (duration >= 90000 && duration <= 900000 && !name.contains(".ogg")) {
//  此处添加music，音乐信息，到列表
                musicRepo.setTitle(title);
                musicRepo.setArtist(arrtist);
                musicRepo.setAlbum(getAlbumArt(context, id, albumId));

//                    musicRepo.setAlbumID();
                musicRepo.setSize(size);
                musicRepo.setDuration(duration);
                musicRepo.setUrl(url);
                musicRepo.setName(name);
                musicRepo.setId(id);
                musicList.add(musicRepo);
            }
        }
        cursor.close();
        return musicList;
    }

    /**
     * 根据id获取图片地址
     *
     * @param context
     * @param album_id
     * @return
     */
    private static String getAlbumArt(Context context, long musicId, int album_id) {
        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[]{"album_art"};
        Cursor cur = context.getContentResolver().query(
                Uri.parse(mUriAlbums + "/" + Integer.toString(album_id)),
                projection, null, null, null);
        String album_art = null;
        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            album_art = cur.getString(0);
        }
        cur.close();
        cur = null;
        return album_art;
    }


    public interface OnCompeleteListenner {
        void onCompelete(List<Music> musics);
    }

    /**
     * 更新本地数据库
     *
     * @param context
     * @param onCompeleteListenner
     */

    public static void updateDB(Context context, OnCompeleteListenner onCompeleteListenner) {
        getMusicListFromProvider(context)
                .compose(new ThreadTransform<>())
                .subscribe(new NextSubscriber<List<Music>>() {
                               @Override
                               public void onNext(List<Music> musics) {
                                   DBUtil.getMusicDao().deleteAll();
                                   DBUtil.getMusicDao().insertInTx(musics);
                                   final int size = musics.size();
                                   MusicList musicList = new MusicList();
                                   musicList.setId(0L);
                                   musicList.setName("默认列表");
                                   DBUtil.getMusicListDao().deleteByKey(0L);
                                   DBUtil.getMusicListDao().insert(musicList);
                                   MusicListItem[] musicListItems = new MusicListItem[size];
                                   for (int i = 0; i < size; i++) {
                                       musicListItems[i] = new MusicListItem(System.currentTimeMillis() + i, musics.get(i).getId(), 0L);
                                   }
                                   DBUtil.getMusicListItemDao().deleteInTx(DBUtil.getMusicListItemDao().queryBuilder().where(MusicListItemDao.Properties.MusicListId.eq(0L)).list());
                                   DBUtil.getMusicListItemDao().insertInTx(musicListItems);

                                   if (onCompeleteListenner != null) {
                                       onCompeleteListenner.onCompelete(musics);
                                   }
                               }
                           }

                );
    }

    /**
     * 更新本地数据库
     *
     * @param context
     */
    public static void updateDB(Context context) {
        updateDB(context, null);
    }
}
