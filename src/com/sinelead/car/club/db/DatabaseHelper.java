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