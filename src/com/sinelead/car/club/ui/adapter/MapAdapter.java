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
