package services;

import ai.tock.demo.common.models.Post;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PostService {


    @GET("/posts/{tag}")
    public Call<Post> getPosts(@Path("tag") String username);
}
