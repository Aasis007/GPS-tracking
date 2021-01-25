package com.kandktech.gpyes.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Userdata {
    @SerializedName("id")
    @Expose
    public Integer id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("email")
    @Expose
    public String email;


    @SerializedName("comp_id")
    @Expose
    public Integer comp_id;

    @SerializedName("user_status")
    @Expose
    public String user_status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Integer getComp_id() {
        return comp_id;
    }

    public void setComp_id(Integer comp_id) {
        this.comp_id = comp_id;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public Userdata(Integer id, String name, String email,  Integer comp_id, String user_status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.comp_id = comp_id;
        this.user_status = user_status;
    }
}
