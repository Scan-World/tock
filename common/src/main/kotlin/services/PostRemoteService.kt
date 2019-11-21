package ai.tock.demo.common.services

import ai.tock.demo.common.models.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Path


/**
 * Created by mathias on 22/11/2017.
 */
class PostRemoteService {

    private val url = "http://1eca3506.ngrok.io"

    private val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    private val service = retrofit.create<RetrofitService>(RetrofitService::class.java)

    interface RetrofitService {
        @GET("posts/{place}")
        fun listPost( @Path("place") place: String): Call<List<Post>>
    }

    fun loadPost(place: String, callback: Callback<List<Post>>) {
        service.listPost(place).enqueue(callback)
    }

}
