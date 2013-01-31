package com.lok.utils.CmdCommunicator;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import android.util.Log;

public class CommunicatorHandler implements IoHandler {

	private static String TAG = "CommunicatorHandler";

	public void exceptionCaught(IoSession arg0, Throwable arg1)
			throws Exception {
//		Log.e(TAG, arg1.getMessage());
	}

	public void messageReceived(IoSession arg0, Object arg1) throws Exception {
//		Log.i(TAG, "messageReceived");
//		Log.i(TAG, "接受到的数据为：" + String.valueOf(arg1));
	}

	public void messageSent(IoSession arg0, Object arg1) throws Exception {
//		Log.i(TAG, "messageSent");
//		Log.i(TAG, "发送了的数据为：" + String.valueOf(arg1));
	}

	public void sessionClosed(IoSession arg0) throws Exception {
		Log.i(TAG, "sessionClosed");
		Log.i(TAG, "会话关闭......");
	}

	public void sessionCreated(IoSession arg0) throws Exception {
		Log.i(TAG, "sessionCreated");
		Log.i(TAG, "会话创建......");
	}

	public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {
		Log.i(TAG, "sessionIdle");
		Log.i(TAG, "会话空闲......");
	}

	public void sessionOpened(IoSession arg0) throws Exception {
		Log.i(TAG, "sessionOpened");
		Log.i(TAG, "会话开始......");
	}
}
