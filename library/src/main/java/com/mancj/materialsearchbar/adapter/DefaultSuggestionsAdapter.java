package com.mancj.materialsearchbar.adapter;

import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.R;

/**
 * Created by mancj on 27.01.17.
 */

public class DefaultSuggestionsAdapter extends SuggestionsAdapter<String, DefaultSuggestionsAdapter.SuggestionHolder> {
    private SuggestionsAdapter.OnItemViewClickListener listener;
    private MaterialSearchBar bar;
    public DefaultSuggestionsAdapter(MaterialSearchBar bar, LayoutInflater inflater) {
        super(inflater);
        this.bar = bar;
    }

    public void setListener(SuggestionsAdapter.OnItemViewClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getSingleViewHeight() {
        return 50;
    }

    @Override
    public DefaultSuggestionsAdapter.SuggestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.item_last_request, parent, false);
        return new DefaultSuggestionsAdapter.SuggestionHolder(view);
    }

    @Override
    public void onBindSuggestionHolder(String suggestion, SuggestionHolder holder, int position) {
        holder.text.setText(getSuggestions().get(position));
    }

    class SuggestionHolder extends RecyclerView.ViewHolder{
        private TextView text;
        private ImageView iv_delete;
        private ImageView iv_suggestion;
        public SuggestionHolder(final View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            iv_delete = (ImageView) itemView.findViewById(R.id.iv_delete);
            iv_suggestion = (ImageView) itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setTag(getSuggestions().get(getAdapterPosition()));
                    listener.OnItemClickListener(getAdapterPosition(),v);
                    MaterialSearchBar.OnSearchActionListener searchListener =bar.getOnSearchListener();
                    bar.hideSuggestionsList();
                    if(searchListener!=null) searchListener.onSearchConfirmed(text.getText().toString());
                    SuggestionsAdapter adapter = bar.getAdapter();
                    if (adapter instanceof DefaultSuggestionsAdapter) new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bar.getAdapter().addSuggestion(text.getText().toString().trim());
                        }
                    }, 500);
                }
            });
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setTag(getSuggestions().get(getAdapterPosition()));
                    listener.OnItemDeleteListener(getAdapterPosition(),v);
                }
            });
            text.setTextColor(bar.suggestionTextColor);
            if (bar.suggestionIconTintEnabled) iv_delete.setColorFilter(bar.suggestionIconTint, PorterDuff.Mode.SRC_IN);
            if (bar.suggestionIconTintEnabled) iv_suggestion.setColorFilter(bar.suggestionIconTint, PorterDuff.Mode.SRC_IN);
        }
    }

    public interface OnItemViewClickListener{
        void OnItemClickListener(int position,View v);
        void OnItemDeleteListener(int position,View v);
    }
}
