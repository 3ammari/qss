package customer.quick.source.qss;

import com.orm.SugarRecord;

/**
 * Created by abdul-rahman on 23/06/15.
 */
public class Vehicles extends SugarRecord<Vehicles> {
    String vehicleID;
    String make;
    String model;
    String fuel;
    String year;
    String name="none";




    public Vehicles() {
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
