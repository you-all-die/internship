package com.example.internship.entity;

import javax.persistence.*;

@Entity
@Table(name = "adress_shop")
public class AdressShop {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long shop_id;

    private String address;
    private String schedule;

    public Long getShop_id() {
        return shop_id;
    }

    public void setShop_id(Long shop_id) {
        this.shop_id = shop_id;
    }

    public AdressShop(Long id, String address, String schedule) {
        this.shop_id = id;
        this.address = address;
        this.schedule = schedule;
    }

    public AdressShop(){

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
