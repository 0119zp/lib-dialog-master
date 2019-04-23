package com.materialdialogs.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.text.AllCapsTransformationMethod;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import com.materialdialogs.GravityEnum;
import com.materialdialogs.util.DialogUtils;
import demo.zp.zpdialogdemo.R;


/**
 * @author Kevin Barry (teslacoil) 4/02/2015
 */
public class MDButton extends AppCompatTextView {

    private boolean mStacked = false;
    private GravityEnum mStackedGravity;

    private int mStackedEndPadding;
    private Drawable mStackedBackground;
    private Drawable mDefaultBackground;

    public MDButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MDButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mStackedEndPadding = context.getResources()
                .getDimensionPixelSize(R.dimen.md_dialog_frame_margin);
        mStackedGravity = GravityEnum.END;
    }

    /**
     * Set if the button should be displayed in stacked mode.
     * This should only be called from MDRootLayout's onMeasure, and we must be measured
     * after calling this.
     */
    /* package */ void setStacked(boolean stacked, boolean force) {
        if (mStacked != stacked || force) {

            setGravity(stacked ? (Gravity.CENTER_VERTICAL | mStackedGravity.getGravityInt()) : Gravity.CENTER);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                //noinspection ResourceType
                setTextAlignment(stacked ? mStackedGravity.getTextAlignment() : TEXT_ALIGNMENT_CENTER);
            }

            DialogUtils.setBackgroundCompat(this, stacked ? mStackedBackground : mDefaultBackground);
            if (stacked) {
                setPadding(mStackedEndPadding, getPaddingTop(), mStackedEndPadding, getPaddingBottom());
            } /* Else the padding was properly reset by the drawable */

            mStacked = stacked;
        }
    }

    public void setStackedGravity(GravityEnum gravity) {
        mStackedGravity = gravity;
    }

    public void setStackedSelector(Drawable d) {
        mStackedBackground = d;
        if (mStacked)
            setStacked(true, true);
    }

    public void setDefaultSelector(Drawable d) {
        mDefaultBackground = d;
        if (!mStacked)
            setStacked(false, true);
    }

    @SuppressLint("RestrictedApi")
    public void setAllCapsCompat(boolean allCaps) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            setAllCaps(allCaps);
        } else {
            if (allCaps)
                setTransformationMethod(new AllCapsTransformationMethod(getContext()));
            else
                setTransformationMethod(null);
        }
    }
}