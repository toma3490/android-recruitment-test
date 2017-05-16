package dog.snow.androidrecruittest.utils;

import dog.snow.androidrecruittest.remote.ApiInterface;
import dog.snow.androidrecruittest.remote.RetrofitClient;

public class ApiUtils {
    public static String BASE_URL = "http://localhost:8080/api/";
    public static ApiInterface getApi(){
        return RetrofitClient.getClient(BASE_URL).create(ApiInterface.class);
    }
}
