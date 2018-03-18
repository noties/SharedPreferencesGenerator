package ru.noties.spg;

public interface SPGSerializer<TYPE, REPRESENTATION> {

    TYPE deserialize(REPRESENTATION representation);

    REPRESENTATION serialize(TYPE type);
}
