package gautamastudios.com.whatbadge;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Badge extends RelativeLayout {

    private final static int OUTER_LAYOUT = 1, INNER_LAYOUT = 2, OUTER_TEXT_VIEW = 3,
            INNER_CIRCLE = 4, INNER_TEXT_VIEW = 5, INNER_RING = 6, PARENT_LAYOUT = 7;
    private final static float innerBadgeWidth = 0.09f, innerBadgeHeight = 0.20f,
            topMarginOffset = 0.03f, marginOffset = 0.035f, innerCircleWidth = 0.32f;

    private int badgeWidth, badgeHeight, outerBorderColor, titleTextSize, titleTextColor,
            innerContainerColor, innerCircleColor, innerCircleBorderColor, innerTextSize,
            innerTextColor, innerCircleBorderWidth;
    private String titleText, innerText;
    private float cornerRadius;
    private Drawable centerIcon;

    public Badge(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.BadgeView, 0, 0);
        try {
            badgeHeight = typedArray.getDimensionPixelSize(R.styleable.BadgeView_badge_height, 380);
            badgeWidth = typedArray.getDimensionPixelSize(R.styleable.BadgeView_badge_width, 220);
            outerBorderColor = typedArray.getColor(R.styleable.BadgeView_border_color, Color.BLACK);
            titleText = typedArray.getString(R.styleable.BadgeView_title_text);
            titleTextSize = typedArray.getDimensionPixelSize(R.styleable.BadgeView_title_text_size, 24);
            titleTextColor = typedArray.getColor(R.styleable.BadgeView_title_text_color, Color.BLACK);
            innerContainerColor = typedArray.getColor(R.styleable.BadgeView_inner_container_background_color, Color.BLACK);
            innerCircleColor = typedArray.getColor(R.styleable.BadgeView_inner_container_circle_color, Color.BLACK);
            innerCircleBorderColor = typedArray.getColor(R.styleable.BadgeView_inner_container_circle_border_color, Color.BLACK);
            innerCircleBorderWidth = typedArray.getInteger(R.styleable.BadgeView_inner_container_circle_border_width, 6);
            innerText = typedArray.getString(R.styleable.BadgeView_inner_text);
            innerTextSize = typedArray.getDimensionPixelSize(R.styleable.BadgeView_inner_text_size, 24);
            innerTextColor = typedArray.getColor(R.styleable.BadgeView_inner_text_color, Color.BLACK);
            cornerRadius = typedArray.getFloat(R.styleable.BadgeView_corner_radius, 0f);
            centerIcon = typedArray.getDrawable(R.styleable.BadgeView_center_icon);
        } finally {
            typedArray.recycle();
        }

        init(attrs);
    }

    private View outerBorderView;
    private TextView titleTextView;
    private View circleBorder;

    private void init(AttributeSet attrs) {
        //Outer Border
        outerBorderView = new View(getContext());
        outerBorderView.setLayoutParams(generateParams(OUTER_LAYOUT, 0));
        outerBorderView.setBackground(generateDrawable(outerBorderColor, OUTER_LAYOUT));

        //Title TextView
        titleTextView = new TextView(getContext());
        titleTextView.setId(View.generateViewId());
        titleTextView.setText(titleText);
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpToPx(titleTextSize));
        titleTextView.setLayoutParams(generateParams(OUTER_TEXT_VIEW, 0));
        titleTextView.setTypeface(Typeface.DEFAULT, Typeface.BOLD_ITALIC);
        titleTextView.setTextColor(titleTextColor);

        //Inner Child Container
        RelativeLayout childLinearLayout = new RelativeLayout(getContext());
        childLinearLayout.setLayoutParams(generateParams(INNER_LAYOUT, titleTextView.getId()));
        childLinearLayout.setBackground(generateDrawable(innerContainerColor, INNER_LAYOUT));

        //Inner Circle ImageView
        ImageView circleImageView = new ImageView(getContext(), attrs);
        circleImageView.setId(View.generateViewId());
        circleImageView.setLayoutParams(generateParams(INNER_CIRCLE, 0));
        circleImageView.setBackground(generateDrawable(innerCircleColor, INNER_CIRCLE));
        circleImageView.setScaleType(ImageView.ScaleType.CENTER);
        circleImageView.setImageDrawable(centerIcon);

        //Circle Border
        circleBorder = new View(getContext(), attrs);
        circleBorder.setLayoutParams(generateParams(INNER_RING, 0));
        circleBorder.setBackground(generateDrawable(innerCircleBorderColor, INNER_RING));

        //Inner Text View
        TextView subText = new TextView(getContext());
        subText.setText(innerText);
        subText.setTextSize(TypedValue.COMPLEX_UNIT_PX, dpToPx(innerTextSize));
        subText.setLayoutParams(generateParams(INNER_TEXT_VIEW, circleImageView.getId()));
        subText.setTypeface(Typeface.create("sans-serif-condensed", Typeface.BOLD));
        subText.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        subText.setTextColor(innerTextColor);

        childLinearLayout.addView(circleImageView);
        childLinearLayout.addView(circleBorder);
        childLinearLayout.addView(subText);

        this.addView(outerBorderView);
        this.addView(titleTextView);
        this.addView(childLinearLayout);
        this.setLayoutParams(generateParams(PARENT_LAYOUT, 0));
    }

    private ViewGroup.LayoutParams generateParams(int layoutType, int viewIdForRule) {

        switch (layoutType) {
            case PARENT_LAYOUT:
                ConstraintLayout.LayoutParams parentParms = new ConstraintLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                return parentParms;
            case OUTER_LAYOUT:
                RelativeLayout.LayoutParams outerLayoutParams = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                outerLayoutParams.height = dpToPx(badgeHeight);
                outerLayoutParams.width = dpToPx(badgeWidth);
                return outerLayoutParams;
            case INNER_LAYOUT:
                RelativeLayout.LayoutParams innerLayoutParams = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                innerLayoutParams.addRule(CENTER_HORIZONTAL, TRUE);
                innerLayoutParams.addRule(BELOW, viewIdForRule);
                innerLayoutParams.height = dpToPx(Math.round(badgeHeight * (1 - innerBadgeHeight)));
                innerLayoutParams.width = dpToPx(Math.round(badgeWidth * (1 - innerBadgeWidth)));
                innerLayoutParams.setMargins(0, 26, 0, 0);
                //TODO Scale inner layout if needed
//                innerLayoutParams.setMargins(
//                        dpToPx(Math.round(badgeWidth - (badgeWidth * (1 - marginOffset)))),
//                        dpToPx(Math.round(badgeHeight - (badgeHeight * (1 - topMarginOffset)))),
//                        dpToPx(Math.round(badgeWidth - (badgeWidth * (1 - marginOffset)))),
//                        dpToPx(Math.round(badgeWidth - (badgeWidth * (1 - marginOffset)))));
                return innerLayoutParams;
            case OUTER_TEXT_VIEW:
                RelativeLayout.LayoutParams outerTextViewParams = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                outerTextViewParams.addRule(CENTER_HORIZONTAL | ALIGN_PARENT_TOP, TRUE);
                outerTextViewParams.topMargin = 15;
                return outerTextViewParams;
            case INNER_CIRCLE:
            case INNER_RING:
                RelativeLayout.LayoutParams innerCircleParams = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                innerCircleParams.addRule(CENTER_HORIZONTAL, TRUE);

                innerCircleParams.height = dpToPx(Math.round(badgeWidth * (1 - innerCircleWidth)));
                innerCircleParams.width = dpToPx(Math.round(badgeWidth * (1 - innerCircleWidth)));
                innerCircleParams.setMargins(
                        dpToPx(0),
                        dpToPx(24),
                        dpToPx(0),
                        dpToPx(24));
                return innerCircleParams;
            case INNER_TEXT_VIEW:
                RelativeLayout.LayoutParams innerTextViewParams = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                innerTextViewParams.addRule(CENTER_HORIZONTAL, TRUE);
                innerTextViewParams.addRule(BELOW, viewIdForRule);
                innerTextViewParams.setMargins(
                        dpToPx(24),
                        0,
                        dpToPx(24),
                        dpToPx(24));
                return innerTextViewParams;
            default:
                return null;
        }
    }

    private Drawable generateDrawable(@ColorInt int color, int layoutType) {

        switch (layoutType) {
            case INNER_RING:
                GradientDrawable ringDrawable = new GradientDrawable();
                ringDrawable.setShape(GradientDrawable.OVAL);
                ringDrawable.setStroke(innerCircleBorderWidth, color);
                return ringDrawable;
            case INNER_CIRCLE:
                GradientDrawable circleDrawable = new GradientDrawable();
                circleDrawable.setShape(GradientDrawable.OVAL);
                circleDrawable.setColor(color);
                return circleDrawable;
            case OUTER_LAYOUT:
            case INNER_LAYOUT:
                GradientDrawable rectDrawable = new GradientDrawable();
                rectDrawable.setShape(GradientDrawable.RECTANGLE);
                rectDrawable.setCornerRadius(cornerRadius);
                rectDrawable.setColor(color);
                return rectDrawable;
            default:
                return new GradientDrawable();

        }
    }

    private int dpToPx(int dp) {
        float density = this.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

    public void hideOuterBorder(boolean hide) {
        outerBorderView.setVisibility(hide ? INVISIBLE : VISIBLE);
        titleTextView.setVisibility(hide ? INVISIBLE : VISIBLE);
    }

    public void hideCircleBorder(boolean hide) {
        circleBorder.setVisibility(hide ? INVISIBLE : VISIBLE);
    }
}
