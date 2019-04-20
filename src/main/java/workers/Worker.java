package workers;

import workers.annotations.*;

@WorkerTable(tableName = "workers")
public class Worker {
    @WorkerField
    @WorkerFieldPrimary
    int id;

    @WorkerField
    String position;

    @WorkerField
    String name;

    @WorkerField
    int age;

    @WorkerField
    int salary;

    @WorkerField
    @WorkerFieldReference(referencedTabletitle = "additional", referencedFieldTitle = "id")
    @WorkerFieldCascadeDelete
    @AdditionalField(tableName = "additional")
    int addInfoID;

    @AdditionalField(tableName = "additional")
    String telephone;
    @AdditionalField(tableName = "additional")
    String address;

 /*   public Worker() {
    }*/

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