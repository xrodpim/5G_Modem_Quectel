package com.exemplo.quectel.ueCommands;

//This command controls whether the result code is transmitted to TE. Other information text transmitted
//as response is not affected.

public class TurnOnResultCodeCommand extends ExtendedCommand {
    public TurnOnResultCodeCommand() {
        super("Q0");
        this.typeOfAnswer = "OK";
    }
}
