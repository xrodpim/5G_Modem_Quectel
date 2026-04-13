package com.exemplo.quectel.ueCommands;

//This command specifies the preferred 5G SA band to be searched by UE.

public class UseBand78Only extends ExtendedCommand {
    public UseBand78Only() {
        super("+QNWPREFCFG= \"nr5g_band\",78"); // Set 5G SA n78 .
        this.typeOfAnswer = "OK";
    }

}
