package ru.noties.spg.processor.data;

import javax.lang.model.element.Element;

/**
 * Created by Dimitry Ivanov on 15.07.2015.
 */
public class KeyHolder {

    public final Element element;
    public final String fieldName;
    public final String name;
    public final KeyType keyType;
    public final ru.noties.spg.processor.data.DefItem defItem;
    public final SerializerHolder serializer;
    public final boolean onUpdate;

    KeyHolder(
            Element element,
            String fieldName,
            String name,
            KeyType keyType,
            ru.noties.spg.processor.data.DefItem defItem,
            SerializerHolder serializer,
            boolean onUpdate
    ) {
        this.element = element;
        this.fieldName = fieldName;
        this.name = name;
        this.keyType = keyType;
        this.defItem = defItem;
        this.serializer = serializer;
        this.onUpdate = onUpdate;
    }

    @Override
    public String toString() {
        return "KeyHolder{" +
                "element=" + element +
                ", fieldName='" + fieldName + '\'' +
                ", name='" + name + '\'' +
                ", keyType=" + keyType +
                ", defItem=" + defItem +
                ", serializer=" + serializer +
                ", onUpdate=" + onUpdate +
                '}';
    }
}
