package com.app.reputation.api;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.List;

import github.ankushsachdeva.emojicon.R;

/**
 * Created by michael on 03/01/18.
 */

public class ExpressionBuilderCallback implements ExpressionCallback {

    private List<Expression> expressions;
    private Context mContext;
    private View contentView;

    public ExpressionBuilderCallback(Context mContext, View contentView) {
        this.mContext = mContext;
        this.contentView = contentView;
    }

    @Override
    public void OnSuccess(List<Expression> expressions) {

        Resources resources = mContext.getResources();

        LinearLayout traitLayout = (LinearLayout)contentView.findViewById(R.id.trait_layout);
        int scale = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, mContext.getResources().getDisplayMetrics());
        int iconScale = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, mContext.getResources().getDisplayMetrics());

        for(Expression e : expressions) {

            final int resourceId = resources.getIdentifier(e.getType(), "drawable", mContext.getPackageName());

            Bitmap bitmap2 = ((BitmapDrawable)mContext.getDrawable(resourceId)).getBitmap();
            Drawable icon2 = new BitmapDrawable(mContext.getResources(), Bitmap.createScaledBitmap(bitmap2, iconScale, iconScale, true));
            ImageButton traitToAdd2 = new ImageButton(mContext);
            traitToAdd2.setLayoutParams(new LinearLayout.LayoutParams(scale, scale));
            traitToAdd2.setBackgroundResource(github.ankushsachdeva.emojicon.R.drawable.trait_bg);
            traitToAdd2.setImageDrawable(icon2);
            traitLayout.addView(traitToAdd2);
        }

    }

    @Override
    public void onError() {

    }

}
