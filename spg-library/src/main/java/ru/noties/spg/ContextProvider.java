package ru.noties.spg;

import android.content.Context;
import android.support.annotation.NonNull;

public interface ContextProvider {

    @NonNull
    Context provide();
}
