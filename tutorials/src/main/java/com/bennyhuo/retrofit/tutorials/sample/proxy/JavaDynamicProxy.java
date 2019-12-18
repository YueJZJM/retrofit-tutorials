package com.bennyhuo.retrofit.tutorials.sample.proxy;

import com.bennyhuo.retrofit.tutorials.GitHub;
import kotlin.io.FilesKt;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

public class JavaDynamicProxy {
    public static void main(String... args) {
        writeProxyToFile();
        //testAbstractClass(); // ERROR
    }

    private static void testAbstractClass(){
        Object proxy = Proxy.newProxyInstance(JavaDynamicProxy.class.getClassLoader(),
                new Class[]{SuperClass.class, GitHub.class}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return null;
                    }
                });
    }

    private static void writeProxyToFile() {
        byte[] proxyClassFile = ProxyGenerator.generateProxyClass(
                "MyProxy", new Class[]{SuperClass.class, GitHub.class}, Modifier.FINAL | Modifier.PUBLIC);
        FilesKt.writeBytes(new File("MyProxy.class"), proxyClassFile);
    }
}
