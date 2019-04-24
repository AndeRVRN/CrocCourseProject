package ru.ander.nc.workers;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class WorkerAdditional {

    public WorkerAdditional() {}
    @JsonIgnore
    private int addInfoID;
    private String telephone;
    private String address;

    public WorkerAdditional(String telephone, String address) {
        this.telephone = telephone;
        this.address = address;
    }

    public int getAddInfoID() {
        return addInfoID;
    }

    public void setAddInfoID(int addInfoID) {
        this.addInfoID = addInfoID;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
