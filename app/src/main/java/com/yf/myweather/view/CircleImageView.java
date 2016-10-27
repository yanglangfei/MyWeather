package com.yf.myweather.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircleImageView extends ImageView {
	private Paint mPaint = new Paint();

	public CircleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Drawable mDrawable = getDrawable();// ��ȡxml�ļ����õ�ͼƬ
		if (null == mDrawable)
			super.onDraw(canvas);// ���Ϊ�գ������ദ��
		Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();// ��ͼƬת����bitmap

		int len = getWidth() < getHeight() ? getWidth() : getHeight();// ��ȡxml�ĳ�������ֵ��ѡ���С��

		Bitmap tempBitmap = Bitmap.createBitmap(len, len,
				Bitmap.Config.ARGB_8888);// �½�һ��bitmap������Ϊ����
		Canvas mCanvas = new Canvas(tempBitmap);// �½���������bitmap����

		mPaint.setAntiAlias(true);// �����
		mCanvas.drawCircle(len / 2, len / 2, len / 2, mPaint);// ��һ��Բ

		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));// ���û��ʵĸ�������

		Matrix matrix = new Matrix(); // ��ʼ��Matrix����
		matrix.setScale((float) len / mBitmap.getWidth(),
				(float) len / mBitmap.getHeight()); // �������ű���
		mCanvas.drawBitmap(mBitmap, matrix, mPaint);// �������ź��ͼƬ

		mPaint.reset();// ���û���
		canvas.drawBitmap(tempBitmap, 0, 0, mPaint);// ���ƻ���ͼƬ
	}

}