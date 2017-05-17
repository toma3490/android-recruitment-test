package dog.snow.androidrecruittest.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import dog.snow.androidrecruittest.R;

public class SearchFragment extends Fragment {

    private EditText searchEditText;
    private ImageButton searchButton;
    private String searchQuery = "";
    private SearchInterface searchInterface;

    public static SearchFragment getSearchFragment(){
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        searchEditText = (EditText) view.findViewById(R.id.search_et);
        searchButton = (ImageButton) view.findViewById(R.id.search_imageButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getActivity().getCurrentFocus();

                hideInput(view);

                isStringEmpty(view, searchQuery);

                searchInterface.scrollView(searchQuery);
            }
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchQuery = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    View view = getActivity().getCurrentFocus();

                    hideInput(view);

                    isStringEmpty(v, searchEditText.getText().toString());

                    searchInterface.scrollView(searchQuery);
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    private void isStringEmpty(View v, String s) {
        if (s.isEmpty()){
            Snackbar.make(v, R.string.empty_query, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void hideInput(View v) {
        if (v != null){
            InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        searchInterface = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            searchInterface = (SearchInterface) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}
