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
		// ��ΪgetWritableDatabase�ڲ�������mContext.openOrCreateDatabase(mName, 0,
		// mFactory);
		// ����Ҫȷ��context�ѳ�ʼ��,���ǿ��԰�ʵ����DBManager�Ĳ������Activity��onCreate��
		db = dbHelper.getWritableDatabase();

		// �����֧��
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
