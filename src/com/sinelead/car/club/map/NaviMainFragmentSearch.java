package com.sinelead.car.club.map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sinelead.car.club.R;
import com.sinelead.car.club.constant.Constant;

public class NaviMainFragmentSearch extends Fragment
{
	private View view;
	private View voiceView;
	private View searchImgView;
	private TextView searchTextView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		view = inflater.inflate(R.layout.fragment_search_top, container, false);
		init();
		return view;
	}

	void init()
	{
		setUpSearch();

	}

	void setUpSearch()
	{
		voiceView = view.findViewById(R.id.imageButton_voice);
		searchImgView = view.findViewById(R.id.imageView_search);
		searchTextView = (TextView) view.findViewById(R.id.textView_search);
		SearchOnClickListener searchListener = new SearchOnClickListener();
		voiceView.setOnClickListener(searchListener);
		searchImgView.setOnClickListener(searchListener);
		searchTextView.setOnClickListener(searchListener);
	}

	protected class SearchOnClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			String key = searchTextView.getText().toString();
			if (!key.equalsIgnoreCase("ËÑË÷"))
			{
				((NaviMainActivity) getActivity()).clearPoiOverlay();
				return;
			}
			Intent intent = new Intent(getActivity(),
					PositionSearchActivity.class);
			startActivityForResult(intent, Constant.SEARCH_REQUEST);
			return;
		}

	}

	public void gotoSearchActivity(String key)
	{
		Intent intent = new Intent(getActivity(), PositionSearchActivity.class);
		intent.putExtra("key", key);
		startActivityForResult(intent, Constant.SEARCH_REQUEST);
	}

	public void setSearchText(String text)
	{
		if (text == null)
		{
			((TextView) searchTextView).setText("ËÑË÷");
			return;
		}
		((TextView) searchTextView).setText(text);
	}
}
