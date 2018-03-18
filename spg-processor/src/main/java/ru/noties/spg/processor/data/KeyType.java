package ru.noties.spg.processor.data;

import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

public enum KeyType {

    BOOL("java.lang.Boolean", "boolean"),
    INT("java.lang.Integer", "int"),
    LONG("java.lang.Long", "long"),
    FLOAT("java.lang.Float", "float"),
    STRING("java.lang.String", "String");

    private final String value;
    private final String repr;

    KeyType(String value, String repr) {
        this.value = value;
        this.repr = repr;
    }

    public String getRepr() {
        return repr;
    }

    public static KeyType parse(TypeMirror mirror) {

        final TypeKind kind = mirror.getKind();

        if (kind.isPrimitive()) {
            switch (kind) {
                case BOOLEAN:
                    return BOOL;
                case INT:
                    return INT;
                case LONG:
                    return LONG;
                case FLOAT:
                    return FLOAT;
            }
            return null;
        }

        if (kind != TypeKind.DECLARED) {
            return null;
        }

        final String s = mirror.toString();

        for (KeyType type : KeyType.values()) {
            if (type.value.equals(s)) {
                return type;
            }
        }

        return null;
    }
}
