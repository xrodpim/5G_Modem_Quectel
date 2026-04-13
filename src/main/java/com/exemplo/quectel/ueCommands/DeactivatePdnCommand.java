package com.exemplo.quectel.ueCommands;

// DeactivatePdnCommand.java
public class DeactivatePdnCommand extends ExtendedCommand {
    public DeactivatePdnCommand() {
        super("+QIDEACT=1"); // desativa o PDN
        this.typeOfAnswer = "OK";
    }
}
