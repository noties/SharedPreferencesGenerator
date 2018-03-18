package ru.noties.spg.processor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.noties.spg.processor.util.TextUtils;

public abstract class StringParser {

    private static final Pattern PATTERN = Pattern.compile("(\\$\\{\\s*)(.+)(\\s*\\})");

    // package visible for testing
    private static final char[] ILLEGAL_CHARS = new char[]{
            ';', '\n', ','
    };

    public static boolean isEvaluationString(@Nullable String in) {
        if (TextUtils.isEmpty(in)) {
            return false;
        }

        final Matcher matcher = PATTERN.matcher(in);
        if (!matcher.matches()) {
            return false;
        }

        in = matcher.group(2);

        // check for illegal chars
        final char[] chars = in.toCharArray();
        for (int i = chars.length; --i >= 0; ) {
            for (int c = ILLEGAL_CHARS.length; --c >= 0; ) {
                if (chars[i] == ILLEGAL_CHARS[c]) {
                    throw new IllegalStateException("Found illegal char: `" + ILLEGAL_CHARS[c] +
                            "` at position: " + i +
                            " in text: " + in);
                }
            }
        }

        return true;
    }

    @Nullable
    public static String extractEvaluation(@NonNull String in) {
        final Matcher matcher = PATTERN.matcher(in);
        if (matcher.matches()) {
            return matcher.group(2);
        }
        return null;
    }

    private StringParser() {
    }
}
