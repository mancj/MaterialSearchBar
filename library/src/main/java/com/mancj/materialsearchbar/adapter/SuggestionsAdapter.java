package com.mancj.materialsearchbar.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mancj on 27.01.17.
 */

/**
 * Create this adapter to customize the suggestions list of the MaterialSearchBar
 * @param <S> type of your suggestions model
 * @param <V> viewholder
 */
public abstract class SuggestionsAdapter<S, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> implements Filterable {

    protected List<S> suggestions = new ArrayList<>();
    protected List<S> suggestions_clone = new ArrayList<>();
    private LayoutInflater inflater;
    protected int maxSuggestionsCount = 5;

    public SuggestionsAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void addSuggestion(S r){
        if (maxSuggestionsCount <= 0)
            return;

        if (r == null)
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
        suggestions_clone = suggestions;
        notifyDataSetChanged();
    }

    public void setSuggestions(List<S> suggestions) {
        this.suggestions = suggestions;
        suggestions_clone = suggestions;
        notifyDataSetChanged();
    }

    public void clearSuggestions() {
        suggestions.clear();
        suggestions_clone = suggestions;
        notifyDataSetChanged();
    }

    public void deleteSuggestion(int position, S r) {
        if(r == null)
            return;
        //delete item with animation
        if(suggestions.contains(r))
        {
            this.notifyItemRemoved(position);
            suggestions.remove(r);
            suggestions_clone = suggestions;
        }
    }

    public List<S> getSuggestions() {
        return suggestions;
    }

    public int getMaxSuggestionsCount() {
        return maxSuggestionsCount;
    }

    public void setMaxSuggestionsCount(int maxSuggestionsCount) {
        this.maxSuggestionsCount = maxSuggestionsCount;
    }

    protected LayoutInflater getLayoutInflater(){
        return this.inflater;
    }

    @Override
    public void onBindViewHolder(V holder, int position) {
        onBindSuggestionHolder(suggestions.get(position), holder, position);
    }

    public abstract void onBindSuggestionHolder(S suggestion, V holder, int position);

    /**
     * Returns the height of single view item in list.
     * <b>Note that all views must have the same height</b>
     * @return height
     */
    public abstract int getSingleViewHeight();

    public int getListHeight(){
        return getItemCount() * getSingleViewHeight();
    }

    @Override
    public int getItemCount() {
        return suggestions.size();
    }

    /**
     * <p>Returns a filter that can be used to constrain data with a filtering
     * pattern.</p>
     * <p>
     * <p>This method is usually implemented by {@link Adapter}
     * classes.</p>
     *
     * @return a filter used to constrain data
     */
    @Override
    public Filter getFilter() {
        return null;
    }

    public interface OnItemViewClickListener{
        void OnItemClickListener(int position,View v);
        void OnItemDeleteListener(int position,View v);
    }

}
