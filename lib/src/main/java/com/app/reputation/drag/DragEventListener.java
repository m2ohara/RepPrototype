package com.app.reputation.drag; /**
 * Created by michael on 17/11/17.
 */

import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.app.reputation.api.UserExpression.AddExpressionToViewCallback;
import com.app.reputation.api.ApiClient;
import com.app.reputation.api.UserExpression.Expression;
import com.app.reputation.api.UserExpression.PostExpressionCallback;

import github.ankushsachdeva.emojicon.R;

/**
 * Created by michael on 17/11/17.
 */

public class DragEventListener implements View.OnDragListener {

    View contactBorder = null;
    View contact = null;
    Context context = null;
    LinearLayout traitLayout = null;
    ApiClient apiClient = null;
    AddExpressionToViewCallback addExpressionToViewCallback = null;

    public DragEventListener(View contactBorder, View contact, Context context, LinearLayout traitLayout) {
        this.contactBorder = contactBorder;
        this.contact = contact;
        this.context = context;
        this.traitLayout = traitLayout;
        this.apiClient = new ApiClient(context);
    }

    public DragEventListener(View contentView, Context context) {
        this.contactBorder = contentView.findViewById(R.id.contact_border);
        this.contact = contentView.findViewById(R.id.contact_img);
        this.context = context;
        this.traitLayout = contentView.findViewById(R.id.trait_layout);
        this.apiClient = new ApiClient(context);
        this.addExpressionToViewCallback = new AddExpressionToViewCallback(context, contentView);
    }

    // This is the method that the system calls when it dispatches a drag event to the
    // listener.
    public boolean onDrag(View v, DragEvent event) {

        // Defines a variable to store the action type for the incoming event
        final int action = event.getAction();

        // Handles each of the expected events
        switch(action) {

            case DragEvent.ACTION_DRAG_STARTED:

                // Determines if this View can accept the dragged data
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {


                    // Invalidate the view to force a redraw in the new tint
                    v.invalidate();

                    // returns true to indicate that the View can accept the dragged data.
                    return true;

                }

                // Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
                return false;

            case DragEvent.ACTION_DRAG_ENTERED:

                contactBorder.setVisibility(View.VISIBLE);
                contact.setVisibility(View.GONE);

                // Invalidate the view to force a redraw in the new tint
                v.invalidate();

                return true;

            case DragEvent.ACTION_DRAG_LOCATION:

                // Ignore the event
                return true;

            case DragEvent.ACTION_DRAG_EXITED:

                contactBorder.setVisibility(View.GONE);
                contact.setVisibility(View.VISIBLE);
                // Invalidate the view to force a redraw in the new tint
                v.invalidate();

                return true;

            case DragEvent.ACTION_DROP:


                String resourceName =  event.getClipData().getItemAt(0).getText().toString();

                Expression expression = new Expression();
                expression.setType(resourceName);

                addExpressionToViewCallback.setExpressionToAdd(expression);
                apiClient.GetAll("", addExpressionToViewCallback);

                // Invalidates the view to force a redraw
                v.invalidate();

                // Returns true. DragEvent.getResult() will return true.
                return true;

            case DragEvent.ACTION_DRAG_ENDED:

                contact.setVisibility(View.VISIBLE);
                contactBorder.setVisibility(View.GONE);
                // Invalidates the view to force a redraw
                v.invalidate();

                // Does a getResult(), and displays what happened.
                if (event.getResult()) {
//                    Toast.makeText(this, "The drop was handled.", Toast.LENGTH_LONG);

                } else {
//                    Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_LONG);

                }

                // returns true; the value is ignored.
                return true;

            // An unknown action type was received.
            default:
                Log.e("DragDrop Example","Unknown action type received by OnDragListener.");
                break;
        }

        return false;
    }

    private void addExpressionToView(DragEvent event) {
        int scale = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, context.getResources().getDisplayMetrics());
        int iconScale = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());

        View droppedEmoji = (View) event.getLocalState();
        droppedEmoji.buildDrawingCache();

        Bitmap bitmap = droppedEmoji.getDrawingCache();
        Drawable icon = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, iconScale, iconScale, true));

        ImageButton traitToAdd = new ImageButton(context);
        traitToAdd.setLayoutParams(new LayoutParams(scale, scale));
        traitToAdd.setBackgroundResource(R.drawable.trait_bg);
        traitToAdd.setImageDrawable(icon);

        traitLayout.addView(traitToAdd);

    }


    private void sendTraitToApi(String resourceName) {

        Expression expression = new Expression();
        expression.setUserId(1);
        expression.setAmount(1);
        expression.setType(resourceName);

        PostExpressionCallback sender = new PostExpressionCallback();
        apiClient.Create(expression, sender);

    }

};