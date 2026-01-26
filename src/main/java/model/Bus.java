package model;

import java.sql.Date;
import java.sql.Time;

public class Bus {

    private int busId;
    private String busnumber;
    private int totalSeats;
    private String busType;

   
    private String fromLocation;
    private String toLocation;
    private double price;
    private Time departureTime;
    private Time arrivalTime;
    private Date travelDate;

  
    public int getBusId() {
        return busId;
    }
    public void setBusId(int busId) {
        this.busId = busId;
    }

    public String getBusnumber() {
        return busnumber;
    }
    public void setBusnumber(String busnumber) {
        this.busnumber = busnumber;
    }

    public int getTotalSeats() {
        return totalSeats;
    }
    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public String getBusType() {
        return busType;
    }
    public void setBusType(String busType) {
        this.busType = busType;
    }

    
    public String getFromLocation() {
        return fromLocation;
    }
    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }
    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public Time getDepartureTime() {
        return departureTime;
    }
    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    public Time getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(Time arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Date getTravelDate() {
        return travelDate;
    }
    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }
}
