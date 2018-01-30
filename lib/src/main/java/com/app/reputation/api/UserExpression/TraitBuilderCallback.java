package com.app.reputation.api.UserExpression;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.reputation.contact.TraitPopover;

import java.util.List;

import github.ankushsachdeva.emojicon.R;

/**
 * Created by michael on 03/01/18.
 */

public class TraitBuilderCallback implements ExpressionCallback {

    private List<Expression> expressions;
    private Context mContext;
    private View contentView;
    private TraitPopover traitPopover;

    public TraitBuilderCallback(Context mContext, View contentView) {
        this.mContext = mContext;
        this.contentView = contentView;
        this.traitPopover = new TraitPopover(mContext, contentView);
    }

    @Override
    public void OnSuccess(List<Expression> expressions) {

        buildTraitLayout(expressions);

    }

    @Override
    public void onError() {

        //TODO: Remove Dummy data
        buildTraitLayout(ExpressionRepo.Get().GetExpressions());
    }

    private void buildTraitLayout(List<Expression> expressions) {

        Resources resources = mContext.getResources();
        final LinearLayout traitLayout = (LinearLayout)contentView.findViewById(R.id.trait_layout);

        if(traitLayout != null) {

            traitLayout.removeAllViews();

            for (Expression e : expressions) {

                final int resourceId = resources.getIdentifier(e.getType(), "drawable", mContext.getPackageName());
                ImageView traitToAdd = buildTraitIcon(e);
                traitToAdd.setTag(resourceId, e);
                traitLayout.addView(traitToAdd);

                traitToAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        traitPopover.setTraitPopover((Expression)view.getTag(resourceId));

                        final View popoverShadow = contentView.findViewById(R.id.popover_shadow);
                        popoverShadow.setVisibility(View.VISIBLE);
                        popoverShadow.bringToFront();
                        popoverShadow.findViewById(R.id.popover_shadow).setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                popoverShadow.setVisibility(View.GONE);
                                ((LinearLayout) contentView.findViewById(R.id.popover_emoji_layout)).removeAllViews();
                            }

                        });
                    }
                });

                TextView textView = buildContactCount(e.getAmount());
                traitLayout.addView(textView);
            }
        }
    }

    private ImageButton buildTraitIcon(Expression e) {

        Resources resources = mContext.getResources();

        int scale = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, mContext.getResources().getDisplayMetrics());
        int iconScale = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, mContext.getResources().getDisplayMetrics());

        final int resourceId = resources.getIdentifier(e.getType(), "drawable", mContext.getPackageName());
        Bitmap bitmap = ((BitmapDrawable) mContext.getDrawable(resourceId)).getBitmap();
        Drawable drawable = new BitmapDrawable(mContext.getResources(), Bitmap.createScaledBitmap(bitmap, iconScale, iconScale, true));
        final ImageButton traitToAdd = new ImageButton(mContext);
        traitToAdd.setLayoutParams(new LinearLayout.LayoutParams(scale, scale));
        traitToAdd.setBackgroundResource(github.ankushsachdeva.emojicon.R.drawable.trait_bg);
        traitToAdd.setImageDrawable(drawable);

        return traitToAdd;
    }

    private TextView buildContactCount(int count) {

        int iconScale = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, mContext.getResources().getDisplayMetrics());
        String expressionCount = Integer.toString(count);
        LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(iconScale/3, iconScale);
        textLayoutParams.setMargins(-25, 0,0,0);
        TextView textView = new TextView(mContext);
        textView.setTextSize((float) 12);
        textView.setTextColor(Color.WHITE);
        textView.setText(expressionCount);
        textView.setLayoutParams(textLayoutParams);

        return  textView;
    }

}
