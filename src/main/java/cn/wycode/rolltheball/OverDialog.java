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

public class OverDialog extends AlertDialog implements View.OnClickListener {

    private WebView webView;

    public TextView tvScore;
    public TextView tvRank;

    private ImageView ivWechat;
    private ImageView ivMoment;
    private ImageView ivBack;
    private ImageView ivRestart;

    private OverDialogListener listener;

    private String url = "http://wycode.cn/game/rollTheBall/adOver.html";

    protected OverDialog(Context context) {
        super(context);
        init();
    }

    private void init() {
        View view = getLayoutInflater().inflate(R.layout.dialog_over, null);
        setView(view);
        webView = (WebView) view.findViewById(R.id.wv_over);
        tvScore = (TextView) view.findViewById(R.id.tv_over_score);
        tvRank = (TextView) view.findViewById(R.id.tv_over_rank);
        ivWechat = (ImageView) view.findViewById(R.id.iv_over_wechat);
        ivMoment = (ImageView) view.findViewById(R.id.iv_over_moment);
        ivBack = (ImageView) view.findViewById(R.id.iv_over_back);
        ivRestart = (ImageView) view.findViewById(R.id.iv_over_restart);

        ivWechat.setOnClickListener(this);
        ivMoment.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        ivRestart.setOnClickListener(this);

        webView.loadUrl(url);
    }

    public void setOverDialogListener(OverDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_over_wechat:
                listener.onClickWechat();
                break;
            case R.id.iv_over_moment:
                listener.onClickMoment();
                break;
            case R.id.iv_over_back:
                listener.onClickBack();
                break;
            case R.id.iv_over_restart:
                listener.onClickRestart();
                break;
        }
    }


    interface OverDialogListener {
        void onClickWechat();

        void onClickMoment();

        void onClickBack();

        void onClickRestart();
    }
}
