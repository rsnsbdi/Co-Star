package com.costar.talkwithidol.ext.widgets.fonts;

import android.content.Context;
import android.util.AttributeSet;

import com.costar.talkwithidol.app.PadloktApplication;

/**
 * Created by shreedhar on 12/14/17.
 */


public class MoonBoldTextView extends android.support.v7.widget.AppCompatTextView {
    public MoonBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomTextStyle();

    }

    public MoonBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomTextStyle();
    }

    public MoonBoldTextView(Context context) {
        super(context);
        setCustomTextStyle();
    }

    private void setCustomTextStyle() {
        this.setTypeface(PadloktApplication.Fonts.MOONBOLD);
    }


}