package com.mancj.materialsearchbar;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Tint the cursor. Tint and change text select handles drawables of an {@link EditText}.
 */

public class EditTextStyleHelper {

    /**
     * Set the cursor and handle colors for an {@link EditText} programmatically.
     *
     * @param editText
     *     The {@link EditText} to tint
     *     The color to apply for the cursor and select handles
     * @throws EditTextStyleChangeError
     *     If an error occured while attempting to tint the view.
     */
    public static void applyChanges(@NonNull EditText editText, int cursorColor, int leftHandleColor, int rightHandleColor, int middleHandleColor,
                                    int leftHandleRes, int rightHandleRes, int middleHandleRes, boolean handlesUseTintEnabled) throws EditTextStyleChangeError {
        EditTextStyleHelper editTextTint = new Builder(editText)
                .setCursorColor(cursorColor)
                .setSelectHandleLeftColor(leftHandleColor)
                .setSelectHandleRightColor(rightHandleColor)
                .setSelectHandleMiddleColor(middleHandleColor)
                .setSelectHandleLeftDrawable(leftHandleRes)
                .setSelectHandleRightDrawable(rightHandleRes)
                .setSelectHandleMiddleDrawable(middleHandleRes)
                .setHandleTintEnabled(handlesUseTintEnabled)
                .build();
        editTextTint.apply();
    }

    private final EditText editText;
    private final Integer cursorColor;
    private final Integer selectHandleLeftColor;
    private final Integer selectHandleRightColor;
    private final Integer selectHandleMiddleColor;
    private final Integer selectHandleLeftDrawable;
    private final Integer selectHandleRightDrawable;
    private final Integer selectHandleMiddleDrawable;
    private final boolean handlesUseTintEnabled;

    private EditTextStyleHelper(Builder builder) {
        editText = builder.editText;
        cursorColor = builder.cursorColor;
        selectHandleLeftColor = builder.selectHandleLeftColor;
        selectHandleRightColor = builder.selectHandleRightColor;
        selectHandleMiddleColor = builder.selectHandleMiddleColor;
        selectHandleLeftDrawable = builder.selectHandleLeftDrawable;
        selectHandleRightDrawable = builder.selectHandleRightDrawable;
        selectHandleMiddleDrawable = builder.selectHandleMiddleDrawable;
        handlesUseTintEnabled = builder.handlesUseTintEnabled;
    }

    /**
     * Sets the color for the cursor and handles on the {@link EditText editText}.
     *
     * @throws EditTextStyleChangeError
     *     if an error occurs while tinting the view.
     */
    public void apply() throws EditTextStyleChangeError {
        try {
//            Resources res = editText.getContext().getResources();

            // Get the editor
            Field field = TextView.class.getDeclaredField("mEditor");
            field.setAccessible(true);
            Object editor = field.get(editText);

            if (cursorColor != null) {
                // Get the cursor drawable, tint it, and set it on the TextView Editor
                field = TextView.class.getDeclaredField("mCursorDrawableRes");
                field.setAccessible(true);
                int cursorDrawableRes = field.getInt(editText);
                Drawable cursorDrawable = ContextCompat.getDrawable(editText.getContext(),cursorDrawableRes).mutate();
                cursorDrawable.setColorFilter(cursorColor, PorterDuff.Mode.SRC_IN);
                Drawable[] drawables = {cursorDrawable, cursorDrawable};
                field = editor.getClass().getDeclaredField("mCursorDrawable");
                field.setAccessible(true);
                field.set(editor, drawables);
            }

            String[] resFieldNames = {"mTextSelectHandleLeftRes", "mTextSelectHandleRightRes", "mTextSelectHandleRes"};
            String[] drawableFieldNames = {"mSelectHandleLeft", "mSelectHandleRight", "mSelectHandleCenter"};
            Integer[] colors = {selectHandleLeftColor, selectHandleRightColor, selectHandleMiddleColor};
            Integer[] drawables = {selectHandleLeftDrawable, selectHandleRightDrawable, selectHandleMiddleDrawable};

            for (int i = 0; i < resFieldNames.length; i++) {
                Integer color = colors[i];
                Integer drawable = drawables[i];
                if (color == null) {
                    continue;
                }

                String resFieldName = resFieldNames[i];
                String drawableFieldName = drawableFieldNames[i];

                field = TextView.class.getDeclaredField(resFieldName);
                field.setAccessible(true);

                Drawable selectHandleDrawable = ContextCompat.getDrawable(editText.getContext(),drawable).mutate();
                //If True The Handles Will Be Tinted else the tints will be removed
                if(handlesUseTintEnabled) {
                    selectHandleDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                }else{
                    selectHandleDrawable.clearColorFilter();
                }


                field = editor.getClass().getDeclaredField(drawableFieldName);
                field.setAccessible(true);
                field.set(editor, selectHandleDrawable);
            }
        } catch (Exception e) {
            throw new EditTextStyleChangeError("Error applying changes to " + editText, e);
        }
    }

    public static class Builder {

        final EditText editText;
        Integer cursorColor;
        Integer selectHandleLeftColor;
        Integer selectHandleRightColor;
        Integer selectHandleMiddleColor;
        Integer selectHandleLeftDrawable;
        Integer selectHandleRightDrawable;
        Integer selectHandleMiddleDrawable;
        boolean handlesUseTintEnabled;

        public Builder(@NonNull EditText editText) {
            this.editText = editText;
        }

        public Builder setCursorColor(@ColorInt int cursorColor) {
            this.cursorColor = cursorColor;
            return this;
        }

        public Builder setSelectHandleLeftColor(@ColorInt int selectHandleLeftColor) {
            this.selectHandleLeftColor = selectHandleLeftColor;
            return this;
        }

        public Builder setSelectHandleRightColor(@ColorInt int selectHandleRightColor) {
            this.selectHandleRightColor = selectHandleRightColor;
            return this;
        }

        public Builder setSelectHandleMiddleColor(@ColorInt int selectHandleMiddleColor) {
            this.selectHandleMiddleColor = selectHandleMiddleColor;
            return this;
        }
        //Custom
        public Builder setSelectHandleLeftDrawable(int selectHandleLeftDrawable){
            this.selectHandleLeftDrawable = selectHandleLeftDrawable;
            return this;
        }

        public Builder setSelectHandleRightDrawable(int selectHandleRightDrawable){
            this.selectHandleRightDrawable = selectHandleRightDrawable;
            return this;
        }

        public Builder setSelectHandleMiddleDrawable(int selectHandleMiddleDrawable){
            this.selectHandleMiddleDrawable = selectHandleMiddleDrawable;
            return this;
        }

        public Builder setHandleTintEnabled(boolean handleTintEnabled){
            this.handlesUseTintEnabled = handleTintEnabled;
            return this;
        }

        public EditTextStyleHelper build() {
            return new EditTextStyleHelper(this);
        }

    }

    public static class EditTextStyleChangeError extends Exception {

        public EditTextStyleChangeError(String message, Throwable cause) {
            super(message, cause);
        }
    }

}