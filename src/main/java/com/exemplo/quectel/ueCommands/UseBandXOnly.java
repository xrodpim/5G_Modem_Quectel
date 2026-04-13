package com.exemplo.quectel.ueCommands;

public class UseBandXOnly extends ExtendedCommand {
    public UseBandXOnly(int X) {

        super("+QNWPREFCFG= \"nr5g_band\"," + X); // Set 5G SA nX .
        this.typeOfAnswer = "OK";

    }

}