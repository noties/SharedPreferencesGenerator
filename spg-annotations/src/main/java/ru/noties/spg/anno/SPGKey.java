package ru.noties.spg.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for keys.
 * It's optional if none of it\'s values are set
 * Created by Dimitry Ivanov on 14.07.2015.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface SPGKey {

    // name for this key
    String name() default "";

    // default value for this key as a string
    // exp: "1", "false", "-1.F", "${calculate()}"
    String defaultValue() default "";

    // serializer for this key
    Class<?> serializer() default Void.class;

    // whether we should generate OnUpdateListener & setter
    boolean onUpdate() default false;
}
