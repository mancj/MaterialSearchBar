package com.mancj.example.custom;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.mancj.example.R;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterActivity extends AppCompatActivity implements View.OnClickListener {
    private MaterialSearchBar searchBar;
    private List<Product> suggestions = new ArrayList<>();
    private CustomSuggestionsAdapter customSuggestionsAdapter;

    // Sample data
    private final String[] products = {
            "Simvastatin",
            "Carrot Daucus carota",
            "Sodium Fluoride",
            "White Kidney Beans",
            "Salicylic Acid",
            "cetirizine hydrochloride",
            "Mucor racemosus",
            "Thymol",
            "TOLNAFTATE",
            "Albumin Human"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_adapter);

        searchBar = findViewById(R.id.searchBar);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        customSuggestionsAdapter = new CustomSuggestionsAdapter(inflater);
        Button addProductBtn = findViewById(R.id.button);
        addProductBtn.setOnClickListener(this);

        searchBar.setMaxSuggestionCount(2);
        searchBar.setHint("Find Product..");

        for (int i = 1; i < 11; i++) {
            suggestions.add(new Product(products[i - 1], i * 10));
        }

        customSuggestionsAdapter.setSuggestions(suggestions);
        searchBar.setCustomSuggestionAdapter(customSuggestionsAdapter);

        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("LOG_TAG", getClass().getSimpleName() + " text changed " + searchBar.getText());
                // send the entered text to our filter and let it manage everything
                customSuggestionsAdapter.getFilter().filter(searchBar.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
    }

    @Override
    public void onClick(View view) {
        customSuggestionsAdapter.addSuggestion(new Product("Product", 100));
    }

}
