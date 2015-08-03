package ru.noties.spg.processor.writer;

import java.util.Arrays;

/**
 * Created by Dimitry Ivanov on 15.07.2015.
 */
public class Indent {

    private int length;
    private String cached;

    public Indent increment() {
        length++;
        cached = null;
        return this;
    }

    public Indent decrement() {
        length--;
        cached = null;
        return this;
    }

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

    static String createCache(int length) {
        if (length == 0) {
            return "";
        }
        final char[] chars = new char[4 * length];
        Arrays.fill(chars, ' ');
        return new String(chars);
    }
}
