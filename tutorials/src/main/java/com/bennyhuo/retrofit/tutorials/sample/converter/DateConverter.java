package com.bennyhuo.retrofit.tutorials.sample.converter;

import retrofit2.Converter;
import retrofit2.Retrofit;

import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter implements Converter<Date, String> {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd_HHmmss");
    @Nullable
    @Override
    public String convert(Date value) throws IOException {
        return SIMPLE_DATE_FORMAT.format(value);
    }

    public static class DateConverterFactory extends Factory {
        @Nullable
        @Override
        public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            if(type == Date.class){
                return new DateConverter();
            }
            return super.stringConverter(type, annotations, retrofit);
        }

        public static Factory create(){
            return new DateConverterFactory();
        }
    }
}
