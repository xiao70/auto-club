package com.sinelead.car.club.ui.adapter;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import cn.trinea.android.common.service.impl.ImageCache;
import cn.trinea.android.common.util.CacheManager;
import cn.trinea.android.common.util.ToastUtils;

import com.sinelead.car.club.ClubApplication;
import com.sinelead.car.club.R;
import com.sinelead.car.club.XinchepingActivity;
import com.sinelead.car.club.ui.RecyclingPagerAdapter;

/**
 * ImagePagerAdapter
 * 
 * @author 
 */
public class ImagePagerAdapter extends RecyclingPagerAdapter
{

	private Context context;
	private Elements bannerList;

	private int size = 5;
	private boolean isInfiniteLoop;
	private Map<View, Integer> imageViews = new HashMap<View, Integer>();

	public void setBannerList(Elements bannerList)
	{
		this.bannerList = bannerList;
	}

	public ImagePagerAdapter(Context context, Elements bannerList)
	{
		this.context = context;
		this.bannerList = bannerList;
		if (bannerList != null)
		{
			this.size = bannerList.size();
		}
		isInfiniteLoop = false;

	}

	private void downLoadImage()
	{

	}

	@Override
	public void notifyDataSetChanged()
	{
		// TODO Auto-generated method stub
		this.size = bannerList.size();
		super.notifyDataSetChanged();
	}

	@Override
	public Object instantiateItem(View container, int position)
	{
		// imageViews.put(container, position);
		// container.setOnClickListener(new OnClickListener()
		// {
		//
		// @Override
		// public void onClick(View v)
		// {
		// Integer position = imageViews.get(v);
		// ToastUtils.show(context, R.string.drop_down_tip);
		//
		// // 打开activity,传入url进行打开操作
		// Intent intent = new Intent();
		// Element href = bannerList.get(position);
		//
		// String newsUrl = href.attr("src");
		// intent.putExtra("url", newsUrl);
		// /* 指定intent要启动的类 */
		// intent.setClass(context, XinchepingActivity.class);
		// /* 启动一个新的Activity */
		// context.startActivity(intent);
		// }
		// });
		return super.instantiateItem(container, position);
	}

	class ImageOnClickListener implements OnClickListener
	{
		private String url = "";
		public void setHref(String url)
		{
			this.url = url;
		}
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			ToastUtils.show(context, R.string.drop_down_tip);
			Intent intent = new Intent();
			intent.putExtra("url", url);
			/* 指定intent要启动的类 */
			intent.setClass(context, XinchepingActivity.class);
			/* 启动一个新的Activity */
			context.startActivity(intent);
		}
	}

	@Override
	public int getCount()
	{
		// Infinite loop
		if (bannerList == null)
		{
			return 5;
		}
		return isInfiniteLoop ? Integer.MAX_VALUE : size;
	}

	/**
	 * get really position
	 * 
	 * @param position
	 * @return
	 */
	private int getPosition(int position)
	{
		return isInfiniteLoop ? position % size : position;
	}

	@Override
	public View getView(int position, View view, ViewGroup container)
	{
		ViewHolder holder;
		if (view == null)
		{
			holder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(
					R.layout.image_banner_layout, null);
			holder.imageView = (ImageView) view.findViewById(R.id.image_banner);
			holder.listener = new ImageOnClickListener();
			holder.imageView.setOnClickListener(holder.listener);
			view.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) view.getTag();
		}
		if (bannerList != null)
		{
			Element href = bannerList.get(position);

			Element img = href.child(0);

			String imageUrl = img.attr("src");
			ClubApplication.IMAGE_CACHE.get(imageUrl, holder.imageView);

			holder.listener.setHref(href.attr("href"));
		}
		else
		{
			((ImageView) holder.imageView)
					.setImageResource(R.drawable.banner_loading);
		}
		return view;
	}

	private static class ViewHolder
	{
		ImageOnClickListener listener;
		ImageView imageView;
	}

	/**
	 * @return the isInfiniteLoop
	 */
	public boolean isInfiniteLoop()
	{
		return isInfiniteLoop;
	}

	/**
	 * @param isInfiniteLoop
	 *            the isInfiniteLoop to set
	 */
	public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop)
	{
		this.isInfiniteLoop = isInfiniteLoop;
		return this;
	}
}
