package com.duodevloopers.foodup;

import android.view.View;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.viewpager2.widget.ViewPager2;

public class MyPageTransformer implements ViewPager2.PageTransformer {


    @Override
    public void transformPage(@NonNull @NotNull View page, float position) {

        ViewPager2 viewPager = (ViewPager2) page.getParent().getParent();
        float offset = position * -(2 * 30 + 20);

        if (viewPager.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
            if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                page.setTranslationX(-offset);
            } else {
                page.setTranslationX(offset);
            }
        } else {
            page.setTranslationY(offset);
        }

    }

}
