package ru.noties.spg.sample.pref;

import ru.noties.spg.SPGSerializer;

/**
 * Created by Dimitry Ivanov on 16.07.2015.
 */
public abstract class GenericJsonSerializer<T> implements SPGSerializer<T, String> {

    final Class<T> mType;

    public GenericJsonSerializer(Class<T> type) {
        this.mType = type;
    }

    @Override
    public T deserialize(String s) {
        return null;
    }

    @Override
    public String serialize(T t) {
        return null;
    }
}
