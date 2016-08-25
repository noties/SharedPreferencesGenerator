package ru.noties.spg;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Dimitry Ivanov on 14.07.2015.
 */
public class SPGManager {

    private static volatile SPGManager sInstance = null;

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

    public static void setContextProvider(ContextProvider contextProvider) {
        SPGManager.getInstance().mContextProvider = contextProvider;
    }

    public static ContextProvider getContextProvider() {
        return SPGManager.getInstance().mContextProvider;
    }

    public static <S extends SPGSerializer<?, ?>> S getSerializer(Class<S> c) {
        final Map map = SPGManager.getInstance().mSerializers;
        //noinspection unchecked
        return (S) map.get(c);
    }

    public static <S extends SPGSerializer<?, ?>> void addSerializer(S serializer) {
        final Map map = SPGManager.getInstance().mSerializers;
        //noinspection unchecked
        map.put(serializer.getClass(), serializer);
    }
}
