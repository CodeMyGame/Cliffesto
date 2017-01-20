package com.cliffesto.cliffesto.picaso;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Kapil Malviya on 7/18/2016.
 */
public class AnimationUtils {
    public static void animate(RecyclerView.ViewHolder holder, boolean goesDown){
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(holder.itemView,"translationY",goesDown==true?200:-200,0);
        objectAnimator.setDuration(1000);
        animatorSet.playTogether(objectAnimator);
        animatorSet.start();
    }
}
