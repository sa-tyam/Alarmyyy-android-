package application.greyhats.alarmee;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekbar;
    TextView timerTextView;
    Button goButton;
    CountDownTimer timer;


    int max_time = 100;
    int seekbar_initial_progress = 5;

    boolean timer_active = false;

    public void updateTimer (int seconds) {

            int minute = seconds / 60;
            int second = seconds % 60;

            String minute_text = String.valueOf(minute);
            String second_text = String.valueOf(second);

            if (second < 10) {
                second_text = "0" + second_text;
            }
            if (minute < 10) {
                minute_text = "0" + minute_text;
            }

            timerTextView.setText(minute_text + ":" + second_text);
    }

    public void StartTimer(View view){
        if (timer_active) {
            timer_active = false;
            seekbar.setProgress(seekbar_initial_progress);
            updateTimer(seekbar_initial_progress);
            seekbar.setEnabled(true);
            goButton.setText("GO");
            timer.cancel();
        } else {
            seekbar.setEnabled(false);
            timer_active = true;
            goButton.setText("STOP");
            timer = new CountDownTimer(seekbar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    seekbar.setProgress((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    updateTimer(0);
                    final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bomb);
                    mediaPlayer.start();
                }
            }.start();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekbar = findViewById(R.id.seekBar);
        timerTextView = findViewById(R.id.timerTextView);
        goButton = findViewById(R.id.button);

        seekbar.setMax(max_time);
        seekbar.setProgress(seekbar_initial_progress);
        updateTimer(seekbar_initial_progress);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("seekbar", String.valueOf(seekBar.getProgress()));
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}