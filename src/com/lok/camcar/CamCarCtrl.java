package com.lok.camcar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import com.lok.eventlistener.CCDSeekBarChangeListener;
import com.lok.eventlistener.CtrlStickListener;
import com.lok.eventlistener.LightBtnListener;
import com.lok.eventlistener.TakePhotoListener;
import com.lok.eventlistener.TakeVideoListener;
import com.lok.eventlistener.VideoConnListener;
import com.lok.eventlistener.XRayOnTouchListener;
import com.lok.utils.NetworkConstant;
import com.lok.utils.NetworkUtils;
import com.lok.utils.CmdCommunicator.Communicator;
import com.lok.widget.CtrlStick;
import yizuoshe.WmiiManager.wiCam.WicamView;
import yizuoshe.WmiiManager.wiCam.WicamViewInterface;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;

public class CamCarCtrl extends Activity implements WicamViewInterface {

	private String TAG = "CamCarCtrl";

	// 视频连接按钮
	private ToggleButton videoConnBtn = null;

	public int MENU_SCAN_TAG = 1;
	public int MENU_SETTING_TAG = 2;
	public int MENU_ABOUT = 3;

	// 视频
	private WicamView wicamVideo = null;
	private VideoConnListener videoConnListener = null;

	// 灯光
	private ImageView light = null;

	private ImageView takePhoto = null;

	private ImageView takeVideo = null;
	private TakeVideoListener videoListener = null;

	// 激光按钮
	private Button xRay = null;

	// 摄像头滑块
	private SeekBar camcarDirection = null;

	// 控制socket
	private DatagramSocket ctrlSocket = null;

	// 传输参数
	private String PreferResolution = "qvga";
	private String PreferBitrate = "300k";

	// 摇杆
	private CtrlStick ctrlStick = null;

	// 血槽
	private ProgressBar progressBar = null;

	// 通讯工具
	private Communicator communicator = null;

	// 全局容器
	// private CamCarApplication appl.ication = null;

	// 屏幕大小
	private int wicamViewHeight, wicamViewWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "CamCarCtrl->onCreate");
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.camcarctrl);
		init();
	}

	public void init() {
		Log.i(TAG, "init");

		checkNetState(this);

		if (NetworkConstant.WICAM_IP == null) {
			Toast.makeText(this, "CamCar对象未选定，请先扫描camcar选定特定对象......",
					Toast.LENGTH_SHORT).show();
		}

		try {
			ctrlSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}

		videoListener = new TakeVideoListener();

		// 找到WicamView，并且设置回调
		wicamVideo = (WicamView) findViewById(R.id.wicamVideo);
		wicamVideo.wc_callback_setup(this);

		// 初始化控制Video
		videoConnBtn = (ToggleButton) findViewById(R.id.tbtn_wicamon);
		videoConnListener = new VideoConnListener(videoConnBtn, wicamVideo,
				NetworkConstant.WICAM_IP, NetworkConstant.WICAM_VEIDO_PORT,
				PreferResolution, PreferBitrate, ctrlSocket,
				NetworkConstant.WICAM_CONTROL_PORT);
		videoConnBtn.setOnClickListener(videoConnListener);

		camcarDirection = (SeekBar) findViewById(R.id.camcardirection);
		xRay = (Button) findViewById(R.id.xray);
		ctrlStick = (CtrlStick) findViewById(R.id.ctrlstick);
		light = (ImageView) findViewById(R.id.ctrl_light);
		takePhoto = (ImageView) findViewById(R.id.takephoto);
		takeVideo = (ImageView) findViewById(R.id.takevideo);
		progressBar = (ProgressBar) findViewById(R.id.hpprogress);

		this.progressBar.setProgress(100);

		this.communicator = new Communicator();
		communicator.connect();

		ctrlStick.setCommunicator(communicator);
		camcarDirection
				.setOnSeekBarChangeListener(new CCDSeekBarChangeListener(
						communicator));
		ctrlStick.setListener(new CtrlStickListener(communicator));
		xRay.setOnTouchListener(new XRayOnTouchListener());
		light.setOnClickListener(new LightBtnListener(communicator));
		takePhoto.setOnClickListener(new TakePhotoListener(wicamVideo));
		takeVideo.setOnClickListener(videoListener);
	}

	public void on_view_created(int width, int height) {
		this.wicamViewHeight = height;
		this.wicamViewWidth = width;
	}

	public void on_view_changed(int width, int height) {

	}

	public void on_view_destroyed() {

	}

	public void on_view_touch_down(int x, int y) {

	}

	public void on_view_touch_move(int x, int y) {

	}

	public void on_view_touch_up(int x, int y) {

	}

	public void on_view_update(Canvas canvas) {

	}

	public void on_stream_flag_info(int flag, byte[] inf, int inf_pos,
			int inf_len) {

	}

	// 报文校验错事件
	public void on_stream_checksum_err(int pktno) {

	}

	// 报文丢失事件
	public void on_stream_lost(int exp, int sn) {

	}

	// 信号强度
	public void on_stream_signal(int snr, int nf) {

	}

	// 定时保活通知
	public void on_stream_keepalive() {

	}

	public void on_stream_frame(byte[] frame, int len, int cnt, int last_nsn,
			int nsn) {
		this.videoListener.saveData(frame, len);
	}

	// 判断网络是否存在并对网络进行设置
	public void checkNetState(final Context context) {
		if (!isConnectNetwork(context)) {
			Builder b = new AlertDialog.Builder(context).setTitle("没有可用的网络")
					.setMessage("是否对网络进行设置？");
			b.setPositiveButton("是", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					Intent mIntent = new Intent("/");
					ComponentName comp = new ComponentName(
							"com.android.settings",
							"com.android.settings.WirelessSettings");
					mIntent.setComponent(comp);
					mIntent.setAction("android.intent.action.VIEW");
					((CamCarCtrl) context).startActivityForResult(mIntent, 0);
				}
			}).setNeutralButton("否", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.cancel();
				}
			}).show();
		}
		return;
	}

	// 判断网络是否存在
	public static boolean isConnectNetwork(Context context) {
		boolean netSataus = false;
		ConnectivityManager cwjManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		cwjManager.getActiveNetworkInfo();
		if (cwjManager.getActiveNetworkInfo() != null) {
			netSataus = cwjManager.getActiveNetworkInfo().isAvailable();
		}
		return netSataus;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_SCAN_TAG, 1, R.string.menu_scanandlist);
		menu.add(0, MENU_SETTING_TAG, 2, R.string.menu_settingonweb);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int tag = item.getItemId();
		if (tag == MENU_SCAN_TAG) {
			Intent intent = new Intent();
			intent.setClass(this, CamCarList.class);
			this.startActivity(intent);
			this.finish();
		}

		if (tag == MENU_SETTING_TAG) {
			if (NetworkConstant.WICAM_IP != null) {
				Intent intent = new Intent();
				Uri uri = Uri.parse(NetworkUtils
						.uriSetting(NetworkConstant.WICAM_IP));
				intent = new Intent(Intent.ACTION_VIEW, uri);
				this.startActivity(intent);
				this.finish();
			} else {
				return false;
			}
		}

		return false;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}