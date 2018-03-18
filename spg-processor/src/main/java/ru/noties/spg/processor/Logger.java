package ru.noties.spg.processor;

import javax.tools.Diagnostic;

public interface Logger {
    void log(Diagnostic.Kind level, String message, Object... args);
}
