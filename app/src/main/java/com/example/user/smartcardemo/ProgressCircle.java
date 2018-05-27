package com.example.user.smartcardemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ProgressCircle extends View {
	private float mRingBias = 0.15f;
	private float mSectionRatio = 10.0f;
	private RectF mSectionRect = new RectF();
	protected float mSectionHeight;
	
	protected float mRadius;
	
	protected int mMaxProgress;
	protected int mProgress;
	
	protected float mCenterX;
	protected float mCenterY;
	
	private Paint mPaint;
	private int mColor1;
	private int mColor2;
	private int mInactiveColor;
	
	{
		mMaxProgress = 200;
		mProgress = 0;
		
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.FILL);
		
		Log.d("Define Color","m1 m2 color");
		mColor2 = Color.parseColor("#ffff5900"); //�Y��color = ��
		mColor1 = Color.parseColor("#ff33b5e5"); //����color = ��
		mInactiveColor = Color.parseColor("#ff404040");
		
		mPaint.setColor(mColor1); // Set default
	}
	//------�غc�l	
	public ProgressCircle(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		initAttributes(context, attrs);
	}
	
	public ProgressCircle(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		initAttributes(context, attrs);
	}
	
	public ProgressCircle(Context context) {
		super(context);
	}
	
	//�غc�l----end
	
	private void initAttributes(Context context, AttributeSet attrs) {
		TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SeekCircle, 0, 0);
		try {
			// Read and clamp max
			int max = attributes.getInteger(R.styleable.SeekCircle_max, 100);
			//���o�w�q��int,�p�G�S���w�q�N�Ϋ��(100)
			mMaxProgress = Math.max(max, 1);
			
			// Read and clamp progress
			int progress = attributes.getInteger(R.styleable.SeekCircle_progress, 0);
			mProgress = Math.max(Math.min(progress, mMaxProgress), 0);
		} finally {
			attributes.recycle();
		}
	}
	
	private void updateDimensions(int width, int height) {
		// Update center position
		mCenterX = width / 2.0f;
		mCenterY = height / 2.0f;
		
		// Find shortest dimension
		int diameter = Math.min(width, height);
		
		float outerRadius = diameter / 2;
		float sectionHeight = outerRadius * mRingBias;
		float sectionWidth = sectionHeight / mSectionRatio;
		
		mRadius = outerRadius - sectionHeight / 2;
		mSectionRect.set(-sectionWidth / 2, -sectionHeight / 2, sectionWidth / 2, sectionHeight / 2);
		mSectionHeight = sectionHeight;
	}
	
	@Override
	protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		
		if (width > height)
			super.onMeasure(heightMeasureSpec, widthMeasureSpec);
		else
			super.onMeasure(widthMeasureSpec, widthMeasureSpec);
		
		updateDimensions(getWidth(), getHeight());
	}
	
	@Override //����size�j�p
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		updateDimensions(w, h);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// Center our canvas
		canvas.translate(mCenterX, mCenterY);//�����쥻���I�h��x,�h��y ex:100,100 -> 101,101
		
		float rotation = 270.0f / (float) mMaxProgress; //rotation ���� 360/100
		for (int i = 0; i < mMaxProgress; ++i) {
			canvas.save();
			
			canvas.rotate((float) i * rotation);
			canvas.translate(0, -mRadius);
			
			if (i < mProgress) {
				float bias = (float) i / (float) (mMaxProgress - 1);
				int color = interpolateColor(mColor1, mColor2, bias);//���h�C��
				mPaint.setColor(color);//��W�C��,�b�o�@�q
			} else {
				canvas.scale(0.7f, 0.7f);
				mPaint.setColor(mInactiveColor);
			}
			
			canvas.drawRect(mSectionRect, mPaint);
			canvas.restore();
		}
		
		super.onDraw(canvas);
	}
	
	private float interpolate(float a, float b, float bias) //interpolate = ����,bias = ����,���t
	{
		return (a + ((b - a) * bias));
	}

	private int interpolateColor(int colorA, int colorB, float bias) {
		float[] hsvColorA = new float[3];
		Color.colorToHSV(colorA, hsvColorA);

		float[] hsvColorB = new float[3];
		Color.colorToHSV(colorB, hsvColorB);

		hsvColorB[0] = interpolate(hsvColorA[0], hsvColorB[0], bias);
		hsvColorB[1] = interpolate(hsvColorA[1], hsvColorB[1], bias);
		hsvColorB[2] = interpolate(hsvColorA[2], hsvColorB[2], bias);
		
		// NOTE For some reason the method HSVToColor fail in edit mode. Just use the start color for now
		if (isInEditMode())
			return colorA;

		return Color.HSVToColor(hsvColorB);
	}
	
	/**
	 * Get max progress
	 * 
	 * @return Max progress
	 */
	public float getMax()
	{
		return mMaxProgress;
	}
	
	/**
	 * Set max progress
	 * 
	 * @param max
	 */
	public void setMax(int max)
	{
		int newMax = Math.max(max, 1);
		if (newMax != mMaxProgress)
			mMaxProgress = newMax;
		
		updateProgress(mProgress);
		invalidate();//���sø�sView
	}
	
	/**
	 * Get Progress
	 * 
	 * @return progress
	 */
	public int getProgress()
	{
		return mProgress;
	}
	
	public void setColor1(int color1,int color2)
	{
		mColor1 = color1;
		mColor2 = color2;
	}
	/**
	 * Set progress
	 * 
	 * @param progress
	 */
	public void setProgress(int progress)
	{		
		updateProgress(progress);//�]�wprogress
	}
	
	/**
	 * Update progress internally. Clamp it to a valid range and invalidate the view if necessary 
	 * 
	 * @param progress
	 * @return true if progress was changed and the view needs an update
	 */
	protected boolean updateProgress(int progress)
	{
		// Clamp progress , clamp = ��
		//progress = Math.max(0, Math.min(mMaxProgress, progress));//����̤j�� �p�G>100 �N��100,�A��0��,�̤p��0 �]�N�O0~100����
		progress = Math.max(0, progress);//�����O0 �L�W��
		if (progress != mProgress) //��ثe���A(mProgress)���P����
		{
			mProgress = progress;
			invalidate();//���sø�sView
			return true;
		}
		
		return false;
	}
}
