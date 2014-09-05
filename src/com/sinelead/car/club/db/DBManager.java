
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
