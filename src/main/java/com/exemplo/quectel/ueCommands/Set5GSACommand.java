package com.exemplo.quectel.ueCommands;

public class Set5GSACommand extends ExtendedCommand {
    public Set5GSACommand() {
        // super("+QNWPREFCFG=\"mode_pref\",LTE:NR5G");
        super("+QNWPREFCFG=\"mode_pref\",NR5G");
        this.typeOfAnswer = "OK";
    }

}
