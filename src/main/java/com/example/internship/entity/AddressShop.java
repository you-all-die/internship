package com.example.internship.entity;

import javax.persistence.*;

//TODO: можно удалить, т.к. функционал уже не актуален
@Entity
@Table(name = "address_shop")
public class AddressShop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "address")
    private String address;
    @Column(name = "schedule")
    private String schedule;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public AddressShop(Long shopId, String address, String schedule) {
        this.shopId = shopId;
        this.address = address;
        this.schedule = schedule;
    }

    public AddressShop(){

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
