package com.zaktaccardi.materialbuttons;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by zak on 2/23/15.
 */
public abstract class RaisedButton extends Button {

    private int colorControlNormal;
    private int colorControlActivated;
    private int colorControlHighlight;

    private int colorButtonNormal;
    private int colorPressedHighlight;
    private int colorLongPressedHighlight;
    private int colorFocusedHighlight;
    private int colorActivatedHighlight;

    protected RaisedButton(Context context) {
        super(context);
    }

    public RaisedButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    protected RaisedButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected RaisedButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MaterialButton,
                0, 0);

        try {
            Resources res = getResources();
//            colorControlNormal = a.getInteger(R.styleable.MaterialButton_colorControlNormal, 0);
//            colorControlActivated = a.getInteger(R.styleable.MaterialButton_colorControlActivated, 0);
//            colorControlHighlight = a.getInt(R.styleable.MaterialButton_colorControlHighlight, 0);

            colorButtonNormal = a.getInteger(R.styleable.MaterialButton_mbColorButtonNormal, Color.CYAN);
            colorPressedHighlight = a.getInteger(R.styleable.MaterialButton_mbColorPressedHighlight, res.getColor(R.color.kitkat_pressed_state));
            colorFocusedHighlight = a.getInteger(R.styleable.MaterialButton_colorFocusedHighlight, res.getColor(R.color.kitkat_focused_state));
//            colorLongPressedHighlight = a.getInteger(R.styleable.MaterialButton_colorLongPressedHighlight, 0);
//            colorActivatedHighlight = a.getInteger(R.styleable.MaterialButton_colorActivatedHighlight, 0);

        } finally {
            a.recycle();
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            RippleDrawable draw = (RippleDrawable) getContext().getApplicationContext().getResources().getDrawable(R.drawable.raised_btn);
            InsetDrawable inset = (InsetDrawable) draw.getDrawable(0);
            GradientDrawable shape = (GradientDrawable) inset.getDrawable();
            shape.setColor(colorButtonNormal);
            setBackground(draw);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//            StateListDrawable states = (StateListDrawable) getContext().getApplicationContext().getResources().getDrawable(R.drawable.raised_btn);
//            states.addState(new int[] {android.R.attr.stateNotNeeded}, getStateDrawable(ButtonState.NORMAL));
//            states.addState(new int[] {android.R.attr.state_pressed, android.R.attr.state_enabled},getStateDrawable(ButtonState.FOCUSED));
//            states.addState(new int[] {android.R.attr.state_focused, android.R.attr.state_enabled},getStateDrawable(ButtonState.PRESSED));
//            setBackground(states);
        } else {

        }
    }




    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Drawable getStateDrawable(ButtonState state){
        InsetDrawable draw = (InsetDrawable) getContext().getApplicationContext().getDrawable(R.drawable.btn_default_mtrl_inset);
        GradientDrawable shape = (GradientDrawable) draw.getDrawable();
        switch(state){
            case NORMAL:
                shape.setColor(colorButtonNormal);
                break;
            case PRESSED:
                shape.setColor(colorPressedHighlight);
                break;
            case FOCUSED:
                shape.setColor(colorFocusedHighlight);
                break;

        }
        return draw;

    }


    public static enum ButtonState {
        NORMAL,
        PRESSED,
        FOCUSED
    }

}
