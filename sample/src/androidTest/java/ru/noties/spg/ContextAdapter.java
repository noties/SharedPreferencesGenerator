package ru.noties.spg;

import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.view.Display;
import android.view.ViewDebug;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@SuppressWarnings({"NewApi", "unused", "deprecation", "NullableProblems", "FieldCanBeLocal"})
public class ContextAdapter extends Context {
    
    private final Context delegate;

    public ContextAdapter(Context delegate) {
        this.delegate = delegate;
    }

    public Context getDelegate() {
        return delegate;
    }

    public AssetManager getAssets() {
        return delegate.getAssets();
    }

    public Resources getResources() {
        return delegate.getResources();
    }

    public PackageManager getPackageManager() {
        return delegate.getPackageManager();
    }

    public ContentResolver getContentResolver() {
        return delegate.getContentResolver();
    }

    public Looper getMainLooper() {
        return delegate.getMainLooper();
    }

    public Context getApplicationContext() {
        return delegate.getApplicationContext();
    }

    public void registerComponentCallbacks(ComponentCallbacks callback) {
        delegate.registerComponentCallbacks(callback);
    }

    public void unregisterComponentCallbacks(ComponentCallbacks callback) {
        delegate.unregisterComponentCallbacks(callback);
    }
    
    public void setTheme(int resid) {
        delegate.setTheme(resid);
    }

    @ViewDebug.ExportedProperty(
        deepExport = true
    )
    public Resources.Theme getTheme() {
        return delegate.getTheme();
    }

    public ClassLoader getClassLoader() {
        return delegate.getClassLoader();
    }

    public String getPackageName() {
        return delegate.getPackageName();
    }

    public ApplicationInfo getApplicationInfo() {
        return delegate.getApplicationInfo();
    }

    public String getPackageResourcePath() {
        return delegate.getPackageResourcePath();
    }

    public String getPackageCodePath() {
        return delegate.getPackageCodePath();
    }

    public SharedPreferences getSharedPreferences(String name, int mode) {
        return delegate.getSharedPreferences(name, mode);
    }

    public FileInputStream openFileInput(String name) throws FileNotFoundException {
        return delegate.openFileInput(name);
    }

    public FileOutputStream openFileOutput(String name, int mode) throws FileNotFoundException {
        return delegate.openFileOutput(name, mode);
    }

    public boolean deleteFile(String name) {
        return delegate.deleteFile(name);
    }

    public File getFileStreamPath(String name) {
        return delegate.getFileStreamPath(name);
    }

    public File getFilesDir() {
        return delegate.getFilesDir();
    }

    @Override
    public File getNoBackupFilesDir() {
        return null;
    }

    public File getExternalFilesDir(String type) {
        return delegate.getExternalFilesDir(type);
    }

    @Override
    public File[] getExternalFilesDirs(String type) {
        return new File[0];
    }

    public File getObbDir() {
        return delegate.getObbDir();
    }

    public File[] getObbDirs() {
        return delegate.getObbDirs();
    }

    public File getCacheDir() {
        return delegate.getCacheDir();
    }

    public File getCodeCacheDir() {
        return delegate.getCodeCacheDir();
    }

    public File getExternalCacheDir() {
        return delegate.getExternalCacheDir();
    }

    public File[] getExternalCacheDirs() {
        return delegate.getExternalCacheDirs();
    }

    public File[] getExternalMediaDirs() {
        return delegate.getExternalMediaDirs();
    }

    public String[] fileList() {
        return delegate.fileList();
    }

