package com.martha.cis3368finalproject.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import javax.print.DocFlavor;

@Entity
@Table(name = "covid2")
public class chartData {
    @Id
    @Column(name = "id")
    String id = "id";
    @Column(name = "country_name")
    String country_name = "country_name";
    @Column(name = "date")
    String date = "date";
    @Column (name = "total_cases")
    String total_cases = "total_cases";
    @Column(name = "total_deaths")
    String total_deaths = "total_deaths";
    @Column(name = "new_cases")
    String new_cases = "new_cases";
    @Column(name = "user_id")
    String user_id = "user_id";

    public chartData() {
    }

    public chartData(String id, String country_name, String date, String total_cases, String total_deaths, String new_cases, String user_id) {
        this.id = id;
        this.country_name = country_name;
        this.date = date;
        this.total_cases = total_cases;
        this.total_deaths = total_deaths;
        this.new_cases = new_cases;
        this.user_id = user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal_cases() {
        return total_cases;
    }

    public void setTotal_cases(String total_cases) {
        this.total_cases = total_cases;
    }

    public String getTotal_deaths() {
        return total_deaths;
    }

    public void setTotal_deaths(String total_deaths) {
        this.total_deaths = total_deaths;
    }

    public String getNew_cases() {
        return new_cases;
    }

    public void setNew_cases(String new_cases) {
        this.new_cases = new_cases;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
