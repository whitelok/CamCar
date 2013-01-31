package com.lok.camcar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class CamCarMain extends Activity {

	private String TAG = "CamCarMain";
	
	private Button single = null;

	private Button mutical = null;

	private Button mainExit = null;

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
		setContentView(R.layout.camcarmain);
		init();
	}

	private void init() {
		this.mainExit = (Button) findViewById(R.id.main_exit);
		this.mainExit.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Log.i(TAG, "main_onClick");
				CamCarMain.this.finish();
			}
		});

		this.single = (Button) findViewById(R.id.main_single);
		this.single.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Log.i(TAG, "single_onClick");
			}

		});

		this.mutical = (Button) findViewById(R.id.main_mutical);
		this.mutical.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Log.i(TAG, "mutical_onClick");
			}

		});
	}
}
