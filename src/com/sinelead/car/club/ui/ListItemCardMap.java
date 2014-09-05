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
