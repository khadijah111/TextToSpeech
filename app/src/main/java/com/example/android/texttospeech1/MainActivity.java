package com.example.android.texttospeech1;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextToSpeech mTTS;
    private EditText text1;
    private android.widget.SeekBar pich_value;
    private android.widget.SeekBar speed_value;
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = findViewById(R.id.textView_word);
        pich_value = findViewById(R.id.seeker_pitch);
        speed_value = findViewById(R.id.seeker_speed);
        button1 = findViewById(R.id.sayit_button);

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("error", "language not supported");
                    } else {
                        button1.setEnabled(true);
                    }
                } else {
                    Log.e("error", "initialization faild");
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speak();
            }
        });
    }

    public void Speak() {
        String text = text1.getText().toString();
        float pitchValue = (float) pich_value.getProgress() / 50;
        if (pitchValue < 0.1) pitchValue = 0.1f;
        float speedValue = (float) speed_value.getProgress() / 50;
        if (speedValue < 0.1) speedValue = 0.1f;

        mTTS.setSpeechRate(speedValue);
        mTTS.setPitch(pitchValue);

        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);

    }

    @Override
    protected void onDestroy() {
        //stop the speak engine when app or activity closed
        if(mTTS != null)
        {
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }
}
