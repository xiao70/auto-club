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
			if (!key.equalsIgnoreCase("搜索"))
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
			((TextView) searchTextView).setText("搜索");
			return;
		}
		((TextView) searchTextView).setText(text);
	}
}
