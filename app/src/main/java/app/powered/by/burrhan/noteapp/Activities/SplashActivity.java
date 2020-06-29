package app.powered.by.burrhan.noteapp.Activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import app.powered.by.burrhan.noteapp.R;


public class SplashActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView textView;
    private Animation fromLeft,fromTop;
    private SharedPreferences onBoardingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow()
                .setFlags(WindowManager
                                .LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.backgroundActionBar));



        setContentView(R.layout.activity_splash);


        imageView=findViewById(R.id.splash_activity_iv);
        textView=findViewById(R.id.splash_activity_tv);

        fromLeft= AnimationUtils.loadAnimation(this,R.anim.from_left_animation);
        textView.setAnimation(fromLeft);
        fromTop= AnimationUtils.loadAnimation(this,R.anim.from_top_animation);
        imageView.setAnimation(fromTop);

        new Handler().postDelayed(() -> {
            onBoardingScreen = getSharedPreferences("onBoardingScreen",MODE_PRIVATE);
            boolean isFirstTime = onBoardingScreen.getBoolean("firstTime",true);

            if (isFirstTime)
            {
                SharedPreferences.Editor editor=onBoardingScreen.edit();
                editor.putBoolean("firstTime",false);
                editor.apply();
                startActivity(new Intent(getApplicationContext(), OnBoardingScreensActivity.class));


            }

            else
            {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

            }
            finish();
        }, 5000);

    }
}