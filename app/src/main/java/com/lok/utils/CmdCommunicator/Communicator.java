package com.lok.utils.CmdCommunicator;

import java.net.InetSocketAddress;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import android.util.Log;

import com.lok.obj.CommandMessage;
import com.lok.utils.NetworkConstant;

public class Communicator {
	public static String TAG = "Communicator";

	private NioSocketConnector connector = null;
	private ConnectFuture future = null;
	private IoSession session = null;

	private boolean flag = false;

	public boolean connect() {
		if (NetworkConstant.WICAM_IP == null) {
			return false;
		}
		connector = new NioSocketConnector();

		// Configure the service.
		connector.setConnectTimeoutMillis(1000);
		connector.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new CommunicatorCodeFactory(
						new CommunicatorDecoder(), new CommunicatorEncoder())));
		connector.setHandler(new CommunicatorHandler());

		for (;;) {
			try {
				future = connector.connect(new InetSocketAddress(
						NetworkConstant.WICAM_IP,
						NetworkConstant.WICAM_CONTROL_PORT));
				future.awaitUninterruptibly();
				session = future.getSession();
				break;
			} catch (RuntimeIoException e) {
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
				return false;
			}
		}

		this.flag = true;
		return true;
	}

	public void send(CommandMessage cmdMessage) {
		if (session == null) {
//			Log.e(TAG, "Sending Session is Null !");
			return;
		}
		session.write(cmdMessage);
	}

	public void disconnect() {
		if (session == null) {
			Log.i(TAG, "Disconnecting Session is Null !");
			return;
		}
		session.getCloseFuture().awaitUninterruptibly();
		connector.dispose();
		this.flag = false;
	}

	public void update() {
		this.disconnect();
		this.connect();
	}

	public boolean isConnecting() {
		return flag;
	}

}
