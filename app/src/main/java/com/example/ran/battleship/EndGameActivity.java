package com.example.ran.battleship;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;



public class EndGameActivity extends AppCompatActivity {

    final static int HIGH_SCORE_MIN_BAR = 5500;

    private String winningPlayer;
    private int difficulty,score;
    private ImageView imageViewTop;
    private ImageView imageViewDown;
    private int worstHighScore;
    private HighScoreRecord highScoreRecord;
    private EditText input;
    private DBHandler mDBHandler;
    private MediaPlayer mPlayer;
    private String difficultyName;
    private String userName;


    private double[] mCoordinates;


//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == 1) {
//
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//
//                }
//            }
//        }
//    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);


        mCoordinates = new double[2];

        imageViewTop =  (ImageView)findViewById(R.id.endGamePictureTop);
        imageViewDown = (ImageView)findViewById(R.id.endGamePictureDown);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.endGame);

        Bundle extras = getIntent().getBundleExtra(GameActivity.THE_BUNDLE);

        if (extras!= null){
            difficulty = extras.getInt(GameActivity.THE_DIFFICULTY);
            winningPlayer = extras.getString(GameActivity.THE_WINNER);
            score = extras.getInt(GameActivity.THE_SCORE);
            mCoordinates = extras.getDoubleArray(GameActivity.THE_LOCATION_KEY);
        }

        switch (winningPlayer) {
            case "player":
                relativeLayout.setBackgroundResource(R.drawable.win);
                imageViewTop.setImageResource(R.drawable.win_text);
                imageViewDown.setImageResource(R.drawable.tressure);
                setDifficultyToString(difficulty);
                Log.i("Win mode:", "score: " + score);
                playWinMusic();


            if (score > HIGH_SCORE_MIN_BAR) {

                mDBHandler = DBHandler.getInstance(this);

                    if (mDBHandler.isTableFull(difficultyName)){
                        worstHighScore = mDBHandler.getWorstRecord(difficultyName).getScore();
                        if (score > worstHighScore){
                            //delete worst record in table
                             mDBHandler.deleteWorstRecord(difficultyName);
                        }
                    }

                new CountDownTimer(1500, 1000) {

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        createNewRecordDialog();
                    }
                }.start();
                }
                break;

            case "computer":
                relativeLayout.setBackgroundResource(R.drawable.rain);
                imageViewTop.setImageResource(R.drawable.failed);
                imageViewDown.setImageResource(R.drawable.dead);
                playLoseMusic();
                break;
        }
    }



    public void playWinMusic() {
        try {
            if (mPlayer != null) {
                mPlayer.release();
                mPlayer = null;
            }

            mPlayer = MediaPlayer.create(EndGameActivity.this, R.raw.win);
            mPlayer.start();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playLoseMusic() {

        try {
            if (mPlayer != null) {
                mPlayer.release();
                mPlayer = null;
            }

            mPlayer = MediaPlayer.create(EndGameActivity.this, R.raw.lost);
            mPlayer.start();

        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public void createNewRecordDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(EndGameActivity.this);
        builder.setTitle("NEW RECORD!");
        builder.setMessage("Congrats! you set a new Record!");

        input = new EditText(this);
        input.setHint(R.string.new_record_placeholder);

        builder.setView(input);
        builder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userName = input.getText().toString();

                if (userName.length()!=0) {
                    highScoreRecord = new HighScoreRecord(userName, score, mCoordinates[0], mCoordinates[1]);
                    mDBHandler.insertRecord(difficultyName, highScoreRecord);
                }
                goToHighScoreActivity();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }



    public void goToHighScoreActivity() {
        Intent intent = new Intent(EndGameActivity.this, HighScoreActivity.class);
        startActivity(intent);
    }



    public void rematchGame(View view){
        Intent intent = new Intent(EndGameActivity.this, GameActivity.class);
        intent.putExtra(Intent.EXTRA_INDEX, difficulty);
        startActivity(intent);
        finish();


    }

    public void goToMainMenu(View view){
        Intent intent = new Intent(EndGameActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    public void setDifficultyToString(int difficulty) {
        switch (difficulty){
            case 0:
                this.difficultyName = "EASY";
                break;
            case 1:
                this.difficultyName = "MEDIUM";
                break;
            case 2:
                this.difficultyName = "HARD";
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    public String getDifficultyName() {
        return difficultyName;
    }





}
