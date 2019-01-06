package com.costar.talkwithidol.ext.widgets.fonts;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.costar.talkwithidol.R;


public class FontTextView extends AppCompatTextView {

    String typeface;

    public FontTextView(Context context) {
        this(context,null);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttributes(attrs,context);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context)
    {
        Typeface lightFont = FontCache.getTypeface(typeface,context);
        setTypeface(lightFont);
    }


    private void getAttributes(AttributeSet attributeSet, Context context)
    {
        final TypedArray typedArray =context.obtainStyledAttributes(attributeSet, R.styleable.FontTextView,0,0);
        typeface = typedArray.getString(R.styleable.FontTextView_addTypeface);
        typedArray.recycle();
    }


}