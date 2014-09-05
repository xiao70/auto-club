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
