package com.bennyhuo.retrofit.tutorials.sample.generic;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GenericType {

    static class SuperType<T>{

    }

    static class SubType extends SuperType<String>{

    }

    public static void main(String... args) throws Exception {
        Method method = GenericType.class.getDeclaredMethod("test", Map.class);
        Type type = method.getGenericReturnType();
        recursivelyCheckType(type);

        for (Type genericParameterType : method.getGenericParameterTypes()) {
            recursivelyCheckType(genericParameterType);
        }

        Field signatureField = Method.class.getDeclaredField("signature");
        signatureField.setAccessible(true);
        System.out.println(signatureField.get(method));

        Type superType = SubType.class.getGenericSuperclass();
        recursivelyCheckType(superType);
    }

    /**
     * Map test(Map map)
     * @param map
     * @return
     */
    public static Map<List<String>, Set<Map<Number, String>>> test(Map<String, Set<Map<Number, String>>> map){
        return null;
    }

    public static void recursivelyCheckType(Type type){
        if(type instanceof ParameterizedType){
            System.out.println("ParameterizedType: " + type);
            for (Type actualTypeArgument : ((ParameterizedType) type).getActualTypeArguments()) {
                recursivelyCheckType(actualTypeArgument);
            }
        } else {
            System.out.println("Type: " + type);
        }
    }

}
