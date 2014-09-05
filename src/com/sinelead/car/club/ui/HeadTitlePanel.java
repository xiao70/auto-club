package com.sinelead.car.club.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinelead.car.club.R;

public class HeadTitlePanel extends RelativeLayout
{

	private Context mContext;
	private TextView mHeadTV;
	private ImageView mHeadIcon;
	private ImageView mHeadDivider;

	public HeadTitlePanel(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
		View parent = LayoutInflater.from(mContext).inflate(
				R.layout.head_title_panel, this, true);
		mHeadTV = (TextView) parent.findViewById(R.id.headTV);
	}

	public void setHeadTitle(String title)
	{
		mHeadTV.setText(title);
	}
}
