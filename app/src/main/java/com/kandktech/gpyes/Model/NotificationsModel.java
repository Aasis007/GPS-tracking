package com.kandktech.gpyes.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationsModel {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("notification_data")
    @Expose
    private List<Notification_data> notification_data = null;


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

    public List<Notification_data> getNotification_data() {
        return notification_data;
    }

    public void setNotification_data(List<Notification_data> notification_data) {
        this.notification_data = notification_data;
    }

    public class Notification_data {

        @SerializedName("id")
        @Expose
        public Integer id;

        @SerializedName("title")
        @Expose
        public  String title;

        @SerializedName("comp_id")
        @Expose
        public String comp_id;

        @SerializedName("created_at")
        @Expose
        public String created_at;

        public String description;

        @Override
        public String toString() {
            return "Notification_data{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", comp_id='" + comp_id + '\'' +
                    ", created_at='" + created_at + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getComp_id() {
            return comp_id;
        }

        public void setComp_id(String comp_id) {
            this.comp_id = comp_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }




}


