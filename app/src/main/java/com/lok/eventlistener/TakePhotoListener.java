package com.lok.eventlistener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lok.camcar.R;

import yizuoshe.WmiiManager.wiCam.WicamView;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class TakePhotoListener implements OnClickListener {

	private String TAG = "TakePhotoListener";

	private WicamView wicamVideo = null;

	private MediaPlayer mp = null;

	public TakePhotoListener(WicamView wicamVideo) {
		this.wicamVideo = wicamVideo;
		this.wicamVideo.setDrawingCacheEnabled(true);
		this.mp = new MediaPlayer().create(wicamVideo.getContext(),
				R.raw.camerasound);
	}

	public void onClick(View view) {
		if (this.wicamVideo == null) {
			Toast.makeText(view.getContext(), "请先初始化WiCam视频......",
					Toast.LENGTH_SHORT).show();
			return;
		}
		mp.start();
		view.getRootView().setDrawingCacheEnabled(true);
		Bitmap bitmap = view.getRootView().getDrawingCache();
//		Bitmap bitmap = this.wicamVideo.getDrawingCache();
		try {
			File sdcard = Environment.getExternalStorageDirectory();
			String dir = "camcar_images";
			String baseName = "capturedimage";
			File pictureDir = new File(sdcard, dir);
			pictureDir.mkdirs();
			File f = null;
			for (int i = 1; i < 200; ++i) {
				String name = baseName + i + ".jpeg";
				f = new File(pictureDir, name);
				if (!f.exists()) {
					break;
				}
			}
			Log.i(TAG,"Image size : " + bitmap.getHeight());
			if (!f.exists()) {
				String name = f.getAbsolutePath();
				FileOutputStream fos = new FileOutputStream(name);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
				Log.i(TAG, "After File Size : " + f.length());
				fos.flush();
				fos.close();
				Toast.makeText(view.getContext(), "照片已保存到"+ name +"......",
						Toast.LENGTH_SHORT).show();
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "Exception in saveBitmap: " + e.getMessage());
		} finally {

		}
	}
}
