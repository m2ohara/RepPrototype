package com.app.reputation.contact;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.reputation.api.Contact.ContactRepo;
import com.app.reputation.api.UserExpression.Expression;

import java.io.File;

import github.ankushsachdeva.emojicon.R;

import static com.app.reputation.api.Contact.ContactRepo.IMAGE_FILTER;

/**
 * Created by michael on 25/01/18.
 */

public class TraitPopover {

    private View contentView;
    private Context mContext;

    public TraitPopover(Context context, View contentView) {
        this.mContext = context;
        this.contentView = contentView;

    }


    public void setTraitPopover(Expression expression) {

        final LinearLayout popoverEmojiLayout = (LinearLayout) contentView.findViewById(R.id.popover_emoji_layout);
        popoverEmojiLayout.addView(buildExpressionIcon(expression));
        TextView textView = new TextView(mContext);
        textView.setText(expression.getDescription());
        textView.setTextColor(Color.WHITE);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setPadding(0, 15, 0, 0);
        textView.setTextSize(20);
        popoverEmojiLayout.addView(textView);

        addContacts(expression.getAmount());
    }


    private ImageButton buildExpressionIcon(Expression e) {

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

    private void addContacts(int contactAmount) {

        final LinearLayout popoverContactLayout = (LinearLayout) contentView.findViewById(R.id.popover_contact_layout);
        popoverContactLayout.removeAllViews();

        File file = mContext.getDir("Images", Context.MODE_PRIVATE);

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        int contactScale = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, mContext.getResources().getDisplayMetrics());

        int counter = 0;

        for(final File f : file.listFiles(IMAGE_FILTER)) {

            if(counter < contactAmount) {

                ImageView imageView = new ImageView(mContext);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(contactScale, contactScale);
                layoutParams.setMargins(0, 0, 10, 10);

                Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bmOptions);
                bitmap = Bitmap.createScaledBitmap(bitmap, contactScale, contactScale, true);
                imageView.setImageBitmap(bitmap);
                imageView.setLayoutParams(layoutParams);

                popoverContactLayout.addView(imageView);

                counter++;
            }

        }
        counter = 0;

    }
}
