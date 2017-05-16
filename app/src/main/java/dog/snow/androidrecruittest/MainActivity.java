package dog.snow.androidrecruittest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import dog.snow.androidrecruittest.fragments.SearchInterface;

public class MainActivity extends AppCompatActivity implements SearchInterface {

    public static String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;

    private TextView emptyTextView;

    private String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void scrollView(String query) {
        searchQuery = query;
        scrollToPosition();
    }

    private void scrollToPosition() {

    }
}
