package dog.snow.androidrecruittest.remote;

import java.util.ArrayList;

import dog.snow.androidrecruittest.model.Item;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("items")
    Call<ArrayList<Item>> getItems ();
}
