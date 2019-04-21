package ru.ander.nc.workers;


import ru.ander.nc.workers.annotations.AdditionalField;
import ru.ander.nc.workers.annotations.WorkerTable;

@WorkerTable(tableName = "additional")
@Deprecated
public class WorkerAdditional {

    public WorkerAdditional() {}
    @AdditionalField(tableName = "additional")
    int addInfoID;

    @AdditionalField(tableName = "additional")
    String telephone;
    @AdditionalField(tableName = "additional")
    String address;

    public WorkerAdditional(int addInfoID, String telephone, String address) {
        this.addInfoID = addInfoID;
        this.telephone = telephone;
        this.address = address;
    }
}
