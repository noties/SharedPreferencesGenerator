package ru.noties.spg;

/**
 * Created by Dimitry Ivanov on 14.07.2015.
 */
public interface SPGSerializer<TYPE, REPRESENTATION> {
    TYPE deserialize(REPRESENTATION representation);
    REPRESENTATION serialize(TYPE type);
}
