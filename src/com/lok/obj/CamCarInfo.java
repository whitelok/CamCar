package com.lok.obj;

// 保存Camcar信息
public class CamCarInfo {
	public String GroupName;
	public String NickName;
	public String Ip;
	public String Mac;
	public int SNR;
	public int NF;
	public String Sn;
	public String Ver;
	public String ServMode;
	public int ServPort;
	public String Stat;

	private static final int BeaconSize = 136;
	private static final int GroupNameSize = 16;
	private static final int NickNameSize = 16;
	private static final int MacSize = 6;
	private static final int SNSize = 8;
	private static final int VerSize = 12;
	// for 2.4.4
	private static final int ServModeSize = 4; // 8 brefore 2.4.4
	private static final int StatSize = 64;

	// 返回信息保存空间
	public static byte[] camcarProbe() {
		byte[] ret = new byte[BeaconSize];
		byte[] bProbStr = "wifimodII".getBytes();
		System.arraycopy(bProbStr, 0, ret, 0, bProbStr.length);

		return ret;
	}

	// 分析返回数据
	public CamCarInfo(byte[] beacon) {
		if (beacon.length < BeaconSize)
			return;
		int pos = 0;
		int cnt;

		cnt = 0;
		while (beacon[pos + cnt] != 0)
			cnt++;
		if (cnt < GroupNameSize && cnt > 0)
			GroupName = new String(beacon, pos, cnt);
		else
			GroupName = "";
		pos += GroupNameSize;

		cnt = 0;
		while (beacon[pos + cnt] != 0)
			cnt++;
		if (cnt < NickNameSize && cnt > 0)
			NickName = new String(beacon, pos, cnt);
		else
			NickName = "";
		pos += NickNameSize;

		Ip = (int) (beacon[pos++] & 0xFF) + "." + (int) (beacon[pos++] & 0xFF)
				+ "." + (int) (beacon[pos++] & 0xFF) + "."
				+ (int) (beacon[pos++] & 0xFF);

		Mac = "";
		for (int i = 0; i < MacSize; i++) {
			if (i > 0)
				Mac += ':';
			String bmac = Integer.toHexString(beacon[pos++] & 0xFF)
					.toUpperCase();
			if (bmac.length() == 1)
				bmac = '0' + bmac;
			Mac += bmac;
		}
		SNR = (int) (beacon[pos++] & 0xFF);
		NF = -(int) (beacon[pos++] & 0xFF);

		Sn = "";
		for (int i = 0; i < SNSize; i++) {
			String bsn = Integer.toHexString(beacon[pos++] & 0xFF);
			if (bsn.length() == 1)
				bsn = '0' + bsn;
			Sn += bsn;
		}

		cnt = 0;
		while (beacon[pos + cnt] != 0)
			cnt++;
		if (cnt < VerSize && cnt > 0)
			Ver = new String(beacon, pos, cnt);
		else
			Ver = "";
		pos += VerSize;

		cnt = 0;
		while (beacon[pos + cnt] != 0)
			cnt++;
		if (cnt < ServModeSize && cnt > 0)
			ServMode = new String(beacon, pos, cnt);
		else
			ServMode = "";
		pos += ServModeSize;

		// for 2.4.4
		ServPort = (int) (beacon[pos++] & 0xFF) + (int) (beacon[pos++] & 0xFF)
				* 256;
		pos += 2;

		cnt = 0;
		while (beacon[pos + cnt] != 0)
			cnt++;
		if (cnt < StatSize && cnt > 0)
			Stat = new String(beacon, pos, cnt);
		else
			Stat = "";
		pos += StatSize;

	}
}
