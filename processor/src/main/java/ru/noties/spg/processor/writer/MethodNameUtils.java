package ru.noties.spg.processor.writer;

import ru.noties.spg.processor.util.TextUtils;

/**
 * Created by Dimitry Ivanov on 15.07.2015.
 */
public class MethodNameUtils {

    private static final String SET = "set";
    private static final String GET = "get";
    private static final String IS  = "is";

    private MethodNameUtils() {}

    public static String createSetter(String value) {
        return SET + TextUtils.capFirstLetter(value);
    }

    public static String createGetter(String value) {
        return GET + TextUtils.capFirstLetter(value);
    }

    public static String createBooleanGetter(String value) {
        // if name starts with `is` when getter would mimic fieldName
        // else add `is`
        if (value.startsWith(IS)) {
            return value;
        }

        return IS + TextUtils.capFirstLetter(value);
    }
}
