package com.sinelead.car.club.ui;

import com.sinelead.car.club.ClubApplication;
import com.sinelead.car.club.R;

import it.gmariotti.cardslib.library.internal.Card;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ListItemCardNews extends Card
{

	protected TextView mTitle;
	protected TextView mDetails;
	protected ImageView mImage;
	private String title;
	private String imageUrl;
	private String details;

	public ListItemCardNews(Context context)
	{
		this(context, R.layout.listview_item_news);
	}

	public ListItemCardNews(Context context, int innerLayout)
	{
		super(context, innerLayout);
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void setImageUrl(String imageUrl)
	{
		this.imageUrl = imageUrl;
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

		if (mImage != null && imageUrl!=null)
		{
			ClubApplication.IMAGE_CACHE.get(imageUrl, mImage);
		}
		if(mTitle != null && title!=null)
		{
			mTitle.setText(title);
		}
		if(mDetails != null && details!=null)
		{
			mDetails.setText(details);
		}
	}
}
