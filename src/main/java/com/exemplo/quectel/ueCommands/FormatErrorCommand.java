package com.exemplo.quectel.ueCommands;



public class FormatErrorCommand extends ExtendedCommand {
    public FormatErrorCommand() {
        super("+CMEE=2"); 
        this.typeOfAnswer = "OK";
    }
}