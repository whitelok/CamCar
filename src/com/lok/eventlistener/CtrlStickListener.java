package com.lok.eventlistener;

import com.lok.obj.CommandMessage;
import com.lok.utils.NetworkConstant;
import com.lok.utils.CmdCommunicator.Communicator;

import android.util.Log;

public class CtrlStickListener {

	private String TAG = "CtrlStickListener";

	private Communicator communicator = null;
	private CommandMessage cmdMessage = null;

	public CtrlStickListener(Communicator communicator) {
		this.communicator = communicator;
		this.cmdMessage = NetworkConstant.cmdMessage;
	}

	public void onSteeringWheelChanged(int action, int angle, int relateLen) {
		Log.i(TAG, "onSteeringWheelChanged Angle:" + String.valueOf(angle)
				+ " RealteLength:" + String.valueOf(relateLen));
		// relate length is 0 ~ 60
		cmdMessage.setSpeed((byte) relateLen);
		// angle 0 ~ 360
		cmdMessage.setDirection((byte) (angle * 127 / 360));
		communicator.send(cmdMessage);
	}
}
