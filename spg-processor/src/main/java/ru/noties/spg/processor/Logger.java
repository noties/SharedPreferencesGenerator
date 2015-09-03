package ru.noties.spg.processor;

import javax.tools.Diagnostic;

/**
 * Created by Dimitry Ivanov on 14.07.2015.
 */
public interface Logger {
    void log(Diagnostic.Kind level, String message, Object... args);
}
