package com.bennyhuo.retrofit.tutorials.sample.deferred

import com.bennyhuo.retrofit.tutorials.GitHubKt
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_URL = "https://api.github.com"

suspend fun main() {
    // Create a very simple REST adapter which points the GitHub API.
    val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(DeferredCallAdapterFactory.create())
            .build()

    // Create an instance of our GitHub API interface.
    val github = retrofit.create(GitHubKt::class.java)

    // Create a call instance for looking up Retrofit contributors.
    val call = github.contributors("square", "retrofit")

    // Fetch and print a list of the contributors to the library.
    GlobalScope.launch {
        val contributors = call.await()
        contributors.forEach { contributor ->
            println("""${contributor.login} (${contributor.contributions})""")
        }
    }.join()
}

