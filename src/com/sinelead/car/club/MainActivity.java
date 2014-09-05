/*
*
*	��Ȩ����(C) 2014 xiao70 <196245957@qq.com>
*   
*
*	������Ϊ������������������������������������GNUͨ�ù�����Ȩ����Ա������ٴη������޸ģ����������ݵ��Ǳ���Ȩ�ĵ����棬������ѡ�ģ���һ�պ��еİ汾��
*	�������ǻ���ʹ��Ŀ�Ķ����Է�����Ȼ�������κε������Σ����޶������Ի��ض�Ŀ����������Ϊ��Ĭʾ�Ե��������������GNUͨ�ù�����Ȩ��
*	��Ӧ���յ������ڱ������GNUͨ�ù�����Ȩ�ĸ��������û�У������
*	<http://www.gnu.org/licenses/>.
* 	�������Ŀ���ñ��ļ��������Ŀ���룬�뱣�����ߡ���Ȩ�����֪ͨ�����������Ϊ��ҵĿ�����ڳ��򽻻����棬������Ϣ�б������ߣ���Ȩ����Ϣ��
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
