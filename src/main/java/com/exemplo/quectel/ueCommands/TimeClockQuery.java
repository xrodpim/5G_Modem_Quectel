package com.exemplo.quectel.ueCommands;

//This command sets and queries the real time clock (RTC) of MT. The current setting is retained until MT
//is totally disconnected from the power supply.

/*
String type. Format: "yy/MM/dd,hh:mm:ss±zz", where characters indicate year (two last
digits), month, day, hour, minutes, seconds and time zone (indicating the difference,
expressed in quarter(s) of an hour, between the local time and GMT; range: -48 to +56).
E.g. May 6 th , 1994, 22:10:00 GMT+2 hours equals "94/05/06,22:10:00+08". */

public class TimeClockQuery extends ExtendedCommand {
    public TimeClockQuery() {
        super("+CCLK?");
        this.typeOfAnswer = "OK";
    }
}
