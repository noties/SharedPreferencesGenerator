package ru.noties.spg.processor.data;

import java.util.List;

import javax.lang.model.element.TypeElement;

public class PreferenceHolder {

    public final TypeElement typeElement;
    public final String name;
    public final boolean defaultName;
    public final int preferenceMode;
    public final List<String> imports;
    public final boolean isSingleton;
    public final List<KeyHolder> keys;
    public final PreferenceDefaults defaults;
    public final boolean toEntity;

    PreferenceHolder(
            TypeElement typeElement,
            String name,
            boolean defaultName,
            int preferenceMode,
            List<String> imports,
            boolean isSingleton,
            List<KeyHolder> keys,
            PreferenceDefaults defaults,
            boolean toEntity
    ) {
        this.typeElement = typeElement;
        this.name = name;
        this.defaultName = defaultName;
        this.preferenceMode = preferenceMode;
        this.imports = imports;
        this.isSingleton = isSingleton;
        this.keys = keys;
        this.defaults = defaults;
        this.toEntity = toEntity;
    }

    @Override
    public String toString() {
        return "PreferenceHolder{" +
                "typeElement=" + typeElement +
                ", name='" + name + '\'' +
                ", defaultName=" + defaultName +
                ", preferenceMode=" + preferenceMode +
                ", imports=" + imports +
                ", isSingleton=" + isSingleton +
                ", keys=" + keys +
                ", defaults=" + defaults +
                ", toEntity=" + toEntity +
                '}';
    }
}
