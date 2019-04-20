package workers;

import workers.annotations.*;

@WorkerTable(tableName = "workers")
public class Worker {
    @WorkerField
    @WorkerFieldPrimary
    int id;

    @WorkerField
    String name;

    @WorkerField
    String position;

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

    public Worker() {}

    public Worker(int id, String name, int age, String position, int salary, String telephone, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.position = position;
        this.salary = salary;
        this.telephone = telephone;
        this.address = address;
    }


    public int getId() {
        return id;
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
