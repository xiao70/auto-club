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
package com.sinelead.car.club;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import cn.trinea.android.common.entity.HttpResponse;
import cn.trinea.android.common.service.HttpCache;
import cn.trinea.android.common.service.HttpCache.HttpCacheListener;
import cn.trinea.android.common.util.ToastUtils;
import cn.trinea.android.common.view.DropDownListView;
import cn.trinea.android.common.view.DropDownListView.OnDropDownListener;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.sinelead.car.club.ui.adapter.ImagePagerAdapter;
import com.sinelead.car.club.ui.adapter.NewsListAdapter;
import com.viewpagerindicator.CirclePageIndicator;

public class NewsFragment extends Fragment
{
	AutoScrollViewPager viewPager;
	private DropDownListView listView;
	private Context context;
	// private LinkedList<String> listItems;
	private CirclePageIndicator indicator;

	protected Elements bannerList;
	private ImagePagerAdapter imagePagerAdapter;

	private NewsListAdapter articleAdapter;
	public static final int MORE_DATA_MAX_COUNT = 100;
	public int moreDataCount = 1;
	private TextView bannerText;
	private View bannerView;
	public String currentImageUrl="";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub

		View v = inflater.inflate(R.layout.tab_item_fragment_news, null);
		context = v.getContext();

		listView = (DropDownListView) v.findViewById(R.id.list_view);
		// 增加banner头
		bannerView = inflater.inflate(R.layout.viewpage_banner_news, null);
		listView.addHeaderView(bannerView);

		initImages();
		initListView();

		// 默认第一页数据
		new GetDataTask(true).execute();

