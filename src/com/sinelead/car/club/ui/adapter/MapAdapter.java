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
package com.sinelead.car.club.ui.adapter;

import it.gmariotti.cardslib.library.view.CardView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.sinelead.car.club.R;
import com.sinelead.car.club.constant.Constant;
import com.sinelead.car.club.ui.ListItemCardMap;
import com.sinelead.car.club.ui.ListItemCardNews;

public class MapAdapter extends ArrayAdapter<Object>
{

	private Context mContext;
	private int mResourcesid;

	public MapAdapter(Context context, int resources)
	{
		super(context, resources);
		mContext = context;
		mResourcesid = resources;
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return Constant.MAP_TITLES.length;
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return super.getItem(position);
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return super.getItemId(position);
	}

	class Holder
	{
		ListItemCardMap card;
		CardView cardView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		Holder holder;

		if (convertView == null)
		{
			holder = new Holder();
			convertView = LayoutInflater.from(mContext).inflate(mResourcesid,
					null);
			holder.cardView = (CardView) convertView
					.findViewById(R.id.card_view);
			holder.card = new ListItemCardMap(mContext,
					R.layout.listview_item_map);
			convertView.setTag(holder);
			setCardValue(holder.card, position);

			holder.cardView.setCard(holder.card);
		}
		else
		{
			holder = (Holder) convertView.getTag();
			setCardValue(holder.card, position);
			holder.cardView.refreshCard(holder.card);
		}

		return convertView;
	}

	private void setCardValue(ListItemCardMap card, int position)
	{

		try
		{
			String title = Constant.MAP_TITLES[position];
			card.setTitle(title);

			String details = Constant.MAP_DETAILS[position];
			card.setDetails(details);
			
			int imageId = Constant.MAP_IMAGES[position];
			card.setImageId(imageId);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
