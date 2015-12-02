package com.house365.browser.util;

import java.net.URISyntaxException;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.MailTo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.house365.browser.BrowserActivity;
import com.house365.browser.Title;

public class BrowserWebClient extends WebViewClient {

	private static final String TAG = "BrowserWebClient";
	private static final boolean DEBUG = false;

	Activity mActivity;
	private final BrowserController mBrowserController;
	private final Title mTitle;

	public BrowserWebClient(Activity activity, Title title, BrowserController browserController) {
		this.mActivity = activity;
		this.mTitle = title;
		this.mBrowserController = browserController;
	}

	// @Override
	// public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
	// return null;
	// }

	@Override
	public void onPageFinished(WebView view, String url) {
		if (view.isShown()) {
			view.invalidate();
		}
		mTitle.setTitle(view.getTitle());
		mBrowserController.update();
	}

	private boolean isShown(WebView view) {
		if (view != null)
			return view.isShown();
		else
			return false;
	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		if (isShown(view)) {
			mBrowserController.updateUrl(url);
			mBrowserController.showActionBar();
		}
//		mTitle.setFavicon(BrowserActivity.mWebpageBitmap);
		mBrowserController.update();
	}

	@Override
	public void onScaleChanged(WebView view, float oldScale, float newScale) {
		if (view.isShown()) {
			view.invalidate();
		}
	}

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		if (url.startsWith("market://") || url.startsWith("http://play.google.com/store/apps")
				|| url.startsWith("https://play.google.com/store/apps")) {
			Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			urlIntent.putExtra(BrowserActivity.mPackageName + ".Origin", 1);
			mActivity.startActivity(urlIntent);
			return true;
		} else if (url.contains("tel:") || TextUtils.isDigitsOnly(url)) {
			mActivity.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(url)));
			return true;
		} else if (url.contains("mailto:")) {
			MailTo mailTo = MailTo.parse(url);
			Intent i = Utils.newEmailIntent(mActivity, mailTo.getTo(), mailTo.getSubject(), mailTo.getBody(),
					mailTo.getCc());
			mActivity.startActivity(i);
			view.reload();
			return true;
		} else if (url.startsWith("magnet:?")) {
			Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			urlIntent.putExtra(BrowserActivity.mPackageName + ".Origin", 1);
			mActivity.startActivity(urlIntent);
		} else if (url.startsWith("intent://")) {
			Intent intent = null;
			try {
				intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
			} catch (URISyntaxException ex) {
				return false;
			}
			if (intent != null) {
				try {
					mActivity.startActivity(intent);
				} catch (ActivityNotFoundException e) {
					Log.e(TAG, "ActivityNotFoundException");
				}
				return true;
			}
		}
		return super.shouldOverrideUrlLoading(view, url);
	}
}