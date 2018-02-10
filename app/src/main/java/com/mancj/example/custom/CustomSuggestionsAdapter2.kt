package com.mancj.example.custom

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mancj.example.R
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter


class CustomSuggestionsAdapter2(inflater: LayoutInflater) : SuggestionsAdapter<String, CustomSuggestionsAdapter2.SuggestionHolder>(inflater) {

    override fun getSingleViewHeight(): Int {
        return 80
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionHolder {
        val view = layoutInflater.inflate(R.layout.item_custom_suggestion, parent, false)
        return SuggestionHolder(view)
    }

    override fun onBindSuggestionHolder(suggestion: String, holder: SuggestionHolder, position: Int) {
        holder.bind()
    }

    /*override fun getFilter(): Filter {
        return object : Filter() {
            protected fun performFiltering(constraint: CharSequence): FilterResults {
                val results = FilterResults()
                val term = constraint.toString()
                if (term.isEmpty())
                    suggestions = suggestions_clone
                else {
                    suggestions = ArrayList()
                    for (item in suggestions_clone)
                        if (item.getTitle().toLowerCase().contains(term.toLowerCase()))
                            suggestions.add(item)
                }
                results.values = suggestions
                return results
            }

            protected fun publishResults(constraint: CharSequence, results: FilterResults) {
                suggestions = results.values
                notifyDataSetChanged()
            }
        }
    }*/

    class SuggestionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() = with(itemView) {

        }
    }

}