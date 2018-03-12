package ru.noties.spg.processor.data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import ru.noties.spg.processor.util.TextUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Dimitry Ivanov on 15.07.2015.
 */
public class SPGPreferenceParser implements ru.noties.spg.processor.Logger {

    private static final String VOID = Void.class.getCanonicalName();
    private static final Pattern SERIALIZER_TYPES = Pattern.compile(
            ".+<(.+),\\s*(.+)>"
    );
    private static final String NULL = "null";

    private final ru.noties.spg.processor.Logger mLogger;
    private final Types mTypes;
    private final Elements mElements;

    public SPGPreferenceParser(ru.noties.spg.processor.Logger logger, Types types, Elements elements) {
        this.mLogger = logger;
        this.mTypes = types;
        this.mElements = elements;
    }

    public PreferenceHolder parse(TypeElement element) {

        log(Diagnostic.Kind.NOTE, "Processing @SPGPreference: " + element.getSimpleName());

        final List<? extends Element> enclosed = element.getEnclosedElements();
        final List<Element> keys = new ArrayList<>();

        for (Element e: enclosed) {
            if (e.getKind() == ElementKind.FIELD) {
                keys.add(e);
            }
        }

        if (keys.size() == 0) {
            log(Diagnostic.Kind.WARNING, "@SPGPreference: %s has no fields", element.getSimpleName());
            return null;
        }

        final ru.noties.spg.anno.SPGPreference preference = element.getAnnotation(ru.noties.spg.anno.SPGPreference.class);
        final String prefName;
        if (!TextUtils.isEmpty(preference.name())) {
            prefName = preference.name();
        } else {
            prefName = element.getSimpleName().toString();
        }
        final boolean prefDefaultName = preference.defaultName();
        final int prefMode = preference.sharedPreferenceMode();
        final boolean isSingleton = preference.isSingleton();
        final String[] importsArray = preference.imports();
        final List<String> imports;
        if (importsArray != null
                && importsArray.length > 0) {
            imports = new ArrayList<>();
            for (String s: importsArray) {
                imports.add(s);
            }
        } else {
            imports = null;
        }

        final List<KeyHolder> keyHolders    = parseKeys(keys);
        final PreferenceDefaults defaults   = parseDefaults(preference);
        final boolean toEntity              = preference.toEntity();

        return new PreferenceHolder(
                element,
                prefName,
                prefDefaultName,
                prefMode,
                imports,
                isSingleton,
                keyHolders,
                defaults,
                toEntity
        );
    }

    private List<KeyHolder> parseKeys(List<? extends Element> enclosed) {
        final List<KeyHolder> keyHolders = new ArrayList<>();
        for (Element e: enclosed) {

            if (e.getModifiers().contains(Modifier.STATIC)) {
                continue;
            }

            final ru.noties.spg.anno.SPGKey key = e.getAnnotation(ru.noties.spg.anno.SPGKey.class);

            final String name;
            final ru.noties.spg.processor.data.DefItem defItem;
            final ru.noties.spg.processor.data.KeyType keyType = ru.noties.spg.processor.data.KeyType.parse(e.asType());
            final SerializerHolder serializer;
            final boolean onUpdate;

            if (key == null) {
                name = e.getSimpleName().toString();
                defItem = null;
                serializer = null;
                onUpdate = false;
            } else {
                if (!TextUtils.isEmpty(key.name())) {
                    name = key.name();
                } else {
                    name = e.getSimpleName().toString();
                }
                serializer = parseSerializer(e, key);
                final String keyDefValue = key.defaultValue();
                if (!TextUtils.isEmpty(keyDefValue)) {
                    if (serializer == null) {
                        // create simple defItem
                        defItem = parseDefaultValue(keyType, key.defaultValue());
                    } else {
                        // check against key type
                        defItem = parseDefaultValue(serializer.keyType, key.defaultValue());
                    }
                } else {
                    defItem = null;
                }
                onUpdate = key.onUpdate();
            }

            if (keyType == null
                    && serializer == null) {
                log(Diagnostic.Kind.ERROR, "Specified type is not natively " +
                        "supported by SharedPreferences: %s," +
                        "enclosed element: %s",
                        e.asType(), e.getEnclosingElement()
                );
                return null;
            }

            keyHolders.add(
                    new KeyHolder(
                            e,
                            e.toString(),
                            name,
                            keyType,
                            defItem,
                            serializer,
                            onUpdate
                    )
            );
        }
        if (keyHolders.size() > 0) {
            return keyHolders;
        }
        return null;
    }

    private SerializerHolder parseSerializer(Element element, ru.noties.spg.anno.SPGKey key) {
        SerializerHolder holder;
        try {
            // do class handling
            holder = parseSerializerClass(key.serializer());
        } catch (MirroredTypeException e) {
            // do TypeMirror handling
            holder = parseSerializerTypeMirror(element, e.getTypeMirror());
        }
        return holder;
    }

