package com.example.ran.battleship;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

/**
 * Created by ran on 09/01/2018.
 */

public class HighScoreListFragment extends Fragment {

    private ArrayList<HighScoreRecord> mAllRecords;
    private String mTableName;
    private DBHandler mHandler;


    public HighScoreListFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = DBHandler.getInstance(getActivity().getApplicationContext());

        Bundle bundle = this.getArguments();
        mTableName = bundle.getString(HighScoreActivity.TABLE_NAME_KEY);

        synchronized (mHandler) {
            mAllRecords = mHandler.getAllRecordsFromTable(mTableName);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_high_score_list, container, false);




        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            recyclerView.setLayoutManager(new LinearLayoutManager(context));

//            recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

            recyclerView.setAdapter(new HighScoreRecyclerViewAdapter(mAllRecords, new HighScoreRecyclerViewAdapter.OnItemClickListener() {

                @Override
                public void onItemClick(HighScoreRecord item) {
                //pass player's id to the parent activity
                    HighScoreRecord hs = new HighScoreRecord(item.getId(),item.getName(),item.getScore(),item.getLatitude(),item.getLongtitude());
                    ((HighScoreActivity)getActivity()).getRecordFromClickedList(hs);

                }
            }));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
