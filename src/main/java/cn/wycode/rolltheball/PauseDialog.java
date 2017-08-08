package cn.wycode.rolltheball;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by wayne on 2017/8/7.
 */

public class PauseDialog extends AlertDialog implements View.OnClickListener {

    private WebView webView;

    private ImageView ivBack;
    private ImageView ivRestart;

    private PauseDialogListener listener;

    private String url = "http://wycode.cn/game/rollTheBall/adPause.html";

    protected PauseDialog(Context context) {
        super(context);
        init();
    }

    private void init() {
        View view = getLayoutInflater().inflate(R.layout.dialog_pause, null);
        this.setView(view);

        webView = (WebView) view.findViewById(R.id.wv_pause);
        ivBack = (ImageView) view.findViewById(R.id.iv_pause_back);
        ivRestart = (ImageView) view.findViewById(R.id.iv_pause_start);

        ivBack.setOnClickListener(this);
        ivRestart.setOnClickListener(this);

        setCancelable(false);


    }


    @Override
    public void show() {
        super.show();
        webView.loadUrl(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_pause_back:
                listener.onClickBack();
                break;
            case R.id.iv_pause_start:
                listener.onClickStart();
                break;
        }
    }

    public void setPauseDialogListener(PauseDialogListener listener) {
        this.listener = listener;
    }

    interface PauseDialogListener {
        void onClickBack();

        void onClickStart();
    }
}
