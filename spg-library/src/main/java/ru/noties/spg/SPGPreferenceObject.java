package ru.noties.spg;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Map;

public interface SPGPreferenceObject {

    @NonNull
    String getSharedPreferencesName();

    int getSharedPreferencesMode();

    @NonNull
    SharedPreferences getSharedPreferences();

    @NonNull
    SharedPreferences.Editor getEditor();

    @NonNull
    Map<String, Object> toMap();

    @Nullable
    <T> T get(@NonNull String key);

}
