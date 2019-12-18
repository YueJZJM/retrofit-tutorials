package com.bennyhuo.retrofit.tutorials

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubKt {
    @GET("/repos/{owner}/{repo}/contributors")
    fun contributors(
            @Path("owner") owner: String,
            @Path("repo") repo: String): Deferred<List<Contributor>>

    @GET("/repos/{owner}/{repo}/contributors")
    suspend fun contributorsSuspend(
            @Path("owner") owner: String,
            @Path("repo") repo: String): List<Contributor>

    class Contributor(val login: String, val contributions: Int)
}