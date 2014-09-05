package com.sinelead.car.club;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sinelead.car.club.ui.HeadTitlePanel;

@SuppressLint("SetJavaScriptEnabled")
public class XinchepingActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Intent Intent = getIntent();
		String url = Intent.getStringExtra("url");
		setContentView(R.layout.activity_content_xincheping);
		HeadTitlePanel header = (HeadTitlePanel) findViewById(R.id.head_title_panel);
		header.setHeadTitle("新车评新闻");

		WebView webView = (WebView) findViewById(R.id.webView_xincheping);
		webView.setWebChromeClient(new WebChromeClient());

		webView.setWebViewClient(new WebViewClient()
		{
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{
				view.loadUrl(url);
				return true;
			}
		});

		webView.getSettings().setJavaScriptEnabled(true);

		webView.loadUrl(url);
	}
}