    private SerializerHolder parseSerializerClass(Class<?> c) {
        if (c == Void.class) {
            return null;
        }
        throw new NotImplementedException();
    }

    private SerializerHolder parseSerializerTypeMirror(Element element, TypeMirror typeMirror) {

        final String s = typeMirror.toString();
        if (VOID.equals(s)) {
            return null;
        }

        final TypeMirror spgInterface = extractSPGSerializerInterface(typeMirror);

        if (spgInterface == null) {
            log(Diagnostic.Kind.ERROR, "Serializer `%s` does not implement SPGSerializer", typeMirror);
            return null;
        }

        final Matcher matcher = SERIALIZER_TYPES.matcher(spgInterface.toString());
        if (!matcher.matches()) {
            log(Diagnostic.Kind.ERROR, "Internal error, could not parse types of: %s", spgInterface);
            return null;
        }

        final String type = matcher.group(1);
        final String repr = matcher.group(2);

        // check against declared type
        // check against representation type
        final TypeElement reprTypeElement = mElements.getTypeElement(repr);
        if (reprTypeElement == null) {
            // could not obtain representation Type. Assume that it's a generic value,
            // we cannot process this
            log(Diagnostic.Kind.ERROR, "Serializer `%s` contains representation type parameter " +
                    "as a generic. Type: %s, interface: %s", typeMirror, spgInterface);
            return null;
        }

        final TypeMirror reprMirror = reprTypeElement.asType();
        final ru.noties.spg.processor.data.KeyType reprKeyType = ru.noties.spg.processor.data.KeyType.parse(reprMirror);
        if (reprKeyType == null) {
            log(Diagnostic.Kind.ERROR, "Representation type for StormSerializer is not supported: %s", reprMirror);
            return null;
        }

        final TypeElement serializerType = mElements.getTypeElement(type);
        if (serializerType == null) {
            // well, assume that it's a generic
            // we cannot check now for it's value
        } else {
            final TypeMirror serializerTypeMirror = serializerType.asType();
            if (!serializerTypeMirror.equals(element.asType())) {
                log(Diagnostic.Kind.ERROR, "Type for StormSerializer is wrong: %s, should be: %s", serializerTypeMirror, element.asType());
                return null;
            }
        }

        return new SerializerHolder(s, reprKeyType);
    }

    private TypeMirror extractSPGSerializerInterface(TypeMirror typeMirror) {

        final String object = "java.lang.Object";
        TypeElement element = (TypeElement) mTypes.asElement(typeMirror);

        if (!element.getSuperclass().toString().equals(object)) {
            TypeMirror si;
            while (!element.getSuperclass().toString().equals(object)) {
                si = extractSPGSerializerInterface(element);
                if (si != null) {
                    return si;
                }
                element = (TypeElement) mTypes.asElement(element.getSuperclass());
            }
        }

        return extractSPGSerializerInterface(element);
    }

    private TypeMirror extractSPGSerializerInterface(TypeElement element) {
        final List<? extends TypeMirror> interfaces = element.getInterfaces();
        for (TypeMirror tp: interfaces) {
            if (tp.toString().startsWith("ru.noties.spg.SPGSerializer")) {
                return tp;
            }
        }
        return null;
    }

    private PreferenceDefaults parseDefaults(ru.noties.spg.anno.SPGPreference p) {
        return new PreferenceDefaults.Builder()
                .add(ru.noties.spg.processor.data.KeyType.BOOL,      parseDefaultValue(ru.noties.spg.processor.data.KeyType.BOOL,     p.defBool()))
                .add(ru.noties.spg.processor.data.KeyType.INT,       parseDefaultValue(ru.noties.spg.processor.data.KeyType.INT,      p.defInt()))
                .add(ru.noties.spg.processor.data.KeyType.LONG,      parseDefaultValue(ru.noties.spg.processor.data.KeyType.LONG,     p.defLong()))
                .add(ru.noties.spg.processor.data.KeyType.FLOAT,     parseDefaultValue(ru.noties.spg.processor.data.KeyType.FLOAT,    p.defFloat()))
                .add(ru.noties.spg.processor.data.KeyType.STRING,    parseDefaultValue(ru.noties.spg.processor.data.KeyType.STRING,   p.defString()))
                .build();
    }

    private static ru.noties.spg.processor.data.DefItem parseDefaultValue(ru.noties.spg.processor.data.KeyType type, String pValue) {

        if (type == ru.noties.spg.processor.data.KeyType.STRING && NULL.equals(pValue)) {
            return new ru.noties.spg.processor.data.DefItem(null, true);
        }

        if (ru.noties.spg.processor.StringParser.isEvaluationString(pValue)) {
            return new ru.noties.spg.processor.data.DefItem(ru.noties.spg.processor.StringParser.extractEvaluation(pValue), true);
        }
        return new ru.noties.spg.processor.data.DefItem(pValue, false);
    }

    @Override
    public void log(Diagnostic.Kind level, String message, Object... args) {
        mLogger.log(level, message, args);
    }
}
