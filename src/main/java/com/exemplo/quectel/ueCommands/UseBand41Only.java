package com.exemplo.quectel.ueCommands;

public class UseBand41Only extends ExtendedCommand {
    public UseBand41Only() {

        super("+QNWPREFCFG= \"nr5g_band\",41"); // Set 5G SA n41.
        this.typeOfAnswer = "OK";

    }

}
