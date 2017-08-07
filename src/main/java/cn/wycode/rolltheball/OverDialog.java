package cn.wycode.rolltheball;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

/**
 * Created by wayne on 2017/8/7.
 */

public class OverDialog extends AlertDialog{

    protected OverDialog(Context context) {
        super(context);
        init();
    }

    private void init() {
        View view = getLayoutInflater().inflate(R.layout.dialog_over,null);
        this.setView(view);
    }
}
