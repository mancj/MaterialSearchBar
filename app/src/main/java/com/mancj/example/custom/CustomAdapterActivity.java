package com.mancj.example.custom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toolbar;

import com.mancj.example.R;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_adapter);

        MaterialSearchBar searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        CustomSuggestionsAdapter customSuggestionsAdapter = new CustomSuggestionsAdapter(inflater);

        List<Product> suggestions = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            suggestions.add(new Product("Product " + i, i * 10));
        }
        customSuggestionsAdapter.setSuggestions(suggestions);

        searchBar.setCustomSuggestionAdapter(customSuggestionsAdapter);

    }
}
