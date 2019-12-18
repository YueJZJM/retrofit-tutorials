/*
 * Copyright (C) 2012 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bennyhuo.retrofit.tutorials.sample;

import com.bennyhuo.retrofit.tutorials.GitHub;
import com.bennyhuo.retrofit.tutorials.GitHub.Contributor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import okhttp3.logging.HttpLoggingInterceptor.Logger;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.io.IOException;
import java.util.List;

public final class SimpleService {
    public static final String API_URL = "https://api.github.com";

    public static void main(String... args) throws IOException {
        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Builder()
                .baseUrl(API_URL)
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new HttpLoggingInterceptor(System.out::println).setLevel(Level.BODY))
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        // Create an instance of our GitHub API interface.
        GitHub github = retrofit.create(GitHub.class);
        //getContributors(github);
        getRepository2(github);
    }

    private static void getRepository(GitHub gitHub){
        gitHub.repo("square", "retrofit")
                .subscribe(System.out::println);
    }

    private static void getRepository2(GitHub gitHub){
        gitHub.repo2("square", "retrofit")
                .subscribe(response -> {
                    System.out.println(response.body());
                });
    }


    private static void getContributors(GitHub github) throws IOException {
        // Create a call instance for looking up Retrofit contributors.
        Call<List<Contributor>> call = github.contributors("square", "retrofit");

        // Fetch and print a list of the contributors to the library.
        List<Contributor> contributors = call.execute().body();
        for (Contributor contributor : contributors) {
            System.out.println(contributor.login + " (" + contributor.contributions + ")");
        }
    }
}
