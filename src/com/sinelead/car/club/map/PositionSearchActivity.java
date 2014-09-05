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
package com.sinelead.car.club.map;

import it.gmariotti.cardslib.library.view.CardView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Inputtips.InputtipsListener;
import com.amap.api.services.help.Tip;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.j256.ormlite.dao.Dao;
import com.sinelead.car.club.ClubApplication;
import com.sinelead.car.club.R;
import com.sinelead.car.club.db.DBManager;
import com.sinelead.car.club.db.DatabaseHelper;
import com.sinelead.car.club.db.MapHistory;

import android.widget.TextView;

public class PositionSearchActivity extends FragmentActivity implements
		TextWatcher
{
	private CardView cardView;
	private SearchResultList<Tip> searchCard;
	private HistoryResultList<MapHistory> historyCard;
	private DBManager dbManager;
	private BootstrapButton searchButton;
	private ScrollView cardScrollview;
	private EditText searchText;
	private ImageView imageViewVoice;
	private ImageView imageViewDelete;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_position_search);
		init();

	}

	public DBManager getDbManager()
	{
		return dbManager;
	}

	private void init()
	{
		cardScrollview = (ScrollView) findViewById(R.id.card_scrollview);
		searchText = (EditText) findViewById(R.id.editText_search);
		imageViewVoice = (ImageView) findViewById(R.id.imageButton_voice);
		imageViewDelete = (ImageView) findViewById(R.id.imageButton_delete);

		cardView = (CardView) findViewById(R.id.card_stockcard);
		searchButton = (BootstrapButton) findViewById(R.id.button_search);

		dbManager = new DBManager(this);

		// cardView.setCard(card2);
		setCardViewHistory();

		searchCard = new SearchResultList<Tip>(PositionSearchActivity.this,
				null);
		searchCard.init();

		initSearchBar();

		// 判断是否有传入进行关键字设置
		Intent intent = getIntent();
		String key = intent.getStringExtra("key");
		if (key != null)
		{
			searchText.setText(key);
		}
	}

	private void initSearchBar()
	{
		searchText.addTextChangedListener(this);

		searchButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				String city = "";
				if (ClubApplication.aLocation != null)
				{
					city = ClubApplication.aLocation.getCityCode();
				}
				DatabaseHelper helper = getDbManager().getHelper();

				try
				{
					Dao<MapHistory, Integer> dao = helper.getMapHistoryDao();
					MapHistory mapHistory = new MapHistory();

					mapHistory.setTitle(((TextView) searchText).getText()
							.toString());
					mapHistory.setDetails("");
					mapHistory.setAdcode(city);
					dao.createOrUpdate(mapHistory);
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}

				new PoiSearchHelper(PositionSearchActivity.this).doSearchQuery(
						((TextView) searchText).getText().toString(), city);
			}

		});
		imageViewDelete.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				searchText.getText().clear();
				setSearchbarState();
			}

		});
		setSearchbarState();
	}

	private void setCardViewHistory()
	{
		searchButton.setVisibility(View.GONE);
		cardView.setVisibility(View.VISIBLE);
		List<MapHistory> historys = new ArrayList<MapHistory>();
		try
		{
			Dao<MapHistory, Integer> dao = dbManager.getHelper()
					.getMapHistoryDao();
			historys = dao.queryForAll();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (historyCard == null)
		{
			historyCard = new HistoryResultList<MapHistory>(this, historys);
			historyCard.init();

		}
		else
		{
			historyCard.updateItems(historys);

		}

		if (cardView.getCard() == null)
		{
			cardView.setCard(historyCard);
		}
		else if (!(cardView.getCard() instanceof HistoryResultList))
		{
			cardView.replaceCard(historyCard);
		}
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count)
	{
		final String newText = s.toString().trim();
		if (newText.isEmpty())
		{
			setCardViewHistory();
			setSearchbarState();
			return;
		}

		Inputtips inputTips = new Inputtips(this, new InputtipsListener()
		{

			@Override
			public void onGetInputtips(List<Tip> tipList, int rCode)
			{
				if (searchCard == null)
				{
					return;
				}
				if (rCode == 0)
				{// 正确返回

					searchCard.updateItems(tipList);
				}
				else
				{
					Toast.makeText(PositionSearchActivity.this, "网络出错...",
							Toast.LENGTH_SHORT).show();
				}
				searchCard.updateProgressBar(true, true);

			}

		});
		try
		{
			String city = null;
			if (ClubApplication.aLocation != null)
			{
				city = ClubApplication.aLocation.getCity();
			}
			inputTips.requestInputtips(newText, city);// 第一个参数表示提示关键字，第二个参数默认代表全国，也可以为城市区号

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		if (!(cardView.getCard() instanceof SearchResultList))
		{
			cardView.replaceCard(searchCard);
		}
		searchCard.updateProgressBar(false, true);
		setSearchbarState();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTextChanged(Editable s)
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void onDestroy()
	{

		if (dbManager != null)
			dbManager.closeDBManager();

		super.onDestroy();
	}

	@Override
	public void onPause()
	{
		super.onPause();
		// 加上了崩溃
		// if (searchCard != null)
		// {
		// searchCard.unregisterDataSetObserver();
		// searchCard = null;
		// }
		// if (historyCard != null)
		// {
		// historyCard.unregisterDataSetObserver();
		// historyCard = null;
		// }
	}

	private void setSearchbarState()
	{
		String text = ((TextView) searchText).getText().toString().trim();
		if (!text.isEmpty())
		{
			imageViewVoice.setVisibility(View.GONE);
			imageViewDelete.setVisibility(View.VISIBLE);
			searchButton.setVisibility(View.VISIBLE);
		}
		else
		{
			imageViewVoice.setVisibility(View.VISIBLE);
			imageViewDelete.setVisibility(View.GONE);
			searchButton.setVisibility(View.GONE);
		}
	}
}
