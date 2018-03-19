package com.example.ran.battleship;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ran on 09/01/2018.
 */

public class HighScoreRecyclerViewAdapter extends RecyclerView.Adapter<HighScoreRecyclerViewAdapter.ViewHolder>{

    public interface OnItemClickListener {
    void onItemClick(HighScoreRecord item);
}


    private  ArrayList<HighScoreRecord> mRecordList;
    private final OnItemClickListener listener;


    public HighScoreRecyclerViewAdapter(ArrayList<HighScoreRecord> recordList, OnItemClickListener listener) {
        mRecordList = recordList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_high_score, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mItem = mRecordList.get(position);
        holder.mName.setText(mRecordList.get(position).getName());
        holder.mScore.setText(String.valueOf(mRecordList.get(position).getScore()));

        holder.bind(mRecordList.get(position),listener);



//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
    }


    @Override
    public int getItemCount() {
        if (mRecordList==null)
            return 0;
        return mRecordList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        public final View mView;
        public final TextView mName;
        public final TextView mScore;
        public HighScoreRecord mItem;


        public ViewHolder(View view ) {
            super(view);
            mView = view;
            mName = (TextView) view.findViewById(R.id.highscore_name_id);
            mScore = (TextView) view.findViewById(R.id.highscore_score_id);
        }

        public void bind(final HighScoreRecord item , final OnItemClickListener listener){

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }

    }


}
