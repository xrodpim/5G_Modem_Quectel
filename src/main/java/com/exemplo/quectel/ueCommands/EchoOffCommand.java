package com.exemplo.quectel.ueCommands;

//This command controls whether TA echoes characters received from TE in AT command mode.

public class EchoOffCommand extends ExtendedCommand {
    public EchoOffCommand() {
        super("E0"); // Desativa o eco dos comandos AT
        this.typeOfAnswer = "OK";
    }
}
