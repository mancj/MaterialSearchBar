package com.mancj.materialsearchbar;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mancj on 20.07.2016.
 */
public class SuggestionsAdapter extends RecyclerView.Adapter<SuggestionsAdapter.SuggestionHolder>{
    private List<String> suggestions = new ArrayList<>();
    private LayoutInflater inflater;
    private OnItemViewClickListener listener;
    protected int maxSuggestionsCount;

    public void setListener(OnItemViewClickListener listener) {
        this.listener = listener;
    }

    public SuggestionsAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void addSuggestion(String r){
        if (r.length() <= 0)
            return;
        if (!suggestions.contains(r))
        {
            if (suggestions.size() >= maxSuggestionsCount) {
                suggestions.remove(maxSuggestionsCount - 1);
            }
            suggestions.add(0, r);
        }else {
            suggestions.remove(r);
            suggestions.add(0, r);
        }
    }

    public void deleteSuggestion(int postion,String r){
        if(r.length()<0)
            return;
        //delete item with animation
        if(suggestions.contains(r))
        {
            this.notifyItemRemoved(postion);
            suggestions.remove(r);
        }
    }

    @Override
    public SuggestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_last_request, parent, false);
        return new SuggestionHolder(view);
    }

    @Override
    public void onBindViewHolder(final SuggestionHolder holder, final int position) {
        holder.text.setText(suggestions.get(position));
    }

    @Override
    public int getItemCount() {
        return suggestions.size();
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
        notifyDataSetChanged();
    }

    class SuggestionHolder extends RecyclerView.ViewHolder{
        private TextView text;
        private ImageView iv_delete;
        public SuggestionHolder(final View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            iv_delete = (ImageView) itemView.findViewById(R.id.iv_delete);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setTag(suggestions.get(getAdapterPosition()));
                    listener.OnItemClickListener(getAdapterPosition(),v);
                }
            });
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setTag(suggestions.get(getAdapterPosition()));
                    listener.OnItemDeleteListener(getAdapterPosition(),v);
                }
            });
        }
    }

    public interface OnItemViewClickListener{
        void OnItemClickListener(int position,View v);
        void OnItemDeleteListener(int position,View v);
    }

}
