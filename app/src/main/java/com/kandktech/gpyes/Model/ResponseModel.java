package com.kandktech.gpyes.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseModel {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("admin_data")
    @Expose
    private List<Admin_data> admin_data = null;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Admin_data> getAdmin_data() {
        return admin_data;
    }

    public void setAdmin_data(List<Admin_data> admin_data) {
        this.admin_data = admin_data;
    }



    public class Admin_data {
        @SerializedName("id")
        @Expose
        public Integer id;

        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("email")
        @Expose
        public String email;

        @SerializedName("password")
        @Expose
        public String password;

        @SerializedName("comp_id")
        @Expose
        public Integer comp_id;

        @SerializedName("user_status")
        @Expose
        public String user_status;

        @SerializedName("created_at")
        @Expose
        public String created_at;

        @SerializedName("updated_at")
        @Expose
        public String updated_at;


    }

}






