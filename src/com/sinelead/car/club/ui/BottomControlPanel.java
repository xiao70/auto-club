/*
*
*	版权所有(C) 2014 xiao70 <196245957@qq.com>
*   
*
*	本程序为自由软件；您可依据自由软件基金会所发表的GNU通用公共授权条款，对本程序再次发布或修改；无论您依据的是本授权的第三版，或（您可选的）任一日后发行的版本。
*	本程序是基于使用目的而加以发布，然而不负任何担保责任；亦无对适售性或特定目的适用性所为的默示性担保。详情请参照GNU通用公共授权。
*	您应已收到附随于本程序的GNU通用公共授权的副本；如果没有，请参照
*	<http://www.gnu.org/licenses/>.
* 	如果您项目引用本文件代码或本项目代码，请保留作者、版权和许可通知，如果您程序为商业目的请在程序交互界面，关于信息中保留作者，版权等信息。
*
*	This software may be used and distributed according to the terms of
*	the GNU General Public License (GPL v3), incorporated herein by reference.
*	Drivers based on or derived from this code fall under the GPL and must
*	retain the authorship, copyright and license notice.  This file is not
*	a complete program and may only be used when the entire operating
*	system is licensed under the GPL.
*
*	See the file COPYING in this distribution for more information.
*/
package com.sinelead.car.club.ui;

import java.util.ArrayList;
import java.util.List;

import com.sinelead.car.club.constant.Constant;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.sinelead.car.club.R;

public class BottomControlPanel extends RelativeLayout implements
		View.OnClickListener
{
	private Context mContext;
	private ImageText mMainBtn = null;
	private ImageText mMsgBtn = null;
	private ImageText mMycarBtn = null;
	private ImageText mToolsBtn = null;
	private ImageText mSettingBtn = null;
	private int DEFALUT_BACKGROUND_COLOR = Color.rgb(243, 243, 243); //红色色调 
	
	private BottomPanelCallback mBottomCallback = null;
	private List<ImageText> viewList = new ArrayList<ImageText>();

	public interface BottomPanelCallback
	{
		public void onBottomPanelClick(int itemId);
	}

	public BottomControlPanel(Context context, AttributeSet attrs)
	{
		
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	protected void onFinishInflate()
	{
		// TODO Auto-generated method stub
		mMainBtn = (ImageText) findViewById(R.id.btn_main);
		mMsgBtn = (ImageText) findViewById(R.id.btn_msg);
		mMycarBtn = (ImageText) findViewById(R.id.btn_mycar);
		mToolsBtn = (ImageText) findViewById(R.id.btn_tools);
		mSettingBtn = (ImageText) findViewById(R.id.btn_setting);
		setBackgroundColor(DEFALUT_BACKGROUND_COLOR);
		viewList.add(mMainBtn);
		viewList.add(mMsgBtn);
		viewList.add(mMycarBtn);
		viewList.add(mToolsBtn);
		viewList.add(mSettingBtn);

	}

	public void initBottomPanel()
	{
		if (mMainBtn != null)
		{
			mMainBtn.setImage(R.drawable.main_unselected);
			mMainBtn.setText("主页");
		}
		if (mMsgBtn != null)
		{
			mMsgBtn.setImage(R.drawable.msg_unselected);
			mMsgBtn.setText("消息");
		}
		if (mMycarBtn != null)
		{
			mMycarBtn.setImage(R.drawable.car_unselected);
			mMycarBtn.setText("爱车");
		}
		if (mToolsBtn != null)
		{
			mToolsBtn.setImage(R.drawable.tools_unselected);
			mToolsBtn.setText("应用");
		}
		if (mSettingBtn != null)
		{
			mSettingBtn.setImage(R.drawable.setting_unselected);
			mSettingBtn.setText("设置");
		}
		setBtnListener();

	}

	private void setBtnListener()
	{
		int num = this.getChildCount();
		for (int i = 0; i < num; i++)
		{
			View v = getChildAt(i);
			if (v != null)
			{
				v.setOnClickListener(this);
			}
		}
	}

	public void setBottomCallback(BottomPanelCallback bottomCallback)
	{
		mBottomCallback = bottomCallback;
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		initBottomPanel();
		int index = -1;
		switch (v.getId())
		{
		case R.id.btn_main:
			index = Constant.BTN_FLAG_MAIN;
			mMainBtn.setChecked(Constant.BTN_FLAG_MAIN);
			break;
		case R.id.btn_msg:
			index = Constant.BTN_FLAG_MSG;
			mMsgBtn.setChecked(Constant.BTN_FLAG_MSG);
			break;
		case R.id.btn_mycar:
			index = Constant.BTN_FLAG_MYCAR;
			mMycarBtn.setChecked(Constant.BTN_FLAG_MYCAR);
			break;
		case R.id.btn_tools:
			index = Constant.BTN_FLAG_TOOLS;
			mToolsBtn.setChecked(Constant.BTN_FLAG_TOOLS);
			break;
		case R.id.btn_setting:
			index = Constant.BTN_FLAG_SETTING;
			mSettingBtn.setChecked(Constant.BTN_FLAG_SETTING);
			break;
		default:
			break;
		}
		if (mBottomCallback != null)
		{
			mBottomCallback.onBottomPanelClick(index);
		}
	}

	public void defaultBtnChecked()
	{
		if (mMainBtn != null)
		{
			mMainBtn.setChecked(Constant.BTN_FLAG_MAIN);
		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom)
	{
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
		layoutItems(left, top, right, bottom);
	}

	/**
	 * 最左边和最右边的view由母布局的padding进行控制位置。这里需对第2、3个view的位置重新设置
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	private void layoutItems(int left, int top, int right, int bottom)
	{
		int n = getChildCount();
		if (n == 0)
		{
			return;
		}
		int paddingLeft = getPaddingLeft();
		int paddingRight = getPaddingRight();
		Log.i("yanguoqi", "paddingLeft = " + paddingLeft + " paddingRight = "
				+ paddingRight);
		int width = right - left;
		int height = bottom - top;
		Log.i("yanguoqi", "width = " + width + " height = " + height);
		int allViewWidth = 0;
		for (int i = 0; i < n; i++)
		{
			View v = getChildAt(i);
			Log.i("yanguoqi", "v.getWidth() = " + v.getWidth());
			allViewWidth += v.getWidth();
		}
		int blankWidth = (width - allViewWidth - paddingLeft - paddingRight)
				/ (n - 1);
		Log.i("yanguoqi", "blankV = " + blankWidth);
		
		for (int i = 1; i < n; i++)
		{
			LayoutParams params = (LayoutParams) viewList.get(i).getLayoutParams();
			params.leftMargin = blankWidth;
			viewList.get(i).setLayoutParams(params);
		}

	}

}
