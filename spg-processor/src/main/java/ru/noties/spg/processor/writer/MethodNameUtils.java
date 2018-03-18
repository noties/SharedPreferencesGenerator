package ru.noties.spg.processor.writer;

import android.support.annotation.NonNull;

import ru.noties.spg.processor.util.TextUtils;

@SuppressWarnings("WeakerAccess")
public abstract class MethodNameUtils {

    private static final String SET = "set";
    private static final String GET = "get";
    private static final String IS = "is";

    @NonNull
    public static String createSetter(@NonNull String value) {
        return SET + TextUtils.capFirstLetter(value);
    }

    @NonNull
    public static String createGetter(@NonNull String value) {
        return GET + TextUtils.capFirstLetter(value);
    }

    @NonNull
    public static String createBooleanGetter(@NonNull String value) {
        // if name starts with `is` when getter would mimic fieldName
        // else add `is`
        if (value.startsWith(IS)) {
            return value;
        }

        return IS + TextUtils.capFirstLetter(value);
    }

    private MethodNameUtils() {
    }
}
