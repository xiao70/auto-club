package com.sinelead.car.club.ui.adapter;

import it.gmariotti.cardslib.library.view.CardView;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sinelead.car.club.ClubApplication;
import com.sinelead.car.club.R;
import com.sinelead.car.club.ui.ListItemCardNews;

public class NewsListAdapter extends ArrayAdapter<Object>
{
	private Context mContext;
	private int mResourcesid;
	private List<JSONObject> articleList = new LinkedList<JSONObject>();
	private String imageRoot = "http://pic.xincheping.com";

	public NewsListAdapter(Context context, int resources)
	{
		super(context, resources);
		mContext = context;
		mResourcesid = resources;
	}
	public String getArticleUrl(int position)
	{
		JSONObject jsonObject = articleList.get(position);
		String Url = "";
		try
		{
			Url = jsonObject.get("Url").toString();
		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Url;
	}
	public void addArticleList(JSONObject jsonObject)
	{
		articleList.add(jsonObject);
	}

	public void addArticleList(JSONArray jsonArray)
	{
		for (int i = 0; i < jsonArray.length(); i++)
		{
			try
			{
				articleList.add(jsonArray.getJSONObject(i));
			}
			catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public int getCount()
	{
		if (articleList.size() == 0)
		{
			return 0;
		}
		return articleList.size();
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
		ListItemCardNews card;
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
			holder.cardView = (CardView) convertView.findViewById(R.id.card_view);
			holder.card = new ListItemCardNews(mContext,
					R.layout.listview_item_news);
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

	private void setCardValue(ListItemCardNews card, int position)
	{
		if (articleList.isEmpty())
		{
			card.setImageUrl(null);
			card.setTitle(null);
			card.setDetails(null);
		}
		else
		{
			try
			{
				String title = articleList.get(position).get("Title")
						.toString();

				card.setTitle(title);

				String imageUrl = imageRoot
						+ articleList.get(position).get("ImagePath").toString();
				card.setImageUrl(imageUrl);

				String details = "分类:"
						+ articleList.get(position).get("RelateTypeName")
								.toString() + " " + "时间:"
						+ articleList.get(position).get("TaxisTime").toString();

				card.setDetails(details);
			}
			catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
