package felix.lightmusic.View;

import felix.lib.Base.Aty.BaseLogoAty;
import felix.lightmusic.MVP.Model.MusicModel;
import felix.lightmusic.R;
import felix.lightmusic.Util.DBUtil;

public class LogoAty extends BaseLogoAty {

    @Override
    protected int getLayoutId() {
        return R.layout.aty_logo;
    }

    @Override
    protected Class getClsAty() {
        return MainAty.class;
    }

    @Override
    protected int getTime() {
        return -1;
    }

    @Override
    protected void afterInitCompelete() {
        DBUtil.init(this.getApplicationContext());
        MusicModel.updateDB(mContext, musics -> super.afterInitCompelete());
    }
}
