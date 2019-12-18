package com.bennyhuo.retrofit.tutorials.sample.baseurl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ConstTest {
    private final int a = 1;
    private int b = 1;

    private static final int c = 1;

    public static void main(String... args) throws Exception {
        accessToFinalInt();
        accessToStaticFinalInt();
    }

    private static void accessToStaticFinalInt() throws NoSuchFieldException, IllegalAccessException {
        System.out.println(ConstTest.c);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);

        Field cField = ConstTest.class.getDeclaredField("c");
        modifiersField.setInt(cField, modifiersField.getInt(cField) ^ Modifier.FINAL);

        cField.setAccessible(true);
        cField.set(null, 2);
        System.out.println(ConstTest.c);
    }


    private static void accessToFinalInt() throws NoSuchFieldException, IllegalAccessException {
        ConstTest constTest = new ConstTest();
        System.out.println(constTest.a);
        System.out.println(constTest.b);
        Field aField = ConstTest.class.getDeclaredField("a");
        aField.setAccessible(true);
        aField.set(constTest, 2);
        System.out.println(constTest.a);
        System.out.println(constTest.b);
    }
}
