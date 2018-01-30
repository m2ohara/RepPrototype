package com.app.reputation.api;

import android.content.Context;
import android.view.View;

import com.app.reputation.api.UserExpression.Expression;
import com.app.reputation.api.UserExpression.ExpressionCallback;
import com.app.reputation.contact.TraitPopover;

import java.util.List;

/**
 * Created by michael on 29/01/18.
 */

public abstract class Callback implements ExpressionCallback {

    protected List<Expression> expressions;
    protected Context context;
    protected View contentView;

    public Callback(Context context, View contentView) {
        this.context = context;
        this.contentView = contentView;
    }

    @Override
    public void OnSuccess(List<Expression> expressions) {
        executeOnSuccess(expressions);
    }

    @Override
    public void onError() {
        executeOnError();
    }

    public abstract void executeOnSuccess(List<Expression> expressions);

    public abstract  void executeOnError();
}
