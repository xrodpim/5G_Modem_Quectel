package com.exemplo.quectel.ueCommands;

//This command reads the NCGI, NCI, NR5G base station ID under 5G SA.

public class BaseStationID_Query extends ExtendedCommand {
    public BaseStationID_Query() {
        super("+QNWCFG=\"nr5g_cell_id\"");
        this.typeOfAnswer = "OK";
    }

}

// Response
// [+QNWCFG: "nr5g_cell_id",<NCGI>,<NCI>,<gNodeB_ID>]
