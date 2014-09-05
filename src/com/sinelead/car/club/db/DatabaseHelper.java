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
		// CursorFactory设置为null,使用默认值
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// 数据库第一次被创建时onCreate会被调用
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource)
	{
		// 创建默认的数据库表
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

	// 如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
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