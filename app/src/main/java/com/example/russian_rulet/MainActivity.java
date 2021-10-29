package com.example.russian_rulet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private SoundPool sound;
    private Integer soundGun, soundFalse, soundBaraban, randomNum, num;
    private Button btnGun, btnBaraban;
    private ImageView blood;
    private TextView tv_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createSoundPool();
        loadSound();
        initListeners();
    }

    protected void createSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
        } else {
            createOldSoundPool();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
        sound = new SoundPool.Builder()
                .setAudioAttributes(attributes).build();
    }

    @SuppressWarnings("deprecation")
    protected void createOldSoundPool() {
        sound = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
    }

    private void loadSound() {
        soundGun = sound.load(this, R.raw.revolver_shot, 1);
        soundFalse = sound.load(this, R.raw.gun_false, 1);
        soundBaraban = sound.load(this, R.raw.revolver_baraban, 1);
    }

    private void initListeners() {
        btnGun = findViewById(R.id.btn_gun);
        btnBaraban = findViewById(R.id.btn_baraban);
        blood = findViewById(R.id.iv_blood);
        tv_num = findViewById(R.id.tv_num);

        btnGun.setOnClickListener(view -> {
            if (num != null && randomNum != null) {
                if (num.equals(randomNum)) {
                    sound.play(soundGun, 1.0f, 1.0f, 1, 0, 1);
                    blood.setVisibility(View.VISIBLE);
                } else {
                    sound.play(soundFalse, 1.0f, 1.0f, 1, 0, 1);
                    num = num + 1;
                }
                tv_num.setText(num.toString());
            }
        });


        btnBaraban.setOnClickListener(view -> {
            sound.play(soundBaraban, 1.0f, 1.0f, 1, 0, 1);
            blood.setVisibility(View.GONE);
            randomNum = new Random().nextInt(9);
            num = 0;
            tv_num.setText("0");
        });
    }
}