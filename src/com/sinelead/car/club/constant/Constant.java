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