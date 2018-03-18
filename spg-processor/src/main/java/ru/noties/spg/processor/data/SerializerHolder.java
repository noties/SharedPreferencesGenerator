package ru.noties.spg.processor.data;

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
