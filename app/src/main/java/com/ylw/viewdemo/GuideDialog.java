package com.ylw.viewdemo;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.view.View;

import java.util.Objects;

import androidx.constraintlayout.widget.ConstraintLayout;


public class GuideDialog {
    public GuideDialog(View ...views) {
        View view = views[0];
        Dialog dialog = new Dialog(view.getContext(), R.style.GuideDialog);
        dialog.setContentView(R.layout.guide_01);
        DarkLayerDrawable drawable = new DarkLayerDrawable(views);
        Objects.requireNonNull(dialog.getWindow()).getDecorView().setBackgroundDrawable(drawable);
        View focus = dialog.findViewById(R.id.focus);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) focus.getLayoutParams();
        layoutParams.leftMargin = drawable.left;
        layoutParams.topMargin =drawable.top;
        dialog.show();
    }

    public GuideDialog onClick(View.OnClickListener listener) {
        return this;
    }

    public void show() {

    }
}
