package dog.snow.androidrecruittest.utils;

import dog.snow.androidrecruittest.remote.ApiInterface;
import dog.snow.androidrecruittest.remote.RetrofitClient;

public class ApiUtils {

    private static String BASE_URL = "http://192.168.0.103:8080/api/";
    public static ApiInterface getApi(){
        return RetrofitClient.getClient(BASE_URL).create(ApiInterface.class);
    }
}
