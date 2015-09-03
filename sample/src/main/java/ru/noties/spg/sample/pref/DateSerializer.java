package ru.noties.spg.sample.pref;

import java.util.Date;

import ru.noties.spg.SPGSerializer;

/**
 * Created by Dimitry Ivanov on 14.07.2015.
 */
public class DateSerializer implements SPGSerializer<Date, Long> {

    private final long NO_DATE = -1L;

    @Override
    public Date deserialize(Long aLong) {
        final long date = aLong;
        if (date == NO_DATE) {
            return null;
        }
        return new Date(date);
    }

    @Override
    public Long serialize(Date date) {
        if (date == null) {
            return NO_DATE;
        }
        return date.getTime();
    }
}
