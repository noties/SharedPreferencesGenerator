package ru.noties.spg.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import ru.noties.spg.processor.data.SPGPreferenceParser;
import ru.noties.spg.processor.writer.SPGPreferenceWriter;

public class SPGProcessor extends AbstractProcessor implements Logger {

    private Types mTypes;
    private Elements mElements;
    private Filer mFiler;
    private Messager mMessager;

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(ru.noties.spg.anno.SPGPreference.class.getCanonicalName());
    }

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        mTypes = env.getTypeUtils();
        mElements = env.getElementUtils();
        mFiler = env.getFiler();
        mMessager = env.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        long start = -1L;
        try {
            for (TypeElement element : annotations) {
                if (start == -1L) {
                    start = System.currentTimeMillis();
                }
                processInner(roundEnv.getElementsAnnotatedWith(element));
            }
            return true;
        } catch (Throwable t) {
            t.printStackTrace();
            log(Diagnostic.Kind.ERROR, "Exception during code generation: " + t);
        } finally {
            if (start != -1L) {
                final long end = System.currentTimeMillis();
                log(Diagnostic.Kind.NOTE, "Processing of @SPGPreference took: %d ms", (end - start));
            }
        }
        return false;
    }

    private boolean processInner(Set<? extends Element> preferences) throws Throwable {
        final List<ru.noties.spg.processor.data.PreferenceHolder> list = new ArrayList<>();
        final SPGPreferenceParser parser = new SPGPreferenceParser(this, mTypes, mElements);
        ru.noties.spg.processor.data.PreferenceHolder holder;
        for (Element element : preferences) {
            holder = parser.parse((TypeElement) element);
            if (holder != null) {
                list.add(holder);
            }
        }

        if (list.size() > 0) {
            final SPGPreferenceWriter writer = new SPGPreferenceWriter(this, mElements, mFiler);
            for (ru.noties.spg.processor.data.PreferenceHolder ph : list) {
                writer.write(ph);
            }
        }

        return true;
    }

    @Override
    public void log(Diagnostic.Kind level, String message, Object... args) {
        if (args != null
                && args.length > 0) {
            mMessager.printMessage(level, String.format(message, args));
            return;
        }

        mMessager.printMessage(level, message);
    }
}
