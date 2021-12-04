package com.example.bluetooth_app.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Member {
    @NonNull
    private String name;
    @NonNull
    @PrimaryKey
    private String mobile_number;
    @NonNull
    private String date_of_birth;
    @NonNull
    private String working_company;

    public Member(@NonNull String name, @NonNull String mobile_number, @NonNull String date_of_birth, @NonNull String working_company) {
        this.name = name;
        this.mobile_number = mobile_number;
        this.date_of_birth = date_of_birth;
        this.working_company = working_company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getWorking_company() {
        return working_company;
    }

    public void setWorking_company(String working_company) {
        this.working_company = working_company;
    }
}
