package com.app.reputation.user;

/**
 * Created by michael on 18/01/18.
 */

public class Trait {

    public Trait(String description, int resourceId) {
        this.description = description;
        this.resourceId = resourceId;
    }

    private String description;
    private int resourceId;

    public String GetDescription() {
        return  description;
    };

    public int GetResourceId() {
        return resourceId;
    }
}
