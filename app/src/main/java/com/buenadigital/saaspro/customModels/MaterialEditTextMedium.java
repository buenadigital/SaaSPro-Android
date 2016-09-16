package com.buenadigital.saaspro.customModels;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.buenadigital.saaspro.Constants;
import com.rengwuxian.materialedittext.MaterialEditText;


public class MaterialEditTextMedium extends MaterialEditText {
    public MaterialEditTextMedium(Context context) {
        super(context);
    }

    public MaterialEditTextMedium(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaterialEditTextMedium(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(Constants.TYPE_FACE_FONT_ROBOTO_MEDIUM);
    }
}
