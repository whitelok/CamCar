package com.lok.utils.CmdCommunicator;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

import com.lok.obj.CommandMessage;

import android.util.Log;

public class CommunicatorDecoder implements MessageDecoder {

	private String TAG = "CommunicatorDecoder";

	public MessageDecoderResult decodable(IoSession session, IoBuffer buffer) {
//		Log.i(TAG, "CommunicatorDecoder");
		// if (buffer.remaining() < 40) {
		// return MessageDecoderResult.NEED_DATA;
		// }
		// byte begin_flag = buffer.get();
		// if (begin_flag == (byte) 0xaa) {
		// Log.i(TAG, "数据包头为:" + String.valueOf(begin_flag));
		// } else {
		// Log.e(TAG, "包头异常");
		// return MessageDecoderResult.NOT_OK;
		// }
		//
		// byte speed = buffer.get();
		// Log.i(TAG, "速度为:" + String.valueOf(speed));
		//
		// byte direction = buffer.get();
		// Log.i(TAG, "方向为:" + String.valueOf(direction));
		//
		// byte head_direction = buffer.get();
		// Log.i(TAG, "摄像头方向为:" + String.valueOf(head_direction));
		//
		// byte light = buffer.get();
		// Log.i(TAG, "灯状态为:" + String.valueOf(light));
		//
		// short end_flag = buffer.get();
		// if (end_flag == (byte) 0xff) {
		// Log.i(TAG, "数据包尾为:" + String.valueOf(end_flag));
		// } else {
		// Log.e(TAG, "包尾异常");
		// return MessageDecoderResult.NOT_OK;
		// }
		return MessageDecoderResult.OK;
	}

	public MessageDecoderResult decode(IoSession session, IoBuffer buffer,
			ProtocolDecoderOutput out) throws Exception {
//		Log.i(TAG, "MessageDecoderResult");
		// byte begin_flag = buffer.get();
		// byte speed = buffer.get();
		// byte direction = buffer.get();
		// byte head_direction = buffer.get();
		// byte light = buffer.get();
		// byte end_flag = buffer.get();
		//
		// CommandMessage cmdMessage = new CommandMessage();
		// cmdMessage.setBegin_flag(begin_flag);
		// cmdMessage.setSpeed(speed);
		// cmdMessage.setDirection(direction);
		// cmdMessage.setHead_direction(head_direction);
		// cmdMessage.setLight(light);
		// cmdMessage.setEnd_flag(end_flag);
		//
		// System.out.println("接收到数据为：" + buffer.toString());
		//
		// out.write(cmdMessage);
		return MessageDecoderResult.OK;
	}

	public void finishDecode(IoSession session, ProtocolDecoderOutput out)
			throws Exception {
//		Log.i(TAG, "finishDecode");
	}

}
