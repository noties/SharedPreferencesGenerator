package ru.noties.spg;

import android.content.Context;

import androidx.annotation.NonNull;

public interface ContextProvider {

    @NonNull
    Context provide();
}
