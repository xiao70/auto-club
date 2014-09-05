package com.sinelead.car.club;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sinelead.car.club.constant.Constant;

public class TabAdapter extends FragmentPagerAdapter {
	// private String[] TITLES = { "新闻", "车友位置", "车会活动", "车友动态", "吃喝玩乐","团购帮"};

	public TabAdapter(FragmentManager fm) {

		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		Fragment fragment = null;
		if (position == 0) {
			fragment = new NewsFragment();
		} else if (position == 1) {
			fragment = new MapFragment();

		} else {
			fragment = new MainFragment();
		}
		return fragment;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return Constant.MAIN_TITLES[position];
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Constant.MAIN_TITLES.length;
	}
}
