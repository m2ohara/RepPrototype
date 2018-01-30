package com.app.reputation.api.Contact;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by michael on 23/01/18.
 */

public class Contact {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("imagePath")
    @Expose
    private Integer imagePath;
    @SerializedName("firstname")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("number")
    @Expose
    private String number;
}
