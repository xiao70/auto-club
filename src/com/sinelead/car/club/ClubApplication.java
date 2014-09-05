package com.sinelead.car.club;

import com.amap.api.location.AMapLocation;
import com.sinelead.car.club.db.DBManager;

import android.app.Application;
import cn.trinea.android.common.service.impl.ImageCache;
import cn.trinea.android.common.util.CacheManager;

public class ClubApplication extends Application
{
	public static final ImageCache IMAGE_CACHE = CacheManager.getImageCache();
	public static AMapLocation aLocation;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
		//SDKInitializer.initialize(this);
		IMAGE_CACHE.setCacheFolder(this.getApplicationContext().getCacheDir().getPath());
		
	}
	@Override
	public void onTerminate()
	{
		// TODO Auto-generated method stub
		super.onTerminate();
	}


}