    public File getDir(String name, int mode) {
        return delegate.getDir(name, mode);
    }

    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        return delegate.openOrCreateDatabase(name, mode, factory);
    }

    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return delegate.openOrCreateDatabase(name, mode, factory, errorHandler);
    }

    public boolean deleteDatabase(String name) {
        return delegate.deleteDatabase(name);
    }

    public File getDatabasePath(String name) {
        return delegate.getDatabasePath(name);
    }

    public String[] databaseList() {
        return delegate.databaseList();
    }

    @Deprecated
    public Drawable getWallpaper() {
        return delegate.getWallpaper();
    }

    @Deprecated
    public Drawable peekWallpaper() {
        return delegate.peekWallpaper();
    }

    @Deprecated
    public int getWallpaperDesiredMinimumWidth() {
        return delegate.getWallpaperDesiredMinimumWidth();
    }

    @Deprecated
    public int getWallpaperDesiredMinimumHeight() {
        return delegate.getWallpaperDesiredMinimumHeight();
    }

    @Deprecated
    public void setWallpaper(Bitmap bitmap) throws IOException {
        delegate.setWallpaper(bitmap);
    }

    @Deprecated
    public void setWallpaper(InputStream data) throws IOException {
        delegate.setWallpaper(data);
    }

    @Deprecated
    public void clearWallpaper() throws IOException {
        delegate.clearWallpaper();
    }

    public void startActivity(Intent intent) {
        delegate.startActivity(intent);
    }

    public void startActivity(Intent intent, Bundle options) {
        delegate.startActivity(intent, options);
    }

    public void startActivities(Intent[] intents) {
        delegate.startActivities(intents);
    }

    public void startActivities(Intent[] intents, Bundle options) {
        delegate.startActivities(intents, options);
    }

    public void startIntentSender(IntentSender intent, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags) throws IntentSender.SendIntentException {
        delegate.startIntentSender(intent, fillInIntent, flagsMask, flagsValues, extraFlags);
    }

    public void startIntentSender(IntentSender intent, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, Bundle options) throws IntentSender.SendIntentException {
        delegate.startIntentSender(intent, fillInIntent, flagsMask, flagsValues, extraFlags, options);
    }

    public void sendBroadcast(Intent intent) {
        delegate.sendBroadcast(intent);
    }

    public void sendBroadcast(Intent intent, String receiverPermission) {
        delegate.sendBroadcast(intent, receiverPermission);
    }

    public void sendOrderedBroadcast(Intent intent, String receiverPermission) {
        delegate.sendOrderedBroadcast(intent, receiverPermission);
    }

    public void sendOrderedBroadcast(Intent intent, String receiverPermission, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
        delegate.sendOrderedBroadcast(intent, receiverPermission, resultReceiver, scheduler, initialCode, initialData, initialExtras);
    }

    public void sendBroadcastAsUser(Intent intent, UserHandle user) {
        delegate.sendBroadcastAsUser(intent, user);
    }

    public void sendBroadcastAsUser(Intent intent, UserHandle user, String receiverPermission) {
        delegate.sendBroadcastAsUser(intent, user, receiverPermission);
    }

    public void sendOrderedBroadcastAsUser(Intent intent, UserHandle user, String receiverPermission, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
        delegate.sendOrderedBroadcastAsUser(intent, user, receiverPermission, resultReceiver, scheduler, initialCode, initialData, initialExtras);
    }

    @Deprecated
    public void sendStickyBroadcast(Intent intent) {
        delegate.sendStickyBroadcast(intent);
    }

    @Deprecated
    public void sendStickyOrderedBroadcast(Intent intent, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
        delegate.sendStickyOrderedBroadcast(intent, resultReceiver, scheduler, initialCode, initialData, initialExtras);
    }

    @Deprecated
    public void removeStickyBroadcast(Intent intent) {
        delegate.removeStickyBroadcast(intent);
    }

    @Deprecated
    public void sendStickyBroadcastAsUser(Intent intent, UserHandle user) {
        delegate.sendStickyBroadcastAsUser(intent, user);
    }

    @Deprecated
    public void sendStickyOrderedBroadcastAsUser(Intent intent, UserHandle user, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
        delegate.sendStickyOrderedBroadcastAsUser(intent, user, resultReceiver, scheduler, initialCode, initialData, initialExtras);
    }

    @Deprecated
    public void removeStickyBroadcastAsUser(Intent intent, UserHandle user) {
        delegate.removeStickyBroadcastAsUser(intent, user);
    }

    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        return delegate.registerReceiver(receiver, filter);
    }

    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, String broadcastPermission, Handler scheduler) {
        return delegate.registerReceiver(receiver, filter, broadcastPermission, scheduler);
    }

    public void unregisterReceiver(BroadcastReceiver receiver) {
        delegate.unregisterReceiver(receiver);
    }

    public ComponentName startService(Intent service) {
        return delegate.startService(service);
    }

    public boolean stopService(Intent service) {
        return delegate.stopService(service);
    }

    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
        return delegate.bindService(service, conn, flags);
    }

    public void unbindService(ServiceConnection conn) {
        delegate.unbindService(conn);
    }

    public boolean startInstrumentation(ComponentName className, String profileFile, Bundle arguments) {
        return delegate.startInstrumentation(className, profileFile, arguments);
    }

    public Object getSystemService(String name) {
        return delegate.getSystemService(name);
    }

    @Override
    public String getSystemServiceName(Class<?> serviceClass) {
        return delegate.getSystemServiceName(serviceClass);
    }

    public int checkPermission(String permission, int pid, int uid) {
        return delegate.checkPermission(permission, pid, uid);
    }

    public int checkCallingPermission(String permission) {
        return delegate.checkCallingPermission(permission);
    }

    public int checkCallingOrSelfPermission(String permission) {
        return delegate.checkCallingOrSelfPermission(permission);
    }

    @Override
    public int checkSelfPermission(String permission) {
        return delegate.checkSelfPermission(permission);
    }

    public void enforcePermission(String permission, int pid, int uid, String message) {
        delegate.enforcePermission(permission, pid, uid, message);
    }

    public void enforceCallingPermission(String permission, String message) {
        delegate.enforceCallingPermission(permission, message);
    }

    public void enforceCallingOrSelfPermission(String permission, String message) {
        delegate.enforceCallingOrSelfPermission(permission, message);
    }

    public void grantUriPermission(String toPackage, Uri uri, int modeFlags) {
        delegate.grantUriPermission(toPackage, uri, modeFlags);
    }

    public void revokeUriPermission(Uri uri, int modeFlags) {
        delegate.revokeUriPermission(uri, modeFlags);
    }

    public int checkUriPermission(Uri uri, int pid, int uid, int modeFlags) {
        return delegate.checkUriPermission(uri, pid, uid, modeFlags);
    }

    public int checkCallingUriPermission(Uri uri, int modeFlags) {
        return delegate.checkCallingUriPermission(uri, modeFlags);
    }

    public int checkCallingOrSelfUriPermission(Uri uri, int modeFlags) {
        return delegate.checkCallingOrSelfUriPermission(uri, modeFlags);
    }

    public int checkUriPermission(Uri uri, String readPermission, String writePermission, int pid, int uid, int modeFlags) {
        return delegate.checkUriPermission(uri, readPermission, writePermission, pid, uid, modeFlags);
    }

    public void enforceUriPermission(Uri uri, int pid, int uid, int modeFlags, String message) {
        delegate.enforceUriPermission(uri, pid, uid, modeFlags, message);
    }

    public void enforceCallingUriPermission(Uri uri, int modeFlags, String message) {
        delegate.enforceCallingUriPermission(uri, modeFlags, message);
    }

    public void enforceCallingOrSelfUriPermission(Uri uri, int modeFlags, String message) {
        delegate.enforceCallingOrSelfUriPermission(uri, modeFlags, message);
    }

    public void enforceUriPermission(Uri uri, String readPermission, String writePermission, int pid, int uid, int modeFlags, String message) {
        delegate.enforceUriPermission(uri, readPermission, writePermission, pid, uid, modeFlags, message);
    }

    public Context createPackageContext(String packageName, int flags) throws PackageManager.NameNotFoundException {
        return delegate.createPackageContext(packageName, flags);
    }

    public Context createConfigurationContext(Configuration overrideConfiguration) {
        return delegate.createConfigurationContext(overrideConfiguration);
    }

    public Context createDisplayContext(Display display) {
        return delegate.createDisplayContext(display);
    }

    public boolean isRestricted() {
        return delegate.isRestricted();
    }
}
