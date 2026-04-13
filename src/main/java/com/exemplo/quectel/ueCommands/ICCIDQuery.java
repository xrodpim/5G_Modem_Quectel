package com.exemplo.quectel.ueCommands;

//This command returns ICCID (Integrated Circuit Card ID) if a (U)SIM card is inserted.

public class ICCIDQuery extends ExtendedCommand {
    public ICCIDQuery() {
        super("+ICCID");
        this.typeOfAnswer = "OK";
    }

}
