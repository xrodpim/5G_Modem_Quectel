package com.exemplo.quectel.ueCommands;

//This command controls whether the result code is transmitted to TE. Other information text transmitted
//as response is not affected.

public class TurnOffResultCodeCommand extends ExtendedCommand {

    public TurnOffResultCodeCommand() {
        super("Q1");
    }

    public boolean checkResponse() {
        return true;
    }

}
