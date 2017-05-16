package dog.snow.androidrecruittest.remote;

import java.util.List;

import dog.snow.androidrecruittest.model.Item;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("items")
    Call<List<Item>> getItems ();
}
