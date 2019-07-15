package ru.noties.spg.processor.writer;

import androidx.annotation.NonNull;

import java.util.Arrays;

@SuppressWarnings("WeakerAccess")
public class Indent {

    private int length;
    private String cached;

    @NonNull
    public Indent increment() {
        length++;
        cached = null;
        return this;
    }

    @NonNull
    public Indent decrement() {
        length--;
        cached = null;
        return this;
    }

    @NonNull
    public Indent setLength(int length) {
        if (this.length != length) {
            this.length = length;
            cached = null;
        }
        return this;
    }

    @Override
    public String toString() {
        if (cached == null) {
            cached = createCache(length);
        }
        return cached;
    }

    @NonNull
    private static String createCache(int length) {
        if (length == 0) {
            return "";
        }
        final char[] chars = new char[4 * length];
        Arrays.fill(chars, ' ');
        return new String(chars);
    }
}
