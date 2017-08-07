package cn.wycode.rolltheball;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

/**
 * Created by wayne on 2017/8/7.
 */

public class PauseDialog extends AlertDialog{

    protected PauseDialog(Context context) {
        super(context);
        init();
    }

    private void init() {
        View view = getLayoutInflater().inflate(R.layout.dialog_pause,null);
        this.setView(view);
    }
}
