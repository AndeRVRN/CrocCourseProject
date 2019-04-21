package workers;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Worker {
    @JsonIgnore
    int id;
    String position;
    String name;
    int age;
    int salary;

    @JsonIgnore
    int addInfoID;
    String telephone;
    String address;

    public Worker() {
    }

    public Worker(String name, String position, int age, int salary, String telephone, String address) {
        this.name = name;
        this.position = position;
        this.age = age;
        this.salary = salary;
        this.telephone = telephone;
        this.address = address;
    }

    @Override
    public String toString() {
        return "ID = " + id + ", name = " + name + ", position = " + position + ", age = " + age + ", salary = "
                + salary + ", addInfoID = " + addInfoID + ", telephone = " + telephone + ", address = " + address;
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