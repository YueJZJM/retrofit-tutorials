package com.bennyhuo.retrofit.tutorials.sample.proxy;

import com.bennyhuo.retrofit.tutorials.GitHub;
import kotlin.io.FilesKt;
import net.sf.cglib.core.*;
import net.sf.cglib.proxy.*;

import java.io.File;
import java.lang.reflect.Method;

public class CglibSample {
    public static void main(String... args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SuperClass.class);
        enhancer.setInterfaces(new Class[]{GitHub.class, Runnable.class});

//        enhancer.setCallback(new InvocationHandler() {
//            private SuperClass superClass = new SuperClass("proxy");
//
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                System.out.println("before");
//                try {
//                    Method method1 = SuperClass.class.getMethod(method.getName(), method.getParameterTypes());
//                    return method1.invoke(superClass, args);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    return null;
//                } finally {
//                    System.out.println("after");
//                }
//            }
//        });

        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                return proxy.invokeSuper(obj, args);
            }
        });

        class MyStrategyNamingPolicy implements GeneratorStrategy, NamingPolicy{
            private GeneratorStrategy generatorStrategy = new DefaultGeneratorStrategy();
            private NamingPolicy namingPolicy = new DefaultNamingPolicy();

            private String className = null;
            @Override
            public byte[] generate(ClassGenerator cg) throws Exception {
                byte[] bytes = generatorStrategy.generate(cg);
                FilesKt.writeBytes(new File(className + ".class"), bytes);
                return bytes;
            }

            @Override
            public String getClassName(String prefix, String source, Object key, Predicate names) {
                className = namingPolicy.getClassName(prefix, source, key, names);
                return className;
            }
        }

        MyStrategyNamingPolicy myStrategyNamingPolicy = new MyStrategyNamingPolicy();
        enhancer.setStrategy(myStrategyNamingPolicy);
        enhancer.setNamingPolicy(myStrategyNamingPolicy);
        Object object = enhancer.create();
        System.out.println(object.toString());
        ((SuperClass)object).hello();
        Object o = ((GitHub)object).contributors("", "");
    }
}
