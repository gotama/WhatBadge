package gautamastudios.com.whatbadge;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Badge extends LinearLayout {

    private final static int OUTER_LAYOUT = 1, INNER_LAYOUT = 2, OUTER_TEXT_VIEW = 3;
    private final static float innerBadgeWidth = 0.09f, innerBadgeHeight = 0.20f,
            topMarginOffset = 0.03f, marginOffset = 0.045f;
    private int badgeWidth, badgeHeight;

    public Badge(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        badgeHeight = 175;//TODO Set as attrs
        badgeWidth = 94;//TODO Set as attrs

        //Parent
        LinearLayout parentLinearLayout = new LinearLayout(context);
        parentLinearLayout.setOrientation(VERTICAL);
        parentLinearLayout.setLayoutParams(getParams(OUTER_LAYOUT));
        parentLinearLayout.setBackground(generateDrawable(Color.GREEN));//TODO set attrs (#61C436)

        //TextView
        TextView titleText = new TextView(context);
        titleText.setText("Up Next");//TODO set attrs
        titleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpToPx(14));//TODO set attrs
        titleText.setLayoutParams(getParams(OUTER_TEXT_VIEW));
        titleText.setTypeface(Typeface.DEFAULT, Typeface.BOLD_ITALIC);
        titleText.setTextColor(Color.WHITE);//TODO set attrs
        parentLinearLayout.addView(titleText);

        //Inner Child Container
        LinearLayout childLinearLayout = new LinearLayout(context);
        childLinearLayout.setLayoutParams(getParams(INNER_LAYOUT));
        childLinearLayout.setBackground(generateDrawable(Color.WHITE));//TODO set attrs
        parentLinearLayout.addView(childLinearLayout);

        //TODO Inner Child Circle Asset

        //TODO Inner Child Circle Boarder

        //TODO Inner Child TextView

        this.addView(parentLinearLayout);
    }

    private LinearLayout.LayoutParams getParams(int layoutType) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        switch (layoutType) {
            case OUTER_LAYOUT:
                params.height = dpToPx(badgeHeight);
                params.width = dpToPx(badgeWidth);
                return params;
            case INNER_LAYOUT:
                params.height = dpToPx(Math.round(badgeHeight * (1 - innerBadgeHeight)));
                params.width = dpToPx(Math.round(badgeWidth * (1 - innerBadgeWidth)));
                params.setMargins(
                        dpToPx(Math.round(badgeWidth - (badgeWidth * (1 - marginOffset)))),
                        dpToPx(Math.round(badgeHeight - (badgeHeight * (1 - topMarginOffset)))),
                        dpToPx(Math.round(badgeWidth - (badgeWidth * (1 - marginOffset)))),
                        dpToPx(Math.round(badgeWidth - (badgeWidth * (1 - marginOffset)))));
                return params;
            case OUTER_TEXT_VIEW:
                params.gravity = Gravity.CENTER_HORIZONTAL;
                params.topMargin = 15;
            default:
                return params;
        }
    }

    private GradientDrawable generateDrawable(@ColorInt int color) {
        GradientDrawable greenBackground = new GradientDrawable();
        greenBackground.setShape(GradientDrawable.RECTANGLE);
        greenBackground.setCornerRadius(25f);//TODO set attrs
        greenBackground.setColor(color);

        return greenBackground;
    }

    private int dpToPx(int dp) {
        float density = this.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }
}
