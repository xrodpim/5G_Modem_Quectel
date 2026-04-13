package com.exemplo.quectel.ueCommands;

//This command returns the IMEI (International Mobile Equipment Identity) number of the ME that permits
//the user to identify the individual ME device.

public class IMEIQuery extends ExtendedCommand {
    public IMEIQuery() {
        super("+GSN");
        this.typeOfAnswer = "OK";
    }
}
