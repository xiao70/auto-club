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
