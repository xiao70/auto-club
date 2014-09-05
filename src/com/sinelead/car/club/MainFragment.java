package com.sinelead.car.club;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainFragment extends Fragment
{
	private int mNewsType = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.tab_item_fragment_main, null);
		TextView textView = (TextView) v.findViewById(R.id.textView_content);
		textView.setText("正在开发中，敬请期待...");
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
}
