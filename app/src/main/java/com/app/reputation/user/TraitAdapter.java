package com.app.reputation.user;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.app.reputation.api.UserExpression.Expression;
import com.example.android.softkeyboard.R;


/**
 * Created by michael on 18/01/18.
 */

public class TraitAdapter extends BaseAdapter {

    private Context context;
    private Expression[] traits;

    public TraitAdapter(@NonNull Context context, Expression[] traits) {
        this.context = context;
        this.traits = traits;
    }

    @Override
    public int getCount() {
        return traits.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Expression trait = traits[position];

        if(convertView == null) {
            final LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.trait_layout, null);
        }

        ImageView backgroundImage = convertView.findViewById(R.id.background);
        ImageView traitImage = convertView.findViewById(R.id.trait);

        final int resourceId = context.getResources().getIdentifier(trait.getType(), "drawable", context.getPackageName());

        backgroundImage.setImageResource(github.ankushsachdeva.emojicon.R.drawable.trait_bg);
        traitImage.setImageResource(resourceId);

        return convertView;
    }
}
