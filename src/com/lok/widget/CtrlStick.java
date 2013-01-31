package com.lok.widget;

import com.lok.eventlistener.CtrlStickListener;
import com.lok.obj.CommandMessage;
import com.lok.utils.MathUtils;
import com.lok.utils.NetworkConstant;
import com.lok.utils.CmdCommunicator.Communicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class CtrlStick extends SurfaceView implements Runnable, Callback {

	private String TAG = "CtrlStick";

	// 画面控制器
	private SurfaceHolder mHolder;
	// 停止标记
	private boolean isStop = false;
	// 图像更新线程
	private Thread mThread;
	// 画笔
	private Paint mPaint;
	// 控件位置
	private Point mRockerPosition;
	// 控杆中心
	private Point mCtrlPoint = new Point(100, 90);
	// 杆子半径
	private int mRudderRadius = 20;
	// 底圈半径
	private int mWheelRadius = 80;
	// 位置监听器
	private CtrlStickListener listener = null;
	// 行动模式
	public static final int ACTION_RUDDER = 1, ACTION_ATTACK = 2;

	// 离圆心距离
	private int relateLen;

	private Communicator communicator = null;
	private CommandMessage cmdMessage = null;

	public CtrlStick(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.i(TAG, "onCreate");

		this.setKeepScreenOn(true);
		mHolder = getHolder();
		mHolder.addCallback(this);
		mThread = new Thread(this);

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mRockerPosition = new Point(mCtrlPoint);
		setFocusable(true);
		setFocusableInTouchMode(true);
		setZOrderOnTop(true);
		// 设置背景透明
		mHolder.setFormat(PixelFormat.TRANSPARENT);

		this.cmdMessage = NetworkConstant.cmdMessage;
	}

	//
	public void setCtrlStickListener(CtrlStickListener ctrlStickListener) {
		Log.i(TAG, "setCtrlStickListener");
		setListener(ctrlStickListener);
	}

	public void run() {
		Canvas canvas = null;
		while (!isStop) {
			try {
				canvas = mHolder.lockCanvas();
				canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
				mPaint.setColor(Color.GRAY);
				mPaint.setAlpha(100);
				canvas.drawCircle(mCtrlPoint.x, mCtrlPoint.y, mWheelRadius,
						mPaint);
				mPaint.setColor(Color.LTGRAY);

				canvas.drawCircle(mRockerPosition.x, mRockerPosition.y,
						mRudderRadius, mPaint);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (canvas != null) {
					mHolder.unlockCanvasAndPost(canvas);
				}
				if (this.communicator != null) {
					this.communicator.send(this.cmdMessage);
				}
			}
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		Log.i(TAG, "surfaceChanged");
	}

	public void surfaceCreated(SurfaceHolder arg0) {
		Log.i(TAG, "surfaceCreated");
		mThread.start();
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
		Log.i(TAG, "surfaceDestroyed");
		isStop = true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// Log.i(TAG, "onTouchEvent");
		int len = MathUtils.getLength(mCtrlPoint.x, mCtrlPoint.y, event.getX(),
				event.getY());
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// 获取手指位置
			if (len > mWheelRadius) {
				relateLen = 0;
				return true;
			}
		}
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			if (len <= mWheelRadius) {
				// 设置杆位置
				mRockerPosition.set((int) event.getX(), (int) event.getY());
				relateLen = len;
			} else {
				// 计算角度和距离
				mRockerPosition = MathUtils.getBorderPoint(mCtrlPoint,
						new Point((int) event.getX() - 20,
								(int) event.getY() - 20), mWheelRadius);
				relateLen = 60;
			}
			if (getListener() != null) {
				float radian = MathUtils.getRadian(mCtrlPoint, new Point(
						(int) event.getX(), (int) event.getY()));
				// 发送指令到监听器上
				getListener().onSteeringWheelChanged(ACTION_RUDDER,
						CtrlStick.this.getAngleCouvert(radian), relateLen);
			}
		}
		// 还原位置
		if (event.getAction() == MotionEvent.ACTION_UP) {
			mRockerPosition = new Point(mCtrlPoint);
			relateLen = 0;
			cmdMessage.setSpeed((byte) 0);
			cmdMessage.setDirection((byte) 0);
		}
		return true;
	}

	// 计算角度
	private int getAngleCouvert(float radian) {
		int tmp = (int) Math.round(radian / Math.PI * 180);
		if (tmp < 0) {
			return -tmp;
		} else {
			return 180 + (180 - tmp);
		}
	}

	public CtrlStickListener getListener() {
		return listener;
	}

	public void setListener(CtrlStickListener listener) {
		this.listener = listener;
	}

	public Communicator getCommunicator() {
		return communicator;
	}

	public void setCommunicator(Communicator communicator) {
		this.communicator = communicator;
	}
}
