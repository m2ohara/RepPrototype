package com.app.reputation.api.userExpression;

import android.content.Context;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;

import com.app.reputation.api.Callback;
import com.app.reputation.api.UserExpression.Expression;
import com.app.reputation.api.UserExpression.ExpressionRepo;
import com.app.reputation.user.TraitAdapter;
import com.example.android.softkeyboard.R;

import java.util.List;

/**
 * Created by michael on 29/01/18.
 */

public class ContactTraitBuilderCallback extends Callback {

    public ContactTraitBuilderCallback(Context context, View contentView) {
        super(context, contentView);
    }

    @Override
    public void executeOnSuccess(List<Expression> expressions) {
        buildTraitGrid(expressions);
    }

    @Override
    public void executeOnError() {
        buildTraitGrid(ExpressionRepo.Get().GetExpressions());
    }

    private void buildTraitGrid(List<Expression> expressions) {

        GridView gridView = contentView.findViewById(R.id.trait_grid);
        TraitAdapter traitAdapter = new TraitAdapter(context, expressions.toArray(new Expression[expressions.size()]));
        gridView.setAdapter(traitAdapter);


    }
}
