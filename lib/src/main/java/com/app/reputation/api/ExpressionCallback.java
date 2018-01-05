package com.app.reputation.api;

import java.util.List;

/**
 * Created by michael on 03/01/18.
 */

public interface ExpressionCallback {

    void OnSuccess(List<Expression> expressions);

    void onError();
}
