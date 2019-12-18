package com.bennyhuo.retrofit.tutorials;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface GitHub {
    @GET("/repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> contributors(
            @Path("owner") String owner,
            @Path("repo") String repo);

    @GET("/repos/{owner}/{repo}")
    Observable<Repository> repo(
            @Path("owner") String owner,
            @Path("repo") String repo);

    @GET("/repos/{owner}/{repo}")
    Observable<Response<Repository>> repo2(
            @Path("owner") String owner,
            @Path("repo") String repo);

    class Contributor {
        public final String login;
        public final int contributions;

        public Contributor(String login, int contributions) {
            this.login = login;
            this.contributions = contributions;
        }
    }

    class Repository {
        public final int id;
        public final String name;
        public final String html_url;

        public Repository(int id, String name, String html_url) {
            this.id = id;
            this.name = name;
            this.html_url = html_url;
        }

        @Override
        public String toString() {
            return "Repository{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", html_url='" + html_url + '\'' +
                    '}';
        }
    }
}