package com.sinelead.car.club.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OpenHelperManager;

public class DBManager
{

	private SQLiteDatabase db;
	private DatabaseHelper dbHelper;

	public DBManager(Context context)
	{
		dbHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
		// 因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0,
		// mFactory);
		// 所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
		db = dbHelper.getWritableDatabase();

		// 打开外键支持
		db.execSQL("PRAGMA foreign_keys=ON;");

	}

	public SQLiteDatabase getDatabase()
	{
		return db;
	}

	public DatabaseHelper getHelper()
	{
		return dbHelper;
	}

	private void closeHelper()
	{
		if (dbHelper != null)
		{
			OpenHelperManager.releaseHelper();
			dbHelper = null;
		}
	}

	private void closeDb()
	{
		if (db != null)
		{
			db.close();
			db = null;
		}
	}

	public void closeDBManager()
	{
		closeDb();
		closeHelper();
	}

	@Override
	protected void finalize() throws Throwable
	{
		// TODO Auto-generated method stub
		closeDBManager();
		super.finalize();
	}

}
