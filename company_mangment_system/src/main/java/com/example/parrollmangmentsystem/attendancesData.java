package com.example.parrollmangmentsystem;

import java.sql.Time;
import java.util.Date;

public class attendancesData {
    Integer employeeid;
    String name;
    Date date;
    Time time;


    public attendancesData(Integer employeeid, String name, Date date, Time time) {
        this.employeeid = employeeid;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Integer getEmployeeid() {
        return employeeid;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }
}
