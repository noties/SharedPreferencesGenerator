package ru.noties.spg;

import android.content.SharedPreferences;

import java.util.Map;

/**
 * Created by Dimitry Ivanov on 16.07.2015.
 */
public interface SPGPreferenceObject {

    String getSharedPreferencesName();
    SharedPreferences getSharedPreferences();
    SharedPreferences.Editor getEditor();
    Map<String, Object> toMap();

    <T> T get(String key);

}
