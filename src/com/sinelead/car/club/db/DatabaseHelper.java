
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

package com.sinelead.car.club.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper
{

	private static final String DATABASE_NAME = "sineleadapp.db";
	private static final int DATABASE_VERSION = 1;
	private Dao<MapHistory, Integer> mapHistoryDao = null;

	public DatabaseHelper(Context context)
	{
		// CursorFactory����Ϊnull,ʹ��Ĭ��ֵ
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// ���ݿ��һ�α�����ʱonCreate�ᱻ����
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource)
	{
		// ����Ĭ�ϵ����ݿ��
		// List<String> sqlStringList = new SqlContainer().getCreateTableList();
		db.beginTransaction();
		try
		{
			try
			{
				TableUtils.createTable(connectionSource, MapHistory.class);

			}
			catch (Exception e)
			{
				return;
			}

			db.setTransactionSuccessful();

		}
		finally
		{
			db.endTransaction();
		}
	}

	// ���DATABASE_VERSIONֵ����Ϊ2,ϵͳ�����������ݿ�汾��ͬ,�������onUpgrade
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion)
	{
		// db.execSQL("ALTER TABLE user ADD COLUMN user_authority TEXT");
		try
		{
			TableUtils.dropTable(connectionSource, MapHistory.class, true);
			onCreate(db, connectionSource);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@Override
	public void close()
	{
		// TODO Auto-generated method stub
		super.close();
		mapHistoryDao = null;

	}

	public Dao<MapHistory, Integer> getMapHistoryDao() throws SQLException
	{
		if (mapHistoryDao == null)
		{
			mapHistoryDao = getDao(MapHistory.class);
		}
		return mapHistoryDao;
	}
}