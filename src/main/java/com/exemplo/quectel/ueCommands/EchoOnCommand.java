package com.exemplo.quectel.ueCommands;

//This command controls whether TA echoes characters received from TE in AT command mode.

public class EchoOnCommand extends ExtendedCommand {
    public EchoOnCommand() {
        super("E1"); // Ativa o eco dos comandos AT
        this.typeOfAnswer = "OK";
    }
}
