package com.exemplo.quectel.ueCommands;

public class NetworkInformationQuery extends ExtendedCommand {
    public NetworkInformationQuery() {
        super("+QNWINFO=?");
        this.typeOfAnswer = "OK";
    }

}
