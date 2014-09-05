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
		// ����bannerͷ
		bannerView = inflater.inflate(R.layout.viewpage_banner_news, null);
		listView.addHeaderView(bannerView);

		initImages();
		initListView();

		// Ĭ�ϵ�һҳ����
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
				// ��activity,����url���д򿪲���
				Intent intent = new Intent();

				intent.putExtra("url", newsUrl);
				/* ָ��intentҪ�������� */
				intent.setClass(context, XinchepingActivity.class);
				/* ����һ���µ�Activity */
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
		// ��ȡ��Ļ���������Ϣ
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);

		viewPager.setLayoutParams(new LayoutParams(dm.widthPixels,
				(int) (dm.widthPixels / 1.68)));

		bannerText = (TextView) bannerView.findViewById(R.id.text_banner);

		imagePagerAdapter = new ImagePagerAdapter(context, bannerList)
				.setInfiniteLoop(false);
		viewPager.setAdapter(imagePagerAdapter);

		viewPager.setInterval(6000); // 6���滻һ������
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
		
				// ��activity,����url���д򿪲���
				Intent intent = new Intent();
				intent.putExtra("url", currentImageUrl);
				/* ָ��intentҪ�������� */
				intent.setClass(context, XinchepingActivity.class);
				/* ����һ���µ�Activity */
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
					Elements uls = doc.select("ul.slides"); // ����class���Ե�ulԪ��

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
			// ��������
			DefaultHttpClient postClient = new DefaultHttpClient();
			org.apache.http.HttpResponse httpResponse = postClient
					.execute(request);
			// �õ�Ӧ����ַ�������Ҳ��һ�� JSON ��ʽ���������
			// HttpEntity entity = httpResponse.getEntity();
			String retSrc = getZipString(httpResponse);
			// ���� JSON ����
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
			// ִ��Get����
			HttpEntity he = response.getEntity();
			// �����ǽ�ѹ���Ĺ���
			GZIPInputStream gis = new GZIPInputStream(he.getContent());
			int l;
			byte[] tmp = new byte[4096];
			while ((l = gis.read(tmp)) != -1)
			{
				bt.append(tmp, 0, l);
			}

			resultString = new String(bt.toByteArray(), "utf-8");
			// ����Ĳ���������վ�ı���һ����˵����UTF-8

		}
		catch (Exception e)
		{
			Log.i("ERR", e.toString()); // �׳������е��쳣
		}

		return resultString;
	}
}
