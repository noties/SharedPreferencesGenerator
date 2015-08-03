package ru.noties.spg;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
        mSerializers = Collections.synchronizedMap(new HashMap<Class<?>, SPGSerializer<?, ?>>());
    }

    public static void setContextProvider(ContextProvider contextProvider) {
        SPGManager.getInstance().mContextProvider = contextProvider;
    }

    public static ContextProvider getContextProvider() {
        return SPGManager.getInstance().mContextProvider;
    }

    public static <T, S extends SPGSerializer<T, ?>> S getSerializer(Class<T> c) {
        final Map map = SPGManager.getInstance().mSerializers;
        //noinspection unchecked
        return (S) map.get(c);
    }

    public static <T, S extends SPGSerializer<T, ?>> void addSerializer(Class<T> c, S serializer) {
        final Map map = SPGManager.getInstance().mSerializers;
        //noinspection unchecked
        map.put(c, serializer);
    }
}
