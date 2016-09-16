package com.buenadigital.saaspro.tools;

import android.app.ActionBar;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.buenadigital.saaspro.activities.LoginActivity;

/**
 * Created by dn on 10/11/2015.
 */
public class AnimationUtils {
    public static void applyAnimation(Context context, View view, int animationResource, int animationOffsetMilisec){
        Animation anim = android.view.animation.AnimationUtils.loadAnimation(context, animationResource);
        anim.setStartOffset(animationOffsetMilisec);
        if(view != null) {
            view.setAnimation(anim);
            view.startAnimation(anim);
        }
    }

    public static void applyAnimationRepeatless(Context context, final View view, int animationResource, int animationOffsetMilisec){
        final Animation anim = android.view.animation.AnimationUtils.loadAnimation(context, animationResource);
        anim.setStartOffset(animationOffsetMilisec);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (view != null) {
                    view.setAnimation(anim);
                    view.startAnimation(anim);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        if(view != null) {
            view.setAnimation(anim);
            view.startAnimation(anim);
        }
    }

    public static void applyAnimationWithRunnable(Context context, final View view, int animationResource, int animationOffsetMilisec, final Runnable toRunAfterAnimation){
        final Animation anim = android.view.animation.AnimationUtils.loadAnimation(context, animationResource);
        anim.setStartOffset(animationOffsetMilisec);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(toRunAfterAnimation != null) {
                    toRunAfterAnimation.run();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        if(view != null) {
            view.setAnimation(anim);
            view.startAnimation(anim);
        }
    }



    public static void translateAnimation(Context context, final View view, final int marginRight, final int marginBottom){
        TranslateAnimation anim = new TranslateAnimation(0, marginRight, 0, marginBottom);
        anim.setDuration(100);

        anim.setAnimationListener(new TranslateAnimation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationRepeat(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                view.clearAnimation();
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)view.getLayoutParams();
                params.topMargin += marginBottom;
                params.leftMargin += marginRight;
                view.setLayoutParams(params);
            }
        });

        view.startAnimation(anim);

    }


    public static void applyAnimationRelocation(final Context context, final View view, int animationResource, int animationOffsetMilisec, final int relLeft, final int relTop, final int relRight, final int relBottom){
        Animation anim = android.view.animation.AnimationUtils.loadAnimation(context, animationResource);
        anim.setStartOffset(animationOffsetMilisec);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                if(view.getLayoutParams() instanceof LinearLayout.LayoutParams){
//                    ((LoginActivity)context).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
                view.clearAnimation();
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        view.getLayoutParams().width, view.getLayoutParams().height);
                layoutParams.setMargins(Utils.dpToPx(relLeft), Utils.dpToPx(relTop), Utils.dpToPx((view instanceof ImageView ? 10 : relRight)), Utils.dpToPx(relBottom));
                view.setLayoutParams(layoutParams);
//                        }
//                    });
//
//                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        if(view != null) {
            view.setAnimation(anim);
            view.startAnimation(anim);
        }
    }


    public static void applyAnimationWithFinalHide(Context context, final View view, int animationResource, int animationOffsetMilisec){
        Animation anim = android.view.animation.AnimationUtils.loadAnimation(context, animationResource);
        anim.setStartOffset(animationOffsetMilisec);
        if(view != null) {
            view.setAnimation(anim);
            view.startAnimation(anim);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }

    public static void applyAnimationWithFinalShow(Context context, final View view, int animationResource, int animationOffsetMilisec){
        view.setVisibility(View.VISIBLE);
        Animation anim = android.view.animation.AnimationUtils.loadAnimation(context, animationResource);
        anim.setStartOffset(animationOffsetMilisec);
        if(view != null) {
            view.setAnimation(anim);
            view.startAnimation(anim);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }
}

