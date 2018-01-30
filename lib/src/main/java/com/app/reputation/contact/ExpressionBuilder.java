package com.app.reputation.contact;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.reputation.api.UserExpression.Expression;

import java.util.List;

import github.ankushsachdeva.emojicon.R;

/**
 * Created by michael on 16/01/18.
 */

public class ExpressionBuilder {

    private List<Expression> expressions;
    private Context mContext;
    private View contentView;


    public void AddExpressionsToView(List<Expression> expressions) {
        Resources resources = mContext.getResources();

        LinearLayout traitLayout = (LinearLayout)contentView.findViewById(R.id.trait_layout);


        if(traitLayout != null) {

            traitLayout.removeAllViews();

            int scale = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, mContext.getResources().getDisplayMetrics());
            int iconScale = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, mContext.getResources().getDisplayMetrics());

            for (Expression e : expressions) {

                final int resourceId = resources.getIdentifier(e.getType(), "drawable", mContext.getPackageName());

                Bitmap bitmap = ((BitmapDrawable) mContext.getDrawable(resourceId)).getBitmap();
                Drawable drawable = new BitmapDrawable(mContext.getResources(), Bitmap.createScaledBitmap(bitmap, iconScale, iconScale, true));
                ImageButton traitToAdd = new ImageButton(mContext);
                traitToAdd.setLayoutParams(new LinearLayout.LayoutParams(scale, scale));
                traitToAdd.setBackgroundResource(github.ankushsachdeva.emojicon.R.drawable.trait_bg);
                traitToAdd.setImageDrawable(drawable);
                traitLayout.addView(traitToAdd);

                String expressionCount = Integer.toString(e.getAmount());

                LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(scale / 6, scale);
                textLayoutParams.setMargins(-25, 0, 0, 0);

                TextView textView = new TextView(mContext);
                textView.setTextSize((float) 12);
                textView.setTextColor(Color.WHITE);
                textView.setText(expressionCount);
                textView.setLayoutParams(textLayoutParams);
                traitLayout.addView(textView);

            }
        }
    }
}
