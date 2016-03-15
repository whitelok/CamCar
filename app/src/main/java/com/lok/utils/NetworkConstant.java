package com.lok.utils;

import java.util.ArrayList;
import com.lok.obj.CamCarInfo;
import com.lok.obj.CommandMessage;

public class NetworkConstant {
	// 判断是否失去控制
	public static boolean isLostControl = false;

	// 保存当前通讯参数
	public static String WICAM_IP = null;
	public static int WICAM_CONTROL_PORT = 55555;
	public static int WICAM_VEIDO_PORT = 5544;

	public static int LOCAL_CMD_TCP_PORT = 4281;
	public static int LOCAL_CMD_UDP_PORT = 4282;

	// 保存camcar对象集合
	public static ArrayList<CamCarInfo> camcarsVector = new ArrayList<CamCarInfo>();
	
	public static CommandMessage cmdMessage = new CommandMessage();

}
