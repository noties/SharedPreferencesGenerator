package ru.noties.spg;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Date;

import ru.noties.spg.sample.pref.DateSerializer;
import ru.noties.spg.sample.pref.OnUpdatePreference;
import ru.noties.spg.sample.pref.SerializationPreference;
import ru.noties.spg.sample.pref.WithDefaultsPreference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class AppTest {

    private Context context;

    @Before
    public void before() {
        context = RuntimeEnvironment.application;
        SPGManager.setContextProvider(new ContextProvider() {
            @NonNull
            @Override
            public Context provide() {
                return context;
            }
        });
    }

//    private void checkCreated() {
//        if (!mCreated) {
//            createApplication();
//            mCreated = true;
//        }
//
//        final Context context = new ContextAdapter(getContext()) {
//            @Override
//            public SharedPreferences getSharedPreferences(String name, int mode) {
//                final SharedPreferences sys = getDelegate().getSharedPreferences(name, mode);
//                return new SharedPreferencesMock(sys);
//            }
//        };
//        setContext(context);
//
//        final ContextProvider provider = new ContextProvider() {
//            @NonNull
//            @Override
//            public Context provide() {
//                return getContext();
//            }
//        };
//        SPGManager.setContextProvider(provider);
//        assertTrue(SPGManager.getContextProvider() != null);
//    }

    @Test
    public void testOnUpdate() {

        final OnUpdatePreference onUpdatePreference = new OnUpdatePreference(context);
        final ImmutableBool bool = new ImmutableBool();
        onUpdatePreference.setSomeBoolUpdateListener(new OnUpdateListener() {
            @Override
            public void onUpdate() {
                bool.setValue(true);
            }
        });
        onUpdatePreference.setSomeBool(true);

        assertTrue(bool.isValue());

        onUpdatePreference.setSomeBoolUpdateListener(null);
    }

    @Test
    public void testOnUpdateFromSys() {

        final OnUpdatePreference onUpdatePreference = new OnUpdatePreference(context);
        final SharedPreferences sysPref = onUpdatePreference.getSharedPreferences();
        final ImmutableBool bool = new ImmutableBool();
        onUpdatePreference.setSomeBoolUpdateListener(new OnUpdateListener() {
            @Override
            public void onUpdate() {
                bool.setValue(true);
            }
        });
        sysPref.edit().putBoolean(OnUpdatePreference.KEY_SOME_BOOL, true).apply();

        assertTrue(bool.isValue());
        onUpdatePreference.setSomeBoolUpdateListener(null);
    }

    @Test
    public void testSerialization() {

        final SerializationPreference preference = new SerializationPreference(context);
        final SharedPreferences sysPref = createSystemPrefs(preference.getSharedPreferencesName(), preference.getSharedPreferencesMode());

        final long someDate = 1000L;
        final DateSerializer dateSerializer = new DateSerializer();
        final Date date = dateSerializer.deserialize(someDate);

        preference.setFirstLaunch(date);

        assertEquals(someDate, sysPref.getLong(SerializationPreference.KEY_FIRST_LAUNCH, SerializationPreference.DEF_LONG));

        final Date fromPref = preference.getFirstLaunch();
        assertEquals(date, fromPref);
    }

    @Test
    public void testDefaults() {
        final WithDefaultsPreference preference = new WithDefaultsPreference(context);
        assertEquals(preference.getSomeString(), "no value");
    }

    @Test
    public void testSingleton() {
        final WithDefaultsPreference preference1 = WithDefaultsPreference.getInstance();
        final WithDefaultsPreference preference2 = WithDefaultsPreference.getInstance();
        assertEquals(preference1, preference2);
    }

    @Test
    public void testSetter() {

        final OnUpdatePreference preference = new OnUpdatePreference(context);
        final ImmutableBool bool = new ImmutableBool();
        final OnUpdateListener listener = new OnUpdateListener() {
            int received = 0;
            @Override
            public void onUpdate() {
                bool.setValue(++received == 1);
            }
        };
        preference.setSomeBoolUpdateListener(listener);
        preference.setter()
                .setSomeBool(true)
                .setSomeBool(false)
                .setSomeBool(true)
                .setSomeBool(true)
                .setSomeDate(null)
                .setSomeBool(false)
                .apply();

        assertTrue(bool.isValue());
        preference.setSomeBoolUpdateListener(null);
    }

    private SharedPreferences createSystemPrefs(String name, int mode) {
        return context.getSharedPreferences(name, mode);
    }

    private static class ImmutableBool {

        private boolean value;

        public void setValue(boolean value) {
            this.value = value;
        }

        public boolean isValue() {
            return value;
        }
    }
}
