package com.ottodk.audiorecorder;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button record, stop, play;
    private MediaRecorder mAudioRecorder;
    private String outputFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = (Button) findViewById(R.id.play);
        stop = (Button) findViewById(R.id.stop);
        record = (Button) findViewById(R.id.record);
        stop.setEnabled(false);
        play.setEnabled(false);

        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";

        mAudioRecorder = new MediaRecorder();
        mAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mAudioRecorder.setOutputFile(outputFile);

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    mAudioRecorder.prepare();
                    mAudioRecorder.start();

                } catch (Exception e) {
                    makeToast(e.toString());
                }

                record.setEnabled(false);
                stop.setEnabled(true);
                makeToast("Recording started");

            }
        });


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAudioRecorder.stop();
                mAudioRecorder.release();
                mAudioRecorder = null;
                record.setEnabled(true);
                stop.setEnabled(false);
                play.setEnabled(true);
                makeToast("Recording is stopped");

            }
        });


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(outputFile);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    makeToast("Audio is playing");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void makeToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }
}
