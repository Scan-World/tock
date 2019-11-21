package services;

import ai.tock.demo.common.models.Post;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostServiceImpl {

    public static Post getPosts(){
        String url = "http://1eca3506.ngrok.io/";

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        PostService service = retrofit.create(PostService.class);
        Call<Post> callSync = service.getPosts("paris");

        try {
            Response<Post> response = callSync.execute();
            Post post = response.body();
            return post;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
