package ru.noties.spg.sample.pref;

import java.util.Date;

import ru.noties.spg.anno.SPGKey;
import ru.noties.spg.anno.SPGPreference;
import ru.noties.spg.sample.BuildConfig;

/**
 * This class could be used to hide preferences description models
 * Created by Dimitry Ivanov on 14.07.2015.
 */
final class Preferences {

    // the simplest way to describe preference
    @SPGPreference
    private static class Simplest {
        String someString;
        long someLong;
        int someInt;
        boolean someBool;
    }

    // each key might have a default value represented as a string
    @SPGPreference(
            name = BuildConfig.BUILD_TYPE,
            isSingleton = true
    )
    private static class WithDefaults {

        @SPGKey(name = "other_name", defaultValue = "no value")
        String someString;

        @SPGKey(defaultValue = "true")
        boolean someBool;

        @SPGKey(defaultValue = "101")
        int someInt;

        @SPGKey(defaultValue = "66") // prefer 66L
        long someLong;

        @SPGKey(defaultValue = "-1") // prefer -1.F
        float someFloat;
    }

    // default values could be evaluated if they are wrapped into a `${...}`
    @SPGPreference(
            imports = { "ru.noties.spg.sample.BuildConfig", "java.util.UUID" }
    )
    private static class Evaluation {

        long firstStarted;
        long lastSessionDate;

        @SPGKey(name = "di", defaultValue = "${UUID.randomUUID().toString()}")
        String uuid;
    }

    // each key could be serialized & deserialized
    @SPGPreference
    private static class Serialization {

        @SPGKey(serializer = DateSerializer.class)
        Date firstLaunch;

        @SPGKey(serializer = DateSerializer.class)
        Date lastSession;

        @SPGKey(serializer = DateSerializer.class, defaultValue = "${System.currentTimeMillis()}")
        Date now;

        @SPGKey(serializer = DateJsonSerializer.class)
        Date jsonDate;
    }

    @SPGPreference
    private static class OnUpdate {

        @SPGKey(onUpdate = true)
        private int someInt;

        @SPGKey(onUpdate = true)
        private boolean someBool;

        @SPGKey(onUpdate = true, serializer = DateSerializer.class)
        private Date someDate;

        @SPGKey(onUpdate = true)
        private String someString;
    }
}
