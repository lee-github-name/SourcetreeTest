/*
 * Copyright 2014 A.C.R. Development
 */
package com.house365.browser.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.house365.newhouse.R;

public class Utils {
	private static final String TAG = "Utils";
	private static final boolean DEBUG = false;

	public static synchronized void addBookmark(Context context, String title, String url) {
		File book = new File(context.getFilesDir(), "bookmarks");
		File bookUrl = new File(context.getFilesDir(), "bookurl");
		if ((title.equals("Bookmarks") || title.equals("History")) && url.startsWith("file://")) {
			return;
		}
		try {
			BufferedReader readUrlRead = new BufferedReader(new FileReader(bookUrl));
			String u;
			while ((u = readUrlRead.readLine()) != null) {
				if (u.contentEquals(url)) {
					readUrlRead.close();
					return;
				}
			}
			readUrlRead.close();

		} catch (FileNotFoundException ignored) {
		} catch (IOException ignored) {
		} catch (NullPointerException ignored) {
		}
		try {
			BufferedWriter bookWriter = new BufferedWriter(new FileWriter(book, true));
			BufferedWriter urlWriter = new BufferedWriter(new FileWriter(bookUrl, true));
			bookWriter.write(title);
			urlWriter.write(url);
			bookWriter.newLine();
			urlWriter.newLine();
			bookWriter.close();
			urlWriter.close();
		} catch (FileNotFoundException ignored) {
		} catch (IOException ignored) {
		} catch (NullPointerException ignored) {
		}
	}

	public static Intent newEmailIntent(Context context, String address, String subject, String body, String cc) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] { address });
		intent.putExtra(Intent.EXTRA_TEXT, body);
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_CC, cc);
		intent.setType("message/rfc822");
		return intent;
	}

	public static void createInformativeDialog(Context context, String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message)
				.setCancelable(true)
				.setPositiveButton(context.getResources().getString(R.string.action_ok),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public static void showToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	public static int convertToDensityPixels(Context context, int densityPixels) {
		float scale = context.getResources().getDisplayMetrics().density;
		int pixels = (int) (densityPixels * scale + 0.5f);
		return pixels;
	}

	public static String getDomainName(String url) {
		URI uri;
		try {
			uri = new URI(url);
		} catch (URISyntaxException e) {
			return url;
		}
		String domain = uri.getHost();
		if (domain == null) {
			return url;
		}
		return domain.startsWith("www.") ? domain.substring(4) : domain;
	}

	public static String[] getArray(String input) {
		return input.split("\\|\\$\\|SEPARATOR\\|\\$\\|");
	}

	public static void trimCache(Context context) {
		try {
			File dir = context.getCacheDir();

			if (dir != null && dir.isDirectory()) {
				deleteDir(dir);
			}
		} catch (Exception ignored) {

		}
	}

	public static boolean deleteDir(File dir) {
		if (dir != null && dir.isDirectory()) {
			String[] children = dir.list();
			for (String aChildren : children) {
				boolean success = deleteDir(new File(dir, aChildren));
				if (!success) {
					return false;
				}
			}
		}
		// The directory is now empty so delete it
		return dir.delete();
	}

	public static void setPluginState(Object mSettingObj, int i) {

		Class<?> pluginStateClass;
		try {
			pluginStateClass = Class.forName("android.webkit.WebSettings$PluginState");
			Object[] constants = pluginStateClass.getEnumConstants();
			for (Object object : constants) {
				if (DEBUG) Log.v(TAG, "PluginState:" + object.getClass());
			}
			if (DEBUG)
				Log.v(TAG,
						"Enum name:  %s%nEnum constants:  %s%n" + pluginStateClass.getName()
								+ Arrays.asList(pluginStateClass.getEnumConstants()));
			Class<?> webSettingClass = Class.forName("android.webkit.WebSettings");
			Method setPluginMethod = webSettingClass.getDeclaredMethod("setPluginState", pluginStateClass);
			setPluginMethod.setAccessible(true);
			setPluginMethod.invoke(mSettingObj, constants[i]);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
