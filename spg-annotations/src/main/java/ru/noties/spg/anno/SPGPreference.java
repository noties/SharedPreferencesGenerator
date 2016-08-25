package ru.noties.spg.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to describe preference.
 * Based on annotated class will be generate a helper class
 * All parameters are optional
 * @see SPGKey
 * Created by Dimitry Ivanov on 14.07.2015.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface SPGPreference {

    String name() default "";
    int sharedPreferenceMode() default 0;
    String[] imports() default {};
    boolean isSingleton() default false;

    String defBool() default "false";
    String defInt() default "-1";
    String defLong() default "-1L";
    String defFloat() default ".0F";
    String defString() default "null";

}
