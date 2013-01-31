package com.lok.eventlistener;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.lok.camcar.R;
import com.lok.utils.NetworkUtils;

import yizuoshe.WmiiManager.wiCam.WicamView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ToggleButton;

public class VideoConnListener implements OnClickListener {

	private String TAG = "VideoConnListener";

	private WicamView wicamVideo;
	private String wicamIP;
	private int videoPort;
	private int ctrlPort;
	private String PreferResolution;
	private String PreferBitrate;
	private byte[] cmd = null;
	private DatagramSocket ctrlSocket = null;
	private DatagramPacket ctrlCmdPacket = null;

	private boolean flag;

	public VideoConnListener(ToggleButton videoConnBtn, WicamView wicamVideo,
			String wicamIP, int videoPort, String PreferResolution,
			String PreferBitrate, DatagramSocket ctrlSocket, int ctrlPort) {
		this.wicamVideo = wicamVideo;
		this.wicamIP = wicamIP;
		this.videoPort = videoPort;
		this.PreferResolution = PreferResolution;
		this.PreferBitrate = PreferBitrate;
		this.ctrlSocket = ctrlSocket;
		this.ctrlPort = ctrlPort;
		this.flag = false;
	}

	public void onClick(View v) {
		// this.videoConnBtn = (ToggleButton)v;
		if (!flag) {
			// 开始播放
			wicamVideo.wc_start(wicamIP, videoPort, PreferResolution,
					PreferBitrate);
			v.setBackgroundResource(R.drawable.connect);
			cmd = NetworkUtils.hexStringToBytes("0xa50300000000");
			flag = true;
		} else { 
			wicamVideo.wc_stop();
			v.setBackgroundResource(R.drawable.unconnect);
			cmd = NetworkUtils.hexStringToBytes("0xa50300010000");
			flag = false;
		} 
		try {
			ctrlCmdPacket = new DatagramPacket(cmd, cmd.length);
			ctrlCmdPacket.setPort(this.ctrlPort);
			ctrlCmdPacket.setAddress(InetAddress.getByName(this.wicamIP));
		} catch (UnknownHostException e) {
			Log.e(TAG, e.getMessage());
		}
		try {
			for (int i = 0; i < 10; i++) {
				this.ctrlSocket.send(ctrlCmdPacket);
			}
		} catch (SocketException e) {
			Log.e(TAG, e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
	}

}
