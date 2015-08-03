package ru.noties.spg.sample.pref;

import java.util.Date;

import ru.noties.spg.SPGSerializer;

/**
 * Created by Dimitry Ivanov on 14.07.2015.
 */
public class DateSerializer implements SPGSerializer<Date, Long> {

    @Override
    public Date deserialize(Long aLong) {
        return new Date(aLong);
    }

    @Override
    public Long serialize(Date date) {
        return date.getTime();
    }
}
