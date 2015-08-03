package ru.noties.spg.processor;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Dimitry Ivanov on 14.07.2015.
 */
public class StringWrapperTest {

//    @Test
//    public void emptyString() {
//        try {
//            StringWrapper wrapper = StringWrapper.create("");
//            assertTrue(wrapper.getString() == null);
//        } catch (StringWrapper.StringWrapperParseException e) {
//            assertTrue(false);
//        }
//    }
//
//    @Test
//    public void nullString() {
//        try {
//            StringWrapper wrapper = StringWrapper.create(null);
//            assertTrue(wrapper.getString() == null);
//        } catch (StringWrapper.StringWrapperParseException e) {
//            assertTrue(false);
//        }
//    }
//
//    @Test
//    public void defaultValue() {
//        try {
//            StringWrapper wrapper = StringWrapper.create(null);
//            String def = "som_def";
//            assertTrue(wrapper.getString(def).equals(def));
//        } catch (StringWrapper.StringWrapperParseException e) {
//            assertTrue(false);
//        }
//    }
//
//    @Test
//    public void illegalCharsTest() {
//        // should not fail on regular strings without evaluation
//        try {
//            for (char c: StringWrapper.ILLEGAL_CHARS) {
//                StringWrapper.create("" + c);
//            }
//            assertTrue(true);
//        } catch (StringWrapper.StringWrapperParseException e) {
//            assertTrue(false);
//        }
//    }
//
//    @Test
//    public void illegalStringWithEvaluation() {
//        try {
//            StringWrapper.create("${doIt();}");
//            assertTrue(false);
//        } catch (StringWrapper.StringWrapperParseException e) {
//            assertTrue(true);
//        }
//    }
//
//    @Test
//    public void normalString() {
//        try {
//            String str = "some_str";
//            StringWrapper wrapper = StringWrapper.create(str);
//            assertTrue(wrapper.getString().equals(str));
//        } catch (StringWrapper.StringWrapperParseException e) {
//            assertTrue(false);
//        }
//    }
//
//    @Test
//    public void normalStringWithEvaluation() {
//        try {
//            String str = "${evaluate()}";
//            StringWrapper wrapper = StringWrapper.create(str);
//            assertTrue(wrapper.getString().equals("evaluate()"));
//        } catch (StringWrapper.StringWrapperParseException e) {
//            assertTrue(false);
//        }
//    }
}
