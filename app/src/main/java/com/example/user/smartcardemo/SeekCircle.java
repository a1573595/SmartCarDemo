package com.example.user.smartcardemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

public class SeekCircle extends ProgressCircle {
	/**
	 * A callback that notifies clients when the progress level has been
	 * changed. This includes changes that were initiated by the user through a
	 * touch gesture or arrow key/trackball as well as changes that were
	 * initiated programmatically.
	 */
	
	//SeekCircle�غc�l,�ŧR
	public SeekCircle(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		Log.d("SeekCircle","in Seek Circle constructor");
	}
	
	public SeekCircle(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		Log.d("SeekCircle","in Seek Circle constructor");
	}
	
	public SeekCircle(Context context)
	{
		super(context);
		Log.d("SeekCircle","in Seek Circle constructor");
	}
	//SeekCircle�غc�l,�ŧR end		
}
