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


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinelead.car.club.R;
import com.sinelead.car.club.constant.Constant;

public class ImageText extends LinearLayout{
	private Context mContext = null;
	private ImageView mImageView = null;
	private TextView mTextView = null;
	private final static int DEFAULT_IMAGE_WIDTH = 48;
	private final static int DEFAULT_IMAGE_HEIGHT = 48;
	private int CHECKED_COLOR = Color.RED;
	private int UNCHECKED_COLOR = Color.GRAY;   //自然灰色
	
	public ImageText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	public ImageText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View parentView = inflater.inflate(R.layout.image_text_layout, this, true);
		mImageView = (ImageView)findViewById(R.id.image_image_text);
		mTextView = (TextView)findViewById(R.id.text_image_text);
	}
	public void setImage(int id){
		if(mImageView != null){
			mImageView.setImageResource(id);
			setImageSize(DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT);
		}
	}

	public void setText(String s){
		if(mTextView != null){
			mTextView.setText(s);
			mTextView.setTextColor(UNCHECKED_COLOR);
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return true;
	}
	private void setImageSize(int w, int h){
		if(mImageView != null){
			ViewGroup.LayoutParams params = mImageView.getLayoutParams();
			params.width = w;
			params.height = h;
			mImageView.setLayoutParams(params);
		}
	}
	public void setChecked(int itemID){
		if(mTextView != null){
			mTextView.setTextColor(CHECKED_COLOR);
		}
		int checkDrawableId = -1;
		switch (itemID){
		case Constant.BTN_FLAG_MAIN:
			checkDrawableId = R.drawable.main_selected;
			break;
		case Constant.BTN_FLAG_MSG:
			checkDrawableId = R.drawable.msg_selected;
			break;
		case Constant.BTN_FLAG_MYCAR:
			checkDrawableId = R.drawable.car_selected;
			break;
		case Constant.BTN_FLAG_TOOLS:
			checkDrawableId = R.drawable.tools_selected;
			break;
		case Constant.BTN_FLAG_SETTING:
			checkDrawableId = R.drawable.setting_selected;
			break;
		default:break;
		}
		if(mImageView != null){
			mImageView.setImageResource(checkDrawableId);
		}
	}

}

