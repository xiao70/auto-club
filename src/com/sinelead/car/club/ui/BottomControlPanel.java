/*
*
*	��Ȩ����(C) 2014 xiao70 <196245957@qq.com>
*   
*
*	������Ϊ������������������������������������GNUͨ�ù�����Ȩ����Ա������ٴη������޸ģ����������ݵ��Ǳ���Ȩ�ĵ����棬������ѡ�ģ���һ�պ��еİ汾��
*	�������ǻ���ʹ��Ŀ�Ķ����Է�����Ȼ�������κε������Σ����޶������Ի��ض�Ŀ����������Ϊ��Ĭʾ�Ե��������������GNUͨ�ù�����Ȩ��
*	��Ӧ���յ������ڱ������GNUͨ�ù�����Ȩ�ĸ��������û�У������
*	<http://www.gnu.org/licenses/>.
* 	�������Ŀ���ñ��ļ��������Ŀ���룬�뱣�����ߡ���Ȩ�����֪ͨ�����������Ϊ��ҵĿ�����ڳ��򽻻����棬������Ϣ�б������ߣ���Ȩ����Ϣ��
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
	private int DEFALUT_BACKGROUND_COLOR = Color.rgb(243, 243, 243); //��ɫɫ�� 
	
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
			mMainBtn.setText("��ҳ");
		}
		if (mMsgBtn != null)
		{
			mMsgBtn.setImage(R.drawable.msg_unselected);
			mMsgBtn.setText("��Ϣ");
		}
		if (mMycarBtn != null)
		{
			mMycarBtn.setImage(R.drawable.car_unselected);
			mMycarBtn.setText("����");
		}
		if (mToolsBtn != null)
		{
			mToolsBtn.setImage(R.drawable.tools_unselected);
			mToolsBtn.setText("Ӧ��");
		}
		if (mSettingBtn != null)
		{
			mSettingBtn.setImage(R.drawable.setting_unselected);
			mSettingBtn.setText("����");
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
	 * ����ߺ����ұߵ�view��ĸ���ֵ�padding���п���λ�á�������Ե�2��3��view��λ����������
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
