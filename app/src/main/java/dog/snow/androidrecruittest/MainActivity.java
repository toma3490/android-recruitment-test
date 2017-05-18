package dog.snow.androidrecruittest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoTools;

import java.util.ArrayList;
import java.util.List;

import dog.snow.androidrecruittest.adapters.ItemAdapter;
import dog.snow.androidrecruittest.controller.DatabaseController;
import dog.snow.androidrecruittest.fragments.SearchFragment;
import dog.snow.androidrecruittest.fragments.SearchInterface;
import dog.snow.androidrecruittest.model.Item;
import dog.snow.androidrecruittest.remote.ApiInterface;
import dog.snow.androidrecruittest.utils.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SearchInterface {

    public static String TAG = MainActivity.class.getSimpleName();
    private ItemAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<Item> itemList;
    private ApiInterface apiInterface;

    private TextView emptyTextView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchQuery = "";
        itemList = new ArrayList<>();

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fragment_container, SearchFragment.getSearchFragment());
            transaction.commit();
        }

        emptyTextView = (TextView) findViewById(R.id.empty_list_tv);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.items_rv);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ItemAdapter(this, itemList);
        recyclerView.setAdapter(adapter);

        if (DatabaseController.getInstance(this).getItems().size() == 0){
            fetchData();
        } else {
            updateUI();
        }
    }

    private void updateUI() {
        swipeRefreshLayout.setRefreshing(false);
        adapter.clearItems();
        itemList.addAll(DatabaseController.getInstance(getApplicationContext()).getItems());
        if (itemList.size() > 0){
            emptyTextView.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        } else {
            emptyTextView.setVisibility(View.VISIBLE);
        }
    }

    private void fetchData() {
        if (!isNetworkAvailable()){
            Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
            return;
        }

        apiInterface = ApiUtils.getApi();
        Call<List<Item>> call = apiInterface.getItems();
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful()){
                    DatabaseController.getInstance(getApplicationContext()).clearDatabase();
                    PicassoTools.clearCache(Picasso.with(getApplicationContext()));
                    List<Item> items = response.body();
                    for (Item item : items) {
                        DatabaseController.getInstance(getApplicationContext()).addItem(item);
//                        Log.d(TAG, "add in list items " + item.getId().toString());
                    }
                    updateUI();
                }else {
                    Log.d(TAG, response.errorBody().toString());
                    Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void scrollView(String query) {
        searchQuery = query;
        scrollToPosition();
    }

    private void scrollToPosition() {
        String name;
        String description;
        for (Item item : itemList) {
            name = item.getName().toLowerCase();
            description = item.getDescription().toLowerCase();
            if (name.contains(searchQuery) || description.contains(searchQuery)){
                layoutManager.scrollToPositionWithOffset(item.getId(), 0);
                break;
            }
        }
    }
}
