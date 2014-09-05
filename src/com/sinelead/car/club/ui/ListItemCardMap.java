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

import it.gmariotti.cardslib.library.internal.Card;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinelead.car.club.R;

public class ListItemCardMap extends Card
{

	protected TextView mTitle;
	protected TextView mDetails;
	protected ImageView mImage;
	private String title;
	private Integer imageId;
	private String details;
	private Context context;

	public ListItemCardMap(Context context)
	{
		this(context, R.layout.listview_item_title_details);
	}

	public ListItemCardMap(Context context, int innerLayout)
	{
		super(context, innerLayout);
		this.context = context;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void setImageId(Integer imageId)
	{
		this.imageId = imageId;
	}

	public void setDetails(String details)
	{
		this.details = details;
	}

	@Override
	public void setupInnerViewElements(ViewGroup parent, View view)
	{
		if (mImage == null)
		{
			mImage = (ImageView) view.findViewById(R.id.imageView_img);
		}
		if (mTitle == null)
		{
			mTitle = (TextView) view.findViewById(R.id.textView_title);
		}
		if (mDetails == null)
		{
			mDetails = (TextView) view.findViewById(R.id.textView_details);
		}

		if (mImage != null && imageId != null)
		{
			mImage.setImageDrawable(context.getResources().getDrawable(imageId));
		}
		if (mTitle != null && title != null)
		{
			mTitle.setText(title);
		}
		if (mDetails != null && details != null)
		{
			mDetails.setText(details);
		}
	}
}
