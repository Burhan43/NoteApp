package app.powered.by.burrhan.noteapp.Activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.powered.by.burrhan.noteapp.Adapters.SliderAdapter;
import app.powered.by.burrhan.noteapp.R;


public class OnBoardingScreensActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout dotsLayout;

    SliderAdapter adapter;
    CardView gettingStarted;

    private TextView[] dots;

    private Animation animation;

    private int currentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_screens);

        getWindow()
                .setFlags(WindowManager
                                .LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        viewPager= findViewById(R.id.onBoarding_slider);
        dotsLayout= findViewById(R.id.dotsLayout);
        gettingStarted= findViewById(R.id.getting_started);

        adapter= new SliderAdapter(this);
        viewPager.setAdapter(adapter);

        addDots(0);

        viewPager.addOnPageChangeListener(pageChangeListener);







    }

    public void skip(View view)
    {
        startActivity(new Intent(this,LoginActivity.class));

        finish();
    }
    public void next(View view)
    {
        viewPager.setCurrentItem(currentPos+1);

    }
    public void gettingStarted(View view)
    {
        startActivity(new Intent(this,LoginActivity.class));
        finish();

    }


    private void addDots(int position) {

        dots= new TextView[6];

        dotsLayout.removeAllViews();

        for (int i = 0; i <dots.length ; i++) {
            dots[i]= new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);


            dotsLayout.addView(dots[i]);


        }

        if (dots.length>0){
            dots[position].setTextColor(getResources().getColor(R.color.colorPickerWhite));
        }
    }

    ViewPager.OnPageChangeListener pageChangeListener= new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDots(position);
            currentPos=position;


            if (position >=0 && position<5)
            {
                gettingStarted.setVisibility(View.INVISIBLE);
            }
            else
            {
                animation= AnimationUtils.loadAnimation(OnBoardingScreensActivity.this,R.anim.bottom_anim);

                gettingStarted.setAnimation(animation);
                gettingStarted.setVisibility(View.VISIBLE);

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}