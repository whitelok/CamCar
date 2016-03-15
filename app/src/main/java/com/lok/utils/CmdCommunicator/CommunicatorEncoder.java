package com.lok.utils.CmdCommunicator;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

import com.lok.obj.CommandMessage;

import android.util.Log;

public class CommunicatorEncoder implements MessageEncoder<CommandMessage> {

	private String TAG = "CommunicatorEncoder";

	public void encode(IoSession session, CommandMessage cmdMessage,
			ProtocolEncoderOutput out) throws Exception {

//		Log.i(TAG, "encode");
		IoBuffer buf = IoBuffer.allocate(100).setAutoExpand(true);

		buf.put(cmdMessage.getBegin_flag());
		buf.put(cmdMessage.getSpeed());
		buf.put(cmdMessage.getDirection());
		buf.put(cmdMessage.getHead_direction());
		buf.put(cmdMessage.getLight());
		buf.put(cmdMessage.getEnd_flag());
		buf.flip();

//		Log.i(TAG, "编码:" + buf.toString());
		out.write(buf);
	}

}
