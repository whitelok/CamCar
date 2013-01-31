package com.lok.eventlistener;

import com.lok.obj.CommandMessage;
import com.lok.utils.NetworkConstant;
import com.lok.utils.CmdCommunicator.Communicator;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class LightBtnListener implements OnClickListener {

	private String TAG = "LightBtnListener";

	private Communicator communicator = null;
	private CommandMessage cmdMessage = null;

	private boolean openFlag = false;

	public LightBtnListener(Communicator communicator) {
		this.communicator = communicator;
		this.cmdMessage = NetworkConstant.cmdMessage;
	}

	public void onClick(View arg0) {
		Log.i(TAG, "onClick");
		if (openFlag) {
			openFlag = false;
			cmdMessage.setLight((byte) 0);
			Log.i(TAG, String.valueOf((byte) 0));
		} else {
			openFlag = true;
			cmdMessage.setLight((byte) 1);
			Log.i(TAG, String.valueOf((byte) 1));
		}
		communicator.send(cmdMessage);
	}

}
