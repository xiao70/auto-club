package com.sinelead.car.club.map;

import com.sinelead.car.club.R;
import com.sinelead.car.club.R.layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class NaviMainFragmentBottom extends Fragment
{
	private View view;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		view = inflater.inflate(R.layout.fragment_map_bottom, container, false);
		init();
		return view;
	}

	void init()
	{
		// 导航功能
		View navView = view.findViewById(R.id.layout_nav);

		navView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// 点击弹出新的activity
				Intent intent = new Intent(getActivity(),
						PositionSearchActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
				return;
			}

		});

	}
}
