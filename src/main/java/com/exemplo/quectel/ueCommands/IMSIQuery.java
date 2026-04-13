package com.exemplo.quectel.ueCommands;

/*This command requests the IMSI (International Mobile Subscriber Identity), which is intended to permit
TE to identify the individual (U)SIM card or active application in UICC (GSM or (U)SIM) that is attached
to MT. */

public class IMSIQuery extends ExtendedCommand {
    public IMSIQuery() {
        super("+CIMI");
        this.typeOfAnswer = "OK";
    }

}
