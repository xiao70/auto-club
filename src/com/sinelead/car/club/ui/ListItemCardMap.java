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
*	This program is free software; you can redistribute it and/or modify
*	it under the terms of the GNU General Public License as published by
*	the Free Software Foundation; either version 3 of the License, or
*	(at your option) any later version.
*      
*	This program is distributed in the hope that it will be useful,
*	but WITHOUT ANY WARRANTY; without even the implied warranty of
*	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
*	GNU General Public License for more details.
*      
*	You should have received a copy of the GNU General Public License
*	along with this program; if not, write to the Free Software
*	Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
*	MA 02110-1301, USA.
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
