package com.exemplo.quectel.ueCommands;

//This command specifies the preferred 5G SA band to be searched by UE.

public class UseBand77Only extends ExtendedCommand {
    public UseBand77Only() {
        super("+QNWPREFCFG= \"nr5g_band\",77"); // Set 5G SA n77.
        this.typeOfAnswer = "OK";
    }

}
