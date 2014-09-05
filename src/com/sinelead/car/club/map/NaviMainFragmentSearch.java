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
*	This program is free software; you can redistribute it and/or modify
*	it under the terms of the GNU General Public License as published by
*	the Free Software Foundation; either version 3 of the License, or
*	(at your option) any later version.
*      
*	This program is distributed in the hope that it will be useful,
*	but WITHOUT ANY WARRANTY; without even the implied warranty of
*	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
*	GNU General Public License for more details.
*      
*	You should have received a copy of the GNU General Public License
*	along with this program; if not, write to the Free Software
*	Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
*	MA 02110-1301, USA.
*/
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
			if (!key.equalsIgnoreCase("����"))
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
			((TextView) searchTextView).setText("����");
			return;
		}
		((TextView) searchTextView).setText(text);
	}
}
