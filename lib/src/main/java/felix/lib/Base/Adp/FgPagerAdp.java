package felix.lib.Base.Adp;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import felix.lib.Base.Aty.BaseFg;

/**
 * Created by felix on 11/13/2016.
 */


public class FgPagerAdp extends FragmentPagerAdapter {
    protected Context mContext;
    private List<BaseFg> mBaseFgs;

    public FgPagerAdp(Context context, FragmentManager fm, List<BaseFg> fgs) {
        super(fm);
        mContext = context;
        mBaseFgs = fgs;
    }

    @Override
    public BaseFg getItem(int position) {
        return mBaseFgs.get(position);
    }



    @Override
    public int getCount() {
        return mBaseFgs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mBaseFgs.get(position).getTitle();
    }
}
