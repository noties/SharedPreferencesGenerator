package ru.noties.spg;

import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Dimitry Ivanov on 03.08.2015.
 */
public class SharedPreferencesMock implements SharedPreferences {

    private final SharedPreferences delegate;
    private OnSharedPreferenceChangeListener listener;

    public SharedPreferencesMock(SharedPreferences delegate) {
        this.delegate = delegate;
    }

    @Override
    public Map<String, ?> getAll() {
        return delegate.getAll();
    }

    @Nullable
    @Override
    public String getString(String key, String defValue) {
        return delegate.getString(key, defValue);
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String key, Set<String> defValues) {
        return delegate.getStringSet(key, defValues);
    }

    @Override
    public int getInt(String key, int defValue) {
        return delegate.getInt(key, defValue);
    }

    @Override
    public long getLong(String key, long defValue) {
        return delegate.getLong(key, defValue);
    }

    @Override
    public float getFloat(String key, float defValue) {
        return delegate.getFloat(key, defValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return delegate.getBoolean(key, defValue);
    }

    @Override
    public boolean contains(String key) {
        return delegate.contains(key);
    }

    @Override
    public Editor edit() {
        return new EditorMock(delegate.edit());
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        this.listener = null;
    }

    private class EditorMock implements SharedPreferences.Editor {

        private final Editor editor;
        private final Set<String> updated;

        private EditorMock(Editor editor) {
            this.editor = editor;
            this.updated = new HashSet<>();
        }

        @Override
        public Editor putString(String key, String value) {
            updated.add(key);
            return this;
        }

        @Override
        public Editor putStringSet(String key, Set<String> values) {
            updated.add(key);
            return this;
        }

        @Override
        public Editor putInt(String key, int value) {
            updated.add(key);
            return this;
        }

        @Override
        public Editor putLong(String key, long value) {
            updated.add(key);
            return this;
        }

        @Override
        public Editor putFloat(String key, float value) {
            updated.add(key);
            return this;
        }

        @Override
        public Editor putBoolean(String key, boolean value) {
            updated.add(key);
            return this;
        }

        @Override
        public Editor remove(String key) {
            updated.remove(key);
            return this;
        }

        @Override
        public Editor clear() {
            updated.clear();
            return this;
        }

        @Override
        public boolean commit() {
            if (listener != null) {
                for (String k: updated) {
                    listener.onSharedPreferenceChanged(SharedPreferencesMock.this, k);
                }
            }
            updated.clear();
            return true;
        }

        @Override
        public void apply() {
            commit();
        }
    }
}
