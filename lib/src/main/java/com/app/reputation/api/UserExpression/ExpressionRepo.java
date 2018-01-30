package com.app.reputation.api.UserExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael on 18/01/18.
 */

public class ExpressionRepo {


    private ExpressionRepo() {

        if(expressions.size() == 0) {
            Expression exp1 = new Expression();
            exp1.setUserId(1);
            exp1.setAmount(3);
            exp1.setType("emoji_1f60e");
            exp1.setDescription("COOL DUDE");
            expressions.add(exp1);

            Expression exp2 = new Expression();
            exp2.setUserId(1);
            exp2.setAmount(2);
            exp2.setType("emoji_1f61d");
            exp2.setDescription("THRILLSEEKER");
            expressions.add(exp2);
        }
    }

    private static ExpressionRepo instance;
    private List<Expression> expressions = new ArrayList<>();

    public static ExpressionRepo Get() {
        if(instance == null) {
            instance = new ExpressionRepo();
        }

        return instance;
    }

    public List<Expression> GetExpressions() {
        return  expressions;
    }

}
