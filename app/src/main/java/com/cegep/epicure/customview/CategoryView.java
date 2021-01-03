package com.cegep.epicure.customview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import com.cegep.epicure.R;

public class CategoryView extends LinearLayout {

    private CardView categoryCard;
    private TextView categoryText;
    private ImageView categoryImage;

    private Typeface normalTypeface;

    private Animation scaleUpAnimation;
    private Animation scaleDownAnimation;

    private int normalElevation;
    private int selectedElevation;

    public CategoryView(Context context) {
        this(context, null);
    }

    public CategoryView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CategoryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CategoryView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attributeSet, int defStyleAttr, int defStyleRes) {
        setOrientation(LinearLayout.VERTICAL);
        TypedArray a = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.CategoryView, defStyleAttr, defStyleRes);
        try {
            int categoryTextId;
            int categoryImageId;
            int category = a.getInteger(R.styleable.CategoryView_category, 0);
            switch (category) {
                case 0:
                    categoryTextId = R.string.vegetarian;
                    categoryImageId = R.drawable.ic_vegetarian;
                    break;
                case 1:
                    categoryTextId = R.string.non_vegetarian;
                    categoryImageId = R.drawable.ic_non_vegetarian;
                    break;
                case 2:
                    categoryTextId = R.string.vegan;
                    categoryImageId = R.drawable.ic_vegan;
                    break;
                case 3:
                    categoryTextId = R.string.drinks;
                    categoryImageId = R.drawable.ic_drinks;
                    break;

                default:
                    categoryTextId = -1;
                    categoryImageId = -1;
            }

            if (categoryTextId == -1) {
                throw new IllegalStateException("No category found for category: " + category);
            }

            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.category_layout, this, true);

            categoryCard = view.findViewById(R.id.category_card);
            categoryText = view.findViewById(R.id.category_text);
            categoryImage = view.findViewById(R.id.category_image);

            categoryText.setText(categoryTextId);
            categoryImage.setImageResource(categoryImageId);

            normalTypeface = categoryText.getTypeface();
        } finally {
            a.recycle();
        }

        scaleUpAnimation = createScaleAnimation(1f, 1.1f, 1f, 1.1f);
        scaleDownAnimation = createScaleAnimation(1.1f, 1f, 1.1f, 1f);

        normalElevation = context.getResources().getDimensionPixelSize(R.dimen.size_s);
        selectedElevation = context.getResources().getDimensionPixelSize(R.dimen.size_ml);
    }

    @Override
    public void setSelected(boolean selected) {
        if (selected) {
            categoryCard.setElevation(selectedElevation);
            categoryText.setTypeface(categoryText.getTypeface(), Typeface.BOLD);
            categoryImage.setImageTintList(ColorStateList.valueOf(getContext().getColor(R.color.color_primary)));
//            startAnimation(scaleUpAnimation);
        } else {
            categoryCard.setElevation(normalElevation);
            categoryText.setTypeface(normalTypeface, Typeface.NORMAL);
            categoryImage.setImageTintList(ColorStateList.valueOf(getContext().getColor(R.color.gray)));
//            startAnimation(scaleDownAnimation);
        }
    }

    private Animation createScaleAnimation(float fromX, float toX, float fromY, float toY) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(fromX, toX, fromY, toY);
        scaleAnimation.setDuration(getContext().getResources().getInteger(android.R.integer.config_mediumAnimTime));
        scaleAnimation.setInterpolator(new DecelerateInterpolator());
        scaleAnimation.setFillAfter(true);
        return scaleAnimation;
    }
}
