package ru.noties.spg.sample.pref;

import java.util.Date;

/**
 * Created by Dimitry Ivanov on 16.07.2015.
 */
public class DateJsonSerializer extends GenericJsonSerializer<Date> {

    public DateJsonSerializer() {
        super(Date.class);
    }
}
