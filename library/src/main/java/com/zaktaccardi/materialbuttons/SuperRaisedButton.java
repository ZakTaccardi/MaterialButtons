package com.zaktaccardi.materialbuttons;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.StateSet;
import android.widget.Button;

/**
 * Created by zak on 2/24/15.
 */
public class SuperRaisedButton extends Button {
    private int colorControlNormal;
    private int colorControlActivated;
    private int colorControlHighlight;

    private int colorButtonNormal;
    private int colorPressedHighlight;
    private int colorLongPressedHighlight;
    private int colorFocusedHighlight;
    private int colorActivatedHighlight;


    public SuperRaisedButton(Context context) {
        super(context);
    }

    public SuperRaisedButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SuperRaisedButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SuperRaisedButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
            colorButtonNormal = a.getInteger(R.styleable.MaterialButton_mbColorButtonNormal, Color.GRAY);
            colorPressedHighlight = a.getInteger(R.styleable.MaterialButton_mbColorPressedHighlight, res.getColor(R.color.kitkat_pressed_state));
            colorFocusedHighlight = a.getInteger(R.styleable.MaterialButton_colorFocusedHighlight, res.getColor(R.color.kitkat_focused_state));
//            colorLongPressedHighlight = a.getInteger(R.styleable.MaterialButton_colorLongPressedHighlight, 0);
//            colorActivatedHighlight = a.getInteger(R.styleable.MaterialButton_colorActivatedHighlight, 0);

        } finally {
            a.recycle();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            RippleDrawable draw = (RippleDrawable) getContext().getApplicationContext().getResources().getDrawable(R.drawable.raised_btn);
//            InsetDrawable inset = (InsetDrawable) draw.getDrawable(0);
//            GradientDrawable shape = (GradientDrawable) inset.getDrawable();
//            shape.setColor(colorButtonNormal);
//            setBackground(draw);
//            Drawable ripple = getPressedColorRippleDrawable(colorButtonNormal, Color.BLUE, context);
            Drawable ripple = new RippleDrawable(getPressedColorSelector(colorButtonNormal, colorPressedHighlight), context.getResources().getDrawable(R.drawable.btn_default_mtrl_inset), null);
            setBackground(ripple);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StateListDrawable states = new StateListDrawable();
            states.addState(new int[]{android.R.attr.state_pressed}, getStateDrawable(ButtonState.PRESSED));
            states.addState(new int[]{android.R.attr.state_focused}, getStateDrawable(ButtonState.FOCUSED));
//            states.addState(new int[]{-android.R.attr.state_enabled}, getStateDrawable(ButtonState.DISABLED));
            states.addState(StateSet.WILD_CARD, getStateDrawable(ButtonState.NORMAL));
            setBackground(states);
        } else {
            StateListDrawable states = new StateListDrawable();
            states.addState(new int[]{android.R.attr.state_pressed}, getStateDrawable(ButtonState.PRESSED));
            states.addState(new int[]{android.R.attr.state_focused}, getStateDrawable(ButtonState.FOCUSED));
            states.addState(StateSet.WILD_CARD, getStateDrawable(ButtonState.NORMAL));
            setBackgroundDrawable(states);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private Drawable getStateDrawableKitKat(ButtonState state) {
        InsetDrawable draw = (InsetDrawable) getContext().getApplicationContext().getResources().getDrawable(R.drawable.btn_default_mtrl_inset).getConstantState().newDrawable().mutate();
        GradientDrawable shape = (GradientDrawable) draw.getDrawable();
        switch (state) {
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

    private Drawable getStateDrawable(ButtonState state) {
        InsetDrawable draw;
        GradientDrawable shape;
        final int colorToSet;
        switch (state) {
            case NORMAL:
                colorToSet = colorButtonNormal;
                break;
            case PRESSED:
                colorToSet = colorPressedHighlight;
                break;
            case FOCUSED:
                colorToSet = colorFocusedHighlight;
                break;
            default:
                colorToSet = colorButtonNormal;
                break;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            draw = (InsetDrawable) getContext().getApplicationContext().getResources().getDrawable(R.drawable.btn_default_mtrl_inset).getConstantState().newDrawable().mutate();
            shape = (GradientDrawable) draw.getDrawable();
            shape.setColor(colorToSet);

        } else {
            // Get shape from XML
            shape = (GradientDrawable) getResources().getDrawable(R.drawable.btn_default_mtrl_shape).getConstantState().newDrawable().mutate();
            shape.setColor(colorToSet);

            // Programmatically create Inset
            draw = new InsetDrawable(shape,
                    getResources().getDimensionPixelSize(R.dimen.button_inset_horizontal_material),
                    getResources().getDimensionPixelSize(R.dimen.button_inset_vertical_material),
                    getResources().getDimensionPixelSize(R.dimen.button_inset_horizontal_material),
                    getResources().getDimensionPixelSize(R.dimen.button_inset_vertical_material));
        }
        return draw;
    }

    private static ColorStateList getPressedColorSelector(int normalColor, int pressedColor) {
        return new ColorStateList(
                new int[][]{
                        new int[]{}
                },
                new int[]{
                        pressedColor
                }
        );
    }

    public static enum ButtonState {
        NORMAL,
        PRESSED,
        FOCUSED,
        DISABLED
    }
}