		return v;
	}

	private void initListView()
	{

		// set drop down listener
		listView.setOnDropDownListener(new OnDropDownListener()
		{

			@Override
			public void onDropDown()
			{
				new GetDataTask(true).execute();
			}
		});

		// set on bottom listener
		listView.setOnBottomListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				new GetDataTask(false).execute();
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				ToastUtils.show(context, R.string.drop_down_tip);
				String newsUrl = articleAdapter.getArticleUrl((int) id);
				// 打开activity,传入url进行打开操作
				Intent intent = new Intent();

				intent.putExtra("url", newsUrl);
				/* 指定intent要启动的类 */
				intent.setClass(context, XinchepingActivity.class);
				/* 启动一个新的Activity */
				context.startActivity(intent);
			}
		});
		// listView.setShowFooterWhenNoMore(true);
		articleAdapter = new NewsListAdapter(context,
				R.layout.listview_item_card);

		listView.setAdapter(articleAdapter);

		// listView.setSelector(new ColorDrawable(Color.LTGRAY));
	}

	private void initImages()
	{
		viewPager = (AutoScrollViewPager) bannerView
				.findViewById(R.id.view_pager);
		// 获取屏幕像素相关信息
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);

		viewPager.setLayoutParams(new LayoutParams(dm.widthPixels,
				(int) (dm.widthPixels / 1.68)));

		bannerText = (TextView) bannerView.findViewById(R.id.text_banner);

		imagePagerAdapter = new ImagePagerAdapter(context, bannerList)
				.setInfiniteLoop(false);
		viewPager.setAdapter(imagePagerAdapter);

		viewPager.setInterval(6000); // 6秒替换一个标题
		viewPager.startAutoScroll();
		viewPager.setCurrentItem(0);
		// viewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);
		indicator = (CirclePageIndicator) bannerView
				.findViewById(R.id.indicator);
		indicator.setViewPager(viewPager);
		indicator.setOnPageChangeListener(new ImageOnPageChangeListener());
		
		viewPager.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				ToastUtils.show(context, R.string.drop_down_tip);
		
				// 打开activity,传入url进行打开操作
				Intent intent = new Intent();
				intent.putExtra("url", currentImageUrl);
				/* 指定intent要启动的类 */
				intent.setClass(context, XinchepingActivity.class);
				/* 启动一个新的Activity */
				context.startActivity(intent);
			}
			
		});
		

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
		viewPager.stopAutoScroll();
	}

	@Override
	public void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		viewPager.startAutoScroll();
	}

	public class ImageOnPageChangeListener implements OnPageChangeListener
	{

		@Override
		public void onPageSelected(int position)
		{
			if (bannerList != null)
			{
				Element href = bannerList.get(position);
				String text = href.text();
				bannerText.setText(text);
				currentImageUrl = href.attr("href");
			}
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels)
		{
		}

		@Override
		public void onPageScrollStateChanged(int arg0)
		{
		}
	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]>
	{

		private boolean isDropDown;

		public GetDataTask(boolean isDropDown)
		{
			this.isDropDown = isDropDown;
		}

		@Override
		protected String[] doInBackground(Void... params)
		{
			try
			{
				if (isDropDown)
				{
					parseNewsUrl();
					parseArticleList(1);
				}
				else
				{
					moreDataCount++;
					parseArticleList(moreDataCount);
				}
			}
			catch (Exception e)
			{
				;
			}
			String[] mStrings = null;
			return mStrings;
		}

		@Override
		protected void onPostExecute(String[] result)
		{

			if (isDropDown)
			{

				articleAdapter.notifyDataSetChanged();

				// should call onDropDownComplete function of DropDownListView
				// at end of drop down complete.
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"MM-dd HH:mm:ss");
				listView.onDropDownComplete(getString(R.string.update_at)
						+ dateFormat.format(new Date()));
			}
			else
			{

				articleAdapter.notifyDataSetChanged();

				if (moreDataCount >= MORE_DATA_MAX_COUNT)
				{
					listView.setHasMore(false);
				}

				// should call onBottomComplete function of DropDownListView at
				// end of on bottom complete.
				listView.onBottomComplete();
			}

			super.onPostExecute(result);
		}
	}

	public void parseNewsUrl()
	{
		HttpCache httpCache = new HttpCache(context);
		httpCache.httpGet("http://m.xincheping.com/", new HttpCacheListener()
		{

			protected void onPreGet()
			{
				// do something like show progressBar before httpGet, runs on
				// the UI thread
			}

			protected void onPostGet(HttpResponse httpResponse,
					boolean isInCache)
			{
				// do something like show data after httpGet, runs on the UI
				// thread
				if (httpResponse != null)
				{
					// get data success
					String html = httpResponse.getResponseBody();
					Document doc = Jsoup.parse(html);
					Elements uls = doc.select("ul.slides"); // 带有class属性的ul元素

					bannerList = uls.first().getElementsByTag("a");

					if (imagePagerAdapter != null)
					{
						imagePagerAdapter.setBannerList(bannerList);
						imagePagerAdapter.notifyDataSetChanged();
					}
					

				}
				else
				{
					// get data fail
				}
			}
		});
		return;
	}

	public void parseArticleList(int pageIndex)
	{
		try
		{
			String URL = "http://m.xincheping.com/index.php?a=Index.ajaxGetIndexList";

			HttpPost request = new HttpPost(URL);

			// request.setParams(getRequest.getParams());

			request.addHeader("Referer", "http://m.xincheping.com/");

			request.addHeader("Origin", "http://m.xincheping.com");
			request.addHeader("X-Requested-With", "XMLHttpRequest");
			request.addHeader("Content-Type",
					"application/x-www-form-urlencoded");
			request.addHeader("Accept", "application/json");
			request.addHeader(
					"User-Agent",
					"Mozilla/5.0 (Linux; U; Android 4.1.2; zh-cn; GT-I8552 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");

			request.addHeader("Accept-Encoding", "gzip,deflate");
			request.addHeader("Accept-Language", "zh-CN, en-US");
			request.addHeader("Accept-Charset",
					"utf-8, iso-8859-1, utf-16, *;q=0.7");
			String page = "page=1";
			page = page.replace("1", String.valueOf(pageIndex));
			StringEntity entity = new StringEntity(page);

			request.setEntity(entity);
			// 发送请求
			DefaultHttpClient postClient = new DefaultHttpClient();
			org.apache.http.HttpResponse httpResponse = postClient
					.execute(request);
			// 得到应答的字符串，这也是一个 JSON 格式保存的数据
			// HttpEntity entity = httpResponse.getEntity();
			String retSrc = getZipString(httpResponse);
			// 生成 JSON 对象
			JSONArray jsonArray = new JSONArray(retSrc);
			articleAdapter.addArticleList(jsonArray);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public String getZipString(org.apache.http.HttpResponse response)
	{

		String resultString = "";
		ByteArrayBuffer bt = new ByteArrayBuffer(4096);
		try
		{
			// 执行Get方法
			HttpEntity he = response.getEntity();
			// 以下是解压缩的过程
			GZIPInputStream gis = new GZIPInputStream(he.getContent());
			int l;
			byte[] tmp = new byte[4096];
			while ((l = gis.read(tmp)) != -1)
			{
				bt.append(tmp, 0, l);
			}

			resultString = new String(bt.toByteArray(), "utf-8");
			// 后面的参数换成网站的编码一般来说都是UTF-8

		}
		catch (Exception e)
		{
			Log.i("ERR", e.toString()); // 抛出处理中的异常
		}

		return resultString;
	}
}
