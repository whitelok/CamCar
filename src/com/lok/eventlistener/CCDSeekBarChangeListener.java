package com.lok.eventlistener;

import com.lok.obj.CommandMessage;
import com.lok.utils.NetworkConstant;
import com.lok.utils.CmdCommunicator.Communicator;

import android.util.Log;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class CCDSeekBarChangeListener implements OnSeekBarChangeListener {

	public static String TAG = "CCDSeekBarChangeListener";

	private Communicator communicator = null;
	private CommandMessage cmdMessage = null;

	public CCDSeekBarChangeListener(Communicator communicator) {
		this.communicator = communicator;
		this.cmdMessage = NetworkConstant.cmdMessage;
	}

	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		Log.i(TAG, "onProgressChanged:" + Integer.toString(progress));
		cmdMessage.setHead_direction((byte) (progress * 2));
		communicator.send(cmdMessage);
	}

	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	public void onStopTrackingTouch(SeekBar seekBar) {

	}

}
