package com.house365.browser.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.VideoView;

import com.house365.newhouse.R;
import com.house365.browser.Title;

public class BrowserChromeClient extends WebChromeClient {

	Context mActivity;
	private final BrowserController mBrowserController;
	private final Title mTitle;

	public BrowserChromeClient(Context context, Title title, BrowserController browserController) {
		mActivity = context;
		this.mTitle = title;
		this.mBrowserController = browserController;
	}

	private boolean isShown(WebView view) {
		if (view != null)
			return view.isShown();
		else
			return false;
	}

	@Override
	public void onProgressChanged(WebView view, int newProgress) {
		if (isShown(view)) {
			mBrowserController.updateProgress(newProgress);
		}
	}

	@Override
	public void onReceivedIcon(WebView view, Bitmap icon) {
//		mTitle.setFavicon(icon);
		mBrowserController.update();
	}

	@Override
	public void onReceivedTitle(WebView view, String title) {
		mTitle.setTitle(title);
		mBrowserController.update();
		mBrowserController.updateHistory(title, view.getUrl());
	}

	@Override
	public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
		final boolean remember = true;
		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		builder.setTitle(mActivity.getString(R.string.location));
		String org = null;
		if (origin.length() > 50) {
			org = origin.subSequence(0, 50) + "...";
		} else {
			org = origin;
		}
		builder.setMessage(org + mActivity.getString(R.string.message_location))
				.setCancelable(true)
				.setPositiveButton(mActivity.getString(R.string.action_allow), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						callback.invoke(origin, true, remember);
					}
				})
				.setNegativeButton(mActivity.getString(R.string.action_dont_allow),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								callback.invoke(origin, false, remember);
							}
						});
		AlertDialog alert = builder.create();
		alert.show();

	}

	@Override
	public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
		mBrowserController.onCreateWindow(isUserGesture, resultMsg);
		return isUserGesture;
	}

	@Override
	public void onCloseWindow(WebView window) {
		// TODO Auto-generated method stub
		super.onCloseWindow(window);
	}

	public void openFileChooser(ValueCallback<Uri> uploadMsg) {
		mBrowserController.openFileChooser(uploadMsg);
	}

	public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
		mBrowserController.openFileChooser(uploadMsg);
	}

	public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
		mBrowserController.openFileChooser(uploadMsg);
	}

	@Override
	public Bitmap getDefaultVideoPoster() {
		return mBrowserController.getDefaultVideoPoster();
	}

	@Override
	public View getVideoLoadingProgressView() {
		return mBrowserController.getVideoLoadingProgressView();
	}

	@Override
	public void onHideCustomView() {
		mBrowserController.onHideCustomView();
		super.onHideCustomView();
	}

	@Override
	public void onShowCustomView(View view, CustomViewCallback callback) {

		if (view instanceof FrameLayout) {
			FrameLayout frame = (FrameLayout) view;
			if (frame.getFocusedChild() instanceof VideoView) {
				VideoView video = (VideoView) frame.getFocusedChild();
				video.stopPlayback();
				frame.removeView(video);
				video.setVisibility(View.GONE);
			}
		} else {
			Activity activity = mBrowserController.getActivity();
			mBrowserController.onShowCustomView(view, activity.getRequestedOrientation(), callback);

		}

		super.onShowCustomView(view, callback);
	}
}