package com.lok.eventlistener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lok.camcar.R;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class TakeVideoListener implements OnClickListener {

	private String TAG = "TakeVideoListener";

	private boolean isTakingVideo = false;

	private File saveFile = null;
	private FileOutputStream fos = null;

	public void onClick(View view) {
		ImageView tempView = (ImageView) view;
		if (!this.isTakingVideo) {
			Log.i(TAG, "开始录像......");
			Toast.makeText(view.getContext(), "开始录像......", Toast.LENGTH_SHORT)
					.show();
			tempView.setImageResource(R.drawable.save);
			this.isTakingVideo = true;

			this.saveFile = new File(Environment.getExternalStorageDirectory(),
					"vedio.h264");
			if (saveFile.exists()) {
				saveFile.delete();
			}

			try {
				fos = new FileOutputStream(saveFile.getPath());
			} catch (FileNotFoundException e) {
				Log.e(TAG, e.getMessage());
			}

		} else if (this.isTakingVideo) {
			Log.i(TAG, "停止录像......");
			Toast.makeText(view.getContext(), "停止录像......", Toast.LENGTH_SHORT)
					.show();
			this.isTakingVideo = false;
			tempView.setImageResource(R.drawable.takevideo);

			try {
				fos.close();
			} catch (IOException e) {
				Log.e(TAG, e.getMessage());
			}
		}
	}

	public void saveData(byte[] frame, int len) {
		if (this.isTakingVideo) {
			Log.i(TAG, String.valueOf(len));
			try {
				fos.write(frame, 0, len);
			} catch (IOException e) {
				Log.e(TAG, e.getMessage());
			}
		}
	}

}
