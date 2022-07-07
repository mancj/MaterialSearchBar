package com.mancj.materialsearchbar.adapter;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mancj.materialsearchbar.R;

/**
 * Created by mancj on 27.01.17.
 */

public class DefaultSuggestionsAdapter extends SuggestionsAdapter<String, DefaultSuggestionsAdapter.SuggestionHolder> {
    private SuggestionsAdapter.OnItemViewClickListener listener;
    int fontFamily = 0;

    public DefaultSuggestionsAdapter(LayoutInflater inflater, int fontFamily) {
        super(inflater);
        this.fontFamily=fontFamily;
    }


    private void setFontFamily(TextView view) {
        if (fontFamily > 0) {
            view.setTypeface(ResourcesCompat.getFont(view.getContext(), fontFamily));
        }
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
        setFontFamily(holder.text);
    }

    public interface OnItemViewClickListener {
        void OnItemClickListener(int position, View v);

        void OnItemDeleteListener(int position, View v);
    }

    class SuggestionHolder extends RecyclerView.ViewHolder {
        private final TextView text;
        private final ImageView iv_delete;

        public SuggestionHolder(final View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            iv_delete = itemView.findViewById(R.id.iv_delete);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setTag(getSuggestions().get(getAdapterPosition()));
                    listener.OnItemClickListener(getAdapterPosition(), v);
                }
            });
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position > 0 && position < getSuggestions().size()) {
                        v.setTag(getSuggestions().get(getAdapterPosition()));
                        listener.OnItemDeleteListener(getAdapterPosition(), v);
                    }
                }
            });
        }
    }
}
