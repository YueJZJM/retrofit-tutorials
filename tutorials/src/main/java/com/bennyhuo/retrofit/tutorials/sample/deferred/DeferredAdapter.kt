package com.bennyhuo.retrofit.tutorials.sample.deferred

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class DeferredCallAdapterFactory private constructor(): CallAdapter.Factory() {
    companion object {
        @JvmStatic
        fun create() = DeferredCallAdapterFactory()
    }

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        if(getRawType(returnType) != Deferred::class.java){
            return null
        }
        if(returnType !is ParameterizedType){
            throw IllegalStateException("Deferred return type must be parameterized" + " as Deferred<Foo> or Deferred<? extends Foo>")
        }
        val innerType = getParameterUpperBound(0, returnType)
        if (getRawType(innerType) != Response::class.java){
            return BodyDeferredAdapter<Any>(innerType)
        }
        if(innerType !is ParameterizedType){
            throw IllegalStateException("Response must be parameterized" + " as Response<Foo> or Response<? extends Foo>")
        }
        val responseType = getParameterUpperBound(0, innerType)
        return ResponseDeferredAdapter<Any>(responseType)
    }
}

class ResponseDeferredAdapter<R>(val responseType: Type): CallAdapter<R, Deferred<Response<R>>>{
    override fun responseType() = responseType

    override fun adapt(call: Call<R>): Deferred<Response<R>> {
        val completableDeferred = CompletableDeferred<Response<R>>()
        call.enqueue(object: Callback<R>{
            override fun onResponse(call: Call<R>, response: Response<R>) {
                completableDeferred.complete(response)
            }

            override fun onFailure(call: Call<R>, t: Throwable) {
                completableDeferred.completeExceptionally(t)
            }
        })
        return completableDeferred
    }
}

class BodyDeferredAdapter<R>(val responseType: Type): CallAdapter<R, Deferred<R>>{
    override fun responseType() = responseType

    override fun adapt(call: Call<R>): Deferred<R> {
        val completableDeferred = CompletableDeferred<R>()
        call.enqueue(object: Callback<R>{
            override fun onResponse(call: Call<R>, response: Response<R>) {
                response.body()?.let(completableDeferred::complete)
                        ?:completableDeferred.completeExceptionally(NullPointerException())
            }

            override fun onFailure(call: Call<R>, t: Throwable) {
                completableDeferred.completeExceptionally(t)
            }
        })
        return completableDeferred
    }
}