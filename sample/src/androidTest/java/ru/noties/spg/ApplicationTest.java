package ru.noties.spg;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import android.test.ApplicationTestCase;

import java.util.Date;

import ru.noties.spg.sample.pref.DateSerializer;
import ru.noties.spg.sample.pref.OnUpdatePreference;
import ru.noties.spg.sample.pref.SerializationPreference;
import ru.noties.spg.sample.pref.WithDefaultsPreference;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    private boolean mCreated;

    public ApplicationTest() {
        super(Application.class);
    }

    private void checkCreated() {
        if (!mCreated) {
            createApplication();
            mCreated = true;
        }

        final Context context = new ContextAdapter(getContext()) {
            @Override
            public SharedPreferences getSharedPreferences(String name, int mode) {
                final SharedPreferences sys = getDelegate().getSharedPreferences(name, mode);
                return new SharedPreferencesMock(sys);
            }
        };
        setContext(context);

        final ContextProvider provider = new ContextProvider() {
            @NonNull
            @Override
            public Context provide() {
                return getContext();
            }
        };
        SPGManager.setContextProvider(provider);
        assertTrue(SPGManager.getContextProvider() != null);
    }

    public void testOnUpdate() throws Throwable {
        checkCreated();
        final OnUpdatePreference onUpdatePreference = new OnUpdatePreference(getContext());
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
        setContext(((ContextAdapter) getContext()).getDelegate());
    }


    public void testOnUpdateFromSys() throws Throwable {
        checkCreated();
        final OnUpdatePreference onUpdatePreference = new OnUpdatePreference(getContext());
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
        setContext(((ContextAdapter) getContext()).getDelegate());
    }

    public void testSerialization() {

        final SerializationPreference preference = new SerializationPreference(getContext());
        final SharedPreferences sysPref = createSystemPrefs(preference.getSharedPreferencesName(), preference.getSharedPreferencesMode());

        final long someDate = 1000L;
        final DateSerializer dateSerializer = new DateSerializer();
        final Date date = dateSerializer.deserialize(someDate);

        preference.setFirstLaunch(date);

        assertEquals(someDate, sysPref.getLong(SerializationPreference.KEY_FIRST_LAUNCH, SerializationPreference.DEF_LONG));

        final Date fromPref = preference.getFirstLaunch();
        assertEquals(date, fromPref);
    }

    public void testDefaults() {
        final WithDefaultsPreference preference = new WithDefaultsPreference(getContext());
        assertEquals(preference.getSomeString(), "no value");
    }

    public void testSingleton() {
        final WithDefaultsPreference preference1 = WithDefaultsPreference.getInstance();
        final WithDefaultsPreference preference2 = WithDefaultsPreference.getInstance();
        assertEquals(preference1, preference2);
    }

    public void testSetter() throws Throwable {
        checkCreated();
        final OnUpdatePreference preference = new OnUpdatePreference(getContext());
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
        setContext(((ContextAdapter) getContext()).getDelegate());
    }

    private SharedPreferences createSystemPrefs(String name, int mode) {
        return getContext().getSharedPreferences(name, mode);
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