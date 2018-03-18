package ru.noties.spg.processor.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PreferenceDefaults {

    public final Map<KeyType, ru.noties.spg.processor.data.DefItem> defs;

    private PreferenceDefaults(Map<KeyType, ru.noties.spg.processor.data.DefItem> in) {
        defs = Collections.unmodifiableMap(in);
    }

    static class Builder {

        final Map<KeyType, ru.noties.spg.processor.data.DefItem> map = new HashMap<>();

        Builder add(KeyType type, ru.noties.spg.processor.data.DefItem item) {
            map.put(type, item);
            return this;
        }

        PreferenceDefaults build() {
            return new PreferenceDefaults(map);
        }
    }
}
