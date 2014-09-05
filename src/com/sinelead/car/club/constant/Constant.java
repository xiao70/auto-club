package com.sinelead.car.club.constant;

import com.sinelead.car.club.R;

public class Constant
{
	// Btn的标识
	public static final int BTN_FLAG_MAIN = 0x01;
	public static final int BTN_FLAG_MSG = 0x01 << 1;
	public static final int BTN_FLAG_MYCAR = 0x01 << 2;
	public static final int BTN_FLAG_TOOLS = 0x01 << 3;
	public static final int BTN_FLAG_SETTING = 0x01 << 4;

	// Fragment的标识
	public static final String FRAGMENT_FLAG_MAIN = "主页";
	public static final String FRAGMENT_FLAG_MSG = "消息";
	public static final String FRAGMENT_FLAG_MYCAR = "我的爱车";
	public static final String FRAGMENT_FLAG_TOOLS = "小工具";
	public static final String FRAGMENT_FLAG_SETTING = "设置";

	public static final String[] MAIN_TITLES = { "  新闻  ", "车友位置", "车会活动",
			"车友动态", "吃喝玩乐", "团购帮", "靓车改装" };

	public static final String[] MAP_TITLES = { "求搭车", "我捎你", "导航" };
	public static final String[] MAP_DETAILS = { "方便的找到附近跟您顺路的车辆",
			"看看有没有白富美或高富帅可以捎一程", "不认路!?怕啥,有神器!" };
	public static final int[] MAP_IMAGES = { R.drawable.map_passanger,
			R.drawable.map_ride, R.drawable.map_navigation };

	public static final int UNKNOWN_REQUEST = -1;
	public static final int SEARCH_REQUEST = 0;

	public static final int RESULT_SEARCH = 2; // 从2开始
}