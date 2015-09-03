package ru.noties.spg.processor.data;

/**
 * Created by Dimitry Ivanov on 15.07.2015.
 */
public class SerializerHolder {

    public final String name;
    public final KeyType keyType;

    SerializerHolder(String name, KeyType keyType) {
        this.name = name;
        this.keyType = keyType;
    }

    @Override
    public String toString() {
        return "SerializerHolder{" +
                "name='" + name + '\'' +
                ", keyType=" + keyType +
                '}';
    }
}
