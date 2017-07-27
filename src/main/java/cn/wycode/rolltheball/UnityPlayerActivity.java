package cn.wycode.rolltheball;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.unity3d.player.*;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class UnityPlayerActivity extends Activity
{
	protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code

	private static final String WX_APP_ID = "wx12249c386a141c6f";
	private static final int THUMB_SIZE = 64;
    private IWXAPI wxApi;

    private static final String shareUrl = "http://wycode.cn/game/rollTheBall/rollshare.html";

	// Setup activity layout
	@Override protected void onCreate (Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		getWindow().setFormat(PixelFormat.RGBX_8888); // <--- This makes xperia play happy

		mUnityPlayer = new UnityPlayer(this);
		setContentView(mUnityPlayer);
		mUnityPlayer.requestFocus();

        regToWx();
	}

	// Quit Unity
	@Override protected void onDestroy ()
	{
		mUnityPlayer.quit();
		super.onDestroy();
	}

	// Pause Unity
	@Override protected void onPause()
	{
		super.onPause();
		mUnityPlayer.pause();
	}

	// Resume Unity
	@Override protected void onResume()
	{
		super.onResume();
		mUnityPlayer.resume();
	}

	// This ensures the layout will be correct.
	@Override public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		mUnityPlayer.configurationChanged(newConfig);
	}

	// Notify Unity of the focus change.
	@Override public void onWindowFocusChanged(boolean hasFocus)
	{
		super.onWindowFocusChanged(hasFocus);
		mUnityPlayer.windowFocusChanged(hasFocus);
	}

	// For some reason the multiple keyevent type is not supported by the ndk.
	// Force event injection by overriding dispatchKeyEvent().
	@Override public boolean dispatchKeyEvent(KeyEvent event)
	{
		if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
			return mUnityPlayer.injectEvent(event);
		return super.dispatchKeyEvent(event);
	}

	// Pass any events not handled by (unfocused) views straight to UnityPlayer
	@Override public boolean onKeyUp(int keyCode, KeyEvent event)     { return mUnityPlayer.injectEvent(event); }
	@Override public boolean onKeyDown(int keyCode, KeyEvent event)   { return mUnityPlayer.injectEvent(event); }
	@Override public boolean onTouchEvent(MotionEvent event)          { return mUnityPlayer.injectEvent(event); }
	/*API12*/ public boolean onGenericMotionEvent(MotionEvent event)  { return mUnityPlayer.injectEvent(event); }

    private void regToWx(){
        wxApi = WXAPIFactory.createWXAPI(this,WX_APP_ID,true);
        wxApi.registerApp(WX_APP_ID);
    }

	public void wechatLogin(){
		Toast.makeText(this,"开发者支付不起300元微信认证费用，此功能暂无法提供！",Toast.LENGTH_LONG).show();
	}

	public void share2wechat(String id,String userName,int score,int rank){
        WXWebpageObject object = new WXWebpageObject();
        object.webpageUrl = shareUrl+"?name="+userName+"&score="+score+"&rank="+rank;

        WXMediaMessage message = new WXMediaMessage(object);
        message.title = "滚自己的蛋，让你们看看！";
        message.description = String.format("我在滚蛋吧排名第%d，不服来战！",rank);
		Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.app_icon);
		Bitmap thumb = Bitmap.createScaledBitmap(bmp,THUMB_SIZE,THUMB_SIZE,true);
		bmp.recycle();
		message.thumbData = bmpToByteArray(thumb,true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = message;
        req.scene = SendMessageToWX.Req.WXSceneSession;

        wxApi.sendReq(req);
    }

    public void share2moment(String id,String userName,int score,int rank){
        WXWebpageObject object = new WXWebpageObject();
		object.webpageUrl = shareUrl+"?name="+userName+"&score="+score+"&rank="+rank;

        WXMediaMessage message = new WXMediaMessage(object);
        message.title = "滚自己的蛋，让你们看看！";
        message.description = String.format("我在滚蛋吧排名第%d，不服来战！",rank);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.app_icon);
		Bitmap thumb = Bitmap.createScaledBitmap(bmp,THUMB_SIZE,THUMB_SIZE,true);
		bmp.recycle();
        message.thumbData = bmpToByteArray(thumb,true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = message;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;

        wxApi.sendReq(req);
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
