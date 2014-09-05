package com.sinelead.car.club;

import com.sinelead.car.club.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.sinelead.car.club.ui.BottomControlPanel;
import com.viewpagerindicator.TabPageIndicator;

public class MainActivity extends FragmentActivity
{
	private TabPageIndicator mPageIndicator;
	private ViewPager mViewPager;
	private FragmentPagerAdapter fragPagerAdapter;
	private BottomControlPanel bottomPanel;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initUI();

		bottomPanel.defaultBtnChecked();

		fragPagerAdapter = new TabAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(fragPagerAdapter);
		mPageIndicator.setViewPager(mViewPager, 0);

	}

	private void initUI()
	{
		mPageIndicator = (TabPageIndicator) findViewById(R.id.page_indicator);
		mViewPager = (ViewPager) findViewById(R.id.view_pager);
		bottomPanel = (BottomControlPanel) findViewById(R.id.bottom_layout);
		if (bottomPanel != null)
		{
			bottomPanel.initBottomPanel();
		}
	}

}
