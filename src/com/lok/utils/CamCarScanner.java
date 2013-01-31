package com.lok.utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.lok.obj.CamCarInfo;

public class CamCarScanner {

	private static String TAG = "CamCarScanner";

	private static Context context = null;

	@SuppressWarnings("unused")
	public static void scan() {
		Log.i(TAG, "CamCarScanner->scan");

		DatagramSocket udpProbeSocket = null;
		int probe_port = 12345;

		NetworkConstant.camcarsVector.clear();

		try {
			udpProbeSocket = new DatagramSocket();
			udpProbeSocket.setSoTimeout(1000);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}

		// probe
		byte[] outBuf = CamCarInfo.camcarProbe();
		DatagramPacket dataPacket = new DatagramPacket(outBuf, outBuf.length);
		dataPacket.setPort(probe_port);
		dataPacket.setLength(outBuf.length);

		// 255.255.255.255
		try {
			dataPacket.setAddress(InetAddress.getByName("255.255.255.255"));
			udpProbeSocket.send(dataPacket); // switch on
		} catch (Exception ex) {
		}

		byte[] RecvBuf = new byte[1024];
		DatagramPacket RecvPacket = new DatagramPacket(RecvBuf, RecvBuf.length);

		String camcarTag = "CamCar";
		int tag = 1;
		do {
			try {
				udpProbeSocket.receive(RecvPacket);
			} catch (IOException e) {
				break;
			}
			NetworkConstant.camcarsVector.add(new CamCarInfo(RecvPacket
					.getData()));
			tag++;

		} while (true);
		tag++;

		if (NetworkConstant.camcarsVector.size() == 0) {

			for (int c = 0; c <= 255; c++) {
				try {
					dataPacket.setAddress(InetAddress.getByName("192.168." + c
							+ ".255"));
					udpProbeSocket.send(dataPacket); // switch on
				} catch (Exception ex) {
					continue;
				}
			}

			do {
				try {
					udpProbeSocket.receive(RecvPacket);
				} catch (IOException e) {
					break;
				}
				NetworkConstant.camcarsVector.add(new CamCarInfo(RecvPacket
						.getData()));

			} while (true);
		}

		try {
			udpProbeSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (NetworkConstant.camcarsVector.size() == 0)
			Toast.makeText(getContext(), "Can not find any CamCar......",
					Toast.LENGTH_SHORT).show();
		Log.i("Amount of Camcar......",
				String.valueOf(NetworkConstant.camcarsVector.size()));
		for (int f = 1; f < NetworkConstant.camcarsVector.size(); f++) {
			Log.i("Camcar======",
					String.valueOf(NetworkConstant.camcarsVector.get(f)));
		}
	}

	public static Context getContext() {
		Log.i(TAG, "getContext");
		return context;
	}

	public static void setContext(Context context) {
		Log.i(TAG, "setContext");
		CamCarScanner.context = context;
	}
}
