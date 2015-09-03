package ru.noties.spg.processor.util;

/**
 * Created by Dimitry Ivanov on 14.07.2015.
 */
public class TextUtils {

    private TextUtils() {}

    public static boolean isEmpty(CharSequence in) {
        return in == null || in.length() == 0;
    }

    public static String capFirstLetter(String value) {
        return Character.toUpperCase(value.charAt(0)) + value.substring(1);
    }
}
