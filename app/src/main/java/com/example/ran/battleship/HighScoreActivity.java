package com.example.ran.battleship;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.view.View;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;


public class HighScoreActivity extends AppCompatActivity implements OnMapReadyCallback{

    public static final String TABLE_NAME_KEY = "TABLE_NAME";
    public static final String DEFAULT_TABLE_NAME = DBHandler.DB_TABLE_EASY;

    private Fragment mFragment;
    private Button mEasyButton , mMediumButton, mHardButton ;

    private DBHandler mDBHandler;
    private GoogleMap mMap;
    private ArrayList <HighScoreRecord> mAllRecords;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        mDBHandler = DBHandler.getInstance(this);

        Bundle bundle = new Bundle();
        bundle.putString(TABLE_NAME_KEY, DEFAULT_TABLE_NAME);

        mFragment = new HighScoreListFragment();
        mFragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.add(R.id.high_score_layout_id, mFragment);
        transaction.commit();


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        mEasyButton = (Button) findViewById(R.id.easy_button_id);
        mMediumButton = (Button) findViewById(R.id.medium_button_id);
        mHardButton = (Button) findViewById(R.id.hard_button_id);
        mMediumButton.setAlpha(.5f);
        mHardButton.setAlpha(.5f);


    }

    public void easyButtonPressed(View v){

        mEasyButton.setAlpha(1);
        mMediumButton.setAlpha(.5f);
        mHardButton.setAlpha(.5f);

        Bundle bundle = new Bundle();
        bundle.putString(TABLE_NAME_KEY, DBHandler.DB_TABLE_EASY);
        mFragment = new  HighScoreListFragment();
        mFragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.high_score_layout_id, mFragment);
        transaction.commit();


        showAllMarkersOnMap(DBHandler.DB_TABLE_EASY);
    }


    public void mediumButtonPressed(View v){

        mMediumButton.setAlpha(1);
        mEasyButton.setAlpha(.5f);
        mHardButton.setAlpha(.5f);

        Bundle bundle = new Bundle();
        bundle.putString(TABLE_NAME_KEY, DBHandler.DB_TABLE_MEDIUM);

        mFragment = new  HighScoreListFragment();
        mFragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.high_score_layout_id, mFragment);
        transaction.commit();

        showAllMarkersOnMap(DBHandler.DB_TABLE_MEDIUM);

    }

    public void hardButtonPressed(View v){

        mHardButton.setAlpha(1);
        mMediumButton.setAlpha(.5f);
        mEasyButton.setAlpha(.5f);

        Bundle bundle = new Bundle();
        bundle.putString(TABLE_NAME_KEY, DBHandler.DB_TABLE_HARD);

        mFragment = new  HighScoreListFragment();
        mFragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.high_score_layout_id, mFragment);
        transaction.commit();

        showAllMarkersOnMap(DBHandler.DB_TABLE_HARD);
    }



    private void showAllMarkersOnMap(String tableName) {
        mMap.clear();
        mMap.animateCamera(CameraUpdateFactory.zoomTo(0f));
        if (mDBHandler.getTableSize(tableName) <= 0 )
            return;

        mAllRecords = mDBHandler.getAllRecordsFromTable(tableName);
       for(HighScoreRecord hs : mAllRecords){
           addPlayerMarkerOnMap(hs);
       }


    }

    private void addPlayerMarkerOnMap(HighScoreRecord hs) {
        double lat = hs.getLatitude();
        double lng = hs.getLongtitude();

        if (lat != 0 && lng != 0) {
            LatLng playerPosition = new LatLng(lat, lng);
             mMap.addMarker(new MarkerOptions().position(playerPosition).title("name:" + hs.getName()).snippet("score: " + hs.getScore()));
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            showAllMarkersOnMap(DEFAULT_TABLE_NAME);

        }


    public void getRecordFromClickedList(HighScoreRecord hs) {

        mMap.clear();
        addPlayerMarkerOnMap(hs);
        double lat =  hs.getLatitude();
        double lng = hs.getLongtitude();
        LatLng position = new LatLng(lat,lng);
        if (lat != 0 && lng !=0){
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 10f));

        }


    }
}


