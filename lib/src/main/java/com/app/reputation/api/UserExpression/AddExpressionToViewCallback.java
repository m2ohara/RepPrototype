package com.app.reputation.api.UserExpression;

import android.content.Context;
import android.view.View;

import com.app.reputation.api.ApiClient;

import java.util.List;

/**
 * Created by michael on 15/01/18.
 */

public class AddExpressionToViewCallback implements ExpressionCallback {

    private Expression expressionToAdd;
    private TraitBuilderCallback traitBuilderCallback;
    private ApiClient apiClient;
    boolean isFound = false;

    public AddExpressionToViewCallback(Context context, View contentView) {
        traitBuilderCallback = new TraitBuilderCallback(context, contentView);
        apiClient = new ApiClient(context);
    }

    public void setExpressionToAdd(Expression expressionToAdd) {
        this.expressionToAdd = expressionToAdd;
    }

    @Override
    public void OnSuccess(List<Expression> expressions) {

        addExpression(expressions);

    }

    @Override
    public void onError() {

        //TODO: Remove Dummy repo
        addExpression(ExpressionRepo.Get().GetExpressions());

    }

    private void addExpression(List<Expression> expressions) {

        if(expressions != null && expressionToAdd != null) {
            for(Expression e : expressions) {

                if(e.getType().equals(expressionToAdd.getType())) {
                    //If expression exists increase amount & update
                    e.setAmount(e.getAmount()+1);
                    apiClient.Execute(null, apiClient.GetApiCall().update(e));
                    isFound = true;
                    break;
                }
            }

            if(isFound == false) {
                //Add new
                expressionToAdd.setAmount(1);
                expressionToAdd.setUserId(1);
                expressionToAdd.setDescription("<DESCRIPTION>");
                expressions.add(expressionToAdd);
                apiClient.Execute(null, apiClient.GetApiCall().create(expressionToAdd));
            }

            //Rebuild
            traitBuilderCallback.OnSuccess(expressions);
            isFound = false;
        }
    }


}


