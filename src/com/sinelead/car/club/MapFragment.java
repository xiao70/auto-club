package com.sinelead.car.club;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import cn.trinea.android.common.util.ToastUtils;
import cn.trinea.android.common.view.DropDownListView;

import com.sinelead.car.club.map.NaviMainActivity;
import com.sinelead.car.club.ui.adapter.MapAdapter;

public class MapFragment extends Fragment
{
	private Context context;
	private ListView listView;
	private Class<?>[] activityArray = { NaviMainActivity.class,
			NaviMainActivity.class, NaviMainActivity.class };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.tab_item_fragment_map, null);
		context = v.getContext();
		listView = (ListView) v.findViewById(R.id.list_view);

		initListView();
		return v;
	}

	private void initListView()
	{
		listView.setAdapter(new MapAdapter(context, R.layout.listview_item_card));
		listView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				ToastUtils.show(context, R.string.drop_down_tip);

				Intent intent = new Intent();
				/* 指定intent要启动的类 */
				intent.setClass(context, activityArray[position]);
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
}
