package com.example.project_ab;
import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("table")
    public String table;
    @SerializedName("id")
    public int id;
    @SerializedName("user_id")
    public String user_id;
    @SerializedName("password")
    public String password;
    @SerializedName("create_date")
    public String create_date;
    @SerializedName("update_date")
    public String update_date;
    @SerializedName("delete_date")
    public String delete_date;
    @SerializedName("status")
    public String status;
    @Override
    public String toString() {
        return "{id: " + id + "user_id: " + user_id + "password: " + password + "}";
    }
}

