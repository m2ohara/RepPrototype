package com.app.reputation.user;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.app.reputation.api.ApiClient;
import com.app.reputation.api.UserExpression.TraitBuilderCallback;
import com.app.reputation.api.userExpression.ContactTraitBuilderCallback;
import com.example.android.softkeyboard.R;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ApiClient apiClient = new ApiClient(this);
        final ContactTraitBuilderCallback callbackHandler = new ContactTraitBuilderCallback(this, this.findViewById(android.R.id.content));

        apiClient.GetAll("UserId", callbackHandler);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

}
