package com.lok.utils.CmdCommunicator;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageEncoder;

import com.lok.obj.CommandMessage;

public class CommunicatorCodeFactory extends DemuxingProtocolCodecFactory {
	private MessageDecoder decoder = null;
	private MessageEncoder<CommandMessage> encoder = null;

	public CommunicatorCodeFactory(MessageDecoder decoder,
			MessageEncoder<CommandMessage> encoder) {
		this.decoder = decoder;
		this.encoder = encoder;

		this.addMessageDecoder(this.decoder);
		this.addMessageEncoder(CommandMessage.class, this.encoder);
	}
}
