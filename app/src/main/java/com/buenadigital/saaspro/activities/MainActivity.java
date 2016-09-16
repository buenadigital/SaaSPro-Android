package com.buenadigital.saaspro.activities;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.buenadigital.saaspro.Constants;
import com.buenadigital.saaspro.R;
import com.buenadigital.saaspro.customModels.MaterialEditTextMedium;
import com.buenadigital.saaspro.navDrawer.NavigationDrawerFragment;
import com.buenadigital.saaspro.tools.AnimationUtils;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private Toolbar mToolBar;
    private NavigationDrawerFragment mDrawerFragment;
    private DrawerLayout mDrawerLayout;

    private ImageView mLogoImageView;
    private TextView mText_S;
    private TextView mText_a;
    private TextView mText_a2;
    private TextView mText_S2;
    private TextView mText_P;
    private TextView mText_r;
    private TextView mText_o;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.animation_fade_in, R.anim.animation_fade_out);
        setContentView(R.layout.activity_main);

        mContext = this;
        mToolBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        mDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);

        mDrawerFragment.setUp(R.id.navigation_drawer_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), mToolBar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mLogoImageView = (ImageView) findViewById(R.id.logo_image);
        mText_S = (TextView) findViewById(R.id.logo_text_S);
        mText_a = (TextView) findViewById(R.id.logo_text_a);
        mText_a2 = (TextView) findViewById(R.id.logo_text_a2);
        mText_S2 = (TextView) findViewById(R.id.logo_text_S2);
        mText_P = (TextView) findViewById(R.id.logo_text_P);
        mText_r = (TextView) findViewById(R.id.logo_text_r);
        mText_o = (TextView) findViewById(R.id.logo_text_o);

        mText_S.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);
        mText_a.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);
        mText_a2.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);
        mText_S2.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);
        mText_P.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);
        mText_r.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);
        mText_o.setTypeface(Constants.TYPE_FACE_FONT_ANTENNA_MEDIUM);

        AnimationUtils.applyAnimation(this, mLogoImageView, R.anim.animation_appear_with_rotation, 0);
        AnimationUtils.applyAnimation(this, mText_S, R.anim.animation_appear_from_nothing, 0);
        AnimationUtils.applyAnimation(this, mText_a, R.anim.animation_appear_from_nothing, 100);
        AnimationUtils.applyAnimation(this, mText_a2, R.anim.animation_appear_from_nothing, 200);
        AnimationUtils.applyAnimation(this, mText_S2, R.anim.animation_appear_from_nothing, 300);
        AnimationUtils.applyAnimation(this, mText_P, R.anim.animation_appear_from_nothing, 400);
        AnimationUtils.applyAnimation(this, mText_r, R.anim.animation_appear_from_nothing, 500);
        AnimationUtils.applyAnimation(this, mText_o, R.anim.animation_appear_from_nothing, 600);


    }
}
