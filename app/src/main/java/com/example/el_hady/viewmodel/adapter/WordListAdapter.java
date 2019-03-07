package com.example.el_hady.viewmodel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.el_hady.viewmodel.R;
import com.example.el_hady.viewmodel.models.Word;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private final LayoutInflater inflater;
    private List<Word> words; // Cached copy of words

   /* public WordListAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }*/
    public WordListAdapter(Context context) {
       inflater = LayoutInflater.from(context);
    }


    class WordViewHolder extends RecyclerView.ViewHolder{

        private final TextView wordItemView;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.recyclerview_item,viewGroup,false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        if (words != null) {
            Word current = words.get(position);
            holder.wordItemView.setText(current.getWord());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Word");
        }

    }

    void setWords(List<Word> words){
        words = words;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (words != null)
            return words.size();
        else return 0;
    }
}
