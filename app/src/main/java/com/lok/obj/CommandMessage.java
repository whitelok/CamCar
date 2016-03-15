package com.lok.obj;

public class CommandMessage {
	byte begin_flag = (byte) 0xaa;

	byte speed = (byte) 0;

	byte direction = (byte) 0;

	byte head_direction = (byte) 100;

	byte light = (byte) 0;

	byte end_flag = (byte) 0xff;

	public byte getBegin_flag() {
		return begin_flag;
	}

	public void setBegin_flag(byte begin_flag) {
		this.begin_flag = begin_flag;
	}

	public byte getSpeed() {
		return speed;
	}

	public void setSpeed(byte speed) {
		this.speed = speed;
	}

	public byte getDirection() {
		return direction;
	}

	public void setDirection(byte direction) {
		this.direction = direction;
	}

	public byte getHead_direction() {
		return head_direction;
	}

	public void setHead_direction(byte head_direction) {
		this.head_direction = head_direction;
	}

	public byte getLight() {
		return light;
	}

	public void setLight(byte light) {
		this.light = light;
	}

	public byte getEnd_flag() {
		return end_flag;
	}

	public void setEnd_flag(byte end_flag) {
		this.end_flag = end_flag;
	}
}
