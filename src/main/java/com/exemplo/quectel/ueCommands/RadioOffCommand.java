package com.exemplo.quectel.ueCommands;

//This command controls UE functionality level. It can also be used for resetting UE.

// RadioOffCommand.java
public class RadioOffCommand extends ExtendedCommand {
    public RadioOffCommand() {
        super("+CFUN=0"); // desliga as funções de rádio (mantém módulo energizado)
        this.typeOfAnswer = "OK";
    }
}
