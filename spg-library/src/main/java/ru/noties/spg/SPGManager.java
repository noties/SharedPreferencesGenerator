package ru.noties.spg;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("WeakerAccess")
public class SPGManager {

    private static volatile SPGManager sInstance = null;

    @NonNull
    public static SPGManager getInstance() {
        SPGManager local = sInstance;
        if (local == null) {
            synchronized (SPGManager.class) {
                local = sInstance;
                if (local == null) {
                    local = sInstance = new SPGManager();
                }
            }
        }
        return local;
    }

    private final Map<Class<?>, SPGSerializer<?, ?>> mSerializers;
    private ContextProvider mContextProvider;

    private SPGManager() {
        mSerializers = new ConcurrentHashMap<>();
    }

    public static void setContextProvider(@Nullable ContextProvider contextProvider) {
        SPGManager.getInstance().mContextProvider = contextProvider;
    }

    @Nullable
    public static ContextProvider getContextProvider() {
        return SPGManager.getInstance().mContextProvider;
    }

    @Nullable
    public static <S extends SPGSerializer<?, ?>> S getSerializer(@NonNull Class<S> c) {
        final Map map = SPGManager.getInstance().mSerializers;
        //noinspection unchecked
        return (S) map.get(c);
    }

    public static <S extends SPGSerializer<?, ?>> void addSerializer(@NonNull S serializer) {
        final Map map = SPGManager.getInstance().mSerializers;
        //noinspection unchecked
        map.put(serializer.getClass(), serializer);
    }
}
