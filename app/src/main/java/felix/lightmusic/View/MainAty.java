package felix.lightmusic.View;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import felix.lib.Base.Adp.FgPagerAdp;
import felix.lib.Base.Aty.BaseAty;
import felix.lib.Base.Aty.BaseFg;
import felix.lib.Base.BindLayout;
import felix.lib.Base.Util.ViewUtil;
import felix.lightmusic.R;
import felix.lightmusic.Receiver.MusicManager;
import felix.lightmusic.Service.MusicService;

/**
 * Created by felix on 11/10/2016.
 */

@BindLayout(R.layout.aty_main)
public class MainAty extends BaseAty {


    @Bind(R.id.toobar)
    Toolbar mToobar;
    @Bind(R.id.tab)
    TabLayout mTab;
    @Bind(R.id.vp_main)
    ViewPager mVpMain;
    @Bind(R.id.nv_main)
    NavigationView mNvMain;
    @Bind(R.id.dl_main)
    DrawerLayout mDlMain;

    /**
     * 音乐控制
     */
    @Bind(R.id.iv_album)
    ImageView mIvAlbum;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_artist)
    TextView mTvArtist;
    @Bind(R.id.iv_previous)
    ImageView mIvPrevious;
    @Bind(R.id.iv_play)
    ImageView mIvPlay;
    @Bind(R.id.iv_next)
    ImageView mIvNext;
    @Bind(R.id.ll_music)
    LinearLayout mLlMusic;
    private FgPagerAdp mFgPagerAdp;
    private MusicManager.MusicObserve mMusicObserve;

    @Override
    protected void initData() {
        super.initData();
        initDrawer();
        List<BaseFg> baseFgs = new ArrayList<>();
        baseFgs.add(new MusicFg());
        // android.support.design.R.drawable.abc_ic_ab_back_material
        //baseFgs.add(new MusicFg());
        baseFgs.add(new OtherFg());
        //baseFgs.add(new OtherFg());
        mFgPagerAdp = new FgPagerAdp(mContext, getSupportFragmentManager(), baseFgs);
        mVpMain.setAdapter(mFgPagerAdp);
        mTab.setupWithViewPager(mVpMain);
        mMusicObserve = music -> {
            mTvArtist.setText(music.getArtist());
            mTvTitle.setText(music.getTitle());
            Glide.with(mContext)
                    .load(music.getAlbum())
                    .placeholder(ViewUtil.getDrawableById(R.drawable.v_def_music))
                    .dontAnimate()
                    .into(mIvAlbum);
        };
        MusicManager.observe(mMusicObserve);

    }

    @OnClick({R.id.iv_next, R.id.iv_previous, R.id.iv_play})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_play:
                final boolean isSelected = mIvPlay.isSelected();
                MusicService.play(mContext, isSelected);
                mIvPlay.setSelected(!isSelected);
                break;
            case R.id.iv_next:
                MusicService.playNext(mContext);
                break;
            case R.id.iv_previous:
                MusicService.playPrevious(mContext);
                break;
            default:
                break;

        }
    }


    private void initDrawer() {
        final View headerView = mNvMain.getHeaderView(0);
        if (headerView != null) {
            headerView.setOnClickListener(v -> {

            });
            mNvMain.setItemIconTintList(null);
        }
        mNvMain.setNavigationItemSelectedListener(item -> {
            final int id = item.getItemId();
            switch (id) {
                default:
                    break;
            }
            return true;
        });
        setSupportActionBar(mToobar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        mToobar.setNavigationOnClickListener(v -> {
            if (mDlMain.isDrawerOpen(mNvMain)) {
                mDlMain.closeDrawer(mNvMain);
            } else {
                mDlMain.openDrawer(mNvMain);
            }
        });
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDlMain, 0, 0) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDlMain.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mNvMain.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.test:
                    startAty(Test.class);
                    //startAty(TestOnCreate.class);
                    break;
                default:
                    break;
            }
            return true;
        });
    }
}
