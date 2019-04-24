package ru.ander.nc.workers;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Worker {
    @JsonIgnore
    private int id;
    private String position;
    private String name;
    private int age;
    private int salary;

    private WorkerAdditional workerAdditional;

    public Worker() {}

    public Worker(String name, String position, int age, int salary, String telephone, String address) {
        this.name = name;
        this.position = position;
        this.age = age;
        this.salary = salary;
        workerAdditional = new WorkerAdditional(telephone, address);
    }

    @Override
    public String toString() {
        return "ID = " + id + ", name = " + name + ", position = " + position + ", age = " + age + ", salary = "
                + salary + ", addInfoID = " + workerAdditional.getAddInfoID() + ", telephone = " + workerAdditional.getTelephone() + ", address = " + workerAdditional.getAddress();
    }

    public WorkerAdditional getWorkerAdditional() {
        return workerAdditional;
    }

    public void setWorkerAdditional(WorkerAdditional workerAdditional) {
        this.workerAdditional = workerAdditional;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

}