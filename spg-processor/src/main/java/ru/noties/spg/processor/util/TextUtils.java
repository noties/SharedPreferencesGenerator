package ru.noties.spg.processor.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public abstract class TextUtils {

    public static boolean isEmpty(@Nullable CharSequence in) {
        return in == null || in.length() == 0;
    }

    @NonNull
    public static String capFirstLetter(@NonNull String value) {
        return Character.toUpperCase(value.charAt(0)) + value.substring(1);
    }

    private TextUtils() {
    }
}